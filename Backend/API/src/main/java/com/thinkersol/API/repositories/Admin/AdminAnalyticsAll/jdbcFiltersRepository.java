package com.thinkersol.API.repositories.Admin.AdminAnalyticsAll;

import com.thinkersol.API.models.Admin.AdminAnalyticsAll.AnalyticsFilters;
import com.thinkersol.API.repositories.GlobalReturnsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The following is licensed under the GNU General Public License v3.0 as found in the LICENSE.md file
 * @author Safwaan Taher
 * @version 1.1
 * @since 7/7/2022
 * <p>
 * Abstract:
 *
 * jdbcFiltersRepository holds all actions that will be executed for 
 * the filters endpoint in the form of database queries, helper methods
 * and error handling. This layer is abstracted away by the Repository class.
 * </p>
 */
@Repository
public class jdbcFiltersRepository implements FiltersRepository{
    // Private fields for running query results
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final GlobalReturnsRepository globalReturns;

    // Base SQL code for all filters queries
    private final String filtersBaseSQL;

    /**
     * constructor: Takes a NamedParameterTemplate and GlobalReturnRepository
     * to be injected into fields
     * @author Safwaan Taher
     * @pre NamedParameterTemplate and GlobalReturnRepo are passed in
     * by the Spring container for injection
     * @post all private fields are initialized
     */
    @Autowired
    public jdbcFiltersRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                                 GlobalReturnsRepository globalReturns) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.globalReturns = globalReturns;
        // SQL code to retrieve a basic filtered student entity
        filtersBaseSQL =
                """
                SELECT S.student_id,
                    S.first_name AS student_firstname,
                    S.last_name AS student_lastname,
                    S.grade, plan.plan_num AS plan,
                    career_name AS career
                FROM Student S
                JOIN Plan ON (Plan.student_id = S.student_id)
                JOIN Career ON (Plan.soc_code = Career.soc_code)
                JOIN Grade ON (Grade.grade = S.grade)""";
    }



    /**
     * addPlansAndGradesFilterSQL: adds the appropriate sort and
     *                             plan and grades filters to
     *                             a given SQL statement
     * @author Safwaan Taher
     * @pre sql String must be concatenated with all checks,
     * WHERE clauses, and error handling
     * @post sql string is returned with proper plans / grades checks
     * and correct ORDER BY clause
     * @return the sql string with the aforementioned conditional clauses
     */
    private String addPlansAndGradesFilterSQL(String sql, String sortBy) {
        // check for plans and grades existing in the list of filtered
        // grades and plans passed in by the admin
        sql +=  " AND (plan.plan_num IN (:plans) AND Grade.grade IN (:grades))";

        if(sortBy.equals("zToA")){
            sql += " ORDER BY S.last_name DESC, plan.plan_num ASC;";
        } else {
            // default a - z ordering
            sql += " ORDER BY S.last_name ASC, plan.plan_num ASC;";
        }
        return sql;
    }

    /**
     * checkPlans:
     * Checks if plans list is empty if so
     * no filter is being applied so plans list is filled with all plan
     * values
     *
     * @author Safwaan Taher
     * @pre plans list must be passed
     * @post a list of all possible plan numbers is returned
     * @return the original plan list
     */
    private List<Integer> checkPlans(List<Integer> plans) {
        if(plans.isEmpty()) {
           return List.of(1,2,3,4,5);
        }
        return plans;
    }

    /**
     * checkGrades:
     * Checks if grades list is empty if so
     * no filter is being applied so plans list is filled with all grade
     * values
     *
     * @author Safwaan Taher
     * @pre grades list must be passed
     * @post a list of all possible grades is returned from the DB
     * @return the original grades list
     */
    private List<Integer> checkGrades(List<Integer> grades) {
        if(grades.isEmpty()) {
            String getAllGradesSQL = """
                    SELECT Grade.grade
                    FROM Grade;
                    """;
            return namedParameterJdbcTemplate.query(getAllGradesSQL, (rs, rowNum) -> rs.getInt(1));
        }
        return grades;
    }



    /**
     * createParamMapForSQL: sets base parameters for SQL template map and returns the map
     *
     * @author Safwaan Taher
     * @pre the schoolId must be checked and valid, the plans list and grades list cannot be empty
     * @post the templated map will contain schoolId, plans, and grades to be run in JDBC query
     * @return a Map that will be used for JDBC template
     */
    private Map<String, Object> createParamMapForSQL(String schoolId, List<Integer> plans, List<Integer> grades) {
        Map<String, Object> mappedParams = new HashMap<>();
        mappedParams.put("school_id", schoolId);
        mappedParams.put("plans", plans);
        mappedParams.put("grades", grades);
        return mappedParams;
    }

    /**
     * getCareerFilteredResults: given a list of parameters specified in the API
     * endpoint documentation, with the path set as "career",
     * return a list of all rows that fulfill the conditions passed
     * in these parameters, for the career filter. This will be returned to the form of a
     * AnalyticsFilters model.
     *
     * @author Safwaan Taher
     * @pre all parameters must be passed in from the corresponding controller class. The schoolName is
     * the schools name, the sortBy defines the lexicographical a-z or z-a sorting, the soc is the first two
     * digits of the soc code that is passed in, the plans and grades lists represent all plans and grades passed
     * into the filter.
     * @post all error handling will occur and if issues are found with the request a NULL object will be returned
     * else the resultant rows will be mapped to the model and returned
     * @return a List of AnalyticsFilters models with the filtered conditions or null if no rows are found
     */
    @Override
    public List<AnalyticsFilters> getCareerFilteredResults(String schoolName, String sortBy,
                                                           String soc, List<Integer> plansList,
                                                           List<Integer> gradesList) {
        // check for empty lists and if found populate with all values
        plansList = checkPlans(plansList);
        gradesList = checkGrades(gradesList);


        // retrieve and error check the school ID
        String schoolId = globalReturns.getSchoolId(schoolName);
        if(schoolId == null){
            return null;
        }

        // get the base SQL code and add a condition for searching in a specific school
        String sql = filtersBaseSQL + " WHERE S.cur_school_id = :school_id";

        int soc_code;

        // if client wants specific soc code range
        if(!soc.equals("ALL")){
            try{
                // error check soc code
                soc_code = Integer.parseInt(soc);
            }catch(NumberFormatException exe){
                System.err.println("Soc code is not a valid number");
                return null;
            }
            // add SQL clause to wildcard match soc code range with VARCHAR cast
            String whereClauseSQL = " AND plan.soc_code::varchar LIKE ";
            whereClauseSQL += " '" + soc_code + "%'";


            sql += whereClauseSQL;
        }

        // add final SQL conditionals
        sql = addPlansAndGradesFilterSQL(sql, sortBy);
        // initialize the templated map
        Map<String, Object> mappedParams = createParamMapForSQL(schoolId, plansList, gradesList);

        // query with JDBC template and return the resultant rows mapped to a model
        List<AnalyticsFilters> result =  namedParameterJdbcTemplate
                .query(sql, mappedParams, BeanPropertyRowMapper.newInstance(AnalyticsFilters.class));
        return result.size() == 0 ? null : result;
    }

    /**
     * getEducationFilteredResults: given a list of parameters specified in the API
     * endpoint documentation, with the path set as "education",
     * return a list of all rows that fulfill the conditions passed
     * in these parameters, for the career filter. This will be returned to the form of a
     * AnalyticsFilters model.
     *
     * @author Safwaan Taher
     * @pre all parameters must be passed in from the corresponding controller class. The schoolName is
     * the schools name, the sortBy defines the lexicographical a-z or z-a sorting, the educationType is the
     * full form of the degree that is passed in, the plans and grades lists represent all plans and grades passed
     * into the filter.
     * @post all error handling will occur and if issues are found with the request a NULL object will be returned
     * else the resultant rows will be mapped to the model and returned
     * @return a List of AnalyticsFilters models with the filtered conditions or null if no rows are found
     */
    @Override
    public List<AnalyticsFilters> getEducationFilteredResults(String schoolName,
                                                              String sortBy, String educationType,
                                                              List<Integer> plansList, List<Integer> gradesList) {
        // check for empty lists and if found populate with all values
        plansList = checkPlans(plansList);
        gradesList = checkGrades(gradesList);

        // retrieve and error check the school ID
        String schoolId = globalReturns.getSchoolId(schoolName);
        if(schoolId == null){
            return null;
        }

        // get the base SQL code
        String sql = filtersBaseSQL;

        // if the client wants a specific type of education
        if(!educationType.equals("ALL")){
            // add SQL clause to check for this specific school and education type
            String whereClauseSQL = """
            JOIN EducationType ET ON (ET.ed_type_id = Career.education_type_id)
            WHERE S.cur_school_id = :school_id AND ET.type_name = :education_name""";

            sql += whereClauseSQL;
        } else {
            // if client wanted all education types search within this school
            sql += " WHERE S.cur_school_id = :school_id";
        }

        // add final SQL conditionals
        sql = addPlansAndGradesFilterSQL(sql, sortBy);

        // create the templated map and add the education name to it
        Map<String, Object> mappedParams = createParamMapForSQL(schoolId, plansList, gradesList);
        mappedParams.put("education_name", educationType);

        // query with JDBC template and return the resultant rows mapped to a model
        List<AnalyticsFilters> result =  namedParameterJdbcTemplate
                .query(sql, mappedParams, BeanPropertyRowMapper.newInstance(AnalyticsFilters.class));
        return result.size() == 0 ? null : result;
    }
    /**
     * getGroupFilteredResults: given a list of parameters specified in the API
     * endpoint documentation, with the path set as "group",
     * return a list of all rows that fulfill the conditions passed
     * in these parameters, for the career filter. This will be returned to the form of a
     * AnalyticsFilters model.
     *
     * @author Safwaan Taher
     * @pre all parameters must be passed in from the corresponding controller class. The schoolName is
     * the schools name, the sortBy defines the lexicographical a-z or z-a sorting, the groupName is the name of
     * the group that the filter has been applied to
     * the plans and grades lists represent all plans and grades passed into the filter.
     * @post all error handling will occur and if issues are found with the request a NULL object will be returned
     * else the resultant rows will be mapped to the model and returned
     * @return a List of AnalyticsFilters models with the filtered conditions or null if no rows are found
     */
    @Override
    public List<AnalyticsFilters> getGroupFilteredResults(String schoolName,
                                                          String sortBy, String groupName,
                                                          List<Integer> plansList, List<Integer> gradesList) {
        // check for empty lists and if found populate with all values
        plansList = checkPlans(plansList);
        gradesList = checkGrades(gradesList);

        // error checking both plans and grades list
        if(plansList.isEmpty() || gradesList.isEmpty()){
            return null;
        }
        // retrieve and error check the school ID
        String schoolId = globalReturns.getSchoolId(schoolName);
        if(schoolId == null){
            return null;
        }

        // create and fill templated map with parameters for the base SQL code
        Map<String, Object> mappedParams = createParamMapForSQL(schoolId, plansList, gradesList);

        // PGSQL FUNCTION call to retrieve group ID
        String groupIdSQL = """
                SELECT getGroupID(:school_name, :group_name);
                """;
        // set templated params for function call
        mappedParams.put("school_name", schoolName);
        mappedParams.put("group_name", groupName);

        // get base SQL code
        String sql = filtersBaseSQL;

        String groupId;
        // if user does not want all groups
        if(!groupName.equals("ALL")){
            try{
                // get the group ID, error check
                groupId = namedParameterJdbcTemplate.queryForObject(groupIdSQL, mappedParams, String.class);
                if(groupId == null){
                    return null;
                }
            }catch(Exception exe){
                return null;
            }
            // add SQL clause to match the group and school
            String whereClauseSQL = """
                JOIN PlanToGroup PT ON (Plan.plan_id = PT.plan_id)
                WHERE PT.group_id = :group_id AND S.cur_school_id = :school_id""" ;

            // add final filters and concat the sql string
            sql += whereClauseSQL;
            sql = addPlansAndGradesFilterSQL(sql, sortBy);

            mappedParams.put("group_id", groupId);
        } else {
            // if all groups required simply search within a school via school ID
            sql += " AND S.cur_school_id = :school_id";
        }

        System.out.println(sql);
        // query with JDBC template and return the resultant rows mapped to a model
        List<AnalyticsFilters> result =  namedParameterJdbcTemplate
                .query(sql, mappedParams, BeanPropertyRowMapper.newInstance(AnalyticsFilters.class));
        return result.size() == 0 ? null : result;

    }


}

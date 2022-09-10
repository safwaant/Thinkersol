package com.thinkersol.API.repositories.Admin.AdminManage;

import com.thinkersol.API.models.Admin.AdminManage.DashboardItem;
import com.thinkersol.API.models.Admin.AdminManage.ManageFilters;
import com.thinkersol.API.repositories.GlobalConstants;
import com.thinkersol.API.repositories.GlobalReturnsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * The following is licensed under the GNU General Public License v3.0 as found in the LICENSE.md file
 * @authors Safwaan Taher, Ashley Mead
 * @version 1.1
 * @since 7/21/2022
 * <p>
 * Abstract:
 * jdbcManageRepository holds all actions that will be executed for
 * the manage page in the form of database queries, helper methods
 * and error handling. This layer is abstracted away by the Repository interface.
 * </p>
 */
@Repository
public class jdbcManageRepository implements ManageRepository{
    // Injected repositories and JDBC objects
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final GlobalReturnsRepository globalReturnsRepository;

    private final JdbcTemplate jdbcTemplate;

    /**
     * constructor: sets up all dependencies
     * @author Safwaan Taher
     * @pre all repository objects must be injected into parameters
     * @post all private fields initialized
     */
    @Autowired
    public jdbcManageRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                                JdbcTemplate jdbcTemplate,
                                GlobalReturnsRepository globalReturnsRepository) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
        this.globalReturnsRepository = globalReturnsRepository;
    }



    /**
     * getSchoolId: retrieves the schoolId for a specific
     *              school given its name
     * @author Safwaan Taher
     * @pre a specific school name must be passed in
     * @post a school is found or an exception is thrown and caught
     * @return the schoolId as a string or null if not found
     */
    private String getSchoolId(String schoolName){
        return globalReturnsRepository.getSchoolId(schoolName);
    }

    /**
     * getStudentsFromFilters: given a list of parameters specified in the API endpoint documentation,
     * return a list of all rows that fulfill the conditions passed in these parameters.
     * If the value of studentLastName or plan status is ALL, then remove these requirements from the filter
     * If plans or grades lists are empty search within all plans or grades
     *
     * @author Safwaan Taher
     * @pre All parameters must be passed in from the corresponding controller class. The schoolName is
     * the school's name, and the studentLastName is the search criteria, the plans list is the plan numbers selected,
     * planStatus being the plan statuses selected, and grades being the student grades that are selected
     * @post The resultant rows will be mapped to the model and returned
     * @return a List of ManageFilters models with the filtered conditions or null if no rows are found.
     */
    @Override
    public List<ManageFilters> getStudentsFromFilters(String schoolName, String studentLastName, String sortBy,
                                                     List<Integer> grades, List<String> planStatuses, List<Integer> plans) {
        // if status is empty return
        if(planStatuses.isEmpty()){
            return null;
        }

        // get and check school ID
        String schoolId = getSchoolId(schoolName);
                // getSchoolId(schoolName);
        if(schoolId == null){
            return null;
        }
        // SQL code to get all rows required for the data model and display
        String sql =
                """
                SELECT Student.student_id, Student.first_name, Student.last_name, Student.grade,
                Plan.career_status AS career, Plan.education_status AS education, Plan.courses_status AS courses, Plan.resume_status AS resume
                FROM Student
                JOIN Plan ON (Student.student_id = Plan.student_id)
                JOIN PlanStatus ON (PlanStatus.plan_status_id = Plan.plan_status_id)
                WHERE Student.cur_school_id = :schoolId
                """;

        boolean planStatusAllCheck = !planStatuses.get(0).equals("ALL");

        // check each filter and dynamically add to the SQL code based on the filter
        if(planStatusAllCheck){
           String planStatusSQL = " AND PlanStatus.name IN (:planStatus)";
           sql += planStatusSQL;
        }
        if(!grades.isEmpty()){
            String gradesSQL = " AND Student.grade IN (:grades)";
            sql += gradesSQL;
        }
        if(!plans.isEmpty()){
            String plansSQL = " AND Plan.plan_num IN (:plans)";
            sql += plansSQL;
        }
        if(!studentLastName.equals("ALL")){
            String lastNameWildcardSQL =  (" AND Student.last_name ILIKE '" + studentLastName + "%'");
            sql += lastNameWildcardSQL;
        }

        // choose the lexicographical ordering based on the parameter sortBy
        if(sortBy.equals("zToA")){
            sql += """
                    ORDER BY Student.last_name DESC, Student.first_name ASC
                   """;
        } else {
            sql += """
                    ORDER BY Student.last_name ASC, Student.first_name ASC
                   """;
        }
        System.out.println(sql);
        // store templated parameters for SQL query
        Map<String, Object> params = new HashMap<>(){{
           put("schoolId", schoolId);
           put("grades", grades);
           put("planStatus", planStatuses);
           put("plans", plans);
        }};
        // run the query and capture result
        List<ManageFilters> result = namedParameterJdbcTemplate.query(sql, params, BeanPropertyRowMapper.newInstance(ManageFilters.class));
        return result.size() == 0 ? null : result;
    }

    /**
     * getStudentsFromSearch: given a list of parameters specified in the API endpoint documentation,
     * return a list of all rows that fulfill the conditions passed in these parameters.
     * If the value of studentLastName is ALL, then return a list of all the rows for the given school.
     * This will be returned to the form of a ManageFilters model.
     *
     * @author Ashley Mead
     * @pre All parameters must be passed in from the corresponding controller class. The schoolName is
     * the school's name, and the studentLastName is the letter sequence passed into the search bar as a filter
     * @post The resultant rows will be mapped to the model and returned
     * @return a List of ManageFilters models with the filtered conditions or null if no rows are found.
     */
    @Override
    public List<ManageFilters> getStudentsFromSearch(String schoolName, String studentLastName) {
        if(studentLastName.isBlank() || studentLastName.isEmpty()){
            return null;
        }


        // retrieve and error check the school ID
        String schoolId = getSchoolId(schoolName);
        if(schoolId == null){
            System.out.println("is null");
            return null;
        }

        // SQL code to return filtered student info
        String sql = """
                SELECT Student.student_id, Student.first_name, Student.last_name, Student.grade,
                Plan.career_status AS career, Plan.education_status AS education, Plan.courses_status AS courses, Plan.resume_status AS resume
                FROM Student
                JOIN Plan ON (Student.student_id = Plan.student_id)
                WHERE Student.cur_school_id = :school_id
                """;

        if(!studentLastName.equals("ALL")) {
            // last name wildcard match
            String likeClause = "AND Student.last_name ILIKE '" + studentLastName + "%'";
            sql += likeClause;
        }
        sql += """
            ORDER BY Student.last_name ASC, Student.first_name ASC;
            """;

        Map<String, Object> mappedParams = createParamMapForSQL(schoolId);

        // run query and error check
        List<ManageFilters> result = namedParameterJdbcTemplate.query(sql, mappedParams, BeanPropertyRowMapper.newInstance(ManageFilters.class));
        return result.size() == 0 ? null : result;
    }

    /**
     * getDashboardInfo: returns info about each plan status within the admin dashboard
     * @author Safwaan Taher
     * @pre the schoolName and plan number must be passed in
     * @post the database will be queried and a model will be returned
     * @return a List of DashboardItem models or null if none are found
     */
    @Override
    public List<DashboardItem> getDashboardInfo(String schoolName, int planNum) {
        String schoolId = globalReturnsRepository.getSchoolId(schoolName);
        // error check the school ID and if the plan number is correct
        if(schoolId == null || planNum > GlobalConstants.NUM_OF_PLANS) {
            return null;
        }

        // get all plan statuses available from the database
        String getAllPlanStatusesSQL =
                """
                SELECT PlanStatus.name
                FROM PlanStatus
                """;
        List<String> planStatuses;
        try {
            planStatuses = jdbcTemplate.queryForList(getAllPlanStatusesSQL, String.class);
        } catch (Exception exe) {
            return null;
        }

        // used to store the resultant dashboard items
        List<DashboardItem> result = new LinkedList<>();

        // get the total number of plans in this school for this particular plan number
        int total = 0;
        String getTotalPlansSQL =
                """
                -- Get total number of plans for specific school
                -- by Safwaan Taher
                SELECT count(plan_id)
                FROM Plan
                JOIN Student ON (Student.student_id = Plan.student_id)
                WHERE Student.cur_school_id = ? AND Plan.plan_num = ?;
                """;
        try {
            total = jdbcTemplate.queryForObject(getTotalPlansSQL, Integer.class, schoolId, planNum);
        } catch (Exception ignored) {}

        String getCompletedPlansSQL =
                """
                -- Get total number of plans which for a particular plan status
                -- by Safwaan Taher
                SELECT count(plan_id)
                FROM Plan
                JOIN Student ON (Student.student_id = Plan.student_id)
                JOIN PlanStatus ON (PlanStatus.plan_status_id = Plan.plan_status_id AND PlanStatus.name = ?)
                WHERE Student.cur_school_id = ? AND Plan.plan_num = ?;
                """;
        int completed = 0;
        // for each plan status retrieve the total number of plans that fulfill the status
        for (String planStatus : planStatuses) {
            try {
               completed = jdbcTemplate.queryForObject(getCompletedPlansSQL, Integer.class, planStatus, schoolId, planNum);
            } catch (Exception ignored){}
            result.add(new DashboardItem(planStatus, completed, total));
        }
        // error check and return
        return result.size() == 0 ? null : result;
    }

    /**
     * createParamMapForSQL: sets base parameters for SQL template map and returns the map
     *
     * @author Ashley Mead
     * @pre the schoolId must be checked and valid, the studentLastName cannot be empty
     * @post the templated map will contain schoolId and studentLastName to be run in JDBC query
     * @return a Map that will be used for JDBC template
     */
    private Map<String, Object> createParamMapForSQL(String schoolId) {
       return new HashMap<>(){{
           put("school_id", schoolId);
        }};
    }


}

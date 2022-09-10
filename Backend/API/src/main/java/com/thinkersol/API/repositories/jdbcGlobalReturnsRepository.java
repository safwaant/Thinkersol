package com.thinkersol.API.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Copyright The following is licensed under the GNU General Public License v3.0 as found in the LICENSE.md file
 * @authors Safwaan Taher
 * @version 1.0
 * @since 8/2/2022
 * <p>
 * Abstract:
 * jdbcGlobalReturnsRepository implements the GlobalReturnsRepository
 * and holds helper methods that will be called multiple times throughout the application
 * </p>
 */
@Repository
@Scope(value="prototype")
public class jdbcGlobalReturnsRepository implements GlobalReturnsRepository {
    // injected beans
    private final JdbcTemplate jdbcTemplate;

    /**
     * constructor: Initializes all fields through dependency injection
     * @author Safwaan Taher
     * @pre Injected beans must be passed in by Spring
     * @post All private fields are initialized
     */
    @Autowired
    public jdbcGlobalReturnsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

    }


    /**
     * getSchoolId: retrieves the schoolId given a school's name
     * @author Safwaan Taher
     * @pre The school name is passed in by the callee
     * @post No post conditions
     * @return The school ID is retrieved or null if no ID is found
     */
    @Override
    public String getSchoolId(String schoolName) {
            String sql = """
                -- Get the school Id given a school name
                -- by Safwaan Taher
                SELECT school_id
                FROM School
                WHERE school_name = ?;
                """;
            String schoolId;
            try{
                schoolId = jdbcTemplate.queryForObject(sql, String.class, schoolName);
            }catch(Exception exe){
                return null;
            }
            return schoolId;
    }


    /**
     * getCustomGroupId: retrieves a particular groups ID
     * @author Safwaan Taher
     * @pre The school name and group name are passed in by the callee
     * @post No post conditions
     * @return The group ID is returned or null if no ID is found
     */
    @Override
    public String getCustomGroupId(String schoolName, String groupName) {
        String sql = """
                -- Get the group id given a name and school
                -- by Safwaan Taher
                SELECT group_id
                FROM CustomGroup
                WHERE group_name = ? AND
                    (SELECT school_id FROM School WHERE school_name = ?) = CustomGroup.school_id;
                """;
        String groupId;
        try {
            groupId = jdbcTemplate.queryForObject(sql, String.class, groupName, schoolName);
        } catch(Exception exe) {
            return null;
        }
        return groupId;
    }

    /**
     * getAllSubjectsInSchool: retrieves all subjects that the school has
     * listed in the database
     * @author Safwaan Taher
     * @pre The school ID is passed in by the callee
     * @post No post conditions
     * @return a List of all subject names or null if no subjects are found
     */
    @Override
    public List<String> getAllSubjectsInSchool(String schoolId) {
        String getAllSubjectsInSchoolSQL = """
                -- Return all subjects in the school
                -- by Safwaan Taher
                SELECT DISTINCT SR.subject
                FROM SubjectRequirements SR
                JOIN SchoolCourses ON
                    (SR.subject = SchoolCourses.subject AND SchoolCourses.school_id = ?);
                """;
        List<String> subjectNames;
        try {
            subjectNames = jdbcTemplate.queryForList(getAllSubjectsInSchoolSQL, String.class, schoolId);
        } catch (Exception exe) {
            return null;
        }
        return subjectNames.size() == 0 ? null : subjectNames;
    }

    /**
     * getAllGradesForSchoolType: retrieves all grades offered in the school
     * based on the type of school
     * @author Safwaan Taher
     * @pre The school ID is passed in by the callee
     * @post No post conditions
     * @return a List of all grades or null if no grades are found
     */
    @Override
    public List<Integer> getAllGradesForSchoolType(String schoolId) {
        String getSchoolTypeSQL =
                """
                -- Returns the type of school given a school's ID
                -- by Safwaan Taher
                SELECT school_type_name
                FROM School
                JOIN SchoolType ON (SchoolType.school_type  = School.school_type)
                WHERE School.school_id = ?;
                """;
        String schoolType;
        try {
            schoolType = jdbcTemplate.queryForObject(getSchoolTypeSQL, String.class, schoolId);
        } catch (Exception exe) {
            return null;
        }
        List<Integer> schoolGrades;
        // based on the type of school get grades in the appropriate range within the table
        switch(schoolType){
            case "High School" -> {
               schoolGrades = getGradesByRange(9, 12);
            }
            case "Middle School" -> {
               schoolGrades = getGradesByRange(6, 8);
            }
            default -> {
               schoolGrades = getGradesByRange(6, 12);
            }
        }
        return schoolGrades.size() == 0 ? null : schoolGrades;
    }

    /**
     * getGradesByRange: retrieves all grades between the lower and upper bounds
     * @author Safwaan Taher
     * @pre a lower and upper bounds is passed in by the callee
     * @post No post conditions
     * @return a List of all grades or null if no grades are found
     */
    private List<Integer> getGradesByRange(int lower, int upper) {
        String sql =
                """
                SELECT Grade.grade
                FROM Grade
                WHERE grade>=? AND grade <=?;
                """;
        try {
            return jdbcTemplate.queryForList(sql, Integer.class, lower, upper);
        } catch (Exception ignored) {}
        return List.of();
    }


}

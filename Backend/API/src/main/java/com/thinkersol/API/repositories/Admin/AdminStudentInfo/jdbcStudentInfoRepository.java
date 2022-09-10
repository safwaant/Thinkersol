package com.thinkersol.API.repositories.Admin.AdminStudentInfo;

import com.thinkersol.API.models.Admin.AdminStudentInfo.StudentInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @Copyright The following is licensed under the GNU General Public License v3.0 as found in the LICENSE.md file
 * @authors Safwaan Taher, Ashley Mead
 * @version 1.0
 * @since 8/2/2022
 * <p>
 * Abstract:
 * jdbcStudentInfoRepository holds actions to view initial data
 * about a student such as id, first and last name
 * and to delete them from a group
 * </p>
 */
@Repository
public class jdbcStudentInfoRepository implements StudentInfoRepository{
    // injected beans
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * constructor: initializes all dependencies needed for
     * this repository
     * @author Safwaan Taher
     * @pre all JDBC templates are injected into parameters
     * @post private fields are initialized
     */
    @Autowired
    public jdbcStudentInfoRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }


    /**
     * deleteStudentFromGroup: removes the specified student from the specified group(s)
     * @author Ashley Mead
     * @pre The studentID of the student to be removed from the group
     * and the name(s) of the group(s) to remove the student from
     * must be passed in.
     * @post The student and their plan(s) are removed from the group(s)
     * @return The number of rows in the database modified as a result of this deletion.
     * Returns 0 if the schoolID or group name(s) do not exist
     */
    @Override
    public int deleteStudentFromGroup(String studentID, int planNum, List<String> groupNames) {
        // get the ID of the school the student attends
        String getSchoolIdSql = """
                SELECT Student.cur_school_id
                FROM Student
                WHERE Student.student_id = :studentId;
                """;

        Map<String, Object> mappedParams = new HashMap<>();
        mappedParams.put("studentId", studentID);

        String schoolId = namedParameterJdbcTemplate.queryForObject(getSchoolIdSql, mappedParams, String.class);

        // ensure the retrieved schoolID exists and is valid
        if(schoolId == null) {
            return 0;
        }

        // get a list of the groupID(s) of the group(s) specified
        String getGroupIdsSql = """
                SELECT DISTINCT group_id
                FROM CustomGroup
                WHERE CustomGroup.school_id = (:schoolId) AND CustomGroup.group_name IN (:groupNames);
                """;

        mappedParams.put("schoolId", schoolId);
        mappedParams.put("groupNames", groupNames);

        List<String> groupIds = namedParameterJdbcTemplate.queryForList(getGroupIdsSql, mappedParams, String.class);

        // ensure the retrieved group ID(s) exists and is valid
        if(groupIds == null) {
            return 0;
        }

        // remove the student from all groups selected
        String removeStudentSql = """
                DELETE FROM StudentToCustomGroup
                WHERE StudentToCustomGroup.group_id IN (:groupIds) AND StudentToCustomGroup.student_id = :studentId;
                """;

        // remove any of the student's plans from all groups selected
        String removePlanSql = """
                DELETE FROM PlanToGroup
                WHERE PlanToGroup.group_id IN (:groupIds) AND PlanToGroup.plan_id =
                  (SELECT plan_id FROM Plan WHERE Plan.student_id = :studentId AND Plan.plan_num = :plan_num);
                """;

        mappedParams.put("groupIds", groupIds);
        mappedParams.put("plan_num", planNum);

        int rows = 0;

        rows += namedParameterJdbcTemplate.update(removeStudentSql, mappedParams);
        rows += namedParameterJdbcTemplate.update(removePlanSql, mappedParams);

        return rows;
    }

    @Override
    public StudentInfo getStudentInfo(String studentID) {
        String getBaseStudentInfoSQL =
                """
                SELECT student_id, first_name, last_name, grade
                FROM Student
                WHERE Student.student_id = ?;
                """;
        StudentInfo studentInfo;
        try {
            studentInfo = jdbcTemplate.queryForObject(getBaseStudentInfoSQL, BeanPropertyRowMapper.newInstance(StudentInfo.class), studentID);
        } catch (EmptyResultDataAccessException exe) {
            return null;
        }

        String getPlanNumsSQL =
                """
                SELECT plan_num
                FROM Plan
                WHERE Plan.student_id = ?;
                """;
        List<Integer> planNums = jdbcTemplate.queryForList(getPlanNumsSQL, Integer.class, studentID);

        studentInfo.setPlans(planNums);
        return studentInfo;
    }
}

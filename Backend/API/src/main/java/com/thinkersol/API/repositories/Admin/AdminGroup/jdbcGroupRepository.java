package com.thinkersol.API.repositories.Admin.AdminGroup;


import com.thinkersol.API.repositories.GlobalReturnsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
/**
 * The following is licensed under the GNU General Public License v3.0 as found in the LICENSE.md file
 * @authors Safwaan Taher, Mustafa Asadulllah, Shakeel Khan, Ashley Mead
 * @version 1.1
 * @since 7/7/2022
 * <p>
 * Abstract:
 *
 * jdbcGroupRepository holds implementation details for all
 * group repository actions that will be executed, by running
 * SQL queries with the JDBC template, as well as utilizing any helper
 * methods that are needed.
 * </p>
 */
@Repository
public class jdbcGroupRepository implements GroupRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    GlobalReturnsRepository globalReturnsRepository;

    /**
     * <p>
     * getSchoolId: Given a school name retrieves the corresponding school ID from
     *              the SQL database
     * </p>

     * @author Safwaan Taher
     * @pre the school name must be passed in
     * @post return condition only
     * @return null if school id is not found, otherwise the id is returned
     */
    private String getSchoolId(String school_name){
        String getSchoolIdSQL = """
                SELECT school_id
                FROM School
                WHERE school_name = ?;
                """;
        String schoolId;
        // execute SQL query for a school ID and error handle
        try {
            schoolId = jdbcTemplate.queryForObject(getSchoolIdSQL, String.class, school_name);
        }catch(EmptyResultDataAccessException exe){
            System.err.println("No school matches given name");
            return null;
        }
        return schoolId;
    }
    /**
     *
     * getAllCustomGroupNames:
     *
     * Retrieves all groups names for groups that an Admin has created
     * apart from the default groups
     *
     * @author Safwaan Taher
     * @pre the school name must be passed in
     * @post return condition only
     * @return null if no groups are found, otherwise a List of Group names models are returned
     */
    @Override
    public List<String> getAllCustomGroupNames(String school_name) {
        String schoolId = getSchoolId(school_name);
        if(schoolId == null){
            return null;
        }

        // SQL code for getting a list of all group names that are
        // not default groups
        String sql = """
                SELECT group_name
                FROM CustomGroup
                JOIN School ON (School.school_id = CustomGroup.school_id)
                WHERE school.school_id = ?
                """;
        // execute query with JDBC and return result as List of GroupName models
        return jdbcTemplate.queryForList(sql, String.class, schoolId);
    }

    @Override
    public int createGroup(String group_id, String group_name, String school_name) {
        String school_id = getSchoolId(school_name);
        if(school_id == null){
            return 0;
        }

        String groupCheckSQL = """
                SELECT count(group_name)
                FROM CustomGroup
                JOIN School ON (School.school_id = CustomGroup.school_id)
                WHERE CustomGroup.group_name = ? AND School.school_id = ?;
                """;
        int duplicateGroups = 0;
        try{
            duplicateGroups = jdbcTemplate.queryForObject(groupCheckSQL, Integer.class, group_name, school_id);
        }catch(NullPointerException exe){
            exe.printStackTrace();
        }

        if(duplicateGroups > 0){
           return 0;
        }

        String sql = """
                INSERT INTO CustomGroup (group_id, group_name, school_id)
                VALUES (?, ?, ?);
                """;
        return jdbcTemplate.update(sql, group_id, group_name, school_id);
    }

    @Override
    public int addStudentsToGroup(List<Map<String, String>> body) {

        String addStudentToGroupSQL = """
                INSERT INTO StudentToCustomGroup (group_id, student_id)
                VALUES (?, ?);
                """;

        String addPlanToGroupSQL = """
                INSERT INTO PlanToGroup (plan_id, group_id)
                VALUES ((SELECT plan_id FROM Plan WHERE student_id = ? AND plan_num = ?), ?);
                """;

        String studentId, schoolName, groupName, groupId;
        int planNum, rows = 0;

        for (Map<String, String> studentInfo : body) {
            try {
                studentId = studentInfo.get("student_id");
                planNum = Integer.parseInt(studentInfo.get("plan_num"));
                schoolName = studentInfo.get("school_name");
                groupName = studentInfo.get("group_name");
                groupId = globalReturnsRepository.getCustomGroupId(schoolName, groupName);
                try {
                    rows += jdbcTemplate.update(addStudentToGroupSQL, groupId, studentId);
                } catch (DuplicateKeyException exe) {
                    System.err.println("Student is already added to this group . . .");
                }

                try {
                    rows += jdbcTemplate.update(addPlanToGroupSQL, studentId, planNum, groupId);
                } catch(DuplicateKeyException exe) {
                    System.err.println("Plan is already added to this group");
                }
            } catch (Exception ignored) {}
        }
        return rows;
    }

    @Override
    public int deleteGroups(String schoolName, List<String> groupNames) {
        List<String> groupIdsToBeDeleted = new LinkedList<>();
        for(String i : groupNames){
            String groupID = globalReturnsRepository.getCustomGroupId(schoolName, i);
            groupIdsToBeDeleted.add(groupID);
        }
        String deleteStudentFromGroupSQL = """
                -- Delete all students from the group
                DELETE FROM StudentToCustomGroup
                WHERE StudentToCustomGroup.group_id = ?;
                """;
        String deleteGroupsFromPlanSQL = """
                -- Set all plans with the specific group to null for the id
                DELETE FROM PlanToGroup
                WHERE PlanToGroup.group_id = ?;
                """;
        String deleteGroupSQL = """
                -- Delete the group
                DELETE FROM CustomGroup
                WHERE CustomGroup.group_id = ?;
                """;

        int rows = 0;
        for(String i : groupIdsToBeDeleted)
        {
            rows += jdbcTemplate.update(deleteStudentFromGroupSQL, i);
            rows += jdbcTemplate.update(deleteGroupsFromPlanSQL, i);
            rows += jdbcTemplate.update(deleteGroupSQL, i);
        }
        return rows;
    }
}

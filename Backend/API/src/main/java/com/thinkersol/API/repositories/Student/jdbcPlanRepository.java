package com.thinkersol.API.repositories.Student;

import com.thinkersol.API.models.Student.AddCareerToPlan;
import com.thinkersol.API.models.Student.StudentPlanInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * The following is licensed under the GNU General Public License v3.0 as found in the LICENSE.md file
 * @authors Ashley Mead
 * @version 1.0
 * @since 8/8/2022
 * <p>
 * Abstract:
 * jdbcPlanRepository holds all actions involving a
 * students plan from the students perspective
 * </p>
 */
@Repository
public class jdbcPlanRepository implements PlanRepository {
    // injected beans
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public jdbcPlanRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * addCareerToPlan: Returns number of rows of career information added to a plan
     * @author Ashley Mead
     * @pre The student's ID, the number of the plan to add the career to, and the SOC code of the career are passed in
     * @post The career is added to the plan for the specified student
     * @return the number of rows/career information added to a database of a plan
     */
    @Override
    public int addCareerToPlan(AddCareerToPlan body) {

        String sql =
                """
                -- Add Career to Plan SQL Code
                -- by Safwaan Taher
                UPDATE Plan
                SET soc_code = ?
                WHERE Plan.student_id = ? AND Plan.plan_num = ?;
                """;
        System.out.println("hi");
        int rows = 0;
        try {
            // getting the fields from the body and querying
            rows += jdbcTemplate.update(sql, body.getSoc(), body.getStudent_id(), body.getPlan_num());
        } catch (Exception exe) {
            return 0;
        }
        return rows;
    }

    /**
     * getStudentPlanInfo: Returns the plan statuses such as the
     * career status, education status, courses status, and resume status
     * @author Ashley Mead
     * @pre The student's ID and the number of the plan to retrieve information from are passed in
     * @post The resultant rows will be mapped to the model and returned
     * @return the number of rows/career information added to a database of a plan
     */
    @Override
    public StudentPlanInfo getStudentPlanInfo(String studentID, int planNum) {
        String sql = """
                -- Get Student Plan Info SQL Code
                -- by Safwaan Taher
                SELECT career_status, education_status, courses_status, resume_status
                FROM Plan
                WHERE Plan.student_id = ? AND Plan.plan_num = ?;
                """;
        return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(StudentPlanInfo.class), studentID, planNum);
    }

}

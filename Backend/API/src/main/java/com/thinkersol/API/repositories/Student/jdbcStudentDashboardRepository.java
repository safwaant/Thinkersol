package com.thinkersol.API.repositories.Student;

import com.thinkersol.API.models.Student.StudentDashboard;
import com.thinkersol.API.repositories.GlobalReturnsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class jdbcStudentDashboardRepository implements StudentDashboardRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    GlobalReturnsRepository globalReturnsRepository;


    @Override
    public StudentDashboard getStudentDashboard(String studentID, int planNum) {
        if(studentID == null){
            return null;
        }

        String sql = """
                SELECT Career.career_name, career_status, education_status, course_status, resume_status
                FROM Plan\s
                JOIN Career ON (Career.soc_code = Plan.soc_code)
                WHERE Plan.plan_num = ? AND Plan.student_id = ?
                """;
        StudentDashboard studentDashboard;
        try{
            studentDashboard = jdbcTemplate.queryForObject(sql, StudentDashboard.class, studentID, planNum);
        }catch(Exception e){
            return null;
        }
        return studentDashboard;
    }
}

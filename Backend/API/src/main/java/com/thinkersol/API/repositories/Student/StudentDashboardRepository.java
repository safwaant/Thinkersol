package com.thinkersol.API.repositories.Student;

import com.thinkersol.API.models.Student.StudentDashboard;

public interface StudentDashboardRepository {

    StudentDashboard getStudentDashboard(String studentID, int planNum );

}

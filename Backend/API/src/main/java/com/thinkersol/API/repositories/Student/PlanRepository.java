package com.thinkersol.API.repositories.Student;

import com.thinkersol.API.models.Student.AddCareerToPlan;
import com.thinkersol.API.models.Student.StudentPlanInfo;

/**
 * The following is licensed under the GNU General Public License v3.0 as found in the LICENSE.md file
 * @authors Ashley Mead
 * @version 1.0
 * @since 8/8/2022
 * <p>
 * Abstract:
 * PlanRepository is a layer of abstraction
 * above the database and holds all operations that can
 * be called by the controller and in turn the client
 * </p>
 */
public interface PlanRepository {

    int addCareerToPlan(AddCareerToPlan body);

    StudentPlanInfo getStudentPlanInfo(String studentID, int planNum);
}

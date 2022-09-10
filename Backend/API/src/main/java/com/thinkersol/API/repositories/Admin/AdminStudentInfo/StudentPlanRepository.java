package com.thinkersol.API.repositories.Admin.AdminStudentInfo;

import com.thinkersol.API.models.Admin.AdminStudentInfo.PlanInfo;
/**
 * @Copyright The following is licensed under the GNU General Public License v3.0 as found in the LICENSE.md file
 * @authors Safwaan Taher, Ashley Mead, Rahul Pillalamarri
 * @version 1.0
 * @since 8/2/2022
 * <p>
 * Abstract:
 * StudentPlanRepository is a layer of
 * abstraction above the internal implementation that
 * queries the database
 * </p>
 */
public interface StudentPlanRepository {
    // get all plan info for a particular student
    PlanInfo getPlanInfo(String schoolName, String studentId, int planNum);
}

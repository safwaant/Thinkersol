package com.thinkersol.API.repositories.Admin.AdminStudentInfo;

import com.thinkersol.API.models.Admin.AdminStudentInfo.StudentInfo;

import java.util.List;
/**
 * @Copyright The following is licensed under the GNU General Public License v3.0 as found in the LICENSE.md file
 * @authors Safwaan Taher, Ashley Mead
 * @version 1.0
 * @since 8/2/2022
 * <p>
 * Abstract:
 * StudentInfoRepository is a layer of abstraction
 * above the database and holds all operations that can
 * be called by the controller and in turn the client
 * </p>
 */
public interface StudentInfoRepository {
    // called when getting the student metadata (name, groups, etc. . .)
    StudentInfo getStudentInfo(String studentID);
    // called when removing the student from a group
    int deleteStudentFromGroup(String studentID, int planNum, List<String> groupNames);
}

package com.thinkersol.API.repositories;

import java.util.List;
/**
 * @Copyright The following is licensed under the GNU General Public License v3.0 as found in the LICENSE.md file
 * @authors Safwaan Taher
 * @version 1.0
 * @since 8/2/2022
 * <p>
 * Abstract:
 * GlobalReturnsRepository hold helper methods that will be called
 * within all other repositories or controllers
 * not unique to a client or repository
 * </p>
 */
public interface GlobalReturnsRepository {
    // returns the school id for a particular school
    String getSchoolId(String schoolName);
    // returns the group ID for a particular group
    String getCustomGroupId(String schoolName, String groupName);
    // returns all subjects the school offers
    List<String> getAllSubjectsInSchool(String schoolId);
    // returns all grades that are available in a particular school
    List<Integer> getAllGradesForSchoolType(String schoolId);

}

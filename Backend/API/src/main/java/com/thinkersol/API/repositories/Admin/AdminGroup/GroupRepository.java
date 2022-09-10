package com.thinkersol.API.repositories.Admin.AdminGroup;

import java.util.List;
import java.util.Map;
/**
 * The following is licensed under the GNU General Public License v3.0 as found in the LICENSE.md file
 * @author Safwaan Taher
 * @version 1.1
 * @since 7/7/2022
 * <p>
 * Abstract:
 * GroupRepository holds all actions for the Admin
 * in terms of creating, deleting, retrieving and
 * adding students to a group and provide a uniform interface for implementation
 * </p>
 */
public interface GroupRepository {
    // making a custom group
    int createGroup(String group_id, String group_name, String school_name);
    // getting all custom group names
    List<String> getAllCustomGroupNames(String school_name);
    // add students to a group
    int addStudentsToGroup(List<Map<String, String>> body);
    // delete a group
    int deleteGroups(String schoolName, List<String> groupNames);
}

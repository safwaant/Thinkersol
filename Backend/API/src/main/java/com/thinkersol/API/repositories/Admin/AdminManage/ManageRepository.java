package com.thinkersol.API.repositories.Admin.AdminManage;

import com.thinkersol.API.models.Admin.AdminManage.DashboardItem;
import com.thinkersol.API.models.Admin.AdminManage.ManageFilters;

import java.util.List;
/**
 * The following is licensed under the GNU General Public License v3.0 as found in the LICENSE.md file
 * @authors Safwaan Taher, Ashley Mead
 * @version 1.1
 * @since 7/21/2022
 * <p>
 * Abstract:
 * ManageRepository holds all methods within the data access layer.
 * It acts as a level of abstraction between DAOs and the controller,
 * as well as a uniform interface for future extension.
 * </p>
 */
public interface ManageRepository {
    // for retrieving students within specific filters on the Manage page
    List<ManageFilters> getStudentsFromFilters(String schoolName, String studentLastName, String sortBy,
                                              List<Integer> grades, List<String> planStatuses, List<Integer> plans);
    // for retrieving students within a specific search only
    List<ManageFilters> getStudentsFromSearch(String schoolName, String studentLastName);

    // for retrieving all dashboard info
    List<DashboardItem> getDashboardInfo(String schoolName, int planNum);
}

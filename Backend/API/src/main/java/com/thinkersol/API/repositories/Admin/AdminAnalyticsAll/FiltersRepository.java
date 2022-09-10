package com.thinkersol.API.repositories.Admin.AdminAnalyticsAll;

import com.thinkersol.API.models.Admin.AdminAnalyticsAll.AnalyticsFilters;

import java.util.List;
/**
 * The following is licensed under the GNU General Public License v3.0 as found in the LICENSE.md file
 * @author Safwaan Taher
 * @version 1.1
 * @since 7/7/2022
 * <p>
 * Abstract:
 * FiltersRepository interface holds all CRUD operations that can be done
 * within the filters endpoints. Its purpose is to abstract away repository implementation
 * and provide a uniform interface
 * </p>
 */
public interface FiltersRepository{
    // method invoked when a career report with given filters in /filters is run
    List<AnalyticsFilters> getCareerFilteredResults(String schoolName,
                                                    String sortBy,
                                                    String soc,
                                                    List<Integer> plansList,
                                                    List<Integer> gradesList);
    // method invoked when a education report with given filters in /filters is run
    List<AnalyticsFilters> getEducationFilteredResults(String schoolName,
                                                       String sortBy,
                                                       String educationType,
                                                       List<Integer> plansList,
                                                       List<Integer> gradesList);
    // method invoked when a group report with given filters in /filters is ru
    List<AnalyticsFilters> getGroupFilteredResults(String schoolName,
                                                   String sortBy,
                                                   String groupName,
                                                   List<Integer> plansList,
                                                   List<Integer> gradesList);
}
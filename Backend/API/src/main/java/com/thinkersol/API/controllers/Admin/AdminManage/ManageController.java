package com.thinkersol.API.controllers.Admin.AdminManage;

import com.thinkersol.API.controllers.ResponseHandler;
import com.thinkersol.API.models.Admin.AdminManage.DashboardItem;
import com.thinkersol.API.models.Admin.AdminManage.ManageFilters;
import com.thinkersol.API.repositories.Admin.AdminManage.ManageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The following is licensed under the GNU General Public License v3.0 as found in the LICENSE.md file
 * @authors Safwaan Taher, Ashley Mead
 * @version 1.1
 * @since 7/21/2022
 * <p>
 * Abstract:
 * ManageController routes each REST HTTP endpoint within the Admin manage page
 * to its corresponding repository action. Results are returned in the form of
 * ResponseEntities and error handling is checked on all levels.
 * </p>
 */


@RestController
@RequestMapping("/admin/manage")
public class ManageController {
    /*
     * Injected beans (repositories, handlers etc. . .)
     */
    private final ManageRepository manageRepository;
    private final ResponseHandler responseHandler;

    /**
     * constructor: initializes all fields via dependency injection
     * @author Safwaan Taher
     * @pre injected beans are passed in by Spring
     * @post all private fields are initialized
     */
    @Autowired
    public ManageController(ManageRepository manageRepository, ResponseHandler responseHandler) {
        this.manageRepository = manageRepository;
        this.responseHandler = responseHandler;
    }

    /**
     * getDashboardInfo:
     * Returns info about the number of plans and their respective statuses
     * for the admin dashboard page
     * @author Safwaan Taher
     * @pre school name and plan number must be passed in by the client
     * @post a ResponseEntity will be generated
     * @return a List of Dashboard data models to be serialized in JSON format with the
     * appropriate status code (200 success, 404 fail)
     */
    @GetMapping()
    public ResponseEntity<List<DashboardItem>> getDashboardInfo(@RequestParam("schoolName") String schoolName,
                                                                @RequestParam("planNum") int planNum) {
        List<DashboardItem> result = manageRepository.getDashboardInfo(schoolName, planNum);
        return responseHandler.getObjResponseEntity(result);
    }



    /**
     * searchForStudents:
     * Returns a list of ManageFilters data model objects that satisfy the given studentLastName parameter
     * @author Ashley Mead
     * @pre The request parameters schoolName and studentLastname must be passed in
     * studentLastName represents a value entered within the last name search bar
     * @post A log will be printed if the resource does not exist with a code of 404, otherwise
     *       the resource will be returned with a 200 code
     * @return a List of ManageFilters data models to be serialized in JSON format
     */
    @GetMapping("/search")
    public ResponseEntity<List<ManageFilters>> searchForStudents(@RequestParam("schoolName") String schoolName,
                                                                 @RequestParam("studentLastname") String studentLastName){
        List<ManageFilters> result = manageRepository.getStudentsFromSearch(schoolName, studentLastName);

        return responseHandler.getObjResponseEntity(result);
    }

    /**
     * runManageFilters:
     * Return a list of ManageFilters data model objects given a set of
     * filters parameters, as inputted by an admin within the Admin > Manage flow
     * @author Safwaan Taher
     * @pre The request parameters school name, studentLastName, sort by, list of plans and grades must be passed in
     * studentLastName represents a value entered within the last name search bar, whereas other parameters reference values
     * passed in their respective filters drop down menu
     * @post A log will be printed if the resource does not exist with a code of 404, otherwise
     *       the resource will be returned with a 200 code
     * @return a List of ManageFilters data models to be serialized in JSON format
     */
    @GetMapping("/filters")
    public ResponseEntity<List<ManageFilters>> runManageFilters(@RequestParam("schoolName") String schoolName,
                                                         @RequestParam("studentLastname") String studentLastName,
                                                         @RequestParam("sort") String sortBy,
                                                         @RequestParam("grades") List<Integer> grades,
                                                         @RequestParam("planStatus") List<String> planStatuses,
                                                         @RequestParam("plans") List<Integer> plans) {
        List<ManageFilters> result = manageRepository.getStudentsFromFilters(schoolName, studentLastName, sortBy,
                                                                            grades, planStatuses, plans);
        return responseHandler.getObjResponseEntity(result);
    }
}

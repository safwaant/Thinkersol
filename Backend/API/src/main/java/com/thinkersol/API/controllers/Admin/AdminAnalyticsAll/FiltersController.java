package com.thinkersol.API.controllers.Admin.AdminAnalyticsAll;

import com.thinkersol.API.controllers.ResponseHandler;
import com.thinkersol.API.models.Admin.AdminAnalyticsAll.AnalyticsFilters;
import com.thinkersol.API.repositories.Admin.AdminAnalyticsAll.FiltersRepository;
import com.thinkersol.API.repositories.GlobalReturnsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * The following is licensed under the GNU General Public License v3.0 as found in the LICENSE.md file
 * @author Safwaan Taher
 * @version 1.1
 * @since 7/7/2022
 * <p>
 * Abstract:
 * FiltersController routes each REST HTTP endpoint to its corresponding
 * verb and calls the repository to do the appropriate CRUD operation. Results are
 * returned in the form of ResponseEntity objects. This controller is in charge of all
 * requests sent to the /admin/analytics/filters endpoint
 * </p>
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/admin/analytics")
public class FiltersController {
    /*
     * Injected beans (repositories, handlers etc. . .)
     */
   private final FiltersRepository filtersRepository;
   private final ResponseHandler responseHandler;
   private final GlobalReturnsRepository globalReturnsRepository;

    /**
     * constructor: initializes all fields via dependency injection
     * @author Safwaan Taher
     * @pre injected beans are passed in by Spring
     * @post all private fields are initialized
     */
   @Autowired
   public FiltersController(FiltersRepository filtersRepository, ResponseHandler responseHandler,
                            GlobalReturnsRepository globalReturnsRepository) {
       this.filtersRepository =  filtersRepository;
       this.responseHandler = responseHandler;
       this.globalReturnsRepository = globalReturnsRepository;
   }

    /**
     *     runCareerReport: Returns results for the running an admin report given
     *     sorting conditions, soc code, and a list of plan numbers and grades
     * @author Safwaan Taher
     * @pre The request parameters school name, sort by, typeValue and list of plans and grades must be passed in
     *      typeValue represents the type of education, the soc code or the group name based on what is passed in
     *      the path variable
     * @post A log will be printed if the resource does not exist with a code of 400, otherwise
     *       the resource will be returned with a 200 code
     * @return an object AnalyticsFilters which represents the table returned, to be serialized in JSON format
     */
    @GetMapping(path = "/filters/{type}")
    public ResponseEntity<List<AnalyticsFilters>> runCareerReport(
            @PathVariable("type") String type,
            @RequestParam("schoolName") String schoolName,
            @RequestParam("sort") String sortBy,
            @RequestParam("typeValue") String typeValue,
            @RequestParam("plans") List<Integer> plans,
            @RequestParam("grades") List<Integer> grades) {
        // switch case constructor based on path variable
        List<AnalyticsFilters> result = switch (type) {
            case "career" -> filtersRepository.getCareerFilteredResults(schoolName, sortBy, typeValue, plans, grades);
            case "education" -> filtersRepository.getEducationFilteredResults(schoolName, sortBy, typeValue, plans, grades);
            case "group" -> filtersRepository.getGroupFilteredResults(schoolName, sortBy, typeValue, plans, grades);
            default -> null;
        };
        // ResponseHandler error checking and HTTP code return
        return responseHandler.getObjResponseEntity(result);
    }

    /**
     * getAllGrades: returns all grades in the school
     * @author Safwaan Taher
     * @pre no parameters or preconditions
     * @post a list of all grades is returned with a code of 400, otherwise
     *       the resource will be returned with a 200 code
     * @return a list of all grades
     */
    @GetMapping("/grades")
    public ResponseEntity<List<Integer>> getAllGrades(@RequestParam("schoolName") String schoolName) {
        String schoolId = globalReturnsRepository.getSchoolId(schoolName);
        List<Integer> result = globalReturnsRepository.getAllGradesForSchoolType(schoolId);
        return responseHandler.getObjResponseEntity(result);
    }

}

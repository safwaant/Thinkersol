package com.thinkersol.API.controllers.Student;

import com.thinkersol.API.controllers.ResponseHandler;
import com.thinkersol.API.models.Student.AddCareerToPlan;
import com.thinkersol.API.models.Student.StudentPlanInfo;
import com.thinkersol.API.repositories.Student.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This project is licensed under the GNU GPL v3 license
 * @author Ashley Mead, Safwaan Taher
 * @version 1.1
 * @since 8/8/2022
 * <p>
 * Abstract:
 *
 *  PlanController routes each REST HTTP endpoint to its corresponding
 *  verb and calls the repository to do the appropriate CRUD operation. Results are
 *  returned in the form of ResponseEntity objects. This controller will take care
 *  of all requests routed to /student
 * </p>
 */
@RestController
@RequestMapping("/student/activities")
public class PlanController {

    private final ResponseHandler responseHandler;
    private final PlanRepository planRepository;

    @Autowired
    public PlanController(ResponseHandler responseHandler, PlanRepository planRepository) {
        this.responseHandler = responseHandler;
        this.planRepository = planRepository;
    }

    /**
     * addCareerToPlan: Returns number of rows of career information added to a plan
     * @author Ashley Mead
     * @pre The student's ID, the number of the plan to add the career to, and the SOC code of the career are passed in
     * @post The career is added to the plan for the specified student
     * @return the number of rows/career information added to a database of a plan
     */
    @PutMapping("/career-search")
    public ResponseEntity<String> addCareerToPlan(@RequestBody AddCareerToPlan body) {
        System.out.println("here I am");
        int rows = planRepository.addCareerToPlan(body);
        return responseHandler.getResponseEntity("Successfully added career to plan!",
                "Invalid Request: Career already in plan or params invalid", rows);
    }

    /**
     * getStudentPlanInfo: Returns the plan statuses such as the
     * career status, education status, courses status, and resume status
     * @author Ashley Mead
     * @pre The student's ID and the number of the plan to retrieve information from are passed in
     * @post The plan statuses will be printed to the user and the method will return a 200 code
     *       otherwise the method will return a code of 400 and a printed log of the error
     * @return an object StudentPlanInfo which represents the table returned, to be serialized in JSON format
     */
    @GetMapping()
    public ResponseEntity<StudentPlanInfo> getStudentPlanInfo(@RequestParam("studentID") String studentID,
                                                              @RequestParam("planNum") int planNum) {
        StudentPlanInfo result = planRepository.getStudentPlanInfo(studentID, planNum);
        return responseHandler.getObjResponseEntity(result);
    }
}

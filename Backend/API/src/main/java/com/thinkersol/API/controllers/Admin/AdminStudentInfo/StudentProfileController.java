package com.thinkersol.API.controllers.Admin.AdminStudentInfo;

import com.thinkersol.API.controllers.ResponseHandler;
import com.thinkersol.API.models.Admin.AdminStudentInfo.PlanInfo;
import com.thinkersol.API.models.Admin.AdminStudentInfo.StudentInfo;
import com.thinkersol.API.repositories.Admin.AdminStudentInfo.StudentInfoRepository;
import com.thinkersol.API.repositories.Admin.AdminStudentInfo.StudentPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * The following is licensed under the GNU General Public License v3.0 as found in the LICENSE.md file
 * @author Safwaan Taher, Ashley Mead
 * @version 1.1
 * @since 7/10/2022
 * <p>
 * Abstract:
 * StudentProfileController handles all request actions mapped
 * to the Admin > Student Profile page. Any requests from the page
 * for CRUD operations will be routed through here
 * </p>
 */
@RestController
@RequestMapping("/admin/manage/student-profile")
public class StudentProfileController {
    /*
     * Injected beans (repositories, handlers etc. . .)
     */
    private final StudentPlanRepository studentPlanRepository;
    private final StudentInfoRepository studentInfoRepository;
    private final ResponseHandler responseHandler;

    /**
     * constructor: initializes all fields via dependency injection
     * @author Safwaan Taher
     * @pre injected beans are passed in by Spring
     * @post all private fields are initialized
     */
    @Autowired
    public StudentProfileController(StudentPlanRepository studentPlanRepository, StudentInfoRepository studentInfoRepository,
                                    ResponseHandler responseHandler) {
        this.studentPlanRepository = studentPlanRepository;
        this.studentInfoRepository = studentInfoRepository;
        this.responseHandler = responseHandler;

    }

    /**
     * getPlanInfo: returns ALL information about a specific student's plan
     * @author Safwaan Taher
     * @pre The school name, studentID of the student, and specific plan number to check in
     * @post No post conditions
     * @return A PlanInfo model is serialized in JSON format and a response with 200 or 404 is returned
     */
    @GetMapping("/plan")
    public ResponseEntity<PlanInfo> getPlanInfo(@RequestParam("schoolName") String schoolName,
                                                @RequestParam("studentID") String studentId, @RequestParam("planNum") int planNum) {
        PlanInfo result = studentPlanRepository.getPlanInfo(schoolName, studentId, planNum);
        return responseHandler.getObjResponseEntity(result);
    }

    /**
     * getStudentProfileInfo: returns info about a specific student
     * as described in API documentation
     * @author Safwaan Taher
     * @pre The students ID is passed in by the client
     * @post No post conditions
     * @return A StudentInfo model is serialized in JSON format and a response with 200 or 404 is returned
     */
    @GetMapping()
    public ResponseEntity<StudentInfo> getStudentProfileInfo(@RequestParam("studentID") String studentID) {
        StudentInfo result = studentInfoRepository.getStudentInfo(studentID);
        return responseHandler.getObjResponseEntity(result);
    }


    /**
     * deleteStudentFromGroup: removes the specified student from the specified group(s)
     * @author Ashley Mead
     * @pre The studentID of the student to be removed from the group
     * and the name(s) of the group(s) to remove the student from
     * must be passed in.
     * @post The student and their plan(s) are removed from the group(s).
     * @return The number of rows modified in the database as a result of removing the student from the group
     */
    @DeleteMapping("/group")
    public ResponseEntity<String> deleteStudentFromGroup(@RequestParam("studentID") String studentID, @RequestParam("planNum") int planNum,
                                                         @RequestParam("groupNames") List<String> groupNames) {
        int rows = studentInfoRepository.deleteStudentFromGroup(studentID, planNum, groupNames);
        return responseHandler.getResponseEntity("Successfully removed student from group(s)!",
                "Invalid Request: student or group(s) do not exist", rows);
    }
}

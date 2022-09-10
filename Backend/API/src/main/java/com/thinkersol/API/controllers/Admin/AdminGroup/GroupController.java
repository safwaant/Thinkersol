package com.thinkersol.API.controllers.Admin.AdminGroup;
import com.thinkersol.API.controllers.ResponseHandler;
import com.thinkersol.API.repositories.Admin.AdminGroup.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
/**
 * The following is licensed under the GNU General Public License v3.0 as found in the LICENSE.md file
 * @author Safwaan Taher. Mustafa Asadullah, Ashley Mead, Shakeel Khan
 * @version 1.1
 * @since 7/7/2022
 * <p>
 * Abstract:
 *  GroupController routes each REST HTTP endpoint to its corresponding
 *  verb and calls the repository to do the appropriate CRUD operation. Results are
 *  returned in the form of ResponseEntity objects. This controller will take care
 *  of all requests routed to /admin/analytics/group
 * </p>
 */
@RestController
@RequestMapping("/admin/group")
public class GroupController {
    /*
     * Injected beans (repositories, handlers etc. . .)
     */
    private final GroupRepository groupRepository;
    private final ResponseHandler responseHandler;

    /**
     * constructor: initializes all fields via dependency injection
     * @author Safwaan Taher
     * @pre injected beans are passed in by Spring
     * @post all private fields are initialized
     */
    @Autowired
    public GroupController(GroupRepository groupRepository, ResponseHandler responseHandler) {
        this.groupRepository = groupRepository;
        this.responseHandler = responseHandler;
    }

    /**
     * getAllCustomGroups: Returns results for all groups that an Admin has
     *                     created apart from the default groups given.
     * @author Safwaan Taher
     * @pre none
     * @post A list of all custom groups will be printed to the user and the method will return a 200 code
     *       otherwise the method will return a code of 400 and a printed log of the error
     * @return an object CareerGroupName which represents the table returned, to be serialized in JSON format
     */
    @GetMapping()
    public ResponseEntity<List<String>> getAllCustomGroups(@RequestParam("schoolName") String schoolName){
        List<String> result = groupRepository.getAllCustomGroupNames(schoolName);
        return responseHandler.getObjResponseEntity(result);
    }


    /**
     * createGroup: Returns the number of new groups created and added to the database
     * @author Ashley Mead
     * @pre 3 strings are given, the group ID, group name, and the school name
     * @post new row of information is added to the database
     * @return a string that either holds the groups created if the new groups are created, otherwise
     *         it returns a http code of 400 as no new groups have been created
     */
    @PostMapping()
    public ResponseEntity<String> createGroup(@RequestParam("schoolName") String schoolName, @RequestParam("groupName") String groupName) {
        UUID gen = UUID.randomUUID();
        int rows = groupRepository.createGroup(gen.toString(), groupName, schoolName);
        ResponseEntity<String> response;
        if(rows == 1) {
            response = new ResponseEntity<>("Created Group!", HttpStatus.OK);
        } else {
            response = new ResponseEntity<>("Invalid Request: Check path, query params or URI", HttpStatus.BAD_REQUEST);
        }
        return response;
    }


    /**
     * addStudentsToGroup: Returns number of rows of student information added to a group
     * @author Safwaan Taher
     * @pre a map with a key that corresponds to each value needed as presented in the API documentation
     * for add students to group endpoint
     * @post the inputted student(s) are added to a group
     * @return returns a success or fail message
     */
    @PutMapping()
    public ResponseEntity<String> addStudentsToGroup(@RequestBody List<Map<String, String>> body){
        int rows = groupRepository.addStudentsToGroup(body);
        return getUpdateOrDeleteResponse("Successfully added student(s) to group!", "Invalid Request: Student(s) already in group or params invalid", rows);
    }

    /**
     * getUpdateOrDeleteResponse:
     * Error checks and returns a corresponding response for a client given a
     * call to a resultant object(s) in the repository
     * @author Safwaan Taher
     * @pre a list of Strings is passed in from the repository
     * @post A ResponseEntity will be returned and the method will return a 200 code with a success message
     *       otherwise the method will return a code of 400 and an error message
     * @return a ResponseEntity which will be serialized in JSON format
     */
    private ResponseEntity<String> getUpdateOrDeleteResponse(String successMsg, String failMsg, int rows) {
        ResponseEntity<String> response;
        if(rows == 0) {
            response = new ResponseEntity<>(failMsg, HttpStatus.BAD_REQUEST);
        } else {
            response = new ResponseEntity<>(successMsg, HttpStatus.OK);
        }
        return response;
    }

    /**
     * deleteGroups: method searches through the database and deletes groups
     *               that the user wants to be deleted
     * @author Mustafa Asadullah
     * @pre a school name and a list of groups existing in the database are passed into the method
     * @post the groups passed into the method are deleted from the database associated with the
     *       provided school name
     * @return the number of rows deleted from the database
     */
    @DeleteMapping()
    public ResponseEntity<String> deleteGroup(@RequestParam("groupName") List<String> groupNames, @RequestParam ("schoolName") String schoolName){
        int rows = groupRepository.deleteGroups(schoolName, groupNames);
        return getUpdateOrDeleteResponse("Successfully deleted group(s)!", "Invalid Request: group(s) do not exist", rows);
    }


}

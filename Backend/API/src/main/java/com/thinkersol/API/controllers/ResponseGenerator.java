package com.thinkersol.API.controllers;

import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
/**
 * The following is licensed under the GNU General Public License v3.0 as found in the LICENSE.md file
 * @authors Safwaan Taher
 * @version 1.1
 * @since 7/26/2022
 * <p>
 * Abstract:
 * ResponseGenerator implements the ResponseHandler interface
 * and retrieves the appropriate ResponseEntity that is required
 * </p>
 */
@Component
@Scope(value = "prototype")
public class ResponseGenerator implements ResponseHandler {

    /**
     * getResponseEntity: generates a response entity for an update
     * operation called by the client
     * @author Safwaan Taher
     * @pre success and fail messages, as well as a number of rows that
     * were updated are passed in by the callee
     * @post No post conditions
     * @return a ResponseEntity with a status code of 200 or 400 and the appropriate
     * response message dependent on the number of rows updated
     */
    @Override
    public ResponseEntity<String> getResponseEntity(String successMsg, String errorMsg, int rows) {
        if(rows == 0) {
            return new ResponseEntity<>(errorMsg, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(successMsg, HttpStatus.OK);
    }

    /**
     * getResponseEntity: generates a response entity for a retrieval operation
     * called by the client
     * @author Safwaan Taher
     * @pre an object (data model) from a retrieval call (GET request)
     * by a client is passed in
     * @post No post conditions
     * @return a ResponseEntity with a status code of 200 or 400 and the appropriate
     * response message dependent on whether the object is null
     */
    @Override
    public <T> ResponseEntity<T> getObjResponseEntity(T obj) {
        if(obj == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }
}

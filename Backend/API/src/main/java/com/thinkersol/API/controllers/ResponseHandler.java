package com.thinkersol.API.controllers;

import org.springframework.http.ResponseEntity;
/**
 * The following is licensed under the GNU General Public License v3.0 as found in the LICENSE.md file
 * @authors Safwaan Taher
 * @version 1.1
 * @since 7/26/2022
 * <p>
 * Abstract:
 * ResponseHandler is an interface that handles retrieving
 * appropriate ResponseEntity objects given a parameter
 * which is the object to be sent out to the client
 * </p>
 */
public interface ResponseHandler {
    // For updating operations involving a certain number of rows updated=
     ResponseEntity<String> getResponseEntity(String successMsg, String errorMsg, int rows);
     // generic parameterized template Entity retrieval
    <T> ResponseEntity<T> getObjResponseEntity(T obj);
}

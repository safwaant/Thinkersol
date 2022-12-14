package com.thinkersol.API;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
/**
 * The following is licensed under the GNU General Public License v3.0 as found in the LICENSE.md file
 * @authors Safwaan Taher
 * @version 1.1
 * @since 8/30/2022
 * <p>
 * Abstract:
 * APIErrorController creates an error page for 
 * an invalid request, removing the "whitelabel error"
 * automatically generated by the SpringBoot application
 * </p>
 */
@Controller
public class APIErrorController implements ErrorController {
    /**
     * handleError: return an error page for a 404 or 500 level error
     * @author Safwaan Taher
     */
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                return "error-404";
            }
            else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "error-500";
            }
        }
        return "error";
    }

}

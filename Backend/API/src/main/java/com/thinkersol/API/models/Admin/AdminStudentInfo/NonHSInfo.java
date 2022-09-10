package com.thinkersol.API.models.Admin.AdminStudentInfo;

import java.util.List;
/**
 * The following is licensed under the GNU General Public License v3.0 as found in the LICENSE.md file
 * @authors Safwaan Taher
 * @version 1.1
 * @since 7/30/2022
 * <p>
 * Abstract:
 * NonHSInfo holds all information about
 * courses taken outside of the school
 * </p>
 */
public class NonHSInfo {
    /*
     * Private fields
     * all programs, institutions and courses that
     * are outside the school
     */
    private List<String> programs, institutions, courses;

    /**
     * constructor: initializes fields
     * @author Safwaan Taher
     * @post All private fields are initialized
     */
    public NonHSInfo(List<String> programs, List<String> institutions, List<String> courses) {
        this.programs = programs;
        this.institutions = institutions;
        this.courses = courses;
    }

    // default contractor
    public NonHSInfo() {
    }

    /*
     * Getters and Setters
     */

    public List<String> getPrograms() {
        return programs;
    }

    public void setPrograms(List<String> programs) {
        this.programs = programs;
    }

    public List<String> getInstitutions() {
        return institutions;
    }

    public void setInstitutions(List<String> institutions) {
        this.institutions = institutions;
    }

    public List<String> getCourses() {
        return courses;
    }

    public void setCourses(List<String> courses) {
        this.courses = courses;
    }

    // toString for debugging
    @Override
    public String toString() {
        return "NonHSInfo{" +
                "programs=" + programs +
                ", institutions=" + institutions +
                ", courses=" + courses +
                '}';
    }
}

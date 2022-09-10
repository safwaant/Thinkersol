package com.thinkersol.API.models.Admin.AdminStudentInfo;

import java.util.List;
/**
 * The following is licensed under the GNU General Public License v3.0 as found in the LICENSE.md file
 * @authors Safwaan Taher
 * @version 1.1
 * @since 8/2/2022
 * <p>
 * Abstract:
 * StudentInfo holds all information about
 * student to be displayed on the student profile page
 * </p>
 */
public class StudentInfo {
    /*
     * Private fields
     * the student's ID, first and last name
     * grade, and plans filled out
     */
    private String student_id, first_name, last_name;
    private int grade;
    private List<Integer> plans;

    /**
     * constructor: initializes fields
     * @author Safwaan Taher
     * @post All private fields are initialized
     */
    public StudentInfo(String student_id, String first_name, String last_name, int grade, List<Integer> plans) {
        this.student_id = student_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.grade = grade;
        this.plans = plans;
    }

    // default contractor
    public StudentInfo() {
    }

    /*
     * Getters and Setters
     */

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public List<Integer> getPlans() {
        return plans;
    }

    public void setPlans(List<Integer> plans) {
        this.plans = plans;
    }

    // toString for debugging
    @Override
    public String toString() {
        return "StudentInfo{" +
                "student_id='" + student_id + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", grade=" + grade +
                ", plans=" + plans +
                '}';
    }
}

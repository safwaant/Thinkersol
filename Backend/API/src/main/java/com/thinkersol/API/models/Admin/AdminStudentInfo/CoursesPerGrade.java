package com.thinkersol.API.models.Admin.AdminStudentInfo;

import java.util.List;
/**
 * The following is licensed under the GNU General Public License v3.0 as found in the LICENSE.md file
 * @authors Safwaan Taher
 * @version 1.1
 * @since 7/30/2022
 * <p>
 * Abstract:
 * CoursesPerGrade hold all courses a student has taken
 * for a particular grade
 * </p>
 */
public class CoursesPerGrade {
    /*
     * Private fields
     * grade: the grade for this model
     * course_names: a list of all courses taken in this grade
     */
    private int grade;
    private List<String> course_names;

    /**
     * constructor: initializes fields
     * @author Safwaan Taher
     * @post All private fields are initialized
     */
    public CoursesPerGrade(int grade, List<String> course_names) {
        this.grade = grade;
        this.course_names = course_names;
    }

    // default contractor
    public CoursesPerGrade() {
    }

    /*
     * Getters and Setters
     */

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public List<String> getCourse_names() {
        return course_names;
    }

    public void setCourse_names(List<String> course_names) {
        this.course_names = course_names;
    }

    // toString for debugging
    @Override
    public String toString() {
        return "CoursesPerGrade{" +
                "grade=" + grade +
                ", course_names=" + course_names +
                '}';
    }
}

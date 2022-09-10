package com.thinkersol.API.models.Admin.AdminStudentInfo;

import java.util.List;
/**
 * The following is licensed under the GNU General Public License v3.0 as found in the LICENSE.md file
 * @authors Safwaan Taher
 * @version 1.1
 * @since 8/1/2022
 * <p>
 * Abstract:
 * SubjectFourYear stores all courses and credits audit
 * for a particular subject
 * </p>
 */
public class SubjectFourYear {
    /*
     * Private fields
     * subject: the subject this model will store
     * eachGradeCourseList: courses sorted by grade for this subject
     * completed, required: completed credits for this subject
     * and the required credits for this subject
     */
    private String subject;
    private List<CoursesPerGrade> eachGradeCourseList;
    private int completed, required;

    /**
     * constructor: initializes fields
     * @author Safwaan Taher
     * @post All private fields are initialized
     */
    public SubjectFourYear(String subject, List<CoursesPerGrade> coursesPerGrade, int completed, int required) {
        this.subject = subject;
        this.eachGradeCourseList = coursesPerGrade;
        this.completed = completed;
        this.required = required;
    }

    // default contractor
    public SubjectFourYear() {}

    /*
     * Getters and Setters
     */

    public List<CoursesPerGrade> getEachGradeCourseList() {
        return eachGradeCourseList;
    }

    public void setEachGradeCourseList(List<CoursesPerGrade> eachGradeCourseList) {
        this.eachGradeCourseList = eachGradeCourseList;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getCompleted() {
        return completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }

    public int getRequired() {
        return required;
    }

    public void setRequired(int required) {
        this.required = required;
    }

    // toString for debugging
    @Override
    public String toString() {
        return "SubjectFourYear{" +
                "coursesPerGrade=" + eachGradeCourseList +
                ", subject='" + subject + '\'' +
                ", completed=" + completed +
                ", required=" + required +
                '}';
    }
}

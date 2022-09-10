package com.thinkersol.API.models.Admin.AdminManage;

/**
 * The following is licensed under the GNU General Public License v3.0 as found in the LICENSE.md file
 * @author Safwaan Taher
 * @version 1.1
 * @since 7/20/2022
 * <p>
 * Abstract:
 * ManageFilters represents a model for a table that will return
 * all student info, involving email, first and last name, grade, and
 * all statuses for career to resume activities
 * </p>
 */

public class ManageFilters {
    /**
     * Private member variables for
     * The student's:
     * unique ID
     * first and last name
     * grade
     * and statuses for career, education, courses, and resume
     */
    private String student_id, first_name, last_name;
    private int grade;
    private boolean career, education, courses, resume;

    /**
     * Constructor:
     * Takes all private properties mapped from a database table
     * and assigns them to fields within this Object
     * @pre all properties must be passed in from a database table returned via query
     * @post all fields will be assigned within this object
     */
    public ManageFilters(String student_id, String first_name, String last_name,
                         int grade, boolean career, boolean education,
                         boolean courses, boolean resume) {
        this.student_id = student_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.grade = grade;
        this.career = career;
        this.education = education;
        this.courses = courses;
        this.resume = resume;
    }

    /**
     * Default Constructor:
     * takes no parameters
     */
    public ManageFilters() {}

    /**
     * Below are basic getters and setters
     * for all fields
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

    public boolean isCareer() {
        return career;
    }

    public void setCareer(boolean career) {
        this.career = career;
    }

    public boolean isEducation() {
        return education;
    }

    public void setEducation(boolean education) {
        this.education = education;
    }

    public boolean isCourses() {
        return courses;
    }

    public void setCourses(boolean courses) {
        this.courses = courses;
    }

    public boolean isResume() {
        return resume;
    }

    public void setResume(boolean resume) {
        this.resume = resume;
    }

    /**
     * Basic toString for debugging
     */
    @Override
    public String toString() {
        return "ManageFilters{" +
                "email='" + student_id + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", grade=" + grade +
                ", career_status=" + career +
                ", education_status=" + education +
                ", courses_status=" + courses +
                ", resume_status=" + resume +
                '}';
    }
}

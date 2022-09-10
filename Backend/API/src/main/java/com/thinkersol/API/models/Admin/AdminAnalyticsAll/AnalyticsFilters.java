package com.thinkersol.API.models.Admin.AdminAnalyticsAll;
/**
 * The following is licensed under the GNU General Public License v3.0 as found in the LICENSE.md file
 * @author Safwaan Taher
 * @version 1.1
 * @since 7/7/2022
 * <p>
 * Abstract:
 * AnalyticsFiltersModel represents a model for a table that will return
 * all student info, student grade, and student career given after running a
 * filters query
 * </p>
 */
public class AnalyticsFilters {
    /**
     * Private Fields: Correspond directly to columns returned from a query
     */

    // the student's first, last name and username
    private String student_id, student_firstname, student_lastname;
    // the student's grade
    private int grade, plan;
    // the student's chosen career for the specific plan
    private String career;

    // default constructor no parameters
    public AnalyticsFilters() {}

    /**
     * <p>
     *     Constructor: Takes parameters specified by table columns returned in the
     *     Filters SQL query, namely combining the Student, Plan, and CareerGroup, and Grade tables
     * </p>
     * @param student_id The student's unique ID
     * @param student_firstname The student's first name
     * @param student_lastname The student's last name
     * @param grade The student's grade (i.e 9 or 10 or 11 . . .)
     * @param plan The plan number which corresponds to this student (between 1 and 5)
     * @param career The soc code for which the filter report was done for
     */
    public AnalyticsFilters(String student_id, String student_firstname, String student_lastname, int grade, int plan, String career) {
        this.student_id = student_id;
        this.student_firstname = student_firstname;
        this.student_lastname = student_lastname;
        this.grade = grade;
        this.plan = plan;
        this.career = career;
    }

    /**
     * <P>
     *     Below are getters and setters for all fields
     * </P>
     */
    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }
    public int getPlan() {
        return plan;
    }

    public void setPlan(int plan) {
        this.plan = plan;
    }

    public String getStudent_firstname() {
        return student_firstname;
    }

    public void setStudent_firstname(String student_firstname) {
        this.student_firstname = student_firstname;
    }

    public String getStudent_lastname() {
        return student_lastname;
    }

    public void setStudent_lastname(String student_lastname) {
        this.student_lastname = student_lastname;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    /**
     * <p>
     *     toString: Used to debug this object
     * </p>
     * @return The object printed in JSON-like format
     */
    @Override
    public String toString() {
        return "AnalyticsFiltersModel{" +
                "student_firstname='" + student_firstname + '\'' +
                ", student_lastname='" + student_lastname + '\'' +
                ", grade=" + grade +
                ", career='" + career + '\'' +
                '}';
    }
}

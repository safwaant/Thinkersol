package com.thinkersol.API.models.Admin.AdminStudentInfo;
/**
 * The following is licensed under the GNU General Public License v3.0 as found in the LICENSE.md file
 * @author Rahul Pillalamarri
 * @version 1.1
 * @since 7/29/2022
 * <p>
 * Abstract:
 *
 * ActivityStatusModel represents a model for a table that will return
 * the statuses of the student's career, education, courses, and resume
 * </p>
 */
public class ActivityStatus {

    /**
     * Private Fields: Correspond directly to columns returned from a query
     */
    private boolean career, education,
            courses, resume;

    /**
     * <p>
     * Constructor: Takes parameters specified by table columns returned in the
     *              ActivityStatus SQL query
     *
     * </p>
     * @param career The status of the student's career plan
     * @param education The status of the student's education
     * @param courses The status of the student's course registration
     * @param resume The status of the student's resume
     */
    public ActivityStatus(boolean career, boolean education,
                          boolean courses, boolean resume){
            this.career = career;
            this.education = education;
            this.courses = courses;
            this.resume = resume;
    }

    // default constructor no parameters
    public ActivityStatus(){ }

    /**
     * <P>
     *     Below are getters and setters for all fields
     * </P>
     */
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
     * <p>
     *     toString: Used to debug this object
     * </p>
     * @return The object printed in JSON-like format
     */
    @Override
    public String toString() {
        return "ActivityStatus{" +
                "career=" + career +
                ", education=" + education +
                ", courses=" + courses +
                ", resume=" + resume +
                '}';
    }
}

package com.thinkersol.API.models.Student;

/**
 * This project is licensed under the GNU GPL v3 license
 * @author Ashley Mead
 * @version 1.1
 * @since 8/9/2022
 * <p>
 * Abstract:
 *
 * StudentPlanInfo represents a model for a table that will return
 * the career status, education status, courses status, and resume status for a student's plan
 * </p>
 */
public class StudentPlanInfo {

    private boolean career_status;
    private boolean education_status;
    private boolean courses_status;
    private boolean resume_status;

    public StudentPlanInfo() {

    }

    public StudentPlanInfo(boolean career_status, boolean education_status, boolean courses_status, boolean resume_status) {
        this.career_status = career_status;
        this.education_status = education_status;
        this.courses_status = courses_status;
        this.resume_status = resume_status;
    }

    public boolean isCareer_status() {
        return career_status;
    }

    public void setCareer_status(boolean career_status) {
        this.career_status = career_status;
    }

    public boolean isEducation_status() {
        return education_status;
    }

    public void setEducation_status(boolean education_status) {
        this.education_status = education_status;
    }

    public boolean isCourses_status() {
        return courses_status;
    }

    public void setCourses_status(boolean courses_status) {
        this.courses_status = courses_status;
    }

    public boolean isResume_status() {
        return resume_status;
    }

    public void setResume_status(boolean resume_status) {
        this.resume_status = resume_status;
    }

    @Override
    public String toString() {
        return "StudentPlanInfo{" +
                "career_status=" + career_status +
                ", education_status=" + education_status +
                ", courses_status=" + courses_status +
                ", resume_status=" + resume_status +
                '}';
    }
}

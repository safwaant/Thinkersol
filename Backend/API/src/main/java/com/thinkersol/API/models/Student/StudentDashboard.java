package com.thinkersol.API.models.Student;

public class StudentDashboard {
    private boolean career_status, education_status, courses_status, resume_status;
    private String career_name;

    public StudentDashboard(boolean career_status, boolean education_status,
                            boolean courses_status, boolean resume_status, String career_name){
        career_name = career_name;
        career_status = career_status;
        education_status = education_status;
        courses_status = courses_status;
        resume_status = resume_status;
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

    public String getCareer_name() {
        return career_name;
    }

    public void setCareer_name(String career_name) {
        this.career_name = career_name;
    }

    @Override
    public String toString() {
        return "StudentDashboard{" +
                "career_status=" + career_status +
                ", education_status=" + education_status +
                ", courses_status=" + courses_status +
                ", resume_status=" + resume_status +
                ", career_name='" + career_name + '\'' +
                '}';
    }
}

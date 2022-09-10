package com.thinkersol.API.models.Admin.AdminStudentInfo;

import java.util.List;
import java.util.Map;
/**
 * The following is licensed under the GNU General Public License v3.0 as found in the LICENSE.md file
 * @authors Safwaan Taher
 * @version 1.1
 * @since 8/1/2022
 * <p>
 * Abstract:
 * PlanInfo stores ALL information
 * about a particular plan within the Admin > Student Profile
 * page that is viewed by an Admin
 * </p>
 */
public class PlanInfo {
    /*
     * Private fields
     * plan_num: the particular plan number
     * career: the plan's chosen career
     * plan_status, task_statuses: the status of the plan
     * and all tasks within the plan
     * extras_and_exp: list of all extracurriculars and experiences
     * associated with this plan and NOT on a resume
     * four_year_plan: all classes sorted by grade and subject for all grades
     * that the student has filled in info for
     * institutions: all institutions that are associated with this plan
     * non_hs_info: any information about classes, courses, institutions that
     * are associated with this plan
     */

    private int plan_num;
    private String career, plan_status;
    private List<String> groups;

    private ActivityStatus task_statuses;

    private Map<String,List<PlanExtrasAndExp>> extras_and_exp;

    private List<SubjectFourYear> four_year_plan;

    private List<String> institutions;

    private NonHSInfo non_hs_info;

    /**
     * constructor: initializes fields
     * @author Safwaan Taher
     * @post All private fields are initialized
     */
    public PlanInfo(int plan_num, String career, String plan_status) {
        this.career = career;
        this.plan_status = plan_status;
    }

    // default contractor
    public PlanInfo() {
    }

    /*
     * Getters and Setters
     */

    public int getPlan_num() {
        return plan_num;
    }

    public void setPlan_num(int plan_num) {
        this.plan_num = plan_num;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public String getPlan_status() {
        return plan_status;
    }

    public void setPlan_status(String plan_status) {
        this.plan_status = plan_status;
    }

    public List<String> getGroups() {
        return groups;
    }

    public void setGroups(List<String> groups) {
        this.groups = groups;
    }

    public ActivityStatus getTask_statuses() {
        return task_statuses;
    }

    public void setTask_statuses(ActivityStatus task_statuses) {
        this.task_statuses = task_statuses;
    }

    public Map<String, List<PlanExtrasAndExp>> getExtras_and_exp() {
        return extras_and_exp;
    }

    public void setExtras_and_exp(Map<String, List<PlanExtrasAndExp>> extras_and_exp) {
        this.extras_and_exp = extras_and_exp;
    }

    public List<SubjectFourYear> getFour_year_plan() {
        return four_year_plan;
    }

    public void setFour_year_plan(List<SubjectFourYear> four_year_plan) {
        this.four_year_plan = four_year_plan;
    }

    public List<String> getInstitutions() {
        return institutions;
    }

    public void setInstitutions(List<String> institutions) {
        this.institutions = institutions;
    }

    public NonHSInfo getNon_hs_info() {
        return non_hs_info;
    }

    public void setNon_hs_info(NonHSInfo non_hs_info) {
        this.non_hs_info = non_hs_info;
    }

    // toString for debugging
    @Override
    public String toString() {
        return "PlanInfo{" +
                "plan_num=" + plan_num +
                ", career='" + career + '\'' +
                ", status='" + plan_status + '\'' +
                ", groups=" + groups +
                ", statuses=" + task_statuses +
                ", extras_and_exp=" + extras_and_exp +
                ", four_year_plan=" + four_year_plan +
                ", institutions=" + institutions +
                ", non_hs_info=" + non_hs_info +
                '}';
    }
}

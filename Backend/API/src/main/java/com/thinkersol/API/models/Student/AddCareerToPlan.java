package com.thinkersol.API.models.Student;

/**
 * This project is licensed under the GNU GPL v3 license
 * @author Ashley Mead
 * @version 1.1
 * @since 8/8/2022
 * <p>
 * Abstract:
 *
 * AddCareerToPlan represents a model for the body passed into the Add Career To Plan endpoint as input
 * </p>
 */
public class AddCareerToPlan {

    private String student_id;
    private int plan_num;
    private int soc;

    public AddCareerToPlan() {

    }

    public AddCareerToPlan(String student_id, int plan_num, int soc) {
        this.student_id = student_id;
        this.plan_num = plan_num;
        this.soc = soc;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public int getPlan_num() {
        return plan_num;
    }

    public void setPlan_num(int plan_num) {
        this.plan_num = plan_num;
    }

    public int getSoc() {
        return soc;
    }

    public void setSoc(int soc) {
        this.soc = soc;
    }

    @Override
    public String toString() {
        return "AddCareerToPlan{" +
                "student_id='" + student_id + '\'' +
                ", plan_num=" + plan_num +
                ", soc=" + soc +
                '}';
    }
}

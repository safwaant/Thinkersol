package com.thinkersol.API.models.Student;

public class CareerSearch {
    private String occ_code, career_name;
    private int soc_code;

    public CareerSearch(String occ_code, String career_name, int soc_code){
        soc_code = soc_code;
        occ_code = occ_code;
        career_name = career_name;
    }

    public int getSoc_code() {
        return soc_code;
    }

    public void setSoc_code(int soc_code) {
        this.soc_code = soc_code;
    }

    public String getCareer_name() {
        return career_name;
    }

    public void setCareer_name(String career_name) {
        this.career_name = career_name;
    }

    public String getOcc_code() {
        return occ_code;
    }

    public void setOcc_code(String occ_code) {
        this.occ_code = occ_code;
    }

    @Override
    public String toString() {
        return "CareerSearch{" +
                "occ_code='" + occ_code + '\'' +
                ", soc_code='" + soc_code + '\'' +
                ", career_name=" + career_name +
                '}';
    }
}

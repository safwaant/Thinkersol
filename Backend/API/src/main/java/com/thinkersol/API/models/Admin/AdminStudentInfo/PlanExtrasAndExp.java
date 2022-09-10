package com.thinkersol.API.models.Admin.AdminStudentInfo;

/**
 * The following is licensed under the GNU General Public License v3.0 as found in the LICENSE.md file
 * @author Rahul Pillalamarri
 * @version 1.1
 * @since 7/29/2022
 * <p>
 * Abstract:
 *
 * ExtracurricularsExperiencesModel represents a model for a table that will return
 * the type, name, and description of the student's extracurriculars and work experiences
 * </p>
 */
public class PlanExtrasAndExp {
    /**
     * Private Fields: Correspond directly to columns returned from a query
     */
    private String type_name, name, description;

    /**
     * <p>
     *     Constructor: Takes parameters specified by table columns returned in the
     *                  ExtracurricularsExperiences SQL query
     * </p>
     * @param type_name the type of extracurricular and experience
     * @param name the name of the extracurricular and experience
     * @param description the description of the extracurricular and experience
     */
    public PlanExtrasAndExp(String type_name, String name, String description){
        this.type_name = type_name;
        this.name = name;
        this.description = description;
    }

    // default constructor no parameters
    public PlanExtrasAndExp(){}

    /**
     * <P>
     *     Below are getters and setters for all fields
     * </P>
     */
    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * <p>
     *     toString: Used to debug this object
     * </p>
     * @return The object printed in JSON-like format
     */
    @Override
    public String toString() {
        return "Extracurriculars{" +
                "type_name='" + type_name + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

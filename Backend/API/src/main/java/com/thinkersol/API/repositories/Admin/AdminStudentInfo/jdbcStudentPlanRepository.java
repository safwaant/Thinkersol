package com.thinkersol.API.repositories.Admin.AdminStudentInfo;

import com.thinkersol.API.models.Admin.AdminStudentInfo.*;
import com.thinkersol.API.repositories.GlobalReturnsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
/**
 * @Copyright The following is licensed under the GNU General Public License v3.0 as found in the LICENSE.md file
 * @authors Safwaan Taher, Ashley Mead, Rahul Pillalamarri
 * @version 1.1
 * @since 8/2/2022
 * <p>
 * Abstract:
 * jdbcStudentPlanRepository holds all actions involving a
 * particular students plan, including retrieving all info
 * about a plan
 * </p>
 */
@Repository
public class jdbcStudentPlanRepository implements StudentPlanRepository {
    // injected beans
    private final JdbcTemplate jdbcTemplate;
    private final GlobalReturnsRepository globalReturnsRepository;
    // private field to store the school ID
    private String schoolId;

    /**
     * constructor: initializes all dependencies required
     * @author Safwaan Taher
     * @pre the required parameters are injected in
     * @post all private fields are initialized
     */
    @Autowired
    public jdbcStudentPlanRepository(JdbcTemplate jdbcTemplate, GlobalReturnsRepository globalReturnsRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.globalReturnsRepository = globalReturnsRepository;
    }


    /**
     * getPlanInfo:
     * returns all information about a particular plan,
     * including institutions connected, non-high school paths
     * and four year audit
     * @author Safwaan Taher
     * @pre all parameters listed must be passed in from the controller
     * @post a data model is returned with all corresponding info for the plan
     * @return the PlanInfo data model
     */
    @Override
    public PlanInfo getPlanInfo(String schoolName, String studentId, int planNum) {
        // get and error check school ID
        schoolId = globalReturnsRepository.getSchoolId(schoolName);
        if(schoolId == null) {
            return null;
        }

        // SQL code to get initial data about plan
        String getPlanBaseInfoSQL =
                """
                SELECT plan_num, career_name AS career, PlanStatus.name AS plan_status
                FROM Plan
                JOIN PlanStatus ON (PlanStatus.plan_status_id = Plan.plan_status_id)
                JOIN Career ON (Career.soc_code = Plan.soc_code)
                WHERE Plan.student_id = ? AND Plan.plan_num = ?;
                """;
        // get and error handle plan initial info
        PlanInfo planInfo;
        try {
            planInfo = jdbcTemplate.queryForObject(getPlanBaseInfoSQL, BeanPropertyRowMapper.newInstance(PlanInfo.class), studentId, planNum);
        } catch(Exception exe) {
            return null;
        }

        // SQL code to retrieve groups that a plan is a part of
        String getPlanGroupsSQL =
                """
                SELECT group_name
                FROM CustomGroup
                JOIN PlanToGroup ON (CustomGroup.group_id = PlanToGroup.group_id)
                WHERE PlanToGroup.plan_id = (SELECT plan_id FROM Plan WHERE Plan.student_id = ? AND Plan.plan_num = ?);
                """;
        // get and error handle group names
        List<String> groupNames = jdbcTemplate.queryForList(getPlanGroupsSQL, String.class, studentId, planNum);
        if(groupNames == null) {
            groupNames = List.of();
        }
        planInfo.setGroups(groupNames);

        /*
         * Method calls for retrieving all other data within a plan
         * names correspond to data that is returned
         */

        ActivityStatus activityStatuses = getActivityStatuses(studentId, planNum);
        planInfo.setTask_statuses(activityStatuses);

        Map<String, List<PlanExtrasAndExp>> extrasAndExp = getExperienceAndExtracurriculars(studentId, planNum);
        planInfo.setExtras_and_exp(extrasAndExp);

        List<SubjectFourYear> fourYearPlan = getAllPlannedCourses(studentId, planNum);
        planInfo.setFour_year_plan(fourYearPlan);

        List<String> institutions = getInstitutionsFromPlan(studentId, planNum);
        planInfo.setInstitutions(institutions);

        NonHSInfo nonHSInfo = getNonSchoolInfo(studentId, planNum);
        planInfo.setNon_hs_info(nonHSInfo);

        return planInfo;
    }

    /**
     * getAllGradesForStudent:
     * returns all grades that the student
     * has inputted any data for
     * @author Safwaan Taher
     * @pre all parameters must be passed in from the controller
     * @post a list of grades is returned
     * @return list of integer grades is returned to the callee
     */
    private List<Integer> getAllGradesForStudent(String schoolId, String studentId, int planNum) {
        String getAllGradesInSchoolSQL = """
                SELECT DISTINCT Grade.grade
                FROM Grade
                JOIN StudentCourses ON (Grade.grade = StudentCourses.grade)
                JOIN CoursesToPlan ON (CoursesToPlan.student_course_id = StudentCourses.student_course_id)
                JOIN Plan ON (Plan.plan_id = CoursesToPlan.plan_id AND Plan.plan_num = ?)
                JOIN Student ON (Student.student_id = Plan.student_id AND
                    (Student.student_id = ? AND Student.cur_school_id = ?))
                WHERE CoursesToPlan.plan_id =
                    (SELECT Plan.plan_id FROM Plan WHERE Plan.plan_num = ? AND Plan.student_id = ?);
                """;
        Object[] args = new Object[]{planNum, studentId, schoolId, planNum, studentId};
        List<Integer> grades = jdbcTemplate.queryForList(getAllGradesInSchoolSQL, Integer.class, args);
        return grades.size() == 0 ? null : grades;
    }

    /**
     * getAllGradesForStudent:
     * returns the completed credits for a particular subject
     * and student
     * @author Safwaan Taher
     * @pre the subject, studentID, and plan number are required
     * @post no post conditions
     * @return the credits count for a subject
     */
    private int getCompletedSubjectCredits(String subject, String studentID, int planNum) {
        String sql =
                """
                SELECT count(credits) AS completed
                FROM SchoolCourses
                JOIN StudentCourses ON (SchoolCourses.course_id = StudentCourses.course_id)
                JOIN CoursesToPlan ON (StudentCourses.student_course_id = CoursesToPlan.student_course_id)
                JOIN Plan ON (Plan.plan_id = CoursesToPlan.plan_id)
                WHERE SchoolCourses.subject = ? AND (Plan.student_id = ? AND Plan.plan_num = ?);
                """;
        int completedCreds = 0;
        try {
            completedCreds = jdbcTemplate.queryForObject(sql, Integer.class, subject, studentID, planNum);
        } catch (EmptyResultDataAccessException ignored) {}
        return completedCreds;
    }

    /**
     * getTotalSubjectCreditsReq:
     * returns the required credits for a particular subject
     * @author Safwaan Taher
     * @pre the school ID and subject are required
     * @post no post conditions
     * @return the credits required for a subject
     */
    private int getTotalSubjectCreditsReq(String schoolId, String subject) {
        String sql =
                """
                SELECT count(subjectrequirements.credits) AS required
                FROM SubjectRequirements
                JOIN SchoolCourses ON (SubjectRequirements.subject = SchoolCourses.subject)
                WHERE SchoolCourses.school_id = ? AND SubjectRequirements.subject = ?;
                """;
        return jdbcTemplate.queryForObject(sql, Integer.class, schoolId, subject);
    }

    /**
     * getAllCoursesPerGrade:
     * returns the completed courses for a specific student and grade
     * @author Safwaan Taher
     * @pre the SQL code, grades, plan number, student ID and subject will be passed in
     * by a callee method
     * @post no post conditions
     * @return a list of all CoursesPerGrade model will be returned
     */
    private List<CoursesPerGrade> getAllCoursesPerGrade(String sql, List<Integer> grades,
                                                        int planNum, String studentID, String subject) {
        List<CoursesPerGrade> allCoursesPerGrade = new LinkedList<>();
        for(int i : grades) {
            List<String> courses;
            if(subject.equals("NonSchool")) {
                courses = jdbcTemplate.queryForList(sql, String.class, i, planNum, schoolId, studentID);
            } else {
                courses = jdbcTemplate.queryForList(sql, String.class, i, planNum, schoolId, studentID, subject);
            }
            allCoursesPerGrade.add(new CoursesPerGrade(i, courses));
        }
        return allCoursesPerGrade;
    }

    /**
     * getAllCoursesPerGrade:
     * returns the completed courses for a specific student and grade
     * @author Safwaan Taher
     * @pre the student ID and plan number must be passed in by the callee
     * @post no post conditions
     * @return a List of SubjectFourYear models, which hold data about each subject and grade
     */
    private List<SubjectFourYear> getAllPlannedCourses(String studentID, int planNum) {

        List<SubjectFourYear> result = new LinkedList<>();

        // get all the grades that this student has filled in info for
        List<Integer> grades = getAllGradesForStudent(schoolId, studentID, planNum);
        if(grades == null) {
            return null;
        }

        // get all subjects in this particular school
        List<String> subjects = globalReturnsRepository.getAllSubjectsInSchool(schoolId);
        if(subjects == null) {
            return null;
        }

        /*
         * SQL code to get courses in a school and outside a school
         */
        String getSchoolCoursesSQL =
                """
                SELECT course_name
                FROM SchoolCourses
                JOIN StudentCourses ON (SchoolCourses.course_id = StudentCourses.course_id AND StudentCourses.grade = ?)
                JOIN CoursesToPlan ON (StudentCourses.student_course_id = CoursesToPlan.student_course_id)
                JOIN Plan ON (Plan.plan_id = CoursesToPlan.plan_id AND Plan.plan_num = ?)
                JOIN Student ON (Student.student_id = Plan.student_id AND (Student.cur_school_id = ? AND Student.student_id = ?))
                WHERE SchoolCourses.subject = ?;
                """;
        String getAllNonSchoolCoursesSQL =
                """
                SELECT course_name
                FROM NonSchoolCourses
                JOIN StudentCourses ON (NonSchoolCourses.non_school_course_id = StudentCourses.non_school_course_id AND StudentCourses.grade = ?)
                JOIN CoursesToPlan ON (StudentCourses.student_course_id = CoursesToPlan.student_course_id)
                JOIN Plan ON (Plan.plan_id = CoursesToPlan.plan_id AND Plan.plan_num = ?)
                JOIN Student ON (Student.student_id = Plan.student_id AND (Student.cur_school_id = ? AND Student.student_id = ?));
                """;

        // for each subject retrieve all courses and credits info
        for (String subject : subjects) {
            List<CoursesPerGrade> allCoursesAcrossAllGrades =
                    getAllCoursesPerGrade(getSchoolCoursesSQL, grades, planNum, studentID, subject);
            int completedCredits = getCompletedSubjectCredits(subject, studentID, planNum);
            int requiredCredits = getTotalSubjectCreditsReq(schoolId, subject);
            result.add(new SubjectFourYear(subject, allCoursesAcrossAllGrades, completedCredits, requiredCredits));
        }

        // for any courses the student has taken outside the school retrieve info
        List<CoursesPerGrade> allNonSchoolCourses =
                getAllCoursesPerGrade(getAllNonSchoolCoursesSQL, grades, planNum, studentID, "NonSchool");

        if(allNonSchoolCourses.size() > 0){
            result.add(new SubjectFourYear("Non School Courses", allNonSchoolCourses, 0, 0));
        }
        // error handle
        return result.size() == 0 ? null : result;
    }



    /**
     * getActivityStatuses: retrieves the statuses of student tasks for a specific
     *                      school, student, and plan number given all of those params
     * @author Rahul Pillalamarri
     * @pre a specific school name, student ID, and plan number must be passed in
     * @post an ActivityStatus data model is found that holds the statuses of student activities
     * specifically the career, education, courses, and resume activities
     * @return returns an ActivityStatus data model or null if not found
     */
    private ActivityStatus getActivityStatuses(String studentID, int planNum) {

        String sql = """
         SELECT career_status AS career, education_status AS education, courses_status AS courses, resume_status AS resume
         FROM Plan
         WHERE Plan.student_id = ? AND Plan.plan_num = ?;
        """;

        ActivityStatus result;
        try {
            result = jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(ActivityStatus.class), studentID, planNum);
        } catch(EmptyResultDataAccessException exe) {
            result = null;
        }
        return result;
    }

    /**
     *
     * getInstitutionsFromPlan:
     * Retrieves all institutions the student with the given studentID has selected
     * as part of the plan identified by planNum.
     *
     * @author Ashley Mead
     * @pre the school name, student ID, and plan number must be passed in
     * @post return condition only
     * @return null if no institutions are found, otherwise a List of Institution names models are returned
     */
    private List<String> getInstitutionsFromPlan(String studentID, int planNum) {

        // SQL code to retrieve colleges in a particular student's plan
        String sql = """
               SELECT PlanToCollege.college_name
                FROM PlanToCollege
                JOIN Plan ON (Plan.plan_id = PlanToCollege.plan_id)
                JOIN Student ON (Student.student_id = Plan.student_id AND (Plan.student_id = ? AND Plan.plan_num = ?))
            """;


        List<String> result = jdbcTemplate.queryForList(sql, String.class,
                studentID, planNum);

        return result.size() == 0 ? null : result;
    }

    /**
     * getExperiencesAndExtracurriculars: retrieves the type, name, and description of extracurriculars and experiences
     *                                    associated with the student and plan number
     * @author Rahul Pillalamarri
     * @pre a specific school name, student ID, and plan number must be passed in
     * @post a list that holds ExtracurricularsExperiences data models that hold the name, type, and description of each
     * extracurricular and experience associated with a student is loaded into a map that holds a list of the extracurriculars
     * a student participated in with the key 'Extracurricular' used to store the list and the same thing is done with the experiences
     * associated with a student with the key being 'Experience'
     * @return a Map that holds the ExtracurricularsExperiences data model with the differentiating factor of extracurriculars
     * and experiences being the key that holds the list of the two
     */
    private Map<String,List<PlanExtrasAndExp>> getExperienceAndExtracurriculars(String studentID, int planNum) {

        Map<String, List<PlanExtrasAndExp>> result = new HashMap<>();

        String getExtrasSQL = """
                 SELECT ET.name AS type_name, Extra.name AS name, Extra.description AS description
                 FROM Extracurricular Extra
                 JOIN ExtraType ET ON (ET.extra_type_id = Extra.type_id)
                 JOIN Plan ON (Plan.plan_id = Extra.plan_id AND Plan.plan_num = ?)
                 JOIN Student ON (Student.student_id = Plan.student_id AND Student.student_id = ?);
                """;

        List<PlanExtrasAndExp> extracurriculars = jdbcTemplate.query(getExtrasSQL, BeanPropertyRowMapper.newInstance(PlanExtrasAndExp.class),
                planNum, studentID);

        if(extracurriculars.size() > 0) {
            result.put("Extracurriculars", extracurriculars);
        }

        String getExperiencesSQL = """
                SELECT WT.name AS type_name, Exp.name AS name, Exp.description AS description\s
                FROM Experience Exp
                JOIN WorkType WT ON (WT.work_id = Exp.type_id)
                JOIN Plan ON (Plan.plan_id = Exp.plan_id AND Plan.plan_num = ?)
                JOIN Student ON (Student.student_id = Plan.student_id AND Student.student_id = ? );
                """;

        List<PlanExtrasAndExp> experiences = jdbcTemplate.query(getExperiencesSQL, BeanPropertyRowMapper.newInstance(PlanExtrasAndExp.class),
                planNum, studentID);
        if(experiences.size() > 0) {
            result.put("Experiences", experiences);
        }

        return result.isEmpty() ? null : result;
    }

    /**
     * getNonSchoolInfo:
     * returns the completed courses outside the school
     * @author Safwaan Taher
     * @pre the student ID and plan number must be passed in by the callee
     * @post no post conditions
     * @return a List of NonHSInfo models which hold data about completed courses
     */
    public NonHSInfo getNonSchoolInfo(String studentID, int planNum) {
        String baseSQL =
                """
                FROM NonSchoolCourses
                JOIN StudentCourses ON (NonSchoolCourses.non_school_course_id = StudentCourses.non_school_course_id)
                JOIN CoursesToPlan ON (StudentCourses.student_course_id = CoursesToPlan.student_course_id)
                JOIN Plan ON (Plan.plan_id = CoursesToPlan.plan_id)
                WHERE Plan.student_id = ? AND Plan.plan_num = ?;
                """;
        String getProgramSQL = "SELECT DISTINCT program_type_name AS programs " + baseSQL,
                getInstSQL = "SELECT DISTINCT inst_name AS institutions " + baseSQL,
                getCoursesSQL = "SELECT DISTINCT course_name AS courses " + baseSQL;

        List<String> programs = jdbcTemplate.queryForList(getProgramSQL, String.class, studentID, planNum);
        List<String> institutions = jdbcTemplate.queryForList(getInstSQL, String.class, studentID, planNum);
        List<String> courses = jdbcTemplate.queryForList(getCoursesSQL, String.class, studentID, planNum);
        if(programs.isEmpty() && institutions.isEmpty() && courses.isEmpty()) {
            return null;
        }
        return new NonHSInfo(programs, institutions, courses);
    }

}

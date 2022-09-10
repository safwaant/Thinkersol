--
-- The following is licensed under the GNU General Public License v3.0 as found in the LICENSE.md file
-- Authors (Tables, Keys and Constraints): Safwaan Taher, Shakeel Khan, Mustafa Asadullah 
-- Authors (Procedures, Functions, Sequences): Safwaan Taher
-- For more info see DB-Info.md
-- 


--
-- Name: VerificationCodes; Type: TABLE; Schema: public; Owner: postgres
--
CREATE TABLE VerificationCodes (
    ver_ID integer,
    school_id varchar,
    user_type varchar,
    code varchar(17) UNIQUE
);


--
-- Name: SchoolType; Type: TABLE; Schema: public; Owner: postgres
--
CREATE TABLE SchoolType (
    school_type varchar(4),
    school_type_name varchar
);

ALTER TABLE School OWNER TO postgres;

--
-- Name: School; Type: TABLE; Schema: public; Owner: postgres
--
CREATE TABLE School (
    school_id varchar,
    school_type varchar(4),
    school_name varchar NOT NULL
);

ALTER TABLE School OWNER TO postgres;

--
-- Name: Admin; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE Admin (
    admin_username varchar NOT NULL,
    first_name varchar,
    last_name varchar NOT NULL,
    school_id varchar NOT NULL,
    is_super_admin boolean NOT NULL
);

ALTER TABLE Admin OWNER TO postgres;

--
-- Name: Counselor; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE Counselor (
  counselor_username varchar,
  first_name varchar,
  last_name varchar NOT NULL,
  title varchar NOT NULL,
  school_id varchar NOT NULL
);


ALTER TABLE Counselor OWNER TO postgres;


--
-- Name: Parent; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE Parent (
    parent_username varchar NOT NULL,
    first_name varchar(25),
    last_name varchar(25) NOT NULL,
    school_id varchar NOT NULL
);

ALTER TABLE Parent OWNER TO postgres;


--
-- Name: ParentToStudent; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE ParentToStudent (
    parent_username varchar NOT NULL,
    student_id varchar NOT NULL
);

ALTER TABLE ParentToStudent OWNER TO postgres;

--
-- Name: Student; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE Student (
    student_id varchar NOT NULL,
    email varchar(254),
    grade integer NOT NULL,
    first_name varchar(25),
    last_name varchar(25) NOT NULL,
    birth_date DATE NOT NULL,
    num_of_plans integer NOT NULL, 
    cur_school_id varchar NOT NULL,
    future_school_id varchar
);

ALTER TABLE Student OWNER TO postgres;

--
-- Name: Resume; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE Resume (
    resume_id INTEGER NOT NULL,
    student_id varchar NOT NULL,
    skills_1 varchar NOT NULL,
    skills_2 varchar NOT NULL,
    skills_3 varchar NOT NULL
);

ALTER TABLE Resume OWNER TO postgres;

--
-- Name: Experience; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE Experience (
    experience_id integer NOT NULL,
    resume_id integer,
    plan_id integer,
    name varchar NOT NULL,
    description TEXT NOT NULL,
    date_from DATE NOT NULL,
    date_to DATE,
    type_id varchar NOT NULL
);

ALTER TABLE Experience OWNER TO postgres;

--
-- Name: WorkType; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE WorkType (
    work_id varchar NOT NULL,
    name varchar(15) NOT NULL
);

ALTER TABLE WorkType OWNER TO postgres;

--
-- Name: Extracurricular; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE Extracurricular (
    extra_id integer NOT NULL,
    resume_id integer,
    plan_id integer,
    name varchar NOT NULL,
    description text NOT NULL,
    date_from DATE NOT NULL,
    date_to DATE,
    type_id varchar NOT NULL
);

ALTER TABLE Extracurricular OWNER TO postgres;

--
-- Name: ExtraType; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE ExtraType (
    extra_type_id varchar NOT NULL,
    name varchar(15) NOT NULL
);

ALTER TABLE ExtraType OWNER TO postgres;

--
-- Name: StudentToCustomGroup; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE StudentToCustomGroup (
    group_id varchar NOT NULL,
    student_id varchar NOT NULL
);

ALTER TABLE StudentToCustomGroup OWNER TO postgres;

--
-- Name: CustomGroup; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE CustomGroup (
    group_id varchar NOT NULL,
    school_id varchar NOT NULL,
    group_name varchar NOT NULL
);

ALTER TABLE CustomGroup OWNER TO postgres;


--
-- Name: DefaultGroup; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE DefaultGroup (
    default_group_id varchar NOT NULL,
    default_group_name varchar NOT NULL
);

ALTER TABLE DefaultGroup OWNER TO postgres;

--
-- Name: StudentToDefaultGroup; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE StudentToDefaultGroup (
    default_group_id varchar NOT NULL,
    student_id varchar NOT NULL
);

ALTER TABLE StudentToCustomGroup OWNER TO postgres;

--
-- Name: Plan; Type: TABLE; Schema: public; Owner: postgres
--


CREATE TABLE Plan (
    plan_id integer NOT NULL,
    student_id varchar NOT NULL,
    soc_code integer,
    plan_num integer NOT NULL,
    num_courses integer,
    career_status boolean NOT NULL,
    education_status boolean NOT NULL,
    courses_status boolean NOT NULL,
    resume_status boolean NOT NULL,
    plan_status_id varchar(2) NOT NULL,
    resume_id integer,
    register_courses boolean,
    UNIQUE(resume_id)
);

ALTER TABLE Plan OWNER TO postgres;

--
-- Name: PlanStatus; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE PlanStatus (
    plan_status_id varchar(2) NOT NULL,
    name varchar(16) NOT NULL
);

ALTER TABLE PlanStatus OWNER TO postgres;

--
-- Name: CoursesToPlan; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE CoursesToPlan (
    plan_id integer NOT NULL,
    student_course_id varchar NOT NULL
);

ALTER TABLE CoursesToPlan OWNER TO postgres;

--
-- Name: PlanToCollege; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE PlanToCollege (
    college_name varchar NOT NULL,
    plan_id integer NOT NULL
);

ALTER TABLE PlanToCollege OWNER TO postgres;


--
-- Name: StudentCourses; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE StudentCourses (
    student_course_id varchar NOT NULL,
    grade integer NOT NULL,
    course_id varchar,
    non_school_course_id varchar,
    is_complete boolean
);

ALTER TABLE StudentCourses OWNER TO postgres;

--
-- Name: Grade; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE Grade (
    grade integer NOT NULL,
    name varchar(10) NOT NULL
);

ALTER TABLE Grade OWNER TO postgres;

--
-- Name: SchoolCourses; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE SchoolCourses (
    course_id varchar NOT NULL,
    course_name varchar NOT NULL,
    credits numeric NOT NULL,
    subject varchar NOT NULL,
    school_id varchar NOT NULL,
    min_grade_offered integer,
    max_grade_offered integer
);

ALTER TABLE SchoolCourses OWNER TO postgres;

--
-- Name: NonSchoolCourses; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE NonSchoolCourses (
    non_school_course_id varchar NOT NULL,
    course_name varchar NOT NULL,
    inst_name varchar,
    program_type_name varchar,
    UNIQUE(course_name)
);

ALTER TABLE SchoolCourses OWNER TO postgres;

--
-- Name: ProgramType; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE ProgramType (
    program_name varchar NOT NULL
);

ALTER TABLE ProgramType OWNER TO postgres;


--
-- Name: SubjectRequirements; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE SubjectRequirements (
    subject varchar NOT NULL,
    credits integer NOT NULL
);

ALTER TABLE SubjectRequirements OWNER TO postgres;

--
-- Name: CreditsToGrade; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE CreditsToGrade (
    credits_to_grade_id integer,
    subject varchar NOT NULL,
    grade integer,
    grade_credits integer NOT NULL
);

ALTER TABLE CreditsToGrade OWNER TO postgres;


--
-- Name: College; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE College(
    college_name varchar,
    city varchar (50) NOT NULL,
    state_id integer NOT NULL,
    INSTURL varchar,
    NPCURL varchar,
    ADM_RATE numeric,
    ACTCMMID integer,
    SAT_AVG integer,
    PCIP01 numeric,
    PCIP03 numeric,
    PCIP04 numeric,
    PCIP05 numeric,
    PCIP09 numeric,
    PCIP10 numeric,
    PCIP11 numeric,
    PCIP12 numeric,
    PCIP13 numeric,
    PCIP14 numeric,
    PCIP15 numeric,
    PCIP16 numeric,
    PCIP19 numeric,
    PCIP22 numeric,
    PCIP23 numeric,
    PCIP24 numeric,
    PCIP25 numeric,
    PCIP26 numeric,
    PCIP27 numeric,
    PCIP29 numeric,
    PCIP30 numeric,
    PCIP31 numeric,
    PCIP38 numeric,
    PCIP39 numeric,
    PCIP40 numeric,
    PCIP41 numeric,
    PCIP42 numeric,
    PCIP43 numeric,
    PCIP44 numeric,
    PCIP45 numeric,
    PCIP46 numeric,
    PCIP47 numeric,
    PCIP48 numeric,
    PCIP49 numeric,
    PCIP50 numeric,
    PCIP51 numeric,
    PCIP52 numeric,
    PCIP54 numeric,
    UGDS integer,
    NPT4_PUB integer,
    NPT4_PRIV integer,
    NPT41_PUB integer,
    NPT42_PUB integer,
    NPT43_PUB integer,
    NPT44_PUB integer,
    NPT45_PUB integer,
    NPT41_PRIV integer,
    NPT42_PRIV integer,
    NPT43_PRIV integer,
    NPT44_PRIV integer,
    NPT45_PRIV integer,
    COSTT4_A integer,
    COSTT4_P integer,
    TUITIONFEE_IN integer,
    TUITIONFEE_OUT integer,
    LO_INC_DEBT_MDN numeric,
    MD_INC_DEBT_MDN numeric,
    HI_INC_DEBT_MDN numeric,
    ADMCON7 integer,
    MD_EARN_WNE_MALE0_P6 integer,
    MD_EARN_WNE_MALE1_P6 integer,
    MD_EARN_WNE_MALE0_P10 integer,
    MD_EARN_WNE_MALE1_P10 integer,
    UGDS_WHITE numeric,
    UGDS_BLACK numeric,
    UGDS_HISP numeric,
    UGDS_ASIAN numeric,
    UGDS_AIAN numeric,
    UGDS_NHPI numeric,
    UGDS_2MORE numeric
  );

ALTER TABLE College OWNER TO postgres;

--
-- Name: State; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE Career (
    soc_code integer,
    occ_code varchar(4),
    pub_date DATE,
    career_name varchar,
    career_description text,
    percent_employment numeric (4,1),
    salary integer,
    education_type_id varchar(2),
    program_type varchar,
    UNIQUE(career_name)
);

ALTER TABLE Career OWNER TO postgres;

--
-- Name: State; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE EducationType (
  ed_type_id varchar(2),
  type_name varchar
);

ALTER TABLE EducationType OWNER TO postgres;

--
-- Name: State; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE State (
    state_id integer NOT NULL,
    name varchar NOT NULL
);

ALTER TABLE State OWNER TO postgres;

--
-- Name: PlanToGroup; Type: TABLE; Schema: public; Owner: postgres
--
CREATE TABLE PlanToGroup(
    plan_id integer,
    group_id varchar
);

ALTER TABLE PlanToGroup OWNER TO postgres;




--
-- The following intializes all PRIMARY KEYs based on latest schema
--



--
-- Name: vercode_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE VerificationCodes
    ADD CONSTRAINT vercode_pkey PRIMARY KEY(ver_ID);


--
-- Name: schooltype_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE SchoolType 
    ADD CONSTRAINT schooltype_pkey PRIMARY KEY(school_type);

--
-- Name: school_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE School 
    ADD CONSTRAINT school_pkey PRIMARY KEY(school_id);

--
-- Name: parent_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE Parent 
    ADD CONSTRAINT parent_pkey PRIMARY KEY(parent_username);

--
-- Name: student_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE Student 
    ADD CONSTRAINT student_pkey PRIMARY KEY(student_id);

--
-- Name: pToS_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

-- TODO: Double check if this is all that's needed.
ALTER TABLE ParentToStudent 
    ADD CONSTRAINT pToS_pkey PRIMARY KEY(parent_username, student_id);

--
-- Name: counselor_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE Counselor
    ADD CONSTRAINT counselor_pkey PRIMARY KEY(counselor_username);

--
-- Name: plan_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE Plan 
    ADD CONSTRAINT plan_pkey PRIMARY KEY(plan_id);

--
-- Name: resume_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE Resume 
    ADD CONSTRAINT resume_pkey PRIMARY KEY(resume_id); 

-- 
--  Name: college_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--
ALTER TABLE College
    ADD CONSTRAINT college_pkey PRIMARY KEY(college_name);

--
-- Name: state_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE State
    ADD CONSTRAINT state_pkey PRIMARY KEY(state_id);

-- 
--  Name: career_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--
ALTER TABLE Career
    ADD CONSTRAINT career_pkey PRIMARY KEY(soc_code);  

--
-- Name: educationtype_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE EducationType
    ADD CONSTRAINT educationtype_pkey PRIMARY KEY(ed_type_id);

--
-- Name: subjectrequirments_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE SubjectRequirements
    ADD CONSTRAINT subjectrequirments_pkey PRIMARY KEY(subject);

--
-- Name: grade_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE Grade
    ADD CONSTRAINT grade_pkey PRIMARY KEY(grade);

--
-- Name: worktype_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE WorkType
    ADD CONSTRAINT worktype_pkey PRIMARY KEY(work_id); 

--
-- Name: extratype_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ExtraType
    ADD CONSTRAINT extratype_pkey PRIMARY KEY(extra_type_id);

--
-- Name: status_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE PlanStatus 
    ADD CONSTRAINT status_pkey PRIMARY KEY(plan_status_id);

--
-- Name: schoolcourses_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE SchoolCourses
    ADD CONSTRAINT schoolcourses_pkey PRIMARY KEY(course_id);

--
-- Name: studentcourses_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE StudentCourses
    ADD CONSTRAINT studentcourses_pkey PRIMARY KEY(student_course_id);


--
-- Name: cTop_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE CoursesToPlan
    ADD CONSTRAINT cTop_pkey PRIMARY KEY(plan_id, student_course_id); 


--
-- Name: pToC_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE PlanToCollege
    ADD CONSTRAINT pToC_pkey PRIMARY KEY(college_name, plan_id); 

--
-- Name: experience_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE Experience
    ADD CONSTRAINT experience_pkey PRIMARY KEY(experience_id);

--
-- Name: admin_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE Admin
    ADD CONSTRAINT admin_pkey PRIMARY KEY(admin_username);

--
-- Name: group_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE CustomGroup
    ADD CONSTRAINT group_pkey PRIMARY KEY(group_id);

--
-- Name: defaultgroup_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--
ALTER TABLE DefaultGroup
    ADD CONSTRAINT defaultgroup_pkey PRIMARY KEY(default_group_id);

--
-- Name: sToG_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--
ALTER TABLE StudentToCustomGroup
    ADD CONSTRAINT sToG_pkey PRIMARY KEY(student_id, group_id);

--
-- Name: sToD_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--
ALTER TABLE StudentToDefaultGroup
    ADD CONSTRAINT sToD_pkey PRIMARY KEY(student_id, default_group_id);

--
-- Name: extracurricular_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE Extracurricular
    ADD CONSTRAINT extracurricular_pkey PRIMARY KEY(extra_id);

--
-- Name: plantogroup_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE PlanToGroup
    ADD CONSTRAINT plantogroup_pkey PRIMARY KEY(plan_id, group_id);

--
-- Name: cToGrade_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE CreditsToGrade
    ADD CONSTRAINT cToGrade_pkey PRIMARY KEY(credits_to_grade_id);


--
-- Name: nonschool_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE NonSchoolCourses
    ADD CONSTRAINT nonschool_pkey PRIMARY KEY(non_school_course_id);

--
-- Name: programtype_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ProgramType
    ADD CONSTRAINT programtype_pkey PRIMARY KEY(program_name);





--
-- The following initializes all FOREIGN KEYS based on latest schema
-- 




--
-- Name: vercode_fkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE VerificationCodes
     ADD CONSTRAINT vercode_fkey FOREIGN KEY(school_id) REFERENCES School(school_id); 



--
-- Name: school_fkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE School
     ADD CONSTRAINT school_fkey FOREIGN KEY(school_type) REFERENCES SchoolType(school_type); 



--
-- Name: admin_fkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE Admin
     ADD CONSTRAINT admin_fkey FOREIGN KEY(school_id) REFERENCES School(school_id); 

--
-- Name: student_fkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE Student
     ADD CONSTRAINT student_fkey FOREIGN KEY(cur_school_id) REFERENCES School(school_id); 

--
-- Name: student_fkey2; Type: CONSTRAINT; Schema: public; Owner: postgres
--
ALTER TABLE Student
    ADD CONSTRAINT student_fkey2 FOREIGN KEY(future_school_id) REFERENCES School(school_id);

--
-- Name: student_fkey3; Type: CONSTRAINT; Schema: public; Owner: postgres
--
ALTER TABLE Student
    ADD CONSTRAINT student_fkey3 FOREIGN KEY(grade) REFERENCES Grade(grade);

--
-- Name: counselor_fkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE Counselor
     ADD CONSTRAINT counselor_fkey FOREIGN KEY(school_id) REFERENCES School(school_id); 

--
-- Name: parent_fkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE Parent
     ADD CONSTRAINT plan_fkey FOREIGN KEY(school_id) REFERENCES School(school_id); 

--
-- Name: customgroup_fkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE CustomGroup
     ADD CONSTRAINT customgroup_fkey FOREIGN KEY(school_id) REFERENCES School(school_id);

--
-- Name: plan_fkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE Plan
     ADD CONSTRAINT plan_fkey FOREIGN KEY(student_id) REFERENCES Student(student_id);


--
-- Name: plan_fkey2; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE Plan
    ADD CONSTRAINT plan_fkey2 FOREIGN KEY(soc_code) REFERENCES Career(soc_code);

--
-- Name: plan_fkey3; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE Plan
    ADD CONSTRAINT plan_fkey3 FOREIGN KEY(plan_status_id) REFERENCES PlanStatus(plan_status_id);

--
-- Name: plan_fkey5; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE Plan
    ADD CONSTRAINT plan_fkey5 FOREIGN KEY(resume_id) REFERENCES Resume(resume_id);

--
-- Name: resume_fkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE Resume 
    ADD CONSTRAINT resume_fkey FOREIGN KEY (student_id) REFERENCES Student(student_id); 


--
-- Name: college_fkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE College
    ADD CONSTRAINT college_fkey FOREIGN KEY(state_id) REFERENCES State(state_id);

--
-- Name: pToC_fkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE PlanToCollege
    ADD CONSTRAINT pToC_fkey FOREIGN KEY(college_name) REFERENCES College(college_name);

--
-- Name: pToC_fkey_2; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE PlanToCollege
    ADD CONSTRAINT pToC_fkey_2 FOREIGN KEY(plan_id) REFERENCES Plan(plan_id);


--
-- Name: career_fkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE Career
    ADD CONSTRAINT career_fkey FOREIGN KEY(education_type_id) REFERENCES EducationType(ed_type_id);


--
-- Name: schoolcourses_fkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE SchoolCourses
    ADD CONSTRAINT schoolcourses_fkey FOREIGN KEY(subject) REFERENCES SubjectRequirements(subject);

--
-- Name: schoolcourses_fkey_2; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE SchoolCourses
    ADD CONSTRAINT schoolcourses_fkey_2 FOREIGN KEY(school_id) REFERENCES School(school_id);

--
-- Name: schoolcourses_fkey_3; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE SchoolCourses
    ADD CONSTRAINT schoolcourses_fkey_3 FOREIGN KEY(min_grade_offered) REFERENCES Grade(grade);

--
-- Name: schoolcourses_fkey_4; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE SchoolCourses
    ADD CONSTRAINT schoolcourses_fkey_4 FOREIGN KEY(max_grade_offered) REFERENCES Grade(grade);

--
-- Name: studentcourses_fkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE StudentCourses
    ADD CONSTRAINT studentcourses_fkey FOREIGN KEY(course_id) REFERENCES SchoolCourses(course_id);

--
-- Name: studentcourses_fkey_2; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE StudentCourses
    ADD CONSTRAINT studentcourses_fkey_2 FOREIGN KEY(grade) REFERENCES Grade(grade);

--
-- Name: studentcourses_fkey_3; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE StudentCourses
    ADD CONSTRAINT studentcourses_fkey_3 FOREIGN KEY(non_school_course_id) REFERENCES NonSchoolCourses(non_school_course_id);

--
-- Name: experience_fkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE Experience
    ADD CONSTRAINT experience_fkey FOREIGN KEY(resume_id) REFERENCES Resume(resume_id);

--
-- Name: experience_fkey_2; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE Experience 
    ADD CONSTRAINT experience_fkey_2 FOREIGN KEY(type_id) REFERENCES WorkType(work_id);


--
-- Name: experience_fkey_3; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE Experience 
    ADD CONSTRAINT experience_fkey_3 FOREIGN KEY(plan_id) REFERENCES Plan(plan_id);

--
-- Name: extracurricular_fkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE Extracurricular
    ADD CONSTRAINT extracurricular_fkey FOREIGN KEY(resume_id) REFERENCES Resume(resume_id);

--
-- Name: extracurricular_fkey_2; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE Extracurricular
    ADD CONSTRAINT extracurricular_fkey_2 FOREIGN KEY(type_id) REFERENCES ExtraType(extra_type_id);

--
-- Name: extracurricular_fkey_3; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE Extracurricular
    ADD CONSTRAINT extracurricular_fkey_3 FOREIGN KEY(plan_id) REFERENCES Plan(plan_id);

--
-- Name: coursestoplan_fkey1; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE CoursesToPlan
    ADD CONSTRAINT coursestoplan_fkey1 FOREIGN KEY(plan_id) REFERENCES Plan(plan_id);

--
-- Name: coursestoplan_fkey2; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE CoursesToPlan
    ADD CONSTRAINT coursestoplan_fkey2 FOREIGN KEY(student_course_id) REFERENCES StudentCourses(student_course_id);

--
-- Name: nonschool_fkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE NonSchoolCourses
    ADD CONSTRAINT nonschool_fkey FOREIGN KEY(program_type_name) REFERENCES ProgramType(program_name);


--
-- Name: studenttocustomgroup_fkey1; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE StudentToCustomGroup
    ADD CONSTRAINT studenttocustomgroup_fkey1 FOREIGN KEY(student_id) REFERENCES Student(student_id);


--
-- Name: studenttocustomgroup_fkey2; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE StudentToCustomGroup
    ADD CONSTRAINT studenttocustomgroup_fkey2 FOREIGN KEY(group_id) REFERENCES CustomGroup(group_id);

--
-- Name: studenttodefaultgroup_fkey1; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE StudentToDefaultGroup
    ADD CONSTRAINT studenttodefaultgroup_fkey1 FOREIGN KEY(student_id) REFERENCES Student(student_id);


--
-- Name: studenttocustomgroup_fkey2; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE StudentToDefaultGroup
    ADD CONSTRAINT studenttodefaultgroup_fkey2 FOREIGN KEY(default_group_id) REFERENCES DefaultGroup(default_group_id);

--
-- Name: studenttocustomgroup_fkey1; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE PlanToGroup
    ADD CONSTRAINT plantogroup_fkey1 FOREIGN KEY(group_id) REFERENCES CustomGroup(group_id);

--
-- Name: studenttocustomgroup_fkey2; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE PlanToGroup
    ADD CONSTRAINT plantogroup_fkey2 FOREIGN KEY(plan_id) REFERENCES Plan(plan_id);

--
-- Name: creditstograde_fkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE CreditsToGrade
    ADD CONSTRAINT creditstograde_fkey FOREIGN KEY(subject) REFERENCES SubjectRequirements(subject);

--
-- Name: creditstograde_fkey_2; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE CreditsToGrade
    ADD CONSTRAINT creditstograde_fkey_2 FOREIGN KEY(grade) REFERENCES Grade(grade);



--
-- Initializing any other constraints (Limits on plans etc . . .)
--

-- Setting a cap on the number of plans that can be created
ALTER TABLE Plan
    ADD CONSTRAINT plan_num_limit CHECK (Plan.plan_num <= 5);

-- Making sure a course is added with a proper ID within or outside the school
ALTER TABLE StudentCourses
    ADD CONSTRAINT courses_requirement CHECK (course_id IS NOT NULL OR non_school_course_id IS NOT NULL);

-- Making sure an experience is associated with a resume or plan
ALTER TABLE Experience
    ADD CONSTRAINT experience_requirment CHECK (resume_id IS NOT NULL OR plan_id IS NOT NULL);


-- Making sure a extracurricular is associated with a resume or plan
ALTER TABLE Extracurricular
    ADD CONSTRAINT extra_requirement CHECK (resume_id IS NOT NULL OR plan_id IS NOT NULL);




--
-- The following initializes all SEQUENCES
-- by Safwaan Taher
--



--
-- Name: resume_id_seq; Owner: resume
--

CREATE SEQUENCE public.vercode_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
ALTER SEQUENCE public.vercode_id_seq OWNED BY public.verificationcodes.ver_ID;


--
-- Name: resume_id_seq; Owner: resume
--

CREATE SEQUENCE public.resume_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
ALTER SEQUENCE public.resume_id_seq OWNED BY public.resume.resume_id;

--
-- Name: experience_id_seq; Owner: experience
--

CREATE SEQUENCE public.experience_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
ALTER SEQUENCE public.experience_id_seq OWNED BY public.experience.experience_id;

--
-- Name: extracurricular_id_seq; Owner: extracurricular
--

CREATE SEQUENCE public.extracurricular_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
ALTER SEQUENCE public.extracurricular_id_seq OWNED BY public.extracurricular.extra_id;

--
-- Name: plan_id_seq; Owner: plan
--

CREATE SEQUENCE public.plan_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
ALTER SEQUENCE public.plan_id_seq OWNED BY public.plan.plan_id;


--
-- Name: creditstograde_id_seq; Owner: plan
--

CREATE SEQUENCE public.creditstograde_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
ALTER SEQUENCE public.creditstograde_id_seq OWNED BY public.creditstograde.credits_to_grade_id;

-- 
-- END DB CREATION FILE
--

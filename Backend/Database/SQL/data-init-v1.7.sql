--
-- The following is licensed under the GNU General Public License v3.0 as found in the LICENSE.md file
-- Authors: Safwaan Taher, Shakeel Khan 
-- For more info see DB-Info.md
-- 

--
-- The following copies initial data from the CSV files
-- NOTE: This file may not be updated to reflect the latest schema
-- found under CSV-Data folder
-- REPLACE ALL FILEPATHS WITH YOUR OWN
--

--
-- School table
--
COPY School
FROM '<your-filepath>\CSV-Data\School-Data-7-3-2022.csv'
DELIMITER ',' CSV;


--
-- Admin table
--
COPY Admin
FROM '<your-filepath>\CSV-Data\Admin-Data-7-3-2022.csv'
DELIMITER ',' CSV;


--
-- Parent table
--
COPY Parent
FROM '<your-filepath>\CSV-Data\Parent-Data-7-3-2022.csv'
DELIMITER ',' CSV;

--
-- Counselor table
--
COPY Counselor
FROM '<your-filepath>\CSV-Data\Counselor-Data-7-3-2022.csv'
DELIMITER ',' CSV;

--
-- Grade table 
--
COPY Grade
FROM '<your-filepath>\CSV-Data\Grade-Data-7-3-2022.csv'
DELIMITER ',' CSV;

--
-- Student table 
--
COPY Student
FROM '<your-filepath>\CSV-Data\Student-Data-7-1-2022.csv'
DELIMITER ',' CSV;

-- 
-- ParentToStudent table 
--
COPY ParentToStudent
FROM '<your-filepath>\CSV-Data\ParentToStudent-Data-7-3-2022.csv'
DELIMITER ',' CSV;


--
-- Group table 
--
COPY CareerGroup
FROM '<your-filepath>\CSV-Data\Group-Data-7-1-2022.csv'
DELIMITER ',' CSV;

--
-- StudentToGroup table 
--
COPY StudentToGroup
FROM '<your-filepath>\CSV-Data\StudentToGroup-Data-7-1-2022.csv'
DELIMITER ',' CSV;

--
-- EducationType table
--
COPY EducationType
FROM '<your-filepath>\CSV-Data\EducationType-Data-7-1-2022.csv'
DELIMITER ',' CSV;

--
-- Career table 
--
COPY Career
FROM '<your-filepath>\CSV-Data\Career-Data-7-2-2022.csv'
DELIMITER ',' CSV;

--
-- State table
--
COPY State
FROM '<your-filepath>\CSV-Data\State-Data-7-1-2022.csv'
DELIMITER ',' CSV;

--
-- College table
--
COPY College
FROM '<your-filepath>\CSV-Data\Revised-College-Data-7-3-2022.csv'
DELIMITER ',' CSV HEADER;

--
-- SubjectRequirements table 
--
COPY SubjectRequirements
FROM '<your-filepath>\CSV-Data\SubjectRequirements-Data-7-2-2022.csv'
DELIMITER ',' CSV;

--
-- SchoolCourses table
COPY SchoolCourses
FROM '<your-filepath>\CSV-Data\School-Courses-7-2-2022.csv'
DELIMITER ',' CSV;

--
-- StudentCourses table
--
COPY StudentCourses
FROM '<your-filepath>\CSV-Data\StudentCourses-Data-7-2-2022.csv'
DELIMITER ',' CSV;

--
-- Status table
--
COPY Status
FROM '<your-filepath>\CSV-Data\Status-Data-7-1-2022.csv'
DELIMITER ',' CSV;

--
-- Resume table
--
COPY Resume
FROM '<your-filepath>\CSV-Data\Resume-Data-7-3-2022.csv'
DELIMITER ',' CSV;

--
-- WorkType table
--
COPY WorkType
FROM '<your-filepath>\CSV-Data\WorkType-Data-7-3-2022.csv'
DELIMITER ',' CSV;

--
-- ExtraType table
--
COPY ExtraType
FROM '<your-filepath>\CSV-Data\ExtraType-Data-7-3-2022.csv'
DELIMITER ',' CSV;

--
-- Extracurricular table
--
COPY Extracurricular
FROM '<your-filepath>\CSV-Data\Extracurricular-Data-7-3-2022.csv'
DELIMITER ',' CSV;

--
-- Experience table
--
COPY Experience
FROM '<your-filepath>\CSV-Data\Experience-Data-7-3-2022.csv'
DELIMITER ',' CSV;


--
-- Plan table
--
COPY Plan
FROM '<your-filepath>\CSV-Data\Plan-Data-7-1-2022.csv'
DELIMITER ',' CSV;

--
-- CoursesToPlan table
--
COPY CoursesToPlan
FROM '<your-filepath>\CSV-Data\CoursesToPlan-Data-7-2-2022.csv'
DELIMITER ',' CSV;
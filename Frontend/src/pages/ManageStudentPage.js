// React component imports.
import { React, useState } from 'react';
import Dropdown from '../components/dropdowns/Dropdown';

import Stack from '@mui/material/Stack';
import Box from '@mui/material/Box';
import ActivityStatusCard from '../components/cards/ActivityStatusCard';
import StudentGroupsCard from '../components/cards/StudentGroupsCard';
import NextStepsCard from '../components/cards/NextStepsCard';
import EducationPlanCard from '../components/cards/EducationPlanCard';
import NonHighSchoolPathCard from '../components/cards/NonHighSchoolPathCard';
import CoursePlanCard from '../components/cards/CoursePlanCard';

const PLAN_FILTER_TITLE = "Plan";
const PLAN_FILTER_WIDTH = '4rem';
// Static data for dropdowns.
const PLAN_LIST = ["1", "2", "3", "4", "5"];

export default function ManageStudentPage(){
    const [planNum, setPlanNum] = useState(PLAN_LIST[0]);

    return(
        <div>
            {/* header */}

    
            <h1>[Student Name] Profile</h1>
            <hr />



                                     

           
            <h4>Student ID: [000000]</h4>
            <h4>Grade: []</h4>



        <span className = "filter-card-span">
                            {/* Plan filter. */}
                            <Dropdown renderCondition = {true}
                                title = {PLAN_FILTER_TITLE}
                                customWidth = {PLAN_FILTER_WIDTH}
                                list = {PLAN_LIST}
                                updateVal = {setPlanNum}
                                val = {planNum}
                            />
        </span>

        
        <Stack
        direction="row"
        justifyContent="flex-start"
        alignItems="center"
        spacing={2}
        >
            <h2>[career]</h2>
            <h2> Plan Status: </h2>
            <h2> Status </h2>
        </Stack>
        <hr></hr>

        <Stack direction="row" spacing={2}> 
            <ActivityStatusCard/>
            <StudentGroupsCard/>
        </Stack>

        <h2> Next Steps </h2>
        <NextStepsCard/>
        <h2> Course Plan </h2>
        <CoursePlanCard/>
        <NonHighSchoolPathCard/>
        <h2> Education Plan</h2>
        <EducationPlanCard/>


        </div>
    );
}
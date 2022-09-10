// React component imports.
import { React, useState } from 'react';

// Custom component imports.
import Card from './Card.js';

import Stack from '@mui/material/Stack';



// function createTableDate(activity, status){
//     return {activity, status};
// }

// const rows = 
// [
//     createTableDate("Career", "Incomplete"),
//     createTableDate("Education", "Incomplete"),
//     createTableDate("Courses", "Incomplete"),
//     createTableDate("Resume", "Incomplete"),
// ];

const EXPERIENCE_TYPE = "[experience type]";
const EXPERIENCE_NAME = "[name]";
const EXPERIENCE_DESCRIPTION = "[description]";

const EXTRACURRICULAR_TYPE = "[extracurricular type]";
const EXTRACURRICULAR_NAME = "[name]";
const EXTRACURRICULAR_DESCRIPTION = "[description]";

export default function NextStepsCard(){
    return(
        <Card>
            <Card.Header>
                <h2 className = "filter-card-header">
                    What can help me prepare for for my career plan?
                </h2>
            </Card.Header>
            <Card.Content>
                <Stack>
                    {/* TODO this may need to be changed into a table to account for multiple types 
                    of experience or extracurriculars */}
                    
                    <h3>Extracurriculars</h3>
                    <Stack
                    direction="row"
                    justifyContent="flex-start"
                    alignItems="center"
                    spacing={2}
                    >
                        {EXPERIENCE_TYPE}
                        {EXPERIENCE_NAME}
                        {EXPERIENCE_DESCRIPTION}
                    </Stack>

                    <h3>Experience</h3>
                    <Stack
                    direction="row"
                    justifyContent="flex-start"
                    alignItems="center"
                    spacing={2}
                    >
                        {EXTRACURRICULAR_TYPE}
                        {EXTRACURRICULAR_NAME}
                        {EXTRACURRICULAR_DESCRIPTION}

                    </Stack>
                </Stack>
            </Card.Content>
        </Card>
    );
}
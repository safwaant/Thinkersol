import { React, useState } from 'react';

// Custom component imports.
import Card from './Card.js';

import Stack from '@mui/material/Stack';


export default function NonHighSchoolPathCard(){
    
    const courses = ["[course name]", "[course name]", "[course name]", "[course name]"];
    return(
        <Card>
            <Card.Header>
                <h2 className = "filter-card-header">
                    Non-high school path
                </h2>
            </Card.Header>
            <Card.Content>
                <Stack>
                    <h3>Program</h3>
                    <Stack
                    direction="row"
                    justifyContent="flex-start"
                    alignItems="left"
                    spacing={2}
                    >
                        [program type]
                    </Stack>

                    <h3>Institution</h3>
                    <Stack
                    direction="row"
                    justifyContent="flex-start"
                    alignItems="left"
                    >
                        [Institution name]
                    </Stack>

                    <h3>Courses</h3>
                    {courses.map((courseName) => (
                        <p>{courseName}</p>
                    ))}
                    
                </Stack>
            </Card.Content>
        </Card>
    );
}
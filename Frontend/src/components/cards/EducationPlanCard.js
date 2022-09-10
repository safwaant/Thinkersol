// React component imports.
import { React, useState } from 'react';

// Custom component imports.
import Card from './Card.js';

import Stack from '@mui/material/Stack';


export default function EducationPlanCard(){
    return(
        <Card>
            <Card.Header>
                <h2 className = "filter-card-header">
                    Institutions
                </h2>
            </Card.Header>
            <Card.Content>
                <Stack>
                    <h3>[Institution name]</h3>
                    Program: [program]
                    
                </Stack>
            </Card.Content>
        </Card>
    );
}
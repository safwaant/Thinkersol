// React component imports.
import { React, useState } from 'react';

// Custom component imports.
import Card from './Card.js';
import AddToGroupBtn from '../../components/buttons/AddToGroupBtn';


import Stack from '@mui/material/Stack';

export default function StudentGroupsCard(){

    const [selectedStudents, updateSelectedStudents] = useState([]);

    return(
        <Card>
            <Card.Header>
                    <Stack
                direction="row"
                justifyContent="flex-start"
                alignItems="center"
                spacing={5}
                >
                    <h2 className = "filter-card-header">
                        Groups
                    </h2>
                    <AddToGroupBtn
                                    selectedStudents = {selectedStudents}
                                    updateSelectedStudents = {updateSelectedStudents}
                                    // onClick = {() => dispatch(updateStudents(selectedStudents))}
                                />
                </Stack>
            </Card.Header>
            <Card.Content>
            <Stack> 
                {/* TODO replace with checkbox items */}
                <h4>Group 1</h4>
                <h4>Group 2</h4>
                <h4>Group 3</h4>
            </Stack>
            </Card.Content>
        </Card>
    );
}
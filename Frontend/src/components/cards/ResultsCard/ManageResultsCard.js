// React component imports.
import { React, useState } from 'react';

import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableRow from '@mui/material/TableRow';

import Card from '../Card';


function createData(id, name, grade, career, education, courses, resume) {
    return { id, name, grade, career, education, courses, resume };
  }

const rows = [
    createData( '0000001', 'Jane Doe', '10', 'Complete', 'Complete', 'Complete', 'Complete' ),
    createData( '0000002', 'John Doe', '9', 'Complete', 'Complete', 'Incomplete', 'Complete' ),
    createData( '0000003', 'James Bond', '10', 'Complete', 'Incomplete', 'Incomplete', 'Complete' ),
  ];

const LABELS = ['ID', 'Name', 'Grade', 'Career', 'Education', 'Courses', 'Resume'];
const LABEL_SPACING = ['17vw', '17vw', '6vw', '6vw', '6vw', '6vw'];

var labelsAndSpaces = LABELS.map(function(ithLabel, i) {
    return [ithLabel, LABEL_SPACING[i]];
});

const CARD_COLOR = "#5E5E5E";

const CARD_TITLE_STYLING = {
    marginTop: '1rem',
    marginBottom: '0.5rem'
};

// Otherwise, prepare the data to be used in making the header for the card.
var labelsAndSpaces = LABELS.map(function(ithLabel, i) {
    return [ithLabel, LABEL_SPACING[i]];
});

function ManageResultsCard() {

    // Check the render flag to see whether we should render this component at all.
    //if (props.renderCondition === false) return;

    return(
        <div>
            <h2 style = {CARD_TITLE_STYLING}>Results</h2>
            <Card
                color = {CARD_COLOR}
            >
                <Card.Header>
                    <div className = "span-container">
                        {labelsAndSpaces.map((labelAndSpace) => (
                            <span
                                className = "label-span"
                                style = {{width: labelAndSpace[1]}}
                                key = {labelAndSpace[0]}
                            >
                                <strong>{labelAndSpace[0]}</strong>
                            </span>
                        ))}
                    </div>
                </Card.Header>
                <Card.Content>
                    <Table sx={{ maxWidth: '100%' }} aria-label="simple table">
                        <TableBody>
                        {rows.map((row) => (
                            <TableRow
                            key={row.name}
                            sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                            >
                            <TableCell component="th" scope="row"> {row.id} </TableCell>
                            <TableCell align="left">{row.name}</TableCell>
                            <TableCell align="left">{row.grade}</TableCell>
                            <TableCell align="right">{row.career}</TableCell>
                            <TableCell align="right">{row.education}</TableCell>
                            <TableCell align="right">{row.courses}</TableCell>
                            <TableCell align="right">{row.resume}</TableCell>

                            </TableRow>
                        ))}
                        </TableBody>
                    </Table>
                </Card.Content>
            </Card>
        </div>
    );
}


export default ManageResultsCard;
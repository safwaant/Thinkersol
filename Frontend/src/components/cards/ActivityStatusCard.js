// React component imports.
import { React, useState } from 'react';

// Custom component imports.
import Card from './Card.js';


// mui table
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';


function createTableDate(activity, status){
    return {activity, status};
}

const rows = 
[
    createTableDate("Career", "Incomplete"),
    createTableDate("Education", "Incomplete"),
    createTableDate("Courses", "Incomplete"),
    createTableDate("Resume", "Incomplete"),
];

export default function ActivityStatusCard() {
    return(
        <Table sx={{ maxWidth: '25em' }} aria-label="simple table">
                    <TableHead>
                    <TableRow>
                        <TableCell>Activites</TableCell>
                        <TableCell align="right">Status</TableCell>

                    </TableRow>
                    </TableHead>
                    <TableBody>
                    {rows.map((row) => (
                        <TableRow
                        key={row.activity}
                        sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                        >
                        <TableCell component="th" scope="row">
                            {row.activity}
                        </TableCell>
                        <TableCell align="right">{row.status}</TableCell>

                        </TableRow>
                    ))}
                    </TableBody>
            </Table>



        // <Card>
        //     <Card.Header>

        //     </Card.Header>
        //     <Card.Content>
                

        //     </Card.Content>
            
        // </Card>
    );

}

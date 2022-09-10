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


function createTableData(category, courseName, credits, required){
    return {category, courseName, credits, required};
}

const rows = 
[
    createTableData("English", "[course name]", "[]", "[]"),
    createTableData("Mathematics", "[course name]", "[]", "[]"),
    createTableData("Science", "[course name]", "[]", "[]"),
    createTableData("Social Studies", "[course name]", "[]", "[]"),
    createTableData("Health and Fitness", "[course name]", "[]", "[]"),
    createTableData("Arts", "[course name]", "[]", "[]"),
    createTableData("World Language", "[course name]", "[]", "[]"),
    createTableData("CTE", "[course name]", "[]", "[]"),
    createTableData("Electives", "[course name]", "[]", "[]")
];

export default function CoursePlanCard() {
    return(
        
        <Card>
            <Card.Header>
                <Table>
                    <TableRow className = "filter-card-header">
                        <TableCell><h2>Grade 9</h2></TableCell>
                        <TableCell><h2>Grade 10</h2></TableCell>
                        <TableCell><h2>Grade 11</h2></TableCell>
                        <TableCell><h2>Grade 12</h2></TableCell>
                    </TableRow>
                </Table>
                <Table>
                    <TableRow className = "filter-card-header">
                        <TableCell><h2>Category</h2></TableCell>
                        <TableCell><h2>Grade 9</h2></TableCell>
                        <TableCell><h2>Credits</h2></TableCell>
                        <TableCell><h2>Required</h2></TableCell>
                    </TableRow>
                </Table>
            </Card.Header>
            <Card.Content>
            <Table sx={{ maxWidth: '25em' }} aria-label="simple table">
                    <TableHead>
                    
                    </TableHead>
                    <TableBody>
                    {rows.map((row) => (
                        <TableRow
                        key={row.category}
                        sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                        >
                        <TableCell component="th" scope="row">
                            {row.category}
                        </TableCell>
                        <TableCell>{row.courseName}</TableCell>
                        <TableCell>{row.credits}</TableCell>
                        <TableCell>{row.required}</TableCell>
                        </TableRow>
                    ))}
                    </TableBody>
            </Table>
            </Card.Content>
        </Card>
    );

}

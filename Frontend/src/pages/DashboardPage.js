/**
 * This project is licensed under the GNU GPL v3 license
 * @author Ashley Mead, Rosie Shen, Shakeel Khan
 * @version 1.1
 * @since 7/6/2022
 * 
 * Abstract:
 *      - Consistent page container with two navigation bars
 */

// React component imports.
import { React, useState } from 'react';
import Card from '../components/cards/Card';
import Dropdown from '../components/dropdowns/Dropdown';

//import * as React from 'react';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';

// redux import to update topnav
import { useDispatch } from 'react-redux';
import { setToDashboardPage } from '../NavSlice'

//img imports
import GreenRect from "../img/green-rect.svg";
import YellowRect from "../img/yellow-rect.svg";
import RedRect from "../img/red-rect.svg";
//  import PageLayout from './PageLayout';

function createData(color, status, numOfPlan, total) {
  return { color, status, numOfPlan, total };
}

// plan list
const PLAN_LIST = ["1", "2", "3", "4", "5"]; // static plan list


// variables to be passed in from DB, 
// TODO GET request with axios 
const studentPop = 100;
const incompletePlanNum = 50;
const pendingPlanNum = 10;
const completePlanNum = 35;

const rows = [
    createData(RedRect, 'Incomplete', incompletePlanNum, studentPop),
    createData(YellowRect, 'Pending Review', pendingPlanNum, studentPop),
    createData(GreenRect, 'Complete', completePlanNum, studentPop),
  ];


function DashboardPage() {
    const [planNum, setPlanNum] = useState(PLAN_LIST[0]); // state management for filter

    const dispatch = useDispatch();
    dispatch(setToDashboardPage()); 

    return(
        <div>
           
            {/* Header */}
            <div>
                <h1>Dashboard</h1>
            </div>
            <hr />

            {/* Select Plan Filter */}
            <Dropdown 
                renderCondition = {true}
                title = {'Select Plan'}
                customWidth = {'10em'}
                list = {PLAN_LIST}
                updateVal = {setPlanNum}
                val = {planNum}
            />
       
            {/* Plan Status Card */}
            <Card>
                <Card.Header>
                    <h2 className = "filter-card-header">
                        Plan Status
                    </h2>
                </Card.Header>
                <Card.Content>
                    {/* <TableContainer component={Paper}> */}
                        <Table sx={{ maxWidth: '75%' }} aria-label="simple table">
                            <TableBody>
                            {rows.map((row) => (
                                <TableRow
                                key={row.name}
                                sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                                >
                                <TableCell component="th" scope="row">
                                    {row.name}
                                </TableCell>
                                <TableCell align="left">
                                    <img className="side-nav-img" src={row.color} alt="color-rect"/>
                                </TableCell>
                                <TableCell align="left">
                                        
                                        {row.status}
                                </TableCell>
                                <TableCell align="right">{row.numOfPlan}</TableCell>
                                <TableCell align="center">of</TableCell>
                                <TableCell align="left">{row.total}</TableCell>

                                </TableRow>
                            ))}
                            </TableBody>
                        </Table>
                    {/* </TableContainer> */}
                </Card.Content>
            </Card>            
        </div>
    );
}

export default DashboardPage;
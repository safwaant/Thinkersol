// React component imports.
import { React, useState } from 'react';

import Card from '../Card';
import Dropdown from '../../dropdowns/Dropdown';

import Button from '@mui/material/Button';
import Stack from '@mui/material/Stack';
import Box from '@mui/material/Box';

import {searchStudent, completeSearch} from '../FilterCard/manageFilterSlice';

import SearchSlice, { updateSearchValue, searchManage, completeSearchManage } from '../../../pages/SearchSlice';
import { NO_ACTION, SEARCH, DEFAULT_SEARCH_CONFIG} from '../../../pages/SearchSlice';



const PLAN_LIST = ["1", "2", "3", "4", "5"];
const ORDER_LIST = {
    "Last Name (A-Z)": 'aToZ',
    "Last Name (Z-A)": 'zToA'
};
const GRADE_LIST = {
    "Grade 7": 7,
    "Grade 8": 8,
    "Grade 9": 9,
    "Grade 10": 10,
    "Grade 11": 11,
    "Grade 12": 12
};
const PLAN_STATUS = ["Complete", "Pending Review", "Incomplete"];



function ManageFilterCard() {

    // State management for dropdowns.


    const [order, setOrder] = useState(Object.values(ORDER_LIST)[0]);
    const [grade, setGrade] = useState(Object.values(GRADE_LIST)[0]);
    const [planNum, setPlanNum] = useState(PLAN_LIST[0]);
    const [planStatus, setPlanStatus] = useState(PLAN_STATUS[0]);


    //  TODO INCOMPLETE
    // Gather the data as is - this is used by the submit button when it dispatches to the Redux
    // store.
    const reportConfig = {
        order: order,
        grade: grade,
        planNum: planNum,
        planStatus: planStatus,
    }

    return(
        <Card>
                <Card.Header>
                    <h2 className = "filter-card-header">
                        Apply Filters
                    </h2> 
                </Card.Header>
                <Card.Content>
                    <Box
                    sx={{
                        width: '60em',
                        maxWidth: '100%',
                        height: '10em'
                    }}
                    >
                        <Stack direction='row' spacing={1} justifyContent="space-around">

                            <Dropdown renderCondition = {true}
                                    title = "Sort By"
                                    customWidth = '10rem'
                                    list = {ORDER_LIST}
                                    updateVal = {setOrder}
                                    val = {order}
                            />
                            <Dropdown renderCondition = {true}
                                    title = "Grade"
                                    customWidth = '10rem'
                                    list = {GRADE_LIST}
                                    updateVal = {setGrade}
                                    val = {grade}
                            />
                            <Dropdown renderCondition = {true}
                                    title = "Plan Status"
                                    customWidth = '10rem'
                                    list = {PLAN_STATUS}
                                    updateVal = {setPlanNum}
                                    val = {planStatus}
                            />
                            <Dropdown renderCondition = {true}
                                    title = "Select Plan"
                                    customWidth = '10rem'
                                    list = {PLAN_LIST}
                                    updateVal = {setPlanStatus}
                                    val = {planNum}
                            />
                            <Button
                                className="btn"
                                sx={{ height: 50, width: 100}}
                                //onClick = {() => dispatch(runReport(reportConfig))}
                                //className = "run-report-btn btn"
                            >
                                    Apply
                            </Button>


                        </Stack>
                        
                    </Box>
                </Card.Content>
            </Card>

    );
}

export default ManageFilterCard;
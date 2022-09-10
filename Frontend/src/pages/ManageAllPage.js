/**
 * This project is licensed under the GNU GPL v3 license
 * @author Rosie Shen
 * @version 1.1
 * @since 7/6/2022
 * 
 * Abstract:
 *      - Consistent page container with two navigation bars
 */

// React component imports.
import { React, useState } from 'react';


// search box component
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';

// apply search button 
import Button from '@mui/material/Button';
import Stack from '@mui/material/Stack';

import Card from '../components/cards/Card';


// Axios imports.
import AdminAxios from '../axiosinstances/AdminAxios.js';


// Slice imports
import SearchSlice, { updateSearchValue, searchManage, completeSearchManage } from './SearchSlice';
import { NO_ACTION, SEARCH, DEFAULT_SEARCH_CONFIG} from './SearchSlice';

// redux import
import { useSelector, useDispatch } from 'react-redux';
import { setToManagePage } from '../NavSlice.js';
import ManageFilterCard from '../components/cards/FilterCard/ManageFilterCard';
import ManageResultsCard from '../components/cards/ResultsCard/ManageResultsCard';

// Imports from external dependencies.
import { StatusCodes } from 'http-status-codes';
import { CREATE_GROUP } from '../components/popups/GroupSlice';

function isEmptyOrSpaces(str){
    return str === null || str.match(/^ *$/) !== null;
  }


function ManageAllPage() {
    // TODO redux call to update top nav
    const dispatch = useDispatch();
    dispatch(setToManagePage()); 

    // State management for results card (selected students).
    const [selectedStudents, updateSelectedStudents] = useState([]);


    // GET request for updating the group list
    let url = "/group/";
    let urlParams = {
        schoolName: "Avengers High"
    };

    // state management to check if the results need to be rendered
    const [ retrieveStudentData, setRetrieveGroupData ] = useState(false); //false until the search btn is clicked




    // Check the render flag to see whether we should render this component at all.
    //if (props.renderCondition === false) return;


    const [currSearchValue, setCurrSearchValue] = useState("");

    // Event handler for the text field.
    const handleChange = (event) => {
        setCurrSearchValue(event.target.value);
    }


    const filtersURL = `/filters/`; // TODO find the url for the filters? check with Saafwaan for documentation
    // Next, prepare the data to be sent.
    console.log("aaa");

    const searchConfig = useSelector(state => state.searchManageFilters.searchConfig);
    console.log(searchConfig);
    // Event handler for the submit button.
    const handleSubmitBtn = () => {
        // GET Request
        if (isEmptyOrSpaces(currSearchValue)) return;

        console.log("submitted");
        console.log(currSearchValue);
        dispatch(updateSearchValue(currSearchValue));    // Send the new group name to the Redux store.
        setCurrSearchValue("");        // Reset our local state var.
        setRetrieveGroupData(true);

  
        //const filtersURL = `/filters/${filterType}`; // TODO UPDATE THIS

        // TODO: school name should not be hardcoded
        urlParams = {
            schoolName: "Avengers High",
            studentLastName: searchConfig.order,
            sort: searchConfig.sort,
            grades: searchConfig.grades, // TODO endpoint documentation states that this is a multi select, but current implementation is a single select
            planStatus: searchConfig.planStatus,
            plans: searchConfig.plans,
        }

        
        // Send the request to retrieve the students.
        if (searchConfig.typeVal !== 'def') {
            // Perform a GET request to return student information for list of students that satisfy the given filters
            AdminAxios.get(filtersURL, { params: urlParams })
                .then((response) => {
                    let parsedData = [];
                    response.data.forEach((student) => {
                        parsedData.push({
                            email: student.student_id,
                            name: `${student.student_firstname} ${student.student_lastname}`,
                            grade: student.grade,
                            planNum: student.plan,
                            career: student.career
                        })
                    });
                    // TODO PARSE THE DATA
                    //setStudents(parsedData); 
                    //if (renderResultsCard === false) setRenderResultsCard(true);
                })
                .catch(error => {
                    // If it's a 404, there were no plans found given the report config. An empty list
                    // will tell the results card to display a message: "No plans found."
                    if (error.response.status === StatusCodes.NOT_FOUND){
                        //setStudents([]);
                        console.log("pass");
                    } 
                    else {  // Otherwise display the error in the console.
                        alert("Sorry, something went wrong on our end. Please try again in a moment.");
                        console.log(error);
                    }
                });
            
            // Regardless of whether the request failed or succeeded, do the following:
            dispatch(completeSearchManage());
        }

    }

    // if(retrieveStudentData) {
    //     let parsedData = currSearchValue;
    //     dispatch(updateSearchValue(parsedData));
    //     setRetrieveGroupData(false);
        
        // set the results render condition is true to return the results card 

        /*

        AdminAxios.get(url, { params: urlParams })
            .then((response) => {
                // Parse the group names from the response and store them in our local state.
                //let parsedData = parseGroupNames(response.data);
                let parsedData = currSearchValue;
                dispatch(updateSearchValue(parsedData));
                setRetrieveGroupData(false);
            })
            .catch(error => {
                // If we got an error (400 or 500) print it to the console.
                if (error.response.status === StatusCodes.NOT_FOUND) {
                    alert("No custom group found!");
                    console.log("ERROR: No groups found!");
                } else if (error.response.status === StatusCodes.INTERNAL_SERVER_ERROR) {
                    alert("Sorry, something went wrong on our end. Please try again in a moment.");
                    console.log("ERROR: Internal server error when retrieving groups!");
                } else {
                    alert("Sorry, something went wrong on our end. Please try again in a moment.");
                    console.log("ERROR: Unexpected error code from API when retrieving groups!");
                }
                console.log(error);
            });
        */
    //}

    // const action = groupSliceData.action;
    // if(action !== NO_ACTION){
    //     if(action === SEARCH0{
    //         // GET REQUEST
    //         console.log("performing search request")
    //         let url = "/ /"
    //     })
    // }


    
    return(
        <div>
            {/* header */}
            <h1>Search Students</h1>
            <hr />

            {/* search box */}
            <Stack direction="row" spacing={2}>
                <Box
                    sx={{
                        width: '60em',
                        maxWidth: '100%',
                    }}
                    >
                    <TextField 
                        fullWidth label="Student Last Name" 
                        id="studentname"
                        onChange = {handleChange}
                    />
                </Box>

                <Button
                className = "btn"
                onClick = {handleSubmitBtn}
                >
                    
                    Search
                </Button>
            </Stack>

            <ManageFilterCard/>

            {/* Results */}
            <ManageResultsCard></ManageResultsCard>
            
        </div>
    );

}

export default ManageAllPage;
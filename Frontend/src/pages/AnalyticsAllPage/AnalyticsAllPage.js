/**
 * This project is licensed under the GNU GPL v3 license
 * @author Ashley Mead, Rosie Shen, Shakeel Khan
 * @version 1.1
 * @since 7/6/2022
 * 
 * Abstract:
 * TODO
 */

// #######
// IMPORTS
// #######

// React component imports.
import { React, useState } from 'react';

// Axios imports.
import AdminAxios from '../../axiosinstances/AdminAxios.js';

// Redux imports.
import { useSelector, useDispatch } from 'react-redux';

import { NO_ACTION,
    CREATE_GROUP,
    DELETE_GROUP,
    ADD_STUDENT_TO_GROUP,
    updateGroups,
    completedAction } from '../../components/popups/GroupSlice';
import { changeActiveCard,
    completedReport,
    CAREER_DATA_CARD,
    EDUCATION_DATA_CARD,
    MY_GROUPS_CARD} from '../../components/cards/FilterCard/analyticsFiltersSlice';

// Imports from external dependencies.
import { StatusCodes } from 'http-status-codes';

// Custom component imports.
import Dropdown from '../../components/dropdowns/Dropdown';
import ManageGroupsMenu from '../../components/menus/ManageGroupsMenu';
import FilterCard from '../../components/cards/FilterCard/FilterCard';
import ResultsCard from '../../components/cards/ResultsCard/ResultsCard.js';

// Stylesheet imports.
import './AnalyticsAllPage.css';
import { setToAnalyticsPage } from '../../NavSlice.js';

// #########
// CONSTANTS
// #########

// These are to be used as URL params for the filter endpoint.
const CAREER_FILTER_TYPE = 'career';
const EDUCATION_FILTER_TYPE = 'education';
const GROUP_FILTER_TYPE = 'group';

// Static data for filters card.
const DATA_TYPE_LIST = [CAREER_DATA_CARD, EDUCATION_DATA_CARD, MY_GROUPS_CARD];

// ################
// HELPER FUNCTIONS
// ################

/**
 * @author Shakeel Khan, Safwaan Taher
 * 
 * TODO
 * 
 * @pre TODO
 * @post TODO
 * 
 * @returns TODO
 */
function parseGroupNames(responseData) {
    let parsedGroupNames = [];
    for (let groupName of responseData) parsedGroupNames.push(groupName["group_name"]);
    return parsedGroupNames;
}

/**
 * @author Shakeel Khan
 * 
 * TODO
 * 
 * @pre TODO
 * @post TODO
 * 
 * @returns TODO
 */
var listToURLParam = input => {
    let json = JSON.stringify(input);   // Convert it to a string.
    let foo = json.substring(1, json.length - 1);   // Strip the brackets.
    let tokens = foo.split(',');    // Tokenize it by commas.
    let result = "";
    for (let i = 0; i < tokens.length; i++) {
        // Grab the current token.
        let token = tokens[i];

        // If the current token is a string, strip the quotation marks off before concatenating
        // it with our result string.
        if (token.charAt(0) === "\"") token = token.substring(1, token.length - 1);
        result += token;

        // For every token except the last, append a comma to the result string.
        if (i < tokens.length - 1) result += ',';
    }
    return result;
}

// #########
// COMPONENT
// #########
export default function Page() {
    // Redux setup.
    const dispatch = useDispatch();
    dispatch(setToAnalyticsPage()); 

    // State management for type filter.
    const activeCard = useSelector(state => state.analyticsFilters.activeCard);

    // State management for whether the results card should be rendered.
    const [renderResultsCard, setRenderResultsCard] = useState(false);

    // State management for list of students (who are displayed in the results card).
    const [students, setStudents] = useState([]);

    // GET request for updating the group list
    let url = "/group/";
    let urlParams = {
        schoolName: "Avengers High"
    };
    const [retrieveGroupData, setRetrieveGroupData] = useState(true);

    if (retrieveGroupData) {
        AdminAxios.get(url, { params: urlParams })
            .then((response) => {
                // Parse the group names from the response and store them in our local state.
                let parsedData = parseGroupNames(response.data);
                dispatch(updateGroups(parsedData));
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
    }

    // First, figure out the URL.
    let filterType;
    if (activeCard === CAREER_DATA_CARD) filterType = CAREER_FILTER_TYPE;
    else if (activeCard === EDUCATION_DATA_CARD) filterType = EDUCATION_FILTER_TYPE;
    else if (activeCard === MY_GROUPS_CARD) filterType = GROUP_FILTER_TYPE;
    else console.log('Big goof');

    const filtersURL = `/filters/${filterType}`;

    // Next, prepare the data to be sent.
    const reportConfig = useSelector(state => state.analyticsFilters.reportConfig);

    // Put it all together. TODO: schoolName shouldn't be hardcoded.
    urlParams = {
        schoolName: "Avengers High", 
        sort: reportConfig.order,
        typeValue: reportConfig.typeVal,
        plans: reportConfig.planNum,
        grades: reportConfig.grade
    };

    // Send the request to retrieve the students.
    if (reportConfig.typeVal !== 'def') {
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
                setStudents(parsedData);
                if (renderResultsCard === false) setRenderResultsCard(true);
            })
            .catch(error => {
                // If it's a 404, there were no plans found given the report config. An empty list
                // will tell the results card to display a message: "No plans found."
                if (error.response.status === StatusCodes.NOT_FOUND) setStudents([]);
                else {  // Otherwise display the error in the console.
                    alert("Sorry, something went wrong on our end. Please try again in a moment.");
                    console.log(error);
                }
            });
        
        // Regardless of whether the request failed or succeeded, do the following:
        dispatch(completedReport());
    }
 
    const groupSliceData = useSelector(state => state.group);
    const action = groupSliceData.action;
    if (action !== NO_ACTION) {
        if (action === CREATE_GROUP) {
            // POST
            // create group logic
            console.log("creating group");
            let url = "/group/";
            let urlParams = {
                schoolName: "Avengers High", 
                groupName: groupSliceData.tmpGroup
            };

            AdminAxios.post(url, {}, { params: urlParams })
                .then(() => {
                    alert(`Successfully created group ${urlParams.groupName}!`);
                    setRetrieveGroupData(true);
                })
                .catch(error => {
                    if (error.response.status === StatusCodes.BAD_REQUEST) {
                        console.log("ERROR: Incorrect parameters or group/school does not exist");
                    } else if (error.response.status === StatusCodes.INTERNAL_SERVER_ERROR) {
                        console.log("ERROR: internal server error when retrieving group data");                       
                    } else {
                        console.log("ERROR: Unexpected error code from API when creating group!");
                    }
                    alert("Sorry, something went wrong on our end. Please try again in a moment.");
                    console.log(error);
                });
        } else if (action === DELETE_GROUP) {
            // Setup the URL and URL params.
            url = "/group/";
            urlParams = {
                schoolName: "Avengers High",
                groupName: listToURLParam(groupSliceData.tmpGroup)
            };

            // Run the DELETE request.
            AdminAxios.delete(url, { params: urlParams })
                .then(() => {
                    // We successfully deleted the groups.
                    alert("Successfully deleted the specified groups!");
                    setRetrieveGroupData(true);
                })
                .catch(error => {
                    // The delete was unsuccessful. Display the error in the console.
                    if (error.response.status === StatusCodes.BAD_REQUEST) {
                        console.log("ERROR: Failed to delete group(s)! This is due either to an invalid group or school name.");
                    } else if (error.response.status === StatusCodes.INTERNAL_SERVER_ERROR) {
                        console.log("ERROR: Internal server error when deleting groups!");
                    } else {
                        console.log("ERROR: Unexpected error code from API when deleting group!");
                    }
                    alert("Sorry, something went wrong on our end. Please try again in a moment.");
                    console.log(error);
                });
        } else if (action === ADD_STUDENT_TO_GROUP) {
            // Setup the URL.
            url = "/group/";

            // Get some data.
            let schoolName = "Avengers High";
            let group = groupSliceData.tmpGroup;

            // Build the request body.
            let requestBody = {};
            groupSliceData.students.forEach((student) => {
                requestBody[student.email] = [student.planNum, schoolName, group];
            });

            // Run the PUT request.
            AdminAxios.put(url, requestBody)
                .then(() => {
                    // We successfully updated the groups.
                    console.log(`Successfully updated ${group}!`);
                    alert(`Successfully added students to the ${group} group!`);
                })
                .catch(error => {
                    // The delete was unsuccessful. Display the error in the console.
                    if (error.response.status === StatusCodes.BAD_REQUEST) {
                        console.log(`ERROR: Failed to update ${group}! This is due to incorrect parameters.`);
                    } else if (error.response.status === StatusCodes.INTERNAL_SERVER_ERROR) {
                        console.log(`ERROR: Internal server error when updating ${group}!`);
                    } else {
                        console.log("ERROR: Unexpected error code from API when updating group!");
                    }
                    alert("Sorry, something went wrong on our end. Please try again in a moment.");
                    console.log(error);
                });
        } else {
            console.log("big goof");
        }

        // Mark the action as complete, that way we don't do it again when the page re-renders.
        dispatch(completedAction());
    }

    // JSX.
    return (
        <div className = "pageWrapper">
            {/* Header */}
            <div
                className = "pageRow"
                id = "headerWrapper"
            >
                <span className = "headerItem">
                    <h1>Manage Groups</h1>
                </span>
                <span className = "headerItem">
                    <ManageGroupsMenu />
                </span>
            </div>
            <hr />

            {/* Apply filters card. */}
            <div
                className = "pageRow"
                style={{marginBottom: '0.5rem'}}
            >
                <Dropdown renderCondition = {true}
                            title = "Type"
                            customWidth = '10rem'
                            list = {DATA_TYPE_LIST}
                            updateVal = {(newVal) => dispatch(changeActiveCard(newVal))}
                            val = {activeCard}
                    />
            </div>

            <div className = "pageRow">
                <FilterCard />
            </div>
            
            {/* Search results card. */}
            <div className = "pageRow">
                <ResultsCard
                    renderCondition = {renderResultsCard}
                    students = {students}
                />
            </div>
        </div>
    );
}
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

import { useSelector, useDispatch } from 'react-redux';

// Axios imports.
import AdminAxios from '../../axiosinstances/AdminAxios.js';

// Custom component imports.
import Dropdown from '../../components/Dropdown';
import ManageGroupsMenu from '../../components/buttons/ManageGroupsMenu';
import FilterCard, { CAREER_LIST } from '../../components/cards/FilterCard';
import {CAREER_DATA_CARD,
        EDUCATION_DATA_CARD,
        MY_GROUPS_CARD} from '../../components/cards/analyticsFiltersSlice';
import ResultsCard from '../../components/cards/ResultsCard.js';
import { NO_ACTION, CREATE_GROUP, DELETE_GROUP, ADD_STUDENT_TO_GROUP, updateGroups, completedAction } from '../../components/popups/GroupSlice';
import { setToAnalyticsPage } from '../../NavSlice'

// Stylesheet imports.
import './Page.css';
import { changeActiveCard } from '../../components/cards/analyticsFiltersSlice.js';

function parseGroupNames(responseData) {
    let parsedGroupNames = [];
    for (let groupName of responseData) parsedGroupNames.push(groupName["group_name"]);
    return parsedGroupNames;
}

function Page() {
    const dispatch = useDispatch();

    // TODO not sure if this dispatch is happening every time a change is made to the page, if so it might be using up resources?
    dispatch(setToAnalyticsPage()); // dispatch to update the top nav title 
    

    // These are to be used as URL params for the filter endpoint.
    const CAREER_FILTER_TYPE = 'career';
    const EDUCATION_FILTER_TYPE = 'education';
    const GROUP_FILTER_TYPE = 'group';

    // State management for type filter.
    const activeCard = useSelector(state => state.analyticsFilters.activeCard);

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
                // TODO: move dispatch from catch to then once backend is up.
            })
            .catch(error => {
                // If we got an error (400 or 500) print it to the console.
                console.log(error);
                dispatch(updateGroups(["group 1", "group 2", "group 3"]));
                setRetrieveGroupData(false);
            });
    }

    // Static data for filters card.
    const dataTypeList = [CAREER_DATA_CARD, EDUCATION_DATA_CARD, MY_GROUPS_CARD];
    

    // TODO: Finish this connection once Safwaan has the filters endpoint done.
    // First, figure out the URL.
    let filterType;
    if (activeCard === CAREER_DATA_CARD) filterType = CAREER_FILTER_TYPE;
    else if (activeCard === EDUCATION_DATA_CARD) filterType = EDUCATION_FILTER_TYPE;
    else if (activeCard === MY_GROUPS_CARD) filterType = GROUP_FILTER_TYPE;
    else console.log('Big goof');

    const filtersURL = `/filters/${filterType}`;

    // TODO: Clean this up!
    // Next, prepare the data to be sent.
    var listToURLParam = input => {
        let json = JSON.stringify(input);
        let foo = json.substring(1, json.length - 1);
        console.log(foo);
        let tokens = foo.split(',');
        console.log(tokens);
        let result = "";
        for (let i = 0; i < tokens.length; i++) {
            let token = tokens[i];
            if (token.charAt(0) === "\"") token = token.substring(1, token.length - 1);
            result += token;
            if (i < tokens.length - 1) result += ',';
        }
        return result;
    }

    // Next, prepare the data to be sent.
    const reportConfig = useSelector(state => state.analyticsFilters.reportConfig);
    console.log(reportConfig);

    // Put it all together. TODO: schoolName shouldn't be hardcoded.
    urlParams = {
        schoolName: "Avengers High", 
        sort: reportConfig.order,
        typeVal: reportConfig.typeVal,
        plans: reportConfig.planNum,
        grades: reportConfig.grade
    };

    // Send the request to retrieve the students.
    // TODO: If a 404 is returned, we should put a message in the box saying no plans found.
    // TODO: Only perform the request once for now. In the future, have this run everytime "Run Report" is clicked.
    if (students.length === 0) {
        // Perform a GET request to return student information for list of students that satisfy the given filters
        AdminAxios.get(filtersURL, { params: urlParams })
            .then((response) => {
                console.log(response.data);
                setStudents([1, 2, 3]);     // TODO: Temporary - this way we don't enter a infinite loop.
            })
            .catch(error => {
                // If it's a 404, there were no plans found given the report config. An empty list
                // will tell the results card to display a message: "No plans found."
                if (error.response.status === 404) setStudents([]);
                else {  // Otherwise display the error in the console. TODO: Remove hardcoded data.
                    console.log(error);
                    const temp = [
                        {
                            email: "nromanoff@ahs.org",
                            name: "Natasha Romanoff",
                            grade: 11,
                            planNum: 1,
                            career: "Actor"
                        },
                        {
                            email: "srogers@ahs.org",
                            name: "Steve Rogers",
                            grade: 9,
                            planNum: 1,
                            career: "Industrial Engineer"
                        }
                    ];
                    setStudents(temp);
                }
            });
    }
 
    const groupSliceData = useSelector(state => state.group);
    const action = groupSliceData.action;
    console.log(groupSliceData);
    if(action != NO_ACTION){
        if(action === CREATE_GROUP){
            // POST
            // create group logic
            console.log("creating group");
            let url = "/group/";
            let urlParams = {
                schoolName: "Avengers High", 
                groupName: groupSliceData.tmpGroup
            };

            AdminAxios.post(url, { params: urlParams })
                .then(() => {
                    setRetrieveGroupData(true);
                })
                .catch(error => {
                    if(error.response.status === 400){
                        console.log("ERROR: Incorrect parameters or group/school does not exist");
                    }
                    else if (error.response.status === 500){
                        console.log("ERROR: internal server error when retrieving group data");                       
                    }
                    else{
                        console.log("big goof");
                    }
                    console.log(error);
                });
        }
        else if(action === DELETE_GROUP) {
            // Setup the URL and URL params.
            url = "/group/";
            urlParams = {
                schoolName: "Avengers High",
                groupName: listToURLParam(groupSliceData.tmpGroup)
            };
            console.log(urlParams);

            // Run the DELETE request.
            AdminAxios.delete(url, { params: urlParams })
                .then(() => {
                    // We successfully deleted the groups.
                    setRetrieveGroupData(true);
                })
                .catch(error => {
                    // The delete was unsuccessful. Display the error in the console.
                    if (error.response.status === 400) {
                        console.log("ERROR: Failed to delete group(s)! This is due either to an invalid group or school name.");
                    } else if (error.response.status === 500) {
                        console.log("ERROR: Internal server error when deleting groups!");
                    } else {
                        console.log("ERROR: A big goof error happened when deleting groups!")
                    }
                    console.log(error);
                });
        }
        else if(action === ADD_STUDENT_TO_GROUP) {
            // PUT request
            // add to group logic
            console.log("adding to group!");
        }
        else {
            console.log("big goof");
        }
        dispatch(completedAction());
    }

    // const createGroupURL = "/group/"
    // //TO DO: Ask Shakeel about proper trigger '&&' for performing the POST request to create group.
    // if(&&) {
    //     adminAxios.post(createGroupURL, { params: {
    //                 groupName: "examplegroup2",
    //                 schoolName: "Avengers High"
    //             }
    //         })
    //             .then(() => {
    //                      setGroupNames([]);
    //                })
    //         .catch(error => {
    //             console.log(error);
    //         });
    // }

    // const deleteGroupURL = "/group"
    // //TO DO: Ask Shakeel about proper trigger '&&' for performing the DELETE request to delete group(s).
    // if(&&) {
    //     adminAxios.delete(deleteGroupURL, { params: {
    //             groupName: "examplegroup2",
    //             schoolName: "Avengers High"
    //         }            
    //     })
    //     .then((response) => {
    //             // TODO: is this filter correct?
    //             let updatedGroups = groupNames.filter(group => group[0] !== response.data[0])
    //             setGroupNames(updatedGroups);
    //             // let updatedList = selectedStudents.filter(student => student[0] !== studentData[0]);
    //             // updateSelectedStudents(updatedList);

    //             // TODO: setting students empty in case the previously filtered group is being deleted
    //             setStudents([]);
    //         }
    //     )
    //     .catch(error => {
    //         console.log(error)
    //     });
    // }

    
    // const updateGroupURL = "/group"
    //TO DO: this function can be called in an onClick for a button
    // function addStudentsToGroup() {       
    //     adminAxios.put(updateGroupURL, {params: {
    //             'tstark@ahs.org': [1, 'Avengers High', 'Aerospace Engineering']
    //         }
    //     })
    //     .then((response) => {
    //             //updateSelectedStudents(oldArray => [...oldArray, studentData]);
    //             setGroupNames(oldGroups => [...oldGroups, response.data]);
    //         }
    //     )
    //     .catch(error => {
    //         console.log(error);
    //     })
    // }

    return (
        <div>
            {/* Header */}
            <div className="pageRow" id="headerWrapper">
                <span className="headerItem">
                    <h1>Manage Groups</h1>
                </span>
                <span className="headerItem">
                    <ManageGroupsMenu />
                </span>
            </div>
            <hr />

            {/* Apply filters card. */}
            <div className="pageRow">
                <Dropdown renderCondition = {true}
                        title = "Type"
                        customWidth = '15rem'
                        list = {dataTypeList}
                        updateVal = {(newVal) => dispatch(changeActiveCard(newVal))}
                        val = {activeCard}
                />
                <FilterCard />
            </div>
            
            {/* Search results card. */}
            <div className="pageRow">
                <ResultsCard students = {students} />
            </div>
        </div>
    );
}

export default Page;
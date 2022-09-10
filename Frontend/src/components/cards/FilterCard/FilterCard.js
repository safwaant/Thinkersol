/**
 * This project is licensed under the GNU GPL v3 license
 * @author Shakeel Khan
 * @version 1.0
 * @since 7/21/2022
 * 
 * Abstract:
 * This component contains the dropdowns inside the purple card for the Admin Analytics page. It
 * manages the dropdowns internally and dispatches to the Redux store when the "Run Report" button
 * is clicked.
 * 
 * This makes use of the analyticsFilterSlice. It passes in all the data from the dropdown to this
 * slice when the "Run Report" button is hit. A useSelector can be used in another component for
 * this data in the slice, as it will be triggered every time the "Run Report" button is clicked
 * (as that's the only time a dispatch is being made to the store).
 * 
 * This component takes 0 props. Most the data it needs are defined as constants, some is retrieved
 * from the Redux store.
 */

// #######
// IMPORTS
// #######

// React component imports.
import { React, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';

// Imports from external dependencies.
import { Button } from '@mui/material';

// Custom component imports.
import Card from '../Card.js';
import Dropdown from '../../dropdowns/Dropdown';
import { CAREER_DATA_CARD, EDUCATION_DATA_CARD, MY_GROUPS_CARD, runReport } from './analyticsFiltersSlice.js';

// Custom stylesheets.
import './FilterCard.css';
import '../../mui-styles.css';

// #########
// CONSTANTS
// #########

// Filter titles.
const CAREER_FILTER_TITLE = "Career Type";
const EDUCATION_FILTER_TITLE = "Education Type";
const GROUP_FILTER_TITLE = "Group";
const ORDER_FILTER_TITLE = "Order";
const GRADE_FILTER_TITLE = "Grade";
const PLAN_FILTER_TITLE = "Plan";

// Filter widths.
const CAREER_FILTER_WIDTH = '35rem';
const EDUCATION_FILTER_WIDTH = '35rem';
const GROUP_FILTER_WIDTH = '35rem';
const ORDER_FILTER_WIDTH = '11rem';
const GRADE_FILTER_WIDTH = '8rem';
const PLAN_FILTER_WIDTH = '4rem';

// Static data for dropdowns.
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
export const CAREER_LIST = {
    "Management Occupations": 11,
    "Business and Financial Occupations": 13,
    "Computer and Mathematical Occupations": 15,
    "Architecture and Engineering Occupations": 17,
    "Life, Physical, and Social Science Occupations": 19,
    "Community and Social Service Occupations": 21,
    "Legal Occupations": 23,
    "Educational Instruction and Library Occupations": 25,
    "Arts, Design, Entertainment, Sports, and Media Occupations": 27,
    "Healthcare Practicioners and Technical Occupations": 29,
    "Healthcare Support Occupations": 31,
    "Protective Service Occupations": 33,
    "Food Preparation and Serving Related Occupations": 35,
    "Building and Grounds Cleaning and Maintenance Occupations": 37,
    "Personal Care and Service Occupations": 39,
    "Sales and Related Occupations": 41,
    "Office and Administrative Support Occupations": 43,
    "Farming, Fishing, and Forestry Occupations": 45,
    "Construction and Extraction Occupations": 47,
    "Installation, Maintenance, and Repair Occupations": 49,
    "Production Occupations": 51,
    "Transportation and Material Moving Occupations": 53
};
const EDUCATION_LIST = ["High School Diploma", "Associate's Degree", "Apprenticeship",
                        "Certification", "Technical School", "Bachelor's Degree",
                        "Master's Degree", "Ph. D", "Graduate School with Exam"];

// ################
// HELPER FUNCTIONS
// ################

/**
 * @author Shakeel Khan
 * 
 * Given the active card for the filter (career, education, or group data) this method will return
 * several flags for which dropdowns should be rendered.
 * 
 * @pre activeCard should have a value of either CAREER_DATA_CARD, EDUCATION_DATA_CARD,
 *      MY_GROUPS_CARD (these constants are defined in analyticsFilterSlice.js). Otherwise,
 *      unexpected behavior can occur.
 * @post 4 flags will be returned, for the career, education, plan, and group dropdowns (in that
 *       order). These flags indicated whether the respective dropdowns should be rendered or not.
 *       if the value of activeCard is something invalid, all the flags will be false.
 * 
 * @returns See post condition.
 */
function determineRenderConditions(activeCard) {
    // Create the flags.
    let renderCareerFilter = false;
    let renderEducationFilter = false;
    let renderPlanFilter = false;
    let renderGroupFilter = false;
       
    // Set them accordingly.
    if (activeCard === CAREER_DATA_CARD) {
        renderCareerFilter = true;
        renderEducationFilter = false;
        renderGroupFilter = false;
        renderPlanFilter = true;
    } else if (activeCard === EDUCATION_DATA_CARD) {
        renderCareerFilter = false;
        renderEducationFilter = true;
        renderGroupFilter = false;
        renderPlanFilter = true;
    } else if (activeCard ===  MY_GROUPS_CARD) {
        renderCareerFilter = false;
        renderEducationFilter = false;
        renderGroupFilter = true;
        renderPlanFilter = false;
    } else {
        console.log('Big goof'); // haha.
    }

    // Return them all.
    return {renderCareerFilter, renderEducationFilter, renderPlanFilter, renderGroupFilter};
}

// #########
// COMPONENT
// #########
export default function FilterCard() {
    // Redux setup.
    const groups = useSelector(state => state.group.groups);
    const activeCard = useSelector(state => state.analyticsFilters.activeCard);
    const dispatch = useDispatch();

    // State management for dropdowns.
    const [careerType, setCareerType] = useState(Object.values(CAREER_LIST)[0]);
    const [educationType, setEducationType] = useState(Object.values(EDUCATION_LIST)[0]);
    const [group, setGroup] = useState('');     // This is set once we retrieve the groups from the store.
    const [order, setOrder] = useState(Object.values(ORDER_LIST)[0]);
    const [grade, setGrade] = useState(Object.values(GRADE_LIST)[0]);
    const [planNum, setPlanNum] = useState(PLAN_LIST[0]);

    // Update group in two conditions:
    // (1) the list of groups has finally been retrieved from the API.
    // (2) the list of groups has been updated (either because new groups were added or old ones
    //     were deleted) and we need to check that the current value of group is still valid (in
    //     the case the currently selected group was deleted).
    let groupsHasBeenRetrieved = groups.length !== 0;
    let curGroupInvalid = groups.indexOf(group) === -1;
    if (groupsHasBeenRetrieved && curGroupInvalid) setGroup(groups[0]);

    // Setup the typeVal, which is dependent on the active card.
    let typeVal;
    if (activeCard === CAREER_DATA_CARD) typeVal = careerType;
    else if (activeCard === EDUCATION_DATA_CARD) typeVal = educationType;
    else if (activeCard === MY_GROUPS_CARD) typeVal = group;
    else console.log("big oof!");

    // Gather the data as is - this is used by the submit button when it dispatches to the Redux
    // store.
    const reportConfig = {
        typeVal: typeVal,
        order: order,
        grade: grade,
        planNum: planNum
    }

    // Get the render flags for the active card.
    let {renderCareerFilter, renderEducationFilter, 
            renderPlanFilter, renderGroupFilter} = determineRenderConditions(activeCard);

    // JSX.
    return (
        <Card>
            <Card.Header>
                <h2 className = "filter-card-header">
                    {activeCard}
                </h2>
            </Card.Header>
            <Card.Content>
                <div className = "filter-card-content-wrapper">
                    <div className = 'filter-card-content-row'>
                        {/* Only 1 of the 3 filters in the following span will be rendered. */}
                        <span className = "filter-card-span">
                            {/* Career filter. */}
                            <Dropdown renderCondition = {renderCareerFilter}
                                title = {CAREER_FILTER_TITLE}
                                customWidth = {CAREER_FILTER_WIDTH}
                                list = {CAREER_LIST}
                                updateVal = {setCareerType}
                                val = {careerType}
                            />

                            {/* Education filter. */}
                            <Dropdown renderCondition = {renderEducationFilter}
                                title = {EDUCATION_FILTER_TITLE}
                                customWidth = {EDUCATION_FILTER_WIDTH}
                                list = {EDUCATION_LIST}
                                updateVal = {setEducationType}
                                val = {educationType}
                            />

                            {/* Group filter. */}
                            <Dropdown renderCondition = {renderGroupFilter}
                                title = {GROUP_FILTER_TITLE}
                                customWidth = {GROUP_FILTER_WIDTH}
                                list = {groups}
                                updateVal = {setGroup}
                                val = {group}
                            />
                        </span>

                        {/* Grade filter. */}
                        <span className = "filter-card-span">
                            <Dropdown renderCondition = {true} 
                                title = {GRADE_FILTER_TITLE}
                                customWidth = {GRADE_FILTER_WIDTH}
                                list = {GRADE_LIST}
                                updateVal = {setGrade}
                                val = {grade}
                            />
                        </span>
                    </div>

                    <div className = 'filter-card-content-row'>
                        {/* We have 2 columns here - 1 for the remaining dropdowns, the other for
                            button. This way, we can fix the button to be in the lower right-hand
                            corner of the card. */}
                        <div className = 'filter-card-content-col'>
                            <span className = "filter-card-span">
                                {/* Results ordering filter. */}
                                <Dropdown renderCondition = {true} 
                                    title = {ORDER_FILTER_TITLE}
                                    customWidth = {ORDER_FILTER_WIDTH}
                                    list = {ORDER_LIST}
                                    updateVal = {setOrder}
                                    val = {order}
                                />
                            </span>

                            <span className = "filter-card-span">
                                {/* Plan filter. */}
                                <Dropdown renderCondition = {renderPlanFilter}
                                    title = {PLAN_FILTER_TITLE}
                                    customWidth = {PLAN_FILTER_WIDTH}
                                    list = {PLAN_LIST}
                                    updateVal = {setPlanNum}
                                    val = {planNum}
                                />
                            </span>
                        </div>
                        
                        <div className = "filter-card-content-col">
                            {/* Submit button - dispatches form data to the Redux store. */}
                            <Button
                                variant = 'contained'
                                onClick = {() => dispatch(runReport(reportConfig))}
                                className = "run-report-btn btn"
                            >
                                    Run Report
                            </Button>
                        </div>
                    </div>
                </div>
            </Card.Content>
        </Card>
    );
}
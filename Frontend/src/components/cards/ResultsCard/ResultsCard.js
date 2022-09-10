/**
 * This project is licensed under the GNU GPL v3 license
 * @author Shakeel Khan
 * @version 1.0
 * @since 7/21/2022
 * 
 * Abstract:
 * This is the Results Card. This is used on the Analytics All page to display resulting students 
 * when a report is run. It lists each student on it's own line along with a checkbox. This way, 
 * you can select students to add to a group. This component keeps track of the selected students,
 * and dispatches them to the Redux store when the "Apply" button in the popover of the
 * AddToGroupBtn component is clicked.
 * 
 * You need to provide the following via props:
 *  - The students to display (students). This should be provided as a list of dictionaries, where
 *    each dictionary represents an individual student. The dictionary should have the following 
 *    key-value pairs:
 *     - email: the student's email/ID.
 *     - name: the student's name.
 *     - grade: the student's current grade level.
 *     - planNum: the number of the plan of the student which came up in the search results.
 *     - career: the career belonging to this plan.
 *  - A render condition flag (renderCondition). If set to true, the component will render,
 *    otherwise, it won't render.
 * 
 * As mentioned before, this component dispatches the selected students to the Redux store. This is
 * done using the groupSlice Redux slice.
 */

// #######
// IMPORTS
// #######

// React component imports.
import { React, useState } from 'react';

// Imports from external Dependencies.
import { useDispatch } from 'react-redux';

// Custom component imports.
import Card from '../Card.js';
import ResultsRow from './ResultsRow';
import AddToGroupBtn from '../../../components/buttons/AddToGroupBtn';
import { updateStudents } from '../../popups/GroupSlice.js';

// Stylesheet imports.
import './ResultsCard.css';

// #########
// CONSTANTS
// #########
const LABELS = ['ID', 'Name', 'Grade', 'Plan', 'Career'];
const LABEL_SPACING = ['17vw', '17vw', '5vw', '5vw', '13vw'];

const CARD_COLOR = "#5E5E5E";

const CARD_TITLE_STYLING = {
    marginTop: '1rem',
    marginBottom: '0.5rem'
};

// #########
// COMPONENT
// #########
export default function ResultsCard(props) {
    // Redux setup.
    const dispatch = useDispatch();

    // State management for results card (selected students).
    const [selectedStudents, updateSelectedStudents] = useState([]);

    function resultsItemCheckboxHandler(event) {
        // Parse the data from the checkbox.
        var studentData = JSON.parse(event.target.value);
        studentData = {
                        email: studentData.email,
                        planNum: studentData.planNum
                    };
        if (event.target.checked) {
            // Add this student to the list.
            updateSelectedStudents(oldArray => [...oldArray, studentData]);
        } else {
            // Remove this student from the list.
            let updatedList = selectedStudents.filter(student => student.email !== studentData.email);
            updateSelectedStudents(updatedList);
        }
    }

    // Check the render flag to see whether we should render this component at all.
    if (props.renderCondition === false) return;

    // Otherwise, prepare the data to be used in making the header for the card.
    var labelsAndSpaces = LABELS.map(function(ithLabel, i) {
        return [ithLabel, LABEL_SPACING[i]];
    });

    // JSX.
    return (
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

                        <span className = "label-span">
                            <AddToGroupBtn
                                selectedStudents = {selectedStudents}
                                updateSelectedStudents = {updateSelectedStudents}
                                onClick = {() => dispatch(updateStudents(selectedStudents))}
                            />
                        </span>
                    </div>
                </Card.Header>
                <Card.Content>
                    { props.students.length === 0 && <p
                                                        style = {{ paddingLeft: '2vw' }}
                                                    >
                                                        <strong>No plans found.</strong>
                                                    </p> }
                    {props.students.map((student) => (
                        <ResultsRow
                            email = {student.email}
                            name = {student.name}
                            grade = {student.grade}
                            planNum = {student.planNum}
                            career = {student.career}
                            checkboxEventHandler = {resultsItemCheckboxHandler}
                            // checked = {selectedStudents.indexOf({email: student.email,
                            //                                     planNum: student.planNum}) !== -1}
                            key = {student.email}
                        />
                    ))}
                </Card.Content>
            </Card>
        </div>
    );
}
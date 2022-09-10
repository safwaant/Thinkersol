/**
 * This project is licensed under the GNU GPL v3 license
 * @author Shakeel Khan
 * @version 1.0
 * @since 7/11/2022
 * 
 * Abstract:
 * This component represents a single row (studnet) in the results card (seen on the analytics all
 * page). Simply pass in the following via props:
 *  - Student ID (email).
 *  - Student name (name).
 *  - Student grade (grade).
 *  - Student plan number (planNum).
 *  - Student career (career).
 *  - An event handler for the checkbox (checkboxEventHandler).
 * 
 * The checkbox event handler is called whenever the value of it changes (it is either selected or
 * unselected).
 */

// #######
// IMPORTS
// #######

// React component imports.
import React from 'react';

// Stylesheet imports.
import './ResultsRow.css';

// #########
// CONSTANTS
// #########
const COL_WIDTHS = ['17vw', '17vw', '5vw', '5vw', '16vw'];

// #########
// COMPONENT
// #########
export default function ResultsRow(props) {
    // Prepare the data for mapping in the JSX.
    const rowData = [props.email, props.name, props.grade, props.planNum, props.career];
    const checkboxData = {'email': props.email, 'planNum': props.planNum};

    console.assert(rowData.length === COL_WIDTHS.length);

    const data = rowData.map(function(ithRowDataVal, i) {
        return [ithRowDataVal, COL_WIDTHS[i]];
    });

    // JSX.
    return (
        <div className = "resultWrapper">
            {/* Student data. */}
            {data.map((cell) => (
                <span
                    className = "resultRowItem"
                    style = {{width: cell[1]}}
                    key = {cell[0]}>
                    {cell[0]}
                </span>
            ))}

            {/* Checkbox. */}
            <span className = "resultRowItem">
                <input
                    type = "checkbox"
                    value = {JSON.stringify(checkboxData)}
                    onChange = {props.checkboxEventHandler}
                    // checked={props.checked}
                />
            </span>
        </div>
    );
}
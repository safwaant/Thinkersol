/**
 * This project is licensed under the GNU GPL v3 license
 * @author Shakeel Khan
 * @version 1.1
 * @since 7/6/2022
 * 
 * Abstract:
 * This is a dropdown component. It is super simple to use. All you need to do is provide the
 * following via props:
 *  - A state variable. This is where the value of the currently selected item in the dropdown is
 *    stored.
 *  - A mutator for your state variable. This can be the mutator provided by useState() if using
 *    local state.
 *  - The items to be displayed in the dropdown. This can either be a dictionary or a list. You
 *    would use a dictionary if the actual listing in the dropdown differs from the value (e.g., 
 *    you want the item to be displayed as "One", but you want your state variable to be set to the
 *    number 1). In this case, the label (what's displayed in the dropdown) will be the key, and
 *    the value (the value your state variable is set to) is the value (for the previous example,
 *    you would have {"One": 1}). Or, if the label and the corresponding value is the same, you can
 *    pass in a list of the labels.
 *  - A title for the dropdown.
 *  - The width for the dropdown.
 *  - An optional render flag. If you don't specify this, the dropdown will render. Otherwise, if
 *    you provide it with the value of true (can either be done by writing "renderCondition" or 
 *    "renderContidion = {true}") it will render, else if given the value of false it won't render.
 * 
 * Summary of props (prop name):
 *  - State var (val).
 *  - Mutator for state var (updateVal).
 *  - Dropdown items (items).
 *  - Title (title).
 *  - Width (customWidth).
 *  - Render contidion flag (renderCondition). THIS IS OPTIONAL.
 */

// #######
// IMPORTS
// #######

// React component imports.
import React from 'react';

// Imports from external dependencies.
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';

// Stylesheet imports.
import './Dropdown.css';

// #########
// COMPONENT
// #########
export default function Dropdown(props) {
    // Only render the component if props.renderCondition is true (which it is by default).
    if (props.renderCondition === undefined || props.renderCondition === true) {
        // Prepare data. If the input is a list, we need to generate a dict, where the key and
        // value match.
        let items = props.list;
        if (Array.isArray(items)) {
            let temp = {};
            items.forEach((item) => temp[item] = item);
            items = temp;
        }

        // JSX.
        return (
            <div
                className = "dropdown"
                style = {{ width: props.customWidth }}
            >
                <h2 className = "dropdown-title"> {props.title} </h2>
                <FormControl fullWidth>
                    <Select
                        value = {props.val}
                        onChange = {(event) => props.updateVal(event.target.value)}
                        className = "select"
                    >
                        {Object.entries(items).map(([itemName, itemVal]) => (
                            <MenuItem
                                value = {itemVal}
                                key = {itemName}
                            >
                                    {itemName}
                            </MenuItem>
                        ))}
                    </Select>
                </FormControl>
            </div>
        );
    } else return;  // Otherwise render nothing.
}
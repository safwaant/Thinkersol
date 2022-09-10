/**
 * This project is licensed under the GNU GPL v3 license
 * @author Ashley Mead, Rosie Shen, Shakeel Khan
 * @version 1.0
 * @since 7/6/2022
 * 
 * Abstract:
 * This component represents the create group popover. This is where you enter the name for the new
 * group and click "Submit".
 * 
 * This utilizes the createGroup action from the groupSlice of the Redux store. This action is 
 * dispatched to the store when the "Submit" button is pressed and the name for the group is not an
 * empty string or a string full of whitespace.
 * 
 * You need to provide the following via props:
 *  - The element to which the popover should anchor itself to (anchorEl).
 *  - A mutator for anchorEl (setAnchorEl).
 *  - The menu close handler. The menu is the Manage Groups menu, which contains the entries to
 *    either create a group or delete some. You need to pass in a handler to close this menu. This
 *    handler is called when a valid group name is given and the "Submit" button has been pressed
 *    (menuCloseHandler).
 */

// #######
// IMPORTS
// #######

// React components import.
import { React, useState } from 'react';

// Imports from external dependencies.
import { useDispatch } from 'react-redux';
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import Popover from '@mui/material/Popover';
import TextField from '@mui/material/TextField';

// Action import from group slice.
import { createGroup } from './GroupSlice';

// Stylesheet imports.
import '../mui-styles.css';
import './CreateGroupPopover.css';

// #########
// CONSTANTS
// #########
const POPOVER_ANCHOR_ORIGIN = {
  vertical: 'center',
  horizontal: 'left'
};
const POPOVER_TRANSFORM_ORIGIN = {
  vertical: 'center',
  horizontal: 'right'
};
const BOX_SX = {
  '& > :not(style)': { width: '25ch' },
};
const TEXT_FIELD_LABEL = "New Group's Name:";

// ################
// HELPER FUNCTIONS
// ################

/**
 * @author Shakeel Khan
 * 
 * Determines whether the given string is null or full of whitespace.
 * 
 * @pre None.
 * @post If the given string is null or full of whitespace, true is returned, else false.
 * 
 * @returns See post condition.
 */
function isEmptyOrSpaces(str){
  return str === null || str.match(/^ *$/) !== null;
}

// #########
// COMPONENT
// #########
export default function CreateGroupPopover(props) {
  // Redux setup.
  const dispatch = useDispatch();

  // State management for the name of the new group to be created.
  const [newGroupName, setNewGroupName] = useState("");

  // Event handler for the text field.
  const handleChange = (event) => {
    setNewGroupName(event.target.value);
  }

  // Event handler for when the popover must be closed.
  const handlePopoverClose = () => {
    props.setAnchorEl(null);
  };

  // Event handler for the submit button.
  const handleSubmitBtn = () => {
    if (isEmptyOrSpaces(newGroupName)) return;
    dispatch(createGroup(newGroupName));    // Send the new group name to the Redux store.
    setNewGroupName("");        // Reset our local state var.
    handlePopoverClose();       // Do everything require to close the popover.
    props.menuCloseHandler();   // Close the manage groups menu.
  };

  // Here we determine whether the popover should be open, and specify an ID for it.
  const open = Boolean(props.anchorEl);
  const id = open ? 'create-group-popover' : undefined;

  // JSX.
  return (
    <Popover
        id = {id}
        open = {open}
        anchorEl = {props.anchorEl}
        onClose = {handlePopoverClose}
        anchorOrigin = {POPOVER_ANCHOR_ORIGIN}
        transformOrigin = {POPOVER_TRANSFORM_ORIGIN}
    >
      <div className="create-group-popover-content">
        {/* Text entry. */}
        <Box
          component = "form"
          sx = {BOX_SX}
          autoComplete = "off"
          className = "create-group-popover-text-entry"
          noValidate
        >
          <TextField
            id = "filled-basic"
            label = {TEXT_FIELD_LABEL}
            variant = "filled"
            onChange = {handleChange}
          />      
        </Box>

        {/* Submit button. */}
        <Button
          aria-describedby = {id}
          onClick = {handleSubmitBtn}
          className = "btn"
        >
          Submit
        </Button>
      </div>
    </Popover>
  );
}

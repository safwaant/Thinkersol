/**
 * This project is licensed under the GNU GPL v3 license
 * @author Ashley Mead, Rosie Shen, Shakeel Khan
 * @version 1.0
 * @since 7/6/2022
 * 
 * Abstract:
 * This component represents the delete group popover. This is where you select which group(s)
 * you'd like to remove and and click "Apply".
 * 
 * This utilizes the deleteGroup action from the groupSlice of the Redux store. This action is 
 * dispatched to the store when the "Apply" button is pressed.
 * 
 * You need to provide the following via props:
 *  - The element to which the popover should anchor itself to (anchorEl).
 *  - A mutator for anchorEl (setAnchorEl).
 *  - The menu close handler. The menu is the Manage Groups menu, which contains the entries to
 *    either delete a group or delete some. You need to pass in a handler to close this menu. This
 *    handler is called when the "Apply" button has been pressed (menuCloseHandler).
 */

// #######
// IMPORTS
// #######

// React component imports.
import { React, useState } from 'react';

// Imports from external dependencies.
import Button from '@mui/material/Button';
import Checkbox from '@mui/material/Checkbox';
import FormControl from '@mui/material/FormControl';
import FormControlLabel from '@mui/material/FormControlLabel';
import FormGroup from '@mui/material/FormGroup';
import FormLabel from '@mui/material/FormLabel';
import Popover from '@mui/material/Popover';

// Redux imports.
import { useSelector, useDispatch } from 'react-redux';

import { deleteGroup } from './GroupSlice';

// Stylesheet imports.
import './DeleteGroupPopover.css';
import '../mui-styles.css';

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

// #########
// COMPONENT
// #########
export default function DeleteGroupPopover(props) {
  // Redux setup.
  const dispatch = useDispatch();

  // Get the list of custom groups from the Redux store.
  const groups = useSelector((state) => state.group.groups); 

  // State management for the selected groups in the list.
  const [selectedGroups, updateSelectedGroups] = useState([]);

  // Event handler for the checkboxes.
  const handleCheckboxChange = (event) => {
    if (event.target.checked) {   // Add the new item to the list.
      updateSelectedGroups(oldArray => [...oldArray, event.target.id]);
    } else {    // Remove the item form the list.
      let groupToRemove = event.target.id;
      let updatedList = selectedGroups.filter(group => group !== groupToRemove);
      updateSelectedGroups(updatedList);
    }
  };

  // Event handler for when the popover must be closed.
  const handlePopoverClose = () => {
    props.setAnchorEl(null);
  };

  // Event handler for the submit button.
  const handleSubmitBtn = () => {
    dispatch(deleteGroup(selectedGroups));    // Send the selected groups to the Redux store.
    updateSelectedGroups([]);   // Reset our local state var.
    handlePopoverClose();       // Do everything require to close the popover.
    props.menuCloseHandler();   // Close the manage groups menu.
  }

  // Here we determine whether the popover should be open, and specify an ID for it.
  const open = Boolean(props.anchorEl);
  const id = open ? 'delete-group-popover' : undefined;

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
      <div className = "delete-group-popover-content">
        <FormControl
          component = "fieldset"
          variant = "standard"
          className = "delete-group-popover-form"
        >
          {groups.length !== 0 &&  <FormLabel
                                    component = "legend"
                                   >
                                    Which groups would you like to delete?
                                   </FormLabel>}
          {groups.length === 0 && <p>
                                    <b>No custom groups to remove!</b>
                                  </p>}
          <FormGroup>
            {groups.map((groupItem) => (
                <FormControlLabel
                  key = {groupItem}
                  control = {<Checkbox
                              id = {groupItem}
                              onChange = {handleCheckboxChange}
                             />}
                  label = {groupItem} 
                />
            ))}
          </FormGroup>
        </FormControl>
        {groups.length !== 0 &&  <Button
                                  aria-describedby = {id}
                                  variant = "contained"
                                  onClick = {handleSubmitBtn}
                                  className = "btn"
                                 >
                                  Apply
                                 </Button>}
      </div>
    </Popover>
  );
}
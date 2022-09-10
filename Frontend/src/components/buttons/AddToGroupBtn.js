// Imports from React and external dependencies.
import React, {useState} from 'react';

import Button from '@mui/material/Button';
import Menu from '@mui/material/Menu';
import { useSelector, useDispatch } from 'react-redux';
import { addStudentToGroup } from '../popups/GroupSlice';

// imports for check box
import Box from '@mui/material/Box';
import FormLabel from '@mui/material/FormLabel';
import FormControl from '@mui/material/FormControl';

import FormControlLabel from '@mui/material/FormControlLabel';
import Radio from '@mui/material/Radio';
import RadioGroup from '@mui/material/RadioGroup';

// Stylesheet imports.
import '../mui-styles.css';

function isEmptyOrSpaces(str){
  return str === null || str.match(/^ *$/) !== null;
}

export default function AddToGroupBtn(props) {
    const groups = useSelector((state) => state.group.groups); 

    const dispatch = useDispatch();

    const [anchorEl, setAnchorEl] = React.useState(null);
    const open = Boolean(anchorEl);

    // State management for selected group in radio form.
    const [selectedGroup, updateSelectedGroup] = useState("");
    
    // Event handler for radio form.
    const handleChange = (event) => updateSelectedGroup(event.target.value);

    // event handler for submit button
    const handleSubmit = () => {
      if (isEmptyOrSpaces(selectedGroup)) {
        alert("Please select a group!");
        return;
      }
      props.updateSelectedStudents([]);
      handleClose();
      props.onClick();
      dispatch(addStudentToGroup(selectedGroup));
    };
    const handleClose = () => {
      setAnchorEl(null);
    }

    // event handler for the addToGroup button (generates the student list once clicked)
    const handleClick = (event) => {
      if (props.selectedStudents.length !== 0) setAnchorEl(event.currentTarget);
      else alert("Please select at least one student!");
    };

    // popover styling
    const id = open ? 'simple-popover' : undefined;
    return (
        <div>
          <Button
            aria-controls={open ? 'basic-menu' : undefined}
            aria-haspopup="true"
            aria-expanded={open ? 'true' : undefined}
            onClick={handleClick}
            className="add-to-group-btn btn"
          >
            Add to Group
          </Button>

          <div className="add-to-group-btn-content">
            <Menu
                id="basic-menu"
                anchorEl={anchorEl}
                open={open}
                onClose={handleClose}
                MenuListProps={{'aria-labelledby': 'basic-button'}}
            >
              <Box sx={{ display: 'flex' }}>
                <FormControl component="fieldset" variant="standard">
                  { groups.length === 0 && <p>
                                              <b>No custom groups to select!</b>
                                            </p> }

                  { groups.length !==  0 && <FormLabel
                                              component="legend">
                                                Select a group:
                                            </FormLabel>}
                  <RadioGroup
                    onChange = {handleChange}
                  >
                    {groups.map((group) => (
                      <FormControlLabel
                        value = {group}
                        control = {<Radio />} 
                        label = {group}
                        key = {group}
                      />
                    ))}
                  </RadioGroup>

                  { groups.length !== 0 &&  <Button
                                              aria-describedby = {id}
                                              variant = "contained"
                                              onClick={handleSubmit}
                                              className="btn"
                                            >
                                              Apply
                                            </Button>}
                </FormControl>
              </Box>
            </Menu>
          </div>
        </div>
    );
}
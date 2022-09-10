/**
 * This project is licensed under the GNU GPL v3 license
 * @author Ashley Mead, Rosie Shen, Shakeel Khan
 * @version 1.1
 * @since 7/6/2022
 * 
 * Abstract:
 * This component defines the "Manage Groups" button and the resulting menu that drops down from
 * it. This component handles the menu as keep track of the anchors for the "Create Group" and 
 * "Delete Group" popovers.
 * 
 * The anchors, when clicked upon, are the entries in the "Manage Groups" menu. The "Create Group"
 * popover is anchored to the "Create Group" entry, and the "Delete Group" entry is anchored to the
 * "Delete Group" entry. These anchors are passed via props as state variables to those respective
 * popovers.
 * 
 * This component takes no props, nor does it utilizes any Redux store. All state management is
 * done locally.
 */

// #######
// IMPORTS
// #######

// React component imports.
import { React, useState } from 'react';

// Imports from external dependencies.
import Button from '@mui/material/Button';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';

// Custom component imports.
import CreateGroupPopover from '../popups/CreateGroupPopover';
import DeleteGroupPopover from '../popups/DeleteGroupPopover';

// Stylesheet imports.
import '../mui-styles.css';

// #########
// CONSTANTS
// #########
const MENU_ID = "manage-groups-menu";
const MENU_BTN_ID = "manage-groups-menu-btn";

// #########
// COMPONENT
// #########
export default function ManageGroupsMenu() {
  // This state var store the element in the DOM the menu should be anchored to.
  const [menuAnchorEl, setMenuAnchorEl] = useState(null);

  // Event handlers for opening and closing the manage groups menu.
  const handleMenuItemClick = (event) => {
    setMenuAnchorEl(event.currentTarget);
  };

  const handleMenuClose = () => {
    setMenuAnchorEl(null);
  };

  // This flag is for determining whether the menu should be open.
  const open = Boolean(menuAnchorEl);

  // This is for the create and delete group popovers. This way, they know which element to anchor
  // themselves to.
  const [createAnchorEl, setCreateAnchorEl] = useState(null);
  const [deleteAnchorEl, setDeleteAnchorEl] = useState(null);

  // JSX.
  return (
    <div>
      <Button
        id = {MENU_BTN_ID}
        aria-controls = {open ? MENU_ID : undefined}
        aria-haspopup = "true"
        aria-expanded = {open ? 'true' : undefined}
        onClick = {handleMenuItemClick}
        className = "manage-groups-btn btn"
      >
        Manage Groups
      </Button>
      <Menu
        id = {MENU_ID}
        anchorEl = {menuAnchorEl}
        open = {open}
        onClose = {handleMenuClose}
        MenuListProps = {{
          'aria-labelledby': MENU_BTN_ID,
        }}
      >
        {/* Create group menu item. */}
        <MenuItem 
          onClick = {(event) => setCreateAnchorEl(event.currentTarget)}
        >
            <p>Create Group</p>
        </MenuItem>

        {/* Delete group(s) menu item. */}
        <MenuItem
          onClick = {(event) => setDeleteAnchorEl(event.currentTarget)}
        >
          <p>Delete Group</p>
        </MenuItem>
      </Menu>

      {/* Define our create group popover here. This is anchored to it's corresponding menu item, and
          is only displayed when someone clicks on the menu items. It goes away if the user clicks
          outside of the menu or clicks the submit button in the popover. */}
      <CreateGroupPopover
        anchorEl = {createAnchorEl}
        setAnchorEl = {setCreateAnchorEl}
        menuCloseHandler = {handleMenuClose}
      />

      {/* Define our delete group popover here. Works in a similar manner to the create groupn
          popover. */}
      <DeleteGroupPopover
        anchorEl = {deleteAnchorEl}
        setAnchorEl = {setDeleteAnchorEl}
        menuCloseHandler = {handleMenuClose}
      />
    </div>
  );
}
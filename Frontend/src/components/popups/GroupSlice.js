/**
 * This project is licensed under the GNU GPL v3 license
 * @author Rosie Shen, Ashley Mead, Shakeel Khan
 * @version 1.0
 * @since 7/6/2022
 * 
 * ABSTRACT:
 * This is the Redux slice for all group related functionality. It stores the current list of
 * groups for any component that needs access to them, and it help facilitate the process of
 * creating a group, deleting a group, and adding a student to a group.
 * 
 * We go over the way it works by going over the state data, and what each part means.
 *  - action: this tells you what action need to be taken. For example, if the "Create Group" form
 *            is submitted, that form will dispatch the "createGroup" action to the store, passing
 *            in relevant information. You can check this variable in any component to see what
 *            action (Axios connection) needs to be performed. Once you've performed the action, 
 *            you must dispatch the "completedAction" action to the store, which sets action to the
 *            value of the constant NO_ACTION.
 *  - groups: this contains the current list of custom groups. This can be updated by dispatching
 *            the "updateGroups" action to the store, where you pass in the new list of groups.
 *  - students: this contains a list of students. This is used specifically for the
 *              ADD_TO_STUDENT_TO_GROUP action, where you may add a number of student to a select
 *              groups. Each students should be represented as a dictionary, with two keys: "email"
 *              and "planNum". The former is the email address/ID of the student, the latter is the
 *              number of the plan beloning to the student that should be added to the specified
 *              group.
 *  - tmpGroup: this variable is used by all the actions. For CREATE_GROUP and
 *              ADD_STUDENT_TO_GROUP, it simply is a string containing the name of the group to be
 *              created/updated respectively. For DELETE_GROUP, a user can delete multiple groups
 *              at once. In this case, tmpGroup is a list of the names of the groups to be deleted.
 *              It's worth noting that this is handled internally - you don't need to concern
 *              yourself with this.
 * 
 * SAMPLE USAGE:
 * To properly explain how this works we'll take CREATE_GROUP as an example. When the form has been
 * submitted, the create group component will dispatch the "createGroup" action to the store. This
 * action will take one parameter: the name of the new group. As you can see in the "createGroup"
 * reducer, we set tmpGroup to this new group name (the payload of the action) and we set the
 * action variable to CREATE_GROUP.
 * 
 * Then, in another component, for example Page.js, where you likely perform all your Axios
 * connections, you would setup a useSelector hook for this slice, which will trigger any time the
 * data in this slice has changed. When this does, you check for the action variable. If it's 
 * NO_ACTION, simply perform no action. But, in this example, where it'll be CREATE_GROUP, you can 
 * perform whatever logic/Axios connection necessary, and you can easily pull the appropriate data
 * from the store.
 * 
 * NOTE: All these words (NO_ACTION, CREATE_GROUP, DELETE_GROUP, and ADD_STUDENT_TO_GROUP) are all
 *       defined as constants in this file and exported. The intention here is, when checking the
 *       action variable in the slice, you should compare it to these constants (which you import).
 *       This makes the code easier to understand and eliminates the chance of you messing up your
 *       logic when checking this variable.
 * 
 * MISC.:
 * This file defines the initial state of this slice, the actions you can take and the
 * corresponding reducers for those actions.
 * 
 * The Reducer is the default import. The actions are also exported so that any component can
 * dispatch them to the store.
 */

// #######
// IMPORTS
// #######

import { createSlice } from '@reduxjs/toolkit'

// #########
// CONSTANTS
// #########
const NO_ACTION = "no action";
const CREATE_GROUP = "create group";
const DELETE_GROUP = "delete group";
const ADD_STUDENT_TO_GROUP = "add to student group";

export { NO_ACTION, CREATE_GROUP, DELETE_GROUP, ADD_STUDENT_TO_GROUP };

// #####
// SLICE
// #####
export const GroupSlice = createSlice({
  name: 'group',
  initialState: {
    groups: [],
    students: [],   // Format: {email: "student_id_here", planNum: 1}.
    tmpGroup: "",
    action: NO_ACTION
  },
  reducers: {
        // This is to be used by Page.js to update the current list of groups from the backend.
        // Pass in a new list to overwrite the old one.
        updateGroups: (state, action) => {
          state.groups = action.payload;
        },
        // This will be exclusively dispatched by the results card which keeps track of the
        // selected students prior to the "Apply" button being clicked in the "Add Student to
        // Group" popover. Simply pass in a new list of students to overwrite the old one.
        updateStudents: (state, action) => {
          state.students = action.payload;
        },
        // This is to be dispatched once an action is complete. This simply sets the "action"
        // variable to the value of NO_ACTION.
        completedAction: (state) => {
          state.action = NO_ACTION;
        },
        // This is to be dispatched by the delete group form, which must pass a list of the names
        // of the groups to be deleted.
        deleteGroup: (state, action) => {
          state.tmpGroup = action.payload;
          state.action = DELETE_GROUP;
        },
        // This is to be dispatched by the create group form, which must pass in the name for the
        // new group.
        createGroup: (state, action) => {
          state.tmpGroup = action.payload;
          state.action = CREATE_GROUP;
        },
        // This is to be called by the add student to group form, which must pass in the name of
        // the group the students must be added to. The actual list of students is maintained by
        // the ResultsCard which dispatches the updateStudents action (i.e,. you must make two
        // dispatches: "updateStudents" to pass in the students, then "addStudentToGroup" to pass
        // in the name of the group to be updated. The latter also sets the action to
        // ADD_STUDENT_TO_GROUP).
        addStudentToGroup: (state, action) => {
          state.tmpGroup = action.payload;
          state.action = ADD_STUDENT_TO_GROUP;
        }
  },
})

// Export the actions.
export const { deleteGroup,
              updateGroups,
              updateStudents,
              completedAction, 
              createGroup, 
              addStudentToGroup } = GroupSlice.actions;

// Export the reducer.
export default GroupSlice.reducer;
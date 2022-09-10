/**
 * This project is licensed under the GNU GPL v3 license
 * @author 
 * @version 1.0
 * @since 8/4/2022
 * 
 * Abstract:
 * This is the Redux slice for the manage filters. It stores all the currently selected
 * information in those filters, so they can be accessed by any other component in the page.
 * 
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


// This is what the reportConfig property of the state should be set to once a report has been run.
// This also tells any components listening that there is no report to run.
const DEFAULT_REPORT_CONFIG = {
    order: 'def',
    grade: 'def',
    planNum: 'def',
    planStatus: 'def'
}

// #####
// SLICE
// #####
export const ManageFilterSlice = createSlice({
    name: 'analyticsFilters',
    initialState: {
        reportConfig: DEFAULT_REPORT_CONFIG
    },
    reducers: {
        // This should be dispatched when the "Run Report" button in the Filters card is clicked.
        searchStudent: (state, action) => {
            state.reportConfig = action.payload;
        },
        // This is dispatched by AnalyticsAllPage when it has completed querying the filters
        // endpoint.
        completeSearch: (state) => {
            state.reportConfig = DEFAULT_REPORT_CONFIG;
        },


    }
});

// Export the actions.
export const { runReport, completedReport, changeActiveCard } = ManageFilterSlice.actions;

// Export the reducer.
export default ManageFilterSlice.reducer;
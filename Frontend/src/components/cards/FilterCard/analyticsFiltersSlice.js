/**
 * This project is licensed under the GNU GPL v3 license
 * @author Shakeel Khan
 * @version 1.0
 * @since 7/6/2022
 * 
 * Abstract:
 * This is the Redux slice for the analytics filters. It stores all the currently selected
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
const CAREER_DATA_CARD = 'Career Data';
const EDUCATION_DATA_CARD = 'Education Data';
const MY_GROUPS_CARD = 'My Groups';

export { CAREER_DATA_CARD, EDUCATION_DATA_CARD, MY_GROUPS_CARD };

// This is what the reportConfig property of the state should be set to once a report has been run.
// This also tells any components listening that there is no report to run.
const DEFAULT_REPORT_CONFIG = {
    typeVal: 'def',
    order: 'def',
    grade: 'def',
    planNum: 'def'
}

// #####
// SLICE
// #####
export const AnalyticsFiltersSlice = createSlice({
    name: 'analyticsFilters',
    initialState: {
        activeCard: CAREER_DATA_CARD,
        reportConfig: DEFAULT_REPORT_CONFIG
    },
    reducers: {
        // This should be dispatched when the "Run Report" button in the Filters card is clicked.
        runReport: (state, action) => {
            state.reportConfig = action.payload;
        },
        // This is dispatched by AnalyticsAllPage when it has completed querying the filters
        // endpoint.
        completedReport: (state) => {
            state.reportConfig = DEFAULT_REPORT_CONFIG;
        },
        // This is for updating the active card.
        changeActiveCard: (state, action) => {
            state.activeCard = action.payload;
        }
    }
});

// Export the actions.
export const { runReport, completedReport, changeActiveCard } = AnalyticsFiltersSlice.actions;

// Export the reducer.
export default AnalyticsFiltersSlice.reducer;
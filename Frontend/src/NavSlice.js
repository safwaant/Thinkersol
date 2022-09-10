import { createSlice } from '@reduxjs/toolkit'

const PROFILE_PAGE = "PROFILE";
const DASHBOARD_PAGE = "DASHBOARD";
const MANAGE_PAGE = "MANAGE";
const ANALYTICS_PAGE = "ANALYTICS";

export { PROFILE_PAGE, DASHBOARD_PAGE, MANAGE_PAGE, ANALYTICS_PAGE };

export const NavSlice = createSlice({
    name: 'navigation',
    initialState: {
        currentPage: DASHBOARD_PAGE
    },
    reducers: {
        setToProfilePage: (state, action) => {
            state.currentPage = PROFILE_PAGE;
        },

        setToDashboardPage: (state, action) => {
            state.currentPage = DASHBOARD_PAGE;
        },

        setToManagePage: (state, action) => {
            state.currentPage = MANAGE_PAGE;
        },

        setToAnalyticsPage: (state, action) => {
            state.currentPage = ANALYTICS_PAGE;
        }
    }

});

// Set page are generated for each case reducer function
export const { setToProfilePage, setToDashboardPage, setToManagePage, setToAnalyticsPage } = NavSlice.actions;

// default export
export default NavSlice.reducer;
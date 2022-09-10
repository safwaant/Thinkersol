/**
 * This project is licensed under the GNU GPL v3 license
 * @author Shakeel Khan
 * @version 1.0
 * @since 7/6/2022
 * 
 * Abstract:
 * This configures the Redux store for the project. Here we simply import the reducers from all our
 * slices and use them in the initialization of the Redux store.
 */

// #######
// IMPORTS
// #######

// Imports from external dependencies.
import { configureStore } from "@reduxjs/toolkit";

// Import all our reducers.
import analyticsFiltersReducer from "./components/cards/FilterCard/analyticsFiltersSlice";
import groupReducer from "./components/popups/GroupSlice";
import navigationReducer from "./NavSlice";
import searchManageFiltersReducer from "./pages/SearchSlice";

// ######
// CONFIG
// ######
export default configureStore ({
    reducer: {
        group: groupReducer,
        analyticsFilters: analyticsFiltersReducer,
        navigation: navigationReducer,
        searchManageFilters: searchManageFiltersReducer
    },
});
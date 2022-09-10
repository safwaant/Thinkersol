
import { createSlice } from '@reduxjs/toolkit'


const NO_ACTION = "no action";
const SEARCH = "search";

const DEFAULT_SEARCH_CONFIG = {
  schoolName: 'def',
  studentLastname: 'def',
  sort: 'def',
  grades: 'def', // TODO endpoint documentation states that this is a multi select, but current implementation is a single select
  planStatus: 'def',
  plans: 'def',
}

export { NO_ACTION, SEARCH, DEFAULT_SEARCH_CONFIG};



// #####
// SLICE
// #####

console.log(DEFAULT_SEARCH_CONFIG);
export const SearchSlice = createSlice({
    name: 'searchManageFilters',
    initialState: {
        searchConfig: DEFAULT_SEARCH_CONFIG,
        test: "AA"
    },
    reducers: {
          // This is to be used by Page.js to update the current list of groups from the backend.
          // Pass in a new list to overwrite the old one.
          updateSearchValue: (state, action) => {
            state.searchConfig.studentLastname = action.payload;
            console.log("update to studentLastname made")
            console.log(state.searchConfig);
          },
          searchManage: (state, action) => {
            state.reportConfig = action.payload;
          },
          completeSearchManage: (state) => {
            state.reportConfig = DEFAULT_SEARCH_CONFIG;
          }
    },
  })
  
  // Export the actions.
  export const { updateSearchValue, searchManage, completeSearchManage } = SearchSlice.actions;

  // Export the reducer.
  export default SearchSlice.reducer;
/**
 * This project is licensed under the GNU GPL v3 license
 * @author Ashley Mead, Rosie Shen, Shakeel Khan
 * @version 1.1
 * @since 7/6/2022
 * 
 * Abstract:
 * This component contains everything for the top bar. The top bar contains some text which is the
 * title of the current page.
 * 
 * No Redux store is utilized. All this takes for props is that title text.
 * TODO: Update the previous line once the Redux code with the top and side nav has been
 * implemented.
 */

// #######
// IMPORTS
// #######

// React component imports.
import React from 'react';

// Stylesheet imports.
import './TopNav.css';

import { PROFILE_PAGE, DASHBOARD_PAGE, MANAGE_PAGE, ANALYTICS_PAGE } from '../../NavSlice';
import { useSelector } from 'react-redux';

function TopNav(props) {

    const navSliceData = useSelector(state => state.navigation);
    const currentPage = navSliceData.currentPage;


    return (
        <div className = "top-nav">
            <nav>
                <div className="top-nav-item">
                    <strong>{currentPage}</strong>
                </div>
            </nav>
        </div>
    );
}

export default TopNav;
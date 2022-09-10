/**
 * This project is licensed under the GNU GPL v3 license
 * @author Shakeel Khan
 * @version 1.0
 * @since 7/6/2022
 * 
 * Abstract:
 * This is the page layout component. This basically sets up the top bar and side nav, and you can
 * create your pages inside like so:
 * 
 * <PageLayout>
 *      // Page content goes here!
 * </PageLayout>
 * 
 * The styling has been done such that the page content will be centered and take up 80% of the
 * remaining whitespace.
 * 
 * Also, this whole component is wrapped with the <StyledEngineProvider /> component - a Material
 * UI component that allows you to override the styling of MUI component with your own styling, 
 * without having to use !important for each attribute.
 */

// #######
// IMPORTS
// #######

// React component imports.
import React from 'react';

// Imports from external dependencies.
import { StyledEngineProvider } from '@mui/material/styles';

// Custom component imports.
import TopNav from '../components/navbars/TopNav';
import SideNav from '../components/navbars/SideNav';

// Stylesheet imports.
import './PageLayout.css';


// #########
// COMPONENT
// #########
export default function PageLayout(props) {
    return (
        <StyledEngineProvider injectFirst>
            <div>
                {/* Setup our top nav bar. */}
                <TopNav description = 'ANALYTICS'/>
                
                <div className = "sideNavAndPageContentWrapper">
                    {/* Setup the side nav bar. */}
                    <SideNav />




                    {/* This extra div is to help center the page content. */}
                    <div className = "pageContentParent">
                        {/* Page content goes here. */}
                        <div className = "pageContent">
                            {props.children}
                        </div> {/* Page content end - this is where the stuff goes! */}
                    </div> {/* Page content parent end. This is used to center the page content in
                               the remaining whitespace. */}
                </div> {/* sideNavAndPageContentWrapper end. */}
            </div>
        </StyledEngineProvider>
    );
}
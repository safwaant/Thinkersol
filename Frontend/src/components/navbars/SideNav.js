/**
 * This project is licensed under the GNU GPL v3 license
 * @author Ashley Mead, Rosie Shen, Shakeel Khan
 * @version 1.1
 * @since 7/11/2022
 * 
 * Abstract:
 * This component contains everything for the side navigation bar. It has icons that serve as links
 * to the different pages.
 * 
 * No Redux store is utilized, nor does this component take any props.
 */

// #######
// IMPORTS
// #######

// React component imports.
import React from 'react';
import { Link } from "react-router-dom";

// Stylesheet imports.
import './SideNav.css';

// Icon imports.
import TOOLS_ICON from "../../img/tools.svg";
import GRAPH_ICON from "../../img/graph-up.svg";
import PROFILE_ICON from "../../img/person-square.svg";
import CLIPBOARD_ICON from "../../img/clipboard-minus.svg";


const ADMIN_ANALYTICS_PAGE_ROUTE = "../analytics-all";

// #########
// COMPONENT
// #########
export default function SideNav() {
    return (
        <div className="navWrapper">
            <nav>
                <ul>
                    {/* Profile button. */}
                    <li>
                        <Link
                            className = "navLink"
                            to = '/profile'
                        >
                            <img
                                className = "side-nav-img"
                                src = {PROFILE_ICON}
                                alt = "profile"
                            />
                            <br/>
                            <strong>Profile</strong>
                        </Link>
                    </li>

                    {/* Dashboard button. */}   
                    <li>
                        <Link
                            className = "navLink"
                            to = '/dashboard'>
                            <img
                                className = "side-nav-img"
                                src = {CLIPBOARD_ICON}
                                alt = "clipboard"
                            />
                            <br/>
                            <strong>Dashboard</strong>
                        </Link>
                    </li>

                    {/* Manage/Tools button. */}
                    <li>
                        <Link
                            className = "navLink"
                            to = '/manage'
                        >
                            <img
                                className = "side-nav-img"
                                src = {TOOLS_ICON}
                                alt = "tools"
                            />
                            <br/>
                            <strong>Manage</strong>
                        </Link>
                    </li>

                    {/* Analytics button. */}
                    <li>
                        <Link
                            className = "navLink"
                            // to = '/analytics'
                            to = {ADMIN_ANALYTICS_PAGE_ROUTE}
                        >
                            <img
                                className = "side-nav-img"
                                src = {GRAPH_ICON}
                                alt = "graph"
                            />
                            <br/>
                            <strong>Analytics</strong>
                        </Link>
                    </li>
                </ul>
            </nav>
        </div>
    );
}
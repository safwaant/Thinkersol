/**
 * This project is licensed under the GNU GPL v3 license
 * @author Ashley Mead, Rosie Shen
 * @version 1.1
 * @since 7/6/2022
 * 
 * Abstract:
 * The definition for our app. This is where the router is defined, along with the PageLayout
 * component. The router is configured such that whichever selected page will be rendered inside
 * the PageLayout component.
 */

// #######
// IMPORTS
// #######

// React component imports.
import React from 'react';
import { BrowserRouter, Routes, Route, Outlet } from "react-router-dom";

// Custom component imports.
import AnalyticsAllPage from './pages/AnalyticsAllPage/AnalyticsAllPage';
import PageLayout from './pages/PageLayout';
import DashboardPage from'./pages/DashboardPage';
import ManageAllPage from './pages/ManageAllPage';
import LoginPage from './pages/LoginPage';


// Stylesheet imports.
import './App.css';
import ManageStudentPage from './pages/ManageStudentPage';

// #########
// CONSTANTS
// #########
const ADMIN_ANALYTICS_PAGE_ROUTE = "/analytics-all";


export default function App() {
  return (
    <BrowserRouter>
      <Routes>
          {/* The outlet here specifies where the child should go. */}
          <Route element={<PageLayout> <Outlet /> </PageLayout>}>
            {/* <Route path="/" element={<ManageStudentPage/>}></Route> */}
            <Route path="/" element={<LoginPage/>}/>
            <Route path="/profile"/>
            <Route path="/manage" element={<ManageAllPage/>}/>
            <Route path='/dashboard' element={<DashboardPage/>}/> 
            <Route path = {ADMIN_ANALYTICS_PAGE_ROUTE} element={<AnalyticsAllPage/>}/>
          </Route>
      </Routes>
    </BrowserRouter>
  );
}
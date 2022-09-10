/**
 * This project is licensed under the GNU GPL v3 license
 * @author Ashley Mead, Rosie Shen, Shakeel Khan
 * @version 1.0
 * @since 7/6/2022
 * 
 * Abstract:
 * This component creates and renders the root element in the DOM.
 */

// #######
// IMPORTS
// #######

// React component imports.
import React from 'react';
import ReactDOM from 'react-dom/client';

// Imports from external dependencies.
import { Provider } from 'react-redux';

// Custom component imports.
import App from './App';
import store from './Store';

// Stylesheets.
import './index.css';

// #########
// COMPONENT
// #########

// Create the root element in the DOM and render it.
const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <Provider store = {store}>
      <App />
    </Provider>
  </React.StrictMode>
);
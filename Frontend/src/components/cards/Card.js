/**
 * This project is licensed under the GNU GPL v3 license
 * @author Ashley Mead, Rosie Shen, Shakeel Khan
 * @version 1.1
 * @since 7/6/2022
 * 
 * Abstract:
 * This is the general Card component. You can provide your own header and whatever content you'd
 * like. The only thing you can specify via props is the color of the card (color).
 * 
 * Here's an example of how to use this component:
 * <Card>
 *     <Card.Header>
 *         // Header goes here! You can put anything you'd like (text, labels, buttons, etc.)
 *     </Card.Header>
 *     <Card.Content>
 *         // Any content you'd like goes here!
 *     </Card.Content>
 * </Card>
 */

// #######
// IMPORTS
// #######

// React component imports.
import React from 'react';

// Stylesheet imports.
import './Card.css';

// #########
// COMPONENT
// #########
function Card(props) {
    return (
        <div className = "border" style = {{ background: props.color }}>
            {props.children}
        </div>          
    );
}

// ####################
// COMPONENT NAMESPACES
// ####################
Card.Header = (props) => (
                        <div className = "header">
                            {props.children}
                        </div>);
Card.Content = (props) => (
                        <div className = "whitespace-parent">
                            <div className = "whitespace">
                                {props.children}
                            </div>
                        </div>
                        );

export default Card;
import React from 'react';
import { NavLink, Link } from 'react-router-dom';
import { userData } from "../App";
import '../scss/sidebar.scss';
import labconnectLogo from "../img/labconnect-logo.png";

function Sidebar(): JSX.Element | null {
    
    let metadata;
    
    if (userData.roleType === "STUDENT") {
        metadata = (
            <div id="nav-metadata">
                Instructor: <span>{userData.instructor}</span><br />
                TA: <span>{userData.assistant}</span>
            </div>
        );
    } else if (userData.roleType === "INSTRUCTOR") {
        metadata = (
            <div id="nav-metadata">
                Course: <span>{userData.course.name} | Section {userData.course.section}</span>
            </div>
        );
    } else {
        metadata = (
            <div id="nav-metadata">
                Course: <span>{userData.course.name} | Section {userData.course.section}</span><br />
                Instructor: <span>{userData.instructor}</span>
            </div>
        );
    }

    return (
        <nav id="nav-sidebar">
            <div id="nav-header">
                <img src={labconnectLogo} />
                <p>LabConnect</p>
            </div>
            { metadata }
            <ul id="nav-links">
                <li>
                    <NavLink exact to="/" className="nav-link" activeClassName="nav-active">
                        <span className="material-icons">assignment</span>
                        <span className="nav-link-text">Dashboard</span>
                    </NavLink>
                </li>
                <li>
                    <NavLink to="/analysis" className="nav-link" activeClassName="nav-active">
                        <span className="material-icons">insights</span>
                        <span className="nav-link-text">Analysis</span>
                    </NavLink>
                </li>
                <li>
                    <NavLink to="/my-notes" className="nav-link" activeClassName="nav-active">
                        <span className="material-icons">note</span>
                        <span className="nav-link-text">My Notes</span>
                    </NavLink>
                </li>
                <li>
                    <NavLink to="/messages" className="nav-link" activeClassName="nav-active">
                        <span className="material-icons">message</span>
                        <span className="nav-link-text">Messages</span>
                    </NavLink>
                </li>
                <li>
                    <NavLink to="/announcements" className="nav-link" activeClassName="nav-active">
                        <span className="material-icons">campaign</span>
                        <span className="nav-link-text">Announcements</span>
                    </NavLink>
                </li>
                {userData.roleType === "INSTRUCTOR" ? <li>
                    <NavLink to="/instructor-panel" className="nav-link" activeClassName="nav-active">
                        <span className="material-icons">vpn_key</span>
                        <span className="nav-link-text">Instructor Panel</span>
                    </NavLink>
                </li> : undefined}
            </ul>
            <div style={{ display: 'none' }} id="nav-livesession"></div>
            <ul id="nav-options">
                <li>
                    <Link to="/logout" className="nav-link">
                        <span className="material-icons">logout</span>
                        <span className="nav-link-text">Logout</span>
                    </Link>
                </li>
                <li>
                    <NavLink to="/settings" className="nav-link" activeClassName="nav-active">
                        <span className="material-icons">settings</span>
                        <span className="nav-link-text">Settings</span>
                    </NavLink>
                </li>
            </ul>
        </nav>
    );
}

export default Sidebar;

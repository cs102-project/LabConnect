import React, { useEffect, useState } from 'react';
import { NavLink, Link } from 'react-router-dom';
import '../scss/sidebar.scss';
import labconnectLogo from "../img/labconnect-logo.png";
import APITools, { IUserSelf } from '../APITools';

function Sidebar(): JSX.Element | null {
    
    const [userSelf, setUserSelf] = useState<IUserSelf>();
    
    let metadata;
    
    useEffect(() => {
        APITools.getUserSelf().then((response) => {
            setUserSelf(response);
        });
        
        if (userSelf?.roleType === "STUDENT") {
            metadata = (
                <div id="nav-metadata">
                    Instructor: <span>{userSelf.instructor}</span><br />
                    TA: <span>{userSelf.assistant}</span>
                </div>
            );
        } else if (userSelf?.roleType === "INSTRUCTOR") {
            metadata = (
                <div id="nav-metadata">
                    Course: <span>{userSelf.courses[0].course} | Section {userSelf.courses[0].section}</span>
                </div>
            );
        } else {
            metadata = (
                <div id="nav-metadata">
                    Course: <span>{userSelf?.courses[0].course} | Section {userSelf?.courses[0].section}</span><br />
                    Instructor: <span>{userSelf?.instructor}</span>
                </div>
            );
        }
        
    }, []);
    
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
                {userSelf?.roleType === "STUDENT" && <li>
                    <NavLink to="/my-notes" className="nav-link" activeClassName="nav-active">
                        <span className="material-icons">note</span>
                        <span className="nav-link-text">My Notes</span>
                    </NavLink>
                </li>}
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
                {userSelf?.roleType === "INSTRUCTOR" && <li>
                    <NavLink to="/instructor-panel" className="nav-link" activeClassName="nav-active">
                        <span className="material-icons">vpn_key</span>
                        <span className="nav-link-text">Instructor Panel</span>
                    </NavLink>
                </li>}
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

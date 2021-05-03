import React from "react";
import "../scss/dashboard.scss";
import { userData } from "../App";
import bilkentLogo from "../img/bilkent.png";

function CourseStatus(): JSX.Element {
    
    return (
        <div id="dashboard-course-status">
            <div id="dashboard-course-info">
                <img src={bilkentLogo} />
                <div>
                    <h3>{userData.institution}</h3>
                    <span>{userData.course.name} | Section {userData.course.section}</span>
                </div>
            </div>
            <div id="dashboard-course-progress">
                <div id="course-progress-text">
                    <p>Course Progress: 3 <span>/ 5 assignments</span></p>
                    <p>Next Deadline: <span>4 days left!</span></p>
                </div>
                <div id="course-progress-bar">
                    <div></div>
                </div>
            </div>
        </div>
    );
    
}

export default CourseStatus;

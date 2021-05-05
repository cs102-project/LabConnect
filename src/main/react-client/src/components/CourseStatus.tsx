import React, { useEffect, useState } from 'react';
import '../scss/dashboard.scss';
import bilkentLogo from '../img/bilkent.png';
import APITools, { IAssignment, IUserSelf } from '../APITools';

function CourseStatus(): JSX.Element {
    
    const [userSelf, setUserSelf] = useState<IUserSelf>();
    const [assignments, setAssignments] = useState<IAssignment[]>();
    const [stats, setStats] = useState({ complete: 0, total: 1 });
    
    useEffect(() => {
        APITools.getUserSelf().then((response) => {
            setUserSelf(response);
        });
        APITools.getAllAssignments().then((response) => {
            setAssignments(response);
            setStats({
                complete: assignments?.filter((el) => APITools.helpers.isAssignmentComplete(el))?.length || 0,
                total: assignments?.length || 1
            });
        });
    }, []);
    
    return (
        <div id="dashboard-course-status">
            <div id="dashboard-course-info">
                <img src={bilkentLogo} />
                <div>
                    <h3>{userSelf?.institution}</h3>
                    <span>
                        {userSelf?.courses[0].course} | Section {userSelf?.courses[0].section}
                    </span>
                </div>
            </div>
            <div id="dashboard-course-progress">
                <div id="course-progress-text">
                    <p>
                        Course Progress: {stats.complete} <span>/ {stats.total} assignments</span>
                    </p>
                    <p>
                        Next Deadline: <span>4 days left!</span>
                    </p>
                </div>
                <div id="course-progress-bar">
                    <div style={{ width: (stats.complete / stats.total).toFixed(3) + "%" || "50%" }}></div>
                </div>
            </div>
        </div>
    );
}

export default CourseStatus;

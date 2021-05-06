import React, { useEffect, useState } from 'react';
import '../scss/dashboard.scss';
import bilkentLogo from '../img/bilkent.png';
import APITools, { IUserSelf } from '../APITools';
import { DateTime } from 'luxon';

function CourseStatus(): JSX.Element {
    const [userSelf, setUserSelf] = useState<IUserSelf>();
    const [stats, setStats] = useState({ complete: 0, total: 1, nextDeadline: '' });

    useEffect(() => {
        APITools.getUserSelf().then((response) => {
            setUserSelf(response);
        });
        APITools.getAllAssignments().then((assignments) => {
            setStats({
                complete: assignments?.filter((el) => APITools.helpers.isAssignmentComplete(el))?.length || 0,
                total: assignments?.length || 1,
                nextDeadline: (() => {
                    const closestUnixDate = assignments
                        ?.slice()
                        .filter((assignment) => !APITools.helpers.isAssignmentComplete(assignment))
                        ?.sort((a, b) => parseInt(a.dueDate) - parseInt(b.dueDate))[0].dueDate;
                    const date = DateTime.fromMillis(parseInt(closestUnixDate));
                    const dateDiff = date.diffNow(['days', 'hours']);

                    return dateDiff.days >= 1
                        ? `${Math.floor(dateDiff.days)} days left!`
                        : `${Math.floor(dateDiff.hours)} hours left!`;
                })(),
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
                        Next Deadline: <span>{stats.nextDeadline}</span>
                    </p>
                </div>
                <div id="course-progress-bar">
                    <div style={{ width: ((stats.complete / stats.total) * 100).toFixed(3) + '%' || '50%' }}></div>
                </div>
            </div>
        </div>
    );
}

export default CourseStatus;

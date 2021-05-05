import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import APITools, { IAssignment } from '../APITools';
import '../scss/dashboard.scss';

function AssignmentsList(): JSX.Element {
    
    const [assignments, setAssignments] = useState<IAssignment[]>();
    
    useEffect(() => {
        APITools.getAllAssignments().then((response) => {
            setAssignments(response);
        });
    }, []);
    
    return (
        <div id="assignments-list">
            <h2>Assignments</h2>

            <div id="assignments-list-header">
                <span>Assignment Summary</span>
                <span>Deadline</span>
                <span>Grade</span>
                <span>Go to Assignment</span>
            </div>

            {assignments?.slice().sort((a, b) => parseInt(b.dueDate) - parseInt(a.dueDate)).map((assignment, i) => {
                return (
                    <article
                        key={i}
                        className={
                            APITools.helpers.isAssignmentComplete(assignment) ? 'inactive-assignment' : undefined
                        }
                    >
                        <section>
                            <div>
                                <span></span>
                                <h3>{assignment.title}</h3>
                            </div>
                            <p>{assignment.shortDescription}</p>
                        </section>
                        <section>{APITools.helpers.stringFromDate(assignment.dueDate)}</section>
                        <section>{assignment.grade || "N/A"}</section>
                        <section>
                            <Link to={`/assignments/${assignment.id}`}>
                                <span className="material-icons">find_in_page</span>
                            </Link>
                        </section>
                    </article>
                );
            })}

            {assignments?.length === 0 && <p id="dashboard-no-assignments">No assignments found</p>}
        </div>
    );
}

export default AssignmentsList;

import React from "react";
import { Link } from "react-router-dom";
import { userData } from "../App";
import "../scss/dashboard.scss";

function AssignmentsList(): JSX.Element {
    
    return (
        <div id="assignments-list">
            
            <h2>Assignments</h2>
            
            <div id="assignments-list-header">
                <span>Assignment Summary</span>
                <span>Deadline</span>
                <span>Grade</span>
                <span>Go to Assignment</span>
            </div>
            
            {userData.assignments.map((assignment, i) => {
                return (<article key={i} className={assignment.isComplete ? "inactive-assignment" : undefined}>
                    <section>
                        <div><span></span><h3>{assignment.title}</h3></div>
                        <p>{assignment.shortDescription}</p>
                    </section>
                    <section>{assignment.deadline}</section>
                    <section>{assignment.grade}</section>
                    <section><Link to={`/assignments/${assignment.assignmentId}`}><span className="material-icons">find_in_page</span></Link></section>
                </article>)
            })}
            
            {
                userData.assignments.length === 0 ? (<p id="dashboard-no-assignments">No assignments found</p>) : undefined
            }
            
        </div>
    );
    
}

export default AssignmentsList;

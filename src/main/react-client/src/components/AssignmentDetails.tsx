import React, { useEffect, useState } from "react";
import { useParams } from "react-router";
import PageHeader from "./PageHeader";
import "../scss/assignmentdetails.scss"
import { Link } from "react-router-dom";
import APITools, { IAssignment, IUserSelf } from "../APITools";

function AssignmentDetails(): JSX.Element {
    
    const { assignmentid } = useParams<{ assignmentid: string }>();
    
    const [assignment, setAssignment] = useState<IAssignment>();
    const [userSelf, setUserSelf] = useState<IUserSelf>();
    
    useEffect(() => {
        APITools.getAssignmentOf(assignmentid).then((response) => {
            setAssignment(response);
        });
        APITools.getUserSelf().then((response) => {
            setUserSelf(response);
        });
    }, []);
    
    return (
        <div id="assignment-details-container">
            <PageHeader pageName={assignment?.title || ""} />
            
            <main id="assignment-details-panel">
                <h3>{assignment?.homeworkType} Homework</h3>
                <a className="button" onClick={() => APITools.helpers.sendDownload(APITools.getInstructionsFileOf(assignmentid))}>Download Instructions</a>
                {
                    userSelf?.roleType === "STUDENT" ? <Link to={`${assignmentid}/submissions/9f3e9ab389ade4f83`} className="button">Go to Submission</Link> : undefined
                }
                <h4>Description:</h4>
                <p>{assignment?.shortDescription}</p>
                <p>You may have a maximum of {assignment?.maxAttempts} attempts in this assignment. The highest possible grade is {assignment?.maxGrade}.</p>
                <h4>Tests:</h4>
                <ul>
                    {
                        assignment?.tests.map((test, i: number) => (<li key={i}>{test}</li>))
                    }
                </ul>
            </main>
            
            {/* If the user is TA/instructor, add a list of appropiate submission links here */}
            
            
        </div>
    );
    
}

export default AssignmentDetails;

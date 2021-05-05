import React, { useEffect, useState } from "react";
import { useParams } from "react-router";
import PageHeader from "./PageHeader";
import "../scss/assignmentdetails.scss"
import { Link } from "react-router-dom";
import APITools, { IAssignment, ISubmission, IUserSelf } from "../APITools";
import AddAttemptButton from "./AddAttemptButton";

function AssignmentDetails(): JSX.Element {
    
    const { assignmentid } = useParams<{ assignmentid: string }>();
    
    const [userSelf, setUserSelf] = useState<IUserSelf>();
    const [assignment, setAssignment] = useState<IAssignment>();
    const [submission, setSubmission] = useState<ISubmission>();
    const [submissions, setSubmissions] = useState<ISubmission[]>();
    const [studentButton, setStudentButton] = useState(<span></span>);
    
    useEffect(() => {
        
        APITools.getUserSelf().then((response) => {
            setUserSelf(response);
            return response;
        }).then((userSelf) => {
            
            if (userSelf?.roleType === "STUDENT") {
                APITools.getSubmissionOfStudentFor(assignmentid).then((response) => {
                    if (typeof response !== "boolean") {
                        setSubmission(response);
                        setStudentButton(<Link to={`${assignmentid}/submissions/${response.id}`} className="button">Go to Submission</Link>);
                    } else {
                        setSubmission(undefined);
                        setStudentButton(<AddAttemptButton assignmentid={assignmentid} />);
                    }
                });
                
            } else if (userSelf?.roleType === "INSTRUCTOR" || userSelf?.roleType === "TEACHING_ASSISTANT") {
                
                APITools.getSubmissionsOfAssignment(assignmentid).then((response) => {
                    setSubmissions(response);
                });
                
            }
            
        });
        
        APITools.getAssignmentOf(assignmentid).then((response) => {
            setAssignment(response);
        });
        
    }, []);
    
    return (
        <div id="assignment-details-container">
            <PageHeader pageName={assignment?.title || ""} />
            
            <main id="assignment-details-panel">
                <h3>{assignment?.homeworkType} Homework</h3>
                <a className="button" onClick={() => APITools.getInstructionsFileOf(assignmentid, true)}>Download Instructions</a>
                { studentButton }
                <h4>Description:</h4>
                <p>{assignment?.shortDescription}</p>
                <p>You may have a maximum of {assignment?.maxAttempts} attempts in this assignment. The highest possible grade is {assignment?.maxGrade}.</p>
                <h4>Tests:</h4>
                <ul>
                    {
                        assignment?.tests.map((test, i: number) => (<li key={i}>{test.name}</li>))
                    }
                </ul>
            </main>
            
            {/* If the user is TA/instructor, add a list of appropiate submission links here */}
            
            
        </div>
    );
    
}

export default AssignmentDetails;

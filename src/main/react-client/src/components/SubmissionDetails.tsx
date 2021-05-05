import React, { ChangeEvent, useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import APITools, { IAssignment, ISubmission } from "../APITools";
import PageHeader from "./PageHeader";

// This page shows a list of attempts made to this assignment (the attempts of this submission)

function SubmissionDetails(): JSX.Element {
    
    const params = useParams<{ assignmentId: string, submissionId: string }>();
    
    const attemptSender = (e: ChangeEvent<HTMLInputElement>) => {
        
        APITools.addAttempt(params.assignmentId, e.target.files?.[0]);
        
    }
    
    const [assignment, setAssignment] = useState<IAssignment>();
    const [submission, setSubmission] = useState<ISubmission>();
    
    useEffect(() => {
        APITools.getSubmissionsOf(params.assignmentId).then((response) => {
            setSubmission(response[0]);
        });
        APITools.getAssignmentOf(params.assignmentId).then((response) => {
            setAssignment(response);
        });
    }, []);
    
    return (
        <div id="submission-details-container">
            <PageHeader pageName="Submission Details" />
            
            <main id="submission-details-panel">
                
                <h3>{assignment?.title}</h3> {/* temporary */}
                
                <button className="button" onClick={() => document.getElementById("add-attempt")?.click()}>Add Attempt</button>
                <input type="file" id="add-attempt" style={{ visibility: "hidden", height: 0 }} onChange={attemptSender} accept=".zip"/>
                
                <div id="submission-attempts-list-header">
                    <span>Attempt Identity</span>
                    <span>Grade</span>
                    <span>Test Success Rate</span>
                    <span>Go to Attempt</span>
                </div>
                
                {submission?.slice().sort((a, b) => b.id - a.id).map((attempt, i) => {
                    return (<article key={i}>
                        <section>#{attempt.id}: {attempt.attemptFilename}</section>
                        <section>{attempt.feedback.grade}</section>
                        <section>{attempt.testResults.filter(testResult => testResult.state === "SUCCESS").length} / {attempt.testResults.length}</section>
                        <section><Link to={`/assignments/${params.assignmentId}/submissions/${params.submissionId}/attempts/${attempt.id}`}><span className="material-icons">find_in_page</span></Link></section>
                    </article>)
                })}
                
            </main>
            
        </div>
    );
    
}

export default SubmissionDetails;

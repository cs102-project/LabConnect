import React, { ChangeEvent } from "react";
import { Link, useParams } from "react-router-dom";
import { userData } from "../App";
import PageHeader from "./PageHeader";

// This page shows a list of attempts made to this assignment (the attempts of this submission)

function SubmissionDetails(): JSX.Element {
    
    const params = useParams<{ assignmentid: string, submissionid: string }>();
    
    const submission = userData.submission;
    const successfulTestCount = submission.attempts.map(attempt => attempt.testResults.filter(testResult => testResult.state === "SUCCESS").length)
    
    const attemptSender = (e: ChangeEvent<HTMLInputElement>) => {
        
        const formData = new FormData();
        formData.append("attemptArchive", e.target.files[0]);
        
        fetch(`/api/assignments/${params.assignmentid}/submissions`, {
            method: "POST",
            body: formData
        });
        
    }
    
    return (
        <div id="submission-details-container">
            <PageHeader pageName="Submission Details" />
            
            <main id="submission-details-panel">
                
                <h3>{userData.assignments[0].title}</h3> {/* temporary */}
                
                <button className="button" onClick={() => document.getElementById("add-attempt").click()}>Add Attempt</button>
                <input type="file" id="add-attempt" style={{ visibility: "hidden", height: 0 }} onChange={attemptSender} accept=".zip"/>
                
                <div id="submission-attempts-list-header">
                    <span>Attempt Identity</span>
                    <span>Grade</span>
                    <span>Test Success Rate</span>
                    <span>Go to Attempt</span>
                </div>
                
                {submission.attempts.map((attempt, i) => {
                    return (<article key={i}>
                        <section>#{attempt.id}: {attempt.attemptFilename}</section>
                        <section>{attempt.feedback.grade}</section>
                        <section>{successfulTestCount[i]} / {attempt.testResults.length}</section>
                        <section><Link to={`/assignments/${params.assignmentid}/submissions/${params.submissionid}/attempts/${attempt.id}`}><span className="material-icons">find_in_page</span></Link></section>
                    </article>)
                })}
                
            </main>
            
        </div>
    );
    
}

export default SubmissionDetails;

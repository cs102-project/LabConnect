import React, { useState } from "react";
import { useParams } from "react-router-dom";
import { userData } from "../App";
import PageHeader from "./PageHeader";

// This page shows the details (such as grade/feedback/testresults) of a single attempt

function AttemptDetails(): JSX.Element {
    
    const attempt = userData.submission.attempts[0];
    
    const [notes, setNotes] = useState(attempt.note);
    
    const params = useParams<{ assignmentid: string, submissionid: string, attemptid: string }>();
    
    const notesSaver = () => {
        
        fetch(`/assignments/${params.assignmentid}/submissions/${params.submissionid}/attempts/${params.attemptid}`, {
            method: "POST",
            body: JSON.stringify(notes)
        });
        
    };
    
    return (
        <div id="attempt-details-container">
            <PageHeader pageName="Attempt Details" />
            
            <main id="attempt-details-panel">
                <h3>#{attempt.id}: {attempt.attemptFilename}</h3>
                { attempt.feedback.date && <div id="attempt-feedback">
                    <h4>Feedback given at {attempt.feedback.date}:</h4>
                    <p>Grade: {attempt.feedback.grade}</p>
                    <p>Comment: {attempt.feedback.content}</p>
                </div> }
                <section id="attempt-test-results">
                    {attempt.testResults.map((result, i) => {
                        return (<article key={i}>
                            <h4>Test: {result.test}</h4>
                            <p>State: {result.state}</p>
                            <p>Output: {result.testOutput.map((line, i) => <p key={i}>{line}</p>)}</p>
                        </article>)
                    })}
                </section>
                <section id="attempt-notes">
                    <h4>Notes:</h4>
                    <textarea onChange={(e) => setNotes(e.target.value)} value={notes}></textarea>
                    <button className="button" onClick={notesSaver}>Save Notes</button>
                </section>
            </main>
            
        </div>
    );
    
}

export default AttemptDetails;

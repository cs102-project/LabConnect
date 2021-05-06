import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import APITools, { IAttempt, IUserSelf } from "../APITools";
import PageHeader from "./PageHeader";
import "../scss/attemptdetails.scss";

// This page shows the details (such as grade/feedback/testresults) of a single attempt

function AttemptDetails(): JSX.Element {
    
    const [notes, setNotes] = useState<string>();
    const [grade, setGrade] = useState<string>();
    const [feedbackContent, setFeedbackContent] = useState<string>();
    
    const [userSelf, setUserSelf] = useState<IUserSelf>();
    
    const params = useParams<{ assignmentid: string, submissionid: string, attemptid: string }>();
    
    const [attempt, setAttempt] = useState<IAttempt>();
    
    useEffect(() => {
        APITools.getAttemptDetailsOf(params.submissionid, params.attemptid).then((response) => {
            setAttempt(response);
        });
        APITools.getUserSelf().then(response => {
            setUserSelf(response);
        });
    }, []);
    
    const notesSaver = () => {
        
        if (notes !== undefined) {
            APITools.addNoteTo(params.submissionid, params.attemptid, notes);
        }
        
    };
    
    const feedbackHandler = () => {
        
        if (grade === undefined) return;
        
        APITools.giveFeedbackTo(params.assignmentid, params.submissionid, params.attemptid, {
            content: feedbackContent || "",
            grade: parseInt(grade)
        });
    };
    
    return (
        <div id="attempt-details-container">
            <PageHeader pageName="Attempt Details" />
            
            <main id="attempt-details-panel">
                <h3>#{attempt?.id}: {attempt?.attemptFilename}</h3>
                { attempt?.feedback?.date && <div id="attempt-feedback">
                    <h4>Feedback given at {APITools.helpers.stringFromDate(attempt.feedback.date)}:</h4>
                    <p>Grade: {attempt.feedback.grade}</p>
                    <p>Comment: {attempt.feedback.content}</p>
                </div> }
                {
                    (userSelf?.roleType === "TEACHING_ASSISTANT" || userSelf?.roleType === "INSTRUCTOR") &&
                    <div id="feedback-box">
                        <h4>Give Feedback</h4>
                        <input type="text" id="feedback-grade" value={grade} onChange={(e) => setGrade(e.target.value)} />
                        <textarea className="lc-textarea" id="feedback-content" value={feedbackContent} onChange={(e) => setFeedbackContent(e.target.value)}></textarea>
                        <button className="button" onClick={feedbackHandler}>Save Feedback</button>
                    </div>
                }
                <section id="attempt-test-results">
                    {attempt?.testResults.map((result, i) => {
                        return (<article key={i}>
                            <h4>Test: {result.testName}</h4>
                            <p>State: {result.state}</p>
                            <p>Output: {result.output?.map((line, i) => <p key={i}>{line}</p>)}</p>
                        </article>)
                    })}
                </section>
                <section id="attempt-notes">
                    <h4>Notes:</h4>
                    <textarea className="lc-textarea" onChange={(e) => setNotes(e.target.value)} value={notes}></textarea>
                    <button className="button" onClick={notesSaver}>Save Notes</button>
                </section>
            </main>
            
        </div>
    );
    
}

export default AttemptDetails;

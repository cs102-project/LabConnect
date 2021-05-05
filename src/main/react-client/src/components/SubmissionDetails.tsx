import React, { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import APITools, { IAssignment, ISubmission } from "../APITools";
import AddAttemptButton from "./AddAttemptButton";
import PageHeader from "./PageHeader";

// This page shows a list of attempts made to this assignment (the attempts of this submission)

function SubmissionDetails(): JSX.Element {
    
    const params = useParams<{ assignmentid: string, submissionid: string }>();
<<<<<<< HEAD
=======
    
    const attemptSender = (e: ChangeEvent<HTMLInputElement>) => {
        
        APITools.addAttempt(params.assignmentid, e.target.files?.[0]);
        
    }
>>>>>>> 87680b1b2cba923bc66354f933c56f1dc5dac975
    
    const [assignment, setAssignment] = useState<IAssignment>();
    const [submission, setSubmission] = useState<ISubmission>();
    
    useEffect(() => {
<<<<<<< HEAD
        APITools.getSubmissionOfStudentFor(params.assignmentid).then((response) => {
            if (APITools.helpers.isSubmissionValid(response)) {
                setSubmission(response);
            }
=======
        APITools.getSubmissionsOf(params.assignmentid).then((response) => {
            setSubmission(response[0]);
>>>>>>> 87680b1b2cba923bc66354f933c56f1dc5dac975
        });
        APITools.getAssignmentOf(params.assignmentid).then((response) => {
            setAssignment(response);
        });
    }, []);
    
    return (
        <div id="submission-details-container">
            <PageHeader pageName="Submission Details" />
            
            <main id="submission-details-panel">
                
                <h3>{assignment?.title}</h3> {/* temporary */}
                
                <AddAttemptButton assignmentid={params.assignmentid} />
                
                <div id="submission-attempts-list-header">
                    <span>Attempt Identity</span>
                    <span>Grade</span>
                    <span>Test Success Rate</span>
                    <span>Go to Attempt</span>
                </div>
                
                {submission?.attempts.slice().sort((a, b) => b.id - a.id).map((attempt, i) => {
                    return (<article key={i}>
                        <section>#{attempt.id}: {attempt.attemptFilename}</section>
                        <section>{attempt.feedback.grade}</section>
                        <section>{attempt.testResults.filter(testResult => testResult.state === "SUCCESS").length} / {attempt.testResults.length}</section>
                        <section><Link to={`/assignments/${params.assignmentid}/submissions/${params.submissionid}/attempts/${attempt.id}`}><span className="material-icons">find_in_page</span></Link></section>
                    </article>)
                })}
                
            </main>
            
        </div>
    );
    
}

export default SubmissionDetails;

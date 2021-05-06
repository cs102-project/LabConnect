import React, { useEffect, useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import APITools, { IAssignment, ISubmission } from '../APITools';
import AddAttemptButton from './AddAttemptButton';
import PageHeader from './PageHeader';
import '../scss/submissiondetails.scss';

// This page shows a list of attempts made to this assignment (the attempts of this submission)

function SubmissionDetails(): JSX.Element {
    const params = useParams<{ assignmentid: string; submissionid: string }>();

    const [assignment, setAssignment] = useState<IAssignment>();
    const [submission, setSubmission] = useState<ISubmission>();

    useEffect(() => {
        APITools.getUserSelf().then((userSelf) => {
            if (userSelf.roleType === 'STUDENT') {
                APITools.getSubmissionOfStudentFor(params.assignmentid).then((response) => {
                    if (APITools.helpers.isSubmissionValid(response)) {
                        setSubmission(response);
                    }
                });
            } else if (userSelf.roleType === 'INSTRUCTOR' || userSelf.roleType === 'TEACHING_ASSISTANT') {
                APITools.getSubmissionOfId(params.submissionid).then((response) => {
                    setSubmission(response);
                });
            }
        });

        APITools.getAssignmentOf(params.assignmentid).then((response) => {
            setAssignment(response);
        });
    }, []);

    return (
        <div id="submission-details-container">
            <PageHeader pageName="Submission Details" />

            <main id="submission-details-panel">
                <h3>{assignment?.title}</h3>
                <AddAttemptButton assignmentid={params.assignmentid} />
                <div id="submission-attempts-list-header">
                    <span>Attempt Identity</span>
                    <span>Grade</span>
                    <span>Test Success Rate</span>
                    <span>Go to Attempt</span>
                </div>
                <div id="submission-attempts-list">
                    {submission?.attempts
                        .slice()
                        .sort((a, b) => b.id - a.id)
                        .map((attempt, i) => {
                            return (
                                <article key={i}>
                                    <section>
                                        #{attempt.id}: {attempt.attemptFilename}
                                    </section>
                                    <section>{attempt.feedback?.grade || 'N/A'}</section>
                                    <section>
                                        {
                                            attempt.testResults.filter((testResult) => testResult.state === 'SUCCESS')
                                                .length
                                        }{' '}
                                        / {attempt.testResults.length}
                                    </section>
                                    <section>
                                        <Link
                                            to={`/assignments/${params.assignmentid}/submissions/${params.submissionid}/attempts/${attempt.id}`}
                                        >
                                            <span className="material-icons">find_in_page</span>
                                        </Link>
                                    </section>
                                </article>
                            );
                        })}
                </div>
            </main>
        </div>
    );
}

export default SubmissionDetails;

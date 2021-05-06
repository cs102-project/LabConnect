import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import PageHeader from './PageHeader';
import "../scss/mynotes.scss";
import APITools, { INotes } from '../APITools';

function MyNotes(): JSX.Element {
    const [assignmentNoteObj, setAssignmentNoteObj] = useState<INotes>();
    const [noteObj, setNoteObj] = useState<INotes[]>();
    const [inputs, setInputs] = useState<{ [key: string]: string }>({});

    const clickHandler = (assignmentObj: INotes) => {
        setInputs({});
        setAssignmentNoteObj(assignmentObj);
    };

    useEffect(() => {
        APITools.getAllNotes().then((response) => {
            setNoteObj(response);
        });
    }, []);
    
    return (
        <div id="my-notes-container">
            <PageHeader pageName="My Notes" />

            <main id="my-notes-panel">
                <section id="my-notes-list">
                    <h3>Assignments</h3>
                    <div id="my-notes-list-assignments">
                        {noteObj?.map((assignmentObj, i) => (
                            <article
                                key={i}
                                onClick={() => clickHandler(assignmentObj)}
                                className="my-notes-list-assignment-item button"
                            >
                                {assignmentObj.assignmentTitle}
                            </article>
                        ))}
                    </div>
                </section>

                {assignmentNoteObj !== undefined && (
                    <section id="my-notes-note">
                        <div id="my-notes-note-header">
                            <h3>Notes for {assignmentNoteObj?.assignmentTitle}</h3>
                            <Link to={`/assignments/${assignmentNoteObj?.assignmentId}`} className="button" >Go to Assignment</Link>
                        </div>
                        <div id="my-notes-note-attempts">
                            {assignmentNoteObj?.attemptNotes.filter(attemptObj => attemptObj.note?.length > 0).map((attemptObj, i) => (
                                <div key={i} className="my-notes-attempt">
                                    <h4>Attempt #{attemptObj.attempt}:</h4>
                                    <textarea
                                        value={inputs[`attemptField${attemptObj.attempt}`] || attemptObj.note}
                                        name={`attemptField${attemptObj.attempt}`}
                                        onChange={(e) =>
                                            setInputs({ ...inputs, [e.currentTarget.name]: e.currentTarget.value })
                                        }
                                    ></textarea>
                                </div>
                            ))}
                        </div>
                    </section>
                )}
            </main>
        </div>
    );
}

export default MyNotes;

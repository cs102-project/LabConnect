import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { userData } from '../App';
import PageHeader from './PageHeader';
import "../scss/mynotes.scss";

const blankSample = [{ assignmentTitle: '', assignmentId: '', attemptNotes: [{ attempt: 0, note: '' }] }];

function MyNotes(): JSX.Element {
    const [assignmentNoteObj, setAssignmentNoteObj] = useState(blankSample[0]);
    const [noteObj, setNoteObj] = useState(blankSample);
    const [inputs, setInputs] = useState({});

    const clickHandler = (assignmentObj) => {
        setInputs({});
        setAssignmentNoteObj(assignmentObj);
    };

    useEffect(() => setNoteObj(userData.notes));

    return (
        <div id="my-notes-container">
            <PageHeader pageName="My Notes" />

            <main id="my-notes-panel">
                <section id="my-notes-list">
                    <h3>Assignments</h3>
                    <div id="my-notes-list-assignments">
                        {noteObj.map((assignmentObj, i) => (
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

                {assignmentNoteObj.assignmentId !== '' && (
                    <section id="my-notes-note">
                        <div id="my-notes-note-header">
                            <h3>Notes for {assignmentNoteObj.assignmentTitle}</h3>
                            <Link to={`/assignments/${assignmentNoteObj.assignmentId}`} className="button" >Go to Assignment</Link>
                        </div>
                        <div id="my-notes-note-attempts">
                            {assignmentNoteObj.attemptNotes.map((attemptObj, i) => (
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

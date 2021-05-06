import React, { useEffect, useState } from 'react';
import { useHistory } from 'react-router';
import APITools, { INewAssignment, ITests, IUserSelf } from '../APITools';
import PageHeader from './PageHeader';
import "../scss/instructorpanel.scss";

// New assignments and announcements are added from here.

function InstructorPanel(): JSX.Element {
    const [announcement, setAnnouncement] = useState('');

    const [title, setTitle] = useState('');
    const [description, setDescription] = useState('');
    const [maxGrade, setMaxGrade] = useState('100');
    const [maxAttempts, setMaxAttempts] = useState('7');
    const [unitTestName, setUnitTestName] = useState('');
    const [unitTestTimeLimit, setUnitTestTimeLimit] = useState('10000');
    const [forbiddenStatements, setForbiddenStatements] = useState('');
    
    const history = useHistory();

    const announcementHandler = () => {
        APITools.sendAnnouncement(announcement);
    };

    const assignmentHandler = () => {
        const styleTests: string[] = [];
        const cNames: string[] = [];
        const secs: number[] = [];

        document
            .querySelectorAll<HTMLInputElement>("#ip-style-tests input[type='checkbox']:checked")
            .forEach((test) => styleTests.push(test.name));
        document
            .querySelectorAll<HTMLInputElement>("#ip-courses input[type='checkbox']:checked")
            .forEach((cbox) => {
                cNames.push(cbox.name.split('-')[0]);
                secs.push(parseInt(cbox.name.split('-')[1]));
            });

        const assignment: INewAssignment = {
            assignmentTitle: title,
            shortDescription: description,
            homeworkType: document.querySelector<HTMLInputElement>("input[name='homeworkType']:checked")?.value,
            dueDate: document.querySelector<HTMLInputElement>("input[type='date']")?.value,
            courseNames: cNames,
            sections: secs,
            maxGrade: maxGrade,
            maxAttempts: maxAttempts,
            styleTests: styleTests,
            unitTestName: unitTestName,
            unitTestTimeLimit: unitTestTimeLimit,
            forbiddenStatements: forbiddenStatements.split('|'),
            instructionsFile: (document.getElementById('ip-instructions') as HTMLInputElement).files?.[0],
            exampleImplementation: (document.getElementById('ip-example') as HTMLInputElement).files?.[0],
            testerClass: (document.getElementById('ip-unittest') as HTMLInputElement).files?.[0],
        };
        
        if (assignment.instructionsFile === undefined || assignment.exampleImplementation === undefined || assignment.testerClass === undefined) {
            return;
        }
        
        APITools.createAssignment(assignment).then(response => {
            history.push(`/assignments/${response.id}`);
        });
        
    };

    const [tests, setTests] = useState<ITests>();
    const [userSelf, setUserSelf] = useState<IUserSelf>();

    useEffect(() => {
        APITools.getAllTests().then((response) => {
            setTests(response);
        });
        APITools.getUserSelf().then((response) => {
            setUserSelf(response);
        });
    }, []);

    return (
        <div id="instructor-panel-container">
            <PageHeader pageName="Instructor Panel" />

            <main id="instructor-panel-main">
                <section id="instructor-panel-announcement">
                    <h4>Make announcement</h4>
                    <textarea className="lc-textarea" value={announcement} onChange={(e) => setAnnouncement(e.target.value)}></textarea>
                    <button className="button" onClick={announcementHandler}>
                        Send
                    </button>
                </section>
                <section id="instructor-panel-assignment">
                    <h4>Create Assignment</h4>

                    <h5>Assignment Type</h5>
                    <div>
                        <div>
                            <input type="radio" id="homework" name="homeworkType" value="homework" />
                            <label htmlFor="homework">Homework</label>
                        </div>
                        <div>
                            <input type="radio" id="lab" name="homeworkType" value="lab" />
                            <label htmlFor="lab" defaultChecked>
                                Lab
                            </label>
                        </div>
                    </div>

                    <h5>Instructions File</h5>
                    <input type="file" id="ip-instructions" />

                    <h5>Assignment Title</h5>
                    <input className="lc-textin" type="text" id="ip-title" value={title} onChange={(e) => setTitle(e.target.value)} />

                    <h5>Short Description</h5>
                    <textarea className="lc-textarea" value={description} onChange={(e) => setDescription(e.target.value)}></textarea>

                    <h5>Due Date</h5>
                    <input className="lc-textin" type="date" id="ip-date" />

                    <h5>Courses</h5>
                    <div id="ip-courses">
                        {userSelf?.courses.map(({ course, section }, i) => (
                            <div key={i}>
                                <input
                                    key={i}
                                    id={`ip-cb-${course}-${section}`}
                                    type="checkbox"
                                    name={`${course}-${section}`}
                                />
                                <label htmlFor={`ip-cb-${course}-${section}`}>
                                    {course} - {section}
                                </label>
                            </div>
                        ))}
                    </div>

                    <h5>Max Grade</h5>
                    <input
                    className="lc-textin"
                        type="text"
                        id="ip-maxgrade"
                        value={maxGrade}
                        onChange={(e) => setMaxGrade(e.target.value)}
                    />

                    <h5>Max Attempts</h5>
                    <input
                    className="lc-textin"
                        type="text"
                        id="ip-maxattempts"
                        value={maxAttempts}
                        onChange={(e) => setMaxAttempts(e.target.value)}
                    />

                    <h5>Style Tests</h5>
                    <div id="ip-style-tests">
                        {tests?.map((test, i) => (
                            <div key={i}>
                                <input key={i} id={'ip-cb-' + test} type="checkbox" name={test} />
                                <label htmlFor={'ip-cb-' + test}>{test}</label>
                            </div>
                        ))}
                    </div>

                    <h5>Example Implementation File</h5>
                    <input type="file" id="ip-example" />

                    <h5>Unit Test File</h5>
                    <input type="file" id="ip-unittest" />

                    <h5>Unit Test Name</h5>
                    <input
                    className="lc-textin"
                        type="text"
                        id="ip-unittestname"
                        value={unitTestName}
                        onChange={(e) => setUnitTestName(e.target.value)}
                    />

                    <h5>Unit Test Timeout</h5>
                    <input
                    className="lc-textin"
                        type="text"
                        id="ip-unittesttimeout"
                        value={unitTestTimeLimit}
                        onChange={(e) => setUnitTestTimeLimit(e.target.value)}
                    />

                    <h5>Forbidden Statements</h5>
                    <p>Split statements using &apos;|&apos;</p>
                    <textarea
                        className="lc-textarea"
                        value={forbiddenStatements}
                        onChange={(e) => setForbiddenStatements(e.target.value)}
                    ></textarea>

                    <button className="button" onClick={assignmentHandler}>
                        Create
                    </button>
                </section>
            </main>
        </div>
    );
}

export default InstructorPanel;

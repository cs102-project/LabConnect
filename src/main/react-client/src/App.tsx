import React from 'react';
import { Switch, Route, Redirect, useLocation } from 'react-router-dom';
import LoginPage from "./components/LoginPage";
import Logout from "./components/Logout";
import Dashboard from "./components/Dashboard";
import MyNotes from "./components/MyNotes";
import Messages from "./components/Messages";
import Announcements from "./components/Announcements";
import LiveSession from "./components/LiveSession";
import InstructorPanel from "./components/InstructorPanel";
import Analysis from "./components/Analysis";
import AssignmentDetails from "./components/AssignmentDetails";
import SubmissionDetails from "./components/SubmissionDetails";
import AttemptDetails from "./components/AttemptDetails";
import Sidebar from './components/Sidebar';
import Settings from './components/Settings';
import NotFoundPage from './components/NotFoundPage';

function App(): JSX.Element {
    return (
        <>
            { useLocation().pathname !== "/login" && useLocation().pathname !== "/logout" && <Sidebar /> }
            <Switch>
                <Route exact path="/index.html">
                    <Redirect to="/" />
                </Route>
                <Route exact path="/" component={Dashboard} />
                <Route exact path="/login" component={LoginPage} />
                <Route exact path="/logout" component={Logout} />
                <Route exact path="/settings" component={Settings} />
                <Route exact path="/my-notes" component={MyNotes} />
                <Route exact path="/messages" component={Messages} />
                <Route exact path="/announcements" component={Announcements} />
                <Route exact path="/live-session" component={LiveSession} />
                <Route exact path="/instructor-panel" component={InstructorPanel} />
                <Route exact path="/analysis" component={Analysis} />
                <Route exact path="/assignments/:assignmentid" component={AssignmentDetails} />
                <Route exact path="/assignments/:assignmentid/submissions/:submissionid" component={SubmissionDetails} />
                <Route exact path="/assignments/:assignmentid/submissions/:submissionid/attempts/:attemptid" component={AttemptDetails} />
                <Route component={NotFoundPage} />
            </Switch>
        </>
    );
}

const userData = {
    roleType: "STUDENT",
    name: "Vedat Eren Arıcan",
    institution: "Bilkent University",
    instructor: "Aynur Dayanık",
    assistant: "Haya Shamim Khan Khattak",
    course: {
        name: "CS 102",
        section: "2"
    },
    assignments: [
        {
            title: "Lab03 | Introduction to GUI in Java",
            shortDescription: "In this lab assignment, you are given a simple console application, for which you must create a GUI. Make sure to reuse existing methods as much as possible.",
            deadline: "25 March 2021 18:00",
            homeworkType: "Lab",
            maxAttempts: 7,
            maxGrade: 100,
            tests: ["Test A", "Test B", "Some Test C or D"],
            grade: "N/A",
            isComplete: false,
            assignmentId: "98ba8926bdea23498dbf89a2366s98"
        },
        {
            title: "Lab03 | Introduction to GUI in Java",
            shortDescription: "In this lab assignment, you are given a simple console application, for which you must create a GUI. Make sure to reuse existing methods as much as possible.",
            deadline: "25 March 2021 18:00",
            homeworkType: "Lab",
            maxAttempts: 7,
            maxGrade: 100,
            tests: ["Test A", "Test B", "Some Test C or D"],
            grade: "80",
            isComplete: false,
            assignmentId: "98ba8926bdea23498dbf89a2366s98"
        },
        {
            title: "Lab03 | Introduction to GUI in Java",
            shortDescription: "In this lab assignment, you are given a simple console application, for which you must create a GUI. Make sure to reuse existing methods as much as possible.",
            deadline: "25 March 2021 18:00",
            homeworkType: "Lab",
            maxAttempts: 7,
            maxGrade: 100,
            tests: ["Test A", "Test B", "Some Test C or D"],
            grade: "100",
            isComplete: true,
            assignmentId: "98ba8926bdea23498dbf89a2366s98"
        }
    ],
    announcements: [
        {
            author: "Aynur Dayanık",
            date: "Some Time in the Past",
            content: "There was a typo in our instruction document bla bla bla balbalbal bla There was a typo in our instruction document bla bla bla balbalbal bla There was a typo in our instruction document bla bla bla balbalbal bla There was a typo in our instruction document bla bla bla balbalbal bla There was a typo in our instruction document bla bla bla balbalbal bla"
        },
        {
            author: "Aynur Dayanık",
            date: "Some Time in the Past",
            content: "There was a typo in our instruction document bla bla bla balbalbal bla There was a typo in our instruction document bla bla bla balbalbal bla There was a typo in our instruction document bla bla bla balbalbal bla There was a typo in our instruction document bla bla bla balbalbal bla There was a typo in our instruction document bla bla bla balbalbal bla"
        },
        {
            author: "Aynur Dayanık",
            date: "Some Time in the Past",
            content: "There was a typo in our instruction document bla bla bla balbalbal bla There was a typo in our instruction document bla bla bla balbalbal bla There was a typo in our instruction document bla bla bla balbalbal bla There was a typo in our instruction document bla bla bla balbalbal bla There was a typo in our instruction document bla bla bla balbalbal bla"
        },
        {
            author: "Aynur Dayanık",
            date: "Some Time in the Past",
            content: "There was a typo in our instruction document bla bla bla balbalbal bla There was a typo in our instruction document bla bla bla balbalbal bla There was a typo in our instruction document bla bla bla balbalbal bla There was a typo in our instruction document bla bla bla balbalbal bla There was a typo in our instruction document bla bla bla balbalbal bla"
        },
        {
            author: "Aynur Dayanık",
            date: "Some Time in the Past",
            content: "There was a typo in our instruction document bla bla bla balbalbal bla There was a typo in our instruction document bla bla bla balbalbal bla There was a typo in our instruction document bla bla bla balbalbal bla There was a typo in our instruction document bla bla bla balbalbal bla There was a typo in our instruction document bla bla bla balbalbal bla"
        },
        {
            author: "Aynur Dayanık",
            date: "Some Time in the Past",
            content: "There was a typo in our instruction document bla bla bla balbalbal bla There was a typo in our instruction document bla bla bla balbalbal bla There was a typo in our instruction document bla bla bla balbalbal bla There was a typo in our instruction document bla bla bla balbalbal bla There was a typo in our instruction document bla bla bla balbalbal bla"
        },
        {
            author: "Aynur Dayanık",
            date: "Some Time in the Past",
            content: "There was a typo in our instruction document bla bla bla balbalbal bla There was a typo in our instruction document bla bla bla balbalbal bla There was a typo in our instruction document bla bla bla balbalbal bla There was a typo in our instruction document bla bla bla balbalbal bla There was a typo in our instruction document bla bla bla balbalbal bla"
        }
    ],
    notes: [
        {
            assignmentTitle: "Lab 03 | something something",
            assignmentId: "923498294",
            attemptNotes: [
                {
                    attempt: 1,
                    note: "hello its a note"
                },
                {
                    attempt: 2,
                    note: "its a note again"
                }
            ]
        },
        {
            assignmentTitle: "Lab 04 | something",
            assignmentId: "74543346",
            attemptNotes: [
                {
                    attempt: 1,
                    note: "its a note"
                },
                {
                    attempt: 2,
                    note: "a note again"
                },
                {
                    attempt: 3,
                    note: "a note once more"
                }
            ]
        }
    ]
};

export default App;
export { userData };

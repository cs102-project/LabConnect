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

export default App;

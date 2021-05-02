import React from 'react';
import { Switch, Route, Redirect } from 'react-router-dom';
import LoginPage from "./components/LoginPage";
import Logout from "./components/Logout";
import Dashboard from "./components/Dashboard";
import PageNotFound from './components/PageNotFound';
import MyNotes from "./components/MyNotes";
import Messages from "./components/Messages";
import Announcements from "./components/Announcements";
import LiveSession from "./components/LiveSession";
import InstructorPanel from "./components/InstructorPanel";
import Analysis from "./components/Analysis";
import AssignmentDetails from "./components/AssignmentDetails";
import SubmissionDetails from "./components/SubmissionDetails";
import AttemptDetails from "./components/AttemptDetails";

function App(): JSX.Element {
    return (
        <Switch>
            <Route exact path="/index.html">
                <Redirect to="/" />
            </Route>
            <Route exact path="/" component={Dashboard} />
            <Route exact path="/login" component={LoginPage} />
            <Route exact path="/logout" component={Logout} />
            <Route exact path="/my-notes" component={MyNotes} />
            <Route exact path="/messages" component={Messages} />
            <Route exact path="/announcements" component={Announcements} />
            <Route exact path="/live-session" component={LiveSession} />
            <Route exact path="/instructor-panel" component={InstructorPanel} />
            <Route exact path="/analysis" component={Analysis} />
            <Route exact path="/assignments/:assignmentid" component={AssignmentDetails} />
            <Route exact path="/assignments/:assignmentid/submissions/:submissionid" component={SubmissionDetails} />
            <Route exact path="/assignments/:assignmentid/submissions/:submissionid/attempts/:attemptid" component={AttemptDetails} />
            <Route path="*" component={PageNotFound} />
        </Switch>
    );
}

export default App;

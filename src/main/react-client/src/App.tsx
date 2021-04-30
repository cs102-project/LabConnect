import React from 'react';
import { Switch, Route, Redirect } from 'react-router-dom';
import LoginPage from "./components/LoginPage";
import Logout from "./components/Logout";
import Dashboard from "./components/Dashboard";
import PageNotFound from './components/PageNotFound';

function App(): JSX.Element {
    return (
        <Switch>
            <Route exact path="/index.html">
                <Redirect to="/" />
            </Route>
            <Route exact path="/" component={Dashboard} />
            <Route exact path="/login" component={LoginPage} />
            <Route exact path="/logout" component={Logout} />
            <Route path="*" component={PageNotFound} />
        </Switch>
    );
}

export default App;

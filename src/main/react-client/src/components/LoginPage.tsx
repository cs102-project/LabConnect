import React, { useEffect } from 'react';
import LoginForm from './LoginForm';
import "../scss/login.scss";
import labconnectLogo from "../img/labconnect-logo.png";

function LoginPage(): JSX.Element {
    
    useEffect(() => { 
        window.document.title = "LabConnect | Login";
    });
    
    return (
        <div id="login-page-container-x">
            <div id="login-primary">
                <img id="login-logo" src={labconnectLogo} />
                <div id="login-primary-content">
                    <h1>LabConnect</h1>
                    <h4>Connect to your lab the right way.</h4>
                    <ul>
                        <li>Internally managed live session queueing mechanism</li>
                        <li>Source control capabilities via Git</li>
                        <li>Minimal note-taking capabilities</li>
                        <li>Ease of use</li>
                    </ul>
                </div>
            </div>
            <div id="login-secondary">
                <LoginForm />
            </div>
        </div>
    );
}

export default LoginPage;

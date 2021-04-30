import React, { useEffect } from "react";
import logo from '../img/logo.svg';
import '../scss/App.scss';

function Dashboard(): JSX.Element {
    
    useEffect(() => { window.document.title = "LabConnect | Dashboard" });
    
    return (
        <div className="App">
            <header className="App-header">
                <img src={logo} className="App-logo" alt="logo" />
            </header>
        </div>
    );
}

export default Dashboard;

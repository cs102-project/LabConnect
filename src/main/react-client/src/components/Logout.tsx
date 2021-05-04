import React, { useEffect } from "react";
import "../scss/login.scss";

function Logout(): JSX.Element {
    
    fetch('/logout', {
        method: 'POST'
    });
    
    useEffect(() => { 
        window.document.title = "LabConnect | Logged out";
    });

    return (
        <div id="logout-container-x">
            <p id="logout-message">You have successfully logged out.</p>
        </div>
    );
}

export default Logout;

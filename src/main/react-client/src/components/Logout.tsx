import React, { useEffect } from 'react';
import { Link } from 'react-router-dom';
import '../scss/login.scss';

function Logout(): JSX.Element {
    fetch('/logout', {
        method: 'POST',
    });

    useEffect(() => {
        window.document.title = 'LabConnect | Logged out';
    });

    return (
        <div id="logout-container-x">
            <p id="logout-message">
                You have successfully logged out.
                <Link className="button" to="/login">Go back to homepage</Link>
            </p>
        </div>
    );
}

export default Logout;

import React, { useEffect } from "react";
import "../scss/login.scss";

function Logout(): JSX.Element {
    fetch('/logout', {
        method: 'POST',
        headers: {
            'X-XSRF-TOKEN':
                document.cookie
                    .split('; ')
                    .find((row) => row.startsWith('XSRF-TOKEN='))
                    ?.split('=')[1] || '',
        },
    });
    
    useEffect(() => { window.document.title = "LabConnect | Logged out" });

    return (
        <p id="logout-message">You have successfully logged out.</p>
    );
}

export default Logout;

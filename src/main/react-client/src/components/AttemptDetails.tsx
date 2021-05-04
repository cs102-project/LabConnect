import React from "react";
import PageHeader from "./PageHeader";

// This page shows the details (such as grade/feedback/testresults) of a single attempt

function AttemptDetails(): JSX.Element {
    
    return (
        <div id="attempt-details-container">
            <PageHeader pageName="Meaningful Attempt Name" />
        </div>
    );
    
}

export default AttemptDetails;

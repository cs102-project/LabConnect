import React from "react";
import BlankTextPanel from "./BlankTextPanel";
import PageHeader from "./PageHeader";

function LiveSession(): JSX.Element {
    
    return (
        <div id="live-session-container">
            <PageHeader pageName="Live Session" />
            <BlankTextPanel message="This feature isn't implemented yet." />
        </div>
    );
    
}

export default LiveSession;

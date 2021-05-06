import React from "react";
import BlankTextPanel from "./BlankTextPanel";
import PageHeader from "./PageHeader";

function Messages(): JSX.Element {
    
    return (
        <div id="messages-container">
            <PageHeader pageName="Messages" />
            <BlankTextPanel message="This feature isn't implemented yet in this version of LabConnect." />
        </div>
    );
    
}

export default Messages;

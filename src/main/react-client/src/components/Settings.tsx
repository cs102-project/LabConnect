import React from "react";
import BlankTextPanel from "./BlankTextPanel";
import PageHeader from "./PageHeader";

function Settings(): JSX.Element {
    
    return (
        <div id="settings-container">
            <PageHeader pageName="Settings" />
            <BlankTextPanel message="This feature isn't implemented yet in this version of LabConnect." />
        </div>
    );
    
}

export default Settings;

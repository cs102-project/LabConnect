import React from "react";
import BlankTextPanel from "./BlankTextPanel";
import PageHeader from "./PageHeader";

function Analysis(): JSX.Element {
    
    return (
        <div id="analysis-container">
            <PageHeader pageName="Analysis" />
            <BlankTextPanel message="This feature isn't implemented yet." />
        </div>
    );
    
}

export default Analysis;

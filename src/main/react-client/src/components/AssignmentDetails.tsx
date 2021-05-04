import React from "react";
import { useParams } from "react-router";
import { userData } from "../App";
import PageHeader from "./PageHeader";

function AssignmentDetails(): JSX.Element {
    
    const { assignmentid } = useParams<{ assignmentid: string }>();
    
    const assignment = userData.assignments[0];
    
    return (
        <div id="assignment-details-container">
            <PageHeader pageName={assignment.title} />
            
            
            
        </div>
    );
    
}

export default AssignmentDetails;

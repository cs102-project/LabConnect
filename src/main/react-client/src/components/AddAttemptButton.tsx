import React, { ChangeEvent } from "react";
import { useHistory } from "react-router";
import APITools from "../APITools";

function AddAttemptButton({ assignmentid }: { assignmentid: string }): JSX.Element {
    
    const history = useHistory();
    
    const attemptSender = (e: ChangeEvent<HTMLInputElement>) => {
        
        if (e.target.files?.[0] === undefined) return;
        APITools.addAttempt(assignmentid, e.target.files?.[0]).then(response => {
            history.push(`/assignments/${assignmentid}/submissions/${response.parentId}/attempts/${response.id}`);
        });
        
    }
    
    return (
        <>
            <button className="button" onClick={() => document.getElementById("add-attempt")?.click()}>Add Attempt</button>
            <input type="file" id="add-attempt" style={{ visibility: "hidden", height: 0 }} onChange={attemptSender} accept=".zip"/>
        </>
    );
    
}

export default AddAttemptButton;

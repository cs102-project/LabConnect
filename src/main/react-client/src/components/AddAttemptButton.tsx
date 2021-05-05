import React, { ChangeEvent } from "react";
import APITools from "../APITools";

function AddAttemptButton({ assignmentid }: { assignmentid: string }): JSX.Element {
    
    const attemptSender = (e: ChangeEvent<HTMLInputElement>) => {
        
        APITools.addAttempt(assignmentid, e.target.files?.[0]);
        
    }
    
    return (
        <div>
            <button className="button" onClick={() => document.getElementById("add-attempt")?.click()}>Add Attempt</button>
            <input type="file" id="add-attempt" style={{ visibility: "hidden", height: 0 }} onChange={attemptSender} accept=".zip"/>
        </div>
    );
    
}

export default AddAttemptButton;

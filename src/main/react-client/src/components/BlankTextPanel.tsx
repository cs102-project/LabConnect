import React from "react";
import "../scss/index.scss";

type BlankTextPanelProps = {
    message: string
}

function BlankTextPanel({ message }: BlankTextPanelProps): JSX.Element {
    
    return (
        <div className="blank-panel">{message}</div>
    );
    
}

export default BlankTextPanel;

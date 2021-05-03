import React from "react";
import { userData } from "../App";
import "../scss/pageheader.scss";

type PageHeaderProps = {
    pageName: string
};

function PageHeader({ pageName }: PageHeaderProps): JSX.Element {
    
    return (
        <header id="page-header">
            <h1>{pageName}</h1>
            <div>
                <span id="notifications" className="material-icons">notifications</span>
                <div id="page-header-divider"></div>
                <span>{userData.name}</span>
                <span id="avatar" className="material-icons">account_circle</span>
            </div>
        </header>
    );
    
}

export default PageHeader;

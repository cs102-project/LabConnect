import React, { useEffect, useState } from "react";
import APITools, { IUserSelf } from "../APITools";
import "../scss/pageheader.scss";

type PageHeaderProps = {
    pageName: string
};

function PageHeader({ pageName }: PageHeaderProps): JSX.Element {
    
    const [userSelf, setUserSelf] = useState<IUserSelf>();
    
    useEffect(() => {
        APITools.getUserSelf().then((response) => {
            setUserSelf(response);
        });
    }, []);
    
    return (
        <header id="page-header">
            <h1>{pageName}</h1>
            <div>
                <span id="notifications" className="material-icons">notifications</span>
                <div id="page-header-divider"></div>
                <span>{userSelf?.name}</span>
                <span id="avatar" className="material-icons">account_circle</span>
            </div>
        </header>
    );
    
}

export default PageHeader;

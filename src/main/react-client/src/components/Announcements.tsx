import React from "react";
import { userData } from "../App";
import PageHeader from "./PageHeader";
import "../scss/announcements.scss";

function Announcements(): JSX.Element {
    
    return (
        <div id="announcements-container">
            <PageHeader pageName="Announcements" />
            
            <main id="announcements-list">
                {
                    userData.announcements.length !== 0 ? userData.announcements.map((announcement, i) => (
                        <article key={i}>
                            <span className="material-icons">announcement</span>
                            <h5>{announcement.author} at {announcement.date}:</h5>
                            <p>{announcement.content}</p>
                        </article>
                    )) : <span>No announcements at this time.</span>
                }
            </main>
            
        </div>
    );
    
}

export default Announcements;

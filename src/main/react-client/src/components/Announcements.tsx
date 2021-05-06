import React, { useEffect, useState } from "react";
import PageHeader from "./PageHeader";
import "../scss/announcements.scss";
import APITools, { IAnnouncement } from "../APITools";

function Announcements(): JSX.Element {
    
    const [announcements, setAnnouncements] = useState<IAnnouncement[]>();
    
    useEffect(() => {
        APITools.getAllAnnouncements().then((response) => {
            setAnnouncements(response);
        });
    }, []);
    
    return (
        <div id="announcements-container">
            <PageHeader pageName="Announcements" />
            
            <main id="announcements-list">
                {
                    announcements?.length !== 0 ? announcements?.slice().sort((a, b) => parseInt(b.date) - parseInt(a.date)).map((announcement, i: number) => (
                        <article key={i}>
                            <span className="material-icons">announcement</span>
                            <h5>{announcement.author} at {APITools.helpers.stringFromDate(announcement.date)}:</h5>
                            <p>{announcement.content}</p>
                        </article>
                    )) : <span>No announcements at this time.</span>
                }
            </main>
            
        </div>
    );
    
}

export default Announcements;

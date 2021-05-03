import React, { useEffect } from "react";
import '../scss/dashboard.scss';
import AssignmentsList from "./AssignmentsList";
import CourseStatus from "./CourseStatus";
import PageHeader from "./PageHeader";

function Dashboard(): JSX.Element {
    
    useEffect(() => { window.document.title = "LabConnect | Dashboard" });
    
    return (
        <div id="dashboard-container">
            <PageHeader pageName="Dashboard" />
            <CourseStatus />
            <AssignmentsList />
        </div>
    );
}

export default Dashboard;

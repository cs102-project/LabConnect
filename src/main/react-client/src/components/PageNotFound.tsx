import React, { useEffect } from "react";

function PageNotFound(): JSX.Element {
    
    useEffect(() => { window.document.title = "LabConnect | Page not found" });
    
    return (
        
        <p>Page not found.</p>
        
    );
    
}

export default PageNotFound;

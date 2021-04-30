package me.labconnect.webapp.controller;

/**
 * An exception thrown when the specified assignment is not found
 * 
 * @author Berkan Şahin
 * @version 30.04.2021
 */
public class AssignmentNotFoundException extends RuntimeException {
    
    /**
     * Initialize a new AssignmentNotFoundException
     * 
     * @param assignmentID The institution ID sent in the request
     */
    public AssignmentNotFoundException(String assignmentID) {
        super("Could not find an assignment with ID " + assignmentID);
    }

    /**
     * Initialize a new AssignmentNotFoundException if an ID is not used in the query
     */
    public AssignmentNotFoundException() {
        super("Could not find the requested assignment");
    }
}

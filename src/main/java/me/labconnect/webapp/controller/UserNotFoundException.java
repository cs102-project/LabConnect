package me.labconnect.webapp.controller;

/**
 * An exception thrown when the user requested by the client is not in the database
 * 
 * @author Berkan Şahin
 * @version 29.04.2021
 */
public class UserNotFoundException extends RuntimeException {
    
    /**
     * Initialize a new UserNotFoundException
     * 
     * @param institutionId The institution ID sent in the request
     */
    UserNotFoundException(Long institutionId) {
        super("Could not find a user with ID " + institutionId);
    }

    /**
     * Initialize a new UserNotFoundException if an ID is not used in the query
     */
    UserNotFoundException() {
        super("Could not find requested user");
    }
}

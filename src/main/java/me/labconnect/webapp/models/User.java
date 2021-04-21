package me.labconnect.webapp.models;

import org.springframework.data.annotation.Id;

/**
 * User superclass for general properties
 * and methods
 * @author Borga Haktan Bilen
 * @version 18/04/2021 
 */
public abstract class User {
    
    // Properties
    @Id
    private Long uuid;     // Maybe mongo equivalent of sequance generator needed?
    private long id;
    private String name;
    private String department;

    // Constructor
    /**
     * Initializes the shared properties
     * @param name The name of the user
     * @param instutionId The unique instutionId of User
     * @param department The department of the User
     */
    public User( String name, long instutionId, String department ) {
        this.name = name;
        id = instutionId;
        this.department = department;
    }

    // Methods
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    /* Need an online status checking method.
     * TO-DO after the implementation of LiveSessionManager
     * class. Additionally, for online status subclasses of this
     * class might need a small modification also
    */
}

package me.labconnect.webapp.models;

import org.springframework.data.annotation.Id;

/**
 * A generic User of LabConnect
 * 
 * @author Borga Haktan Bilen
 * @author Berkan Şahin
 * @version 22.04.2021
 */
public abstract class User {

    // Properties
    @Id
    private String objectID;
    private long userID;
    private String name;
    private String department;

    // Constructor
    /**
     * Initializes the shared properties
     * 
     * @param name          The name of the user
     * @param institutionId The unique institutionId of User
     * @param department    The department of the User
     */
    public User(String name, long institutionId, String department) {
        this.name = name;
        userID = institutionId;
        this.department = department;
    }

    // Methods
    public long getUserId() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    // TODO Live session methods
}

package me.labconnect.webapp.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * A generic User of LabConnect
 * 
 * @author Borga Haktan Bilen
 * @author Berkan Şahin
 * @version 22.04.2021
 */
@Document(collection = "users")
public abstract class User {

    // Properties
    @Id
    private String objectID;
    private long institutionID;
    private String name;
    private String department;
    private boolean isOnline;

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
        this.institutionID = institutionId;
        this.department = department;
    }

    /**
     * Internal constructor for MongoDB use
     */
    protected User() {
    }

    // Methods
    /**
     * Gets the user id
     * 
     * @return The user id as long type
     */
    public long getUserId() {
        return institutionID;
    }

    /**
     * Gets the name of the user
     * 
     * @return Name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the department of the user.
     * 
     * @return Department of the user.
     */
    public String getDepartment() {
        return department;
    }

    /**
     * Check if the given user is this user
     * 
     * @param other The object to check for equality
     * @return {@code true} if the institution IDs are equal, otherwise
     *         {@code false}
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof User) {
            User tmp;
            tmp = (User) other;
            return institutionID == tmp.institutionID;
        }

        return false;
    }

    /**
     * Set the online status of the user
     * 
     * @param isOnline The new online status of the user
     */
    public void setOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

    /**
     * Returns the online status of the user
     * 
     * @return the online status of the user
     */
    public boolean isOnline() {
        return isOnline;
    }
}

package me.labconnect.webapp.models;

import org.springframework.data.annotation.Id;

/**
 * User superclass for general properties
 * and methods
 * @author Borga Haktan Bilen
 * @version 18/04/2021 
 */
public class User {
    
    // Variable
    @Id
    private Long uuid;     // Maybe mongo equivalent of sequance generator needed?
    private long id;
    private String name;
    private String departmant;

    // Methods
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDepartmant() {
        return departmant;
    }

    /* Need an online status checking method.
     * TO-DO after the implementation of LiveSessionManager
     * class.
    */
}

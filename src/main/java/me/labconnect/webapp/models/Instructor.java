package me.labconnect.webapp.models;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * An instructor, which is the user that creates assignments, starts live
 * sessions and uploads tests
 * 
 * @author Berkan Şahin
 * @version 27.04.2021
 */
@Document(collection = "instructors")
public class Instructor extends User {

    // Variables
    private int[] sections;

    /**
     * Create a new Instructor instance
     * 
     * @param name          The name of the instructor
     * @param institutionId The unique institution ID of the instructor
     * @param department    The department of the instructor
     * @param sections      The sections taught by this instructor
     */
    public Instructor(String name, long institutionId, String department, int[] sections) {
        super(name, institutionId, department);
        this.sections = sections;
    }

    private Instructor() {
    }

    // Methods

    /**
     * Returns the sections taught by this instructor
     * 
     * @return the sections taught by this instructor
     */
    public int[] getSections() {
        return sections;
    }

}

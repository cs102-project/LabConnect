package me.labconnect.webapp.models;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Class representing a Tutor, i.e., a user that can meet with Students during a
 * live session
 * 
 * @author Borga Haktan Bilen
 * @version 22.04.2021
 */
@Document(collection = "tutors")
public class Tutor extends User implements Meetable {

    // Properties
    private String meetingLink;

    // Constructors
    /**
     * Initializes the necessary properties except meeting link.
     * 
     * @param name          Name of the tutor.
     * @param institutionId Unique institution id of the tutor.
     * @param department    Tutor's department
     */
    public Tutor(String name, long institutionId, String department) {
        this(name, institutionId, department, null);
    }

    /**
     * Initializes all properties of Tutor object.
     * 
     * @param name          Name of the tutor.
     * @param institutionId Unique institution id of the tutor.
     * @param department    Tutor's department.
     * @param meetingLink   The {@code URL} of the online meeting.
     */
    public Tutor(String name, long institutionId, String department, String meetingLink) {
        super(name, institutionId, department);
        this.meetingLink = meetingLink;
    }

    private Tutor() {
    }

    // Methods
    /**
     * Gets the meeting link as String.
     * 
     * @return The {@code URL} of the online meeting.
     */
    @Override
    public String getMeetingLink() {
        return meetingLink;
    }

    /**
     * Sets the meeting link for Tutor object.
     * 
     * @param meetingLink The meeting link.
     */
    public void setMeetingLink(String meetingLink) {
        this.meetingLink = meetingLink;
    }
}

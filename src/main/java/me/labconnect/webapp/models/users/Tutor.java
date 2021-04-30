package me.labconnect.webapp.models.users;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import me.labconnect.webapp.models.livesession.Meetable;

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

    /**
     * Initialize all properties of a Tutor object, including Object ID. Intended to
     * be used while retrieving entries from the database
     * 
     * @param name          Name of the tutor
     * @param institutionId Unique institution ID of the tutor
     * @param department    Tutor's department
     * @param meetingLink   The URL of the online meeting.
     * @param isOnline      The online status of the tutor
     * @param objectID      The unique object ID assigned to the database entry
     */
    @PersistenceConstructor
    public Tutor(String name, long institutionId, String department, String meetingLink, boolean isOnline,
            String objectID) {
        this(name, institutionId, department, meetingLink);
        this.isOnline = isOnline;
        this.objectID = objectID;
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

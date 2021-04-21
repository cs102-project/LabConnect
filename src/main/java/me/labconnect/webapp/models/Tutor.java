package me.labconnect.webapp.models;

/**
 * Class representing the Tutor of the online lab setting.
 * 
 * @author Borga Haktan Bilen
 * @version 22/04/2021
 */
public class Tutor extends User implements Meetable {

    // Properties
    private String meetingLink;
    
    // Constructors
    /**
     * Initializes the necessary properties except meeting link.
     * 
     * @param name Name of the tutor.
     * @param instutionId Unique instution id of the tutor.
     * @param department Tutor's department
     */
    public Tutor ( String name, long instutionId, String department ) {
        super(name, instutionId, department);
    }

    /**
     * Initializes all properties of Tutor object.
     * 
     * @param name Name of the tutor.
     * @param instutionId Unique instution id of the tutor.
     * @param department Tutor's department.
     * @param meetingLink The {@code URL} of the online meeting. 
     */
    public Tutor ( String name, long instutionId, 
                    String department, String meetingLink ) {
        super(name, instutionId, department);
        this.meetingLink = meetingLink;
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
    public void setMeetingLink( String meetingLink ) {
        this.meetingLink = meetingLink;
    }
}

package me.labconnect.webapp.models;

import java.util.ArrayList;

/**
 * Class representing the Tutor's of lab setting
 * 
 * @author Borga Haktan Bilen
 * @version 22/04/2021
 */
public class TeachingAssistant extends User implements Meetable {
    
    // Properties
    private String meetingLink;
    private ArrayList<Student> students;

    // Constructors
    /**
     * Initializes the necessary properties except meeting link.
     * 
     * @param name Name of the TA.
     * @param instutionId Unique instution id of the TA.
     * @param department TA's department
     */
    public TeachingAssistant ( String name, long instutionId, 
                                String department, ArrayList<Student> assignedStudents ) {
        super(name, instutionId, department);
        students = assignedStudents;
    }

    /**
     * Initializes all properties of Tutor object.
     * 
     * @param name Name of the TA.
     * @param instutionId Unique instution id of the TA.
     * @param department TA's department.
     * @param meetingLink The {@code URL} of the online meeting. 
     */
    public TeachingAssistant ( String name, long instutionId, 
                                String department, String meetingLink,
                                ArrayList<Student> assignedStudents ) {
        super(name, instutionId, department);
        this.meetingLink = meetingLink;
        students = assignedStudents;
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
     * Sets the meeting link for Teaching Assistant object.
     * 
     * @param meetingLink The {@code URL} of the online meeting.
     */
    public void setMeetingLink( String meetingLink ) {
        this.meetingLink = meetingLink;
    }

    /**
     * Adds the student to the list of assigned students
     * to unique teaching assistant.
     * 
     * @param theStudent Student object which is going to be added.
     */
    public void addStudent( Student theStudent ) {
        students.add( theStudent );
    }
    
    /**
     * Removes the specified student from the list of 
     * assigned students to unique teaching assistant.
     * 
     * @param theStudent Student object which is going to be removed. 
     */
    public void removeStudent( Student theStudent ) {
        students.remove( theStudent );
    }

    /**
     * Gets the students which is assigned to this 
     * teaching assistant.
     * 
     * @return ArrayList of assigned Student objects.
     */
    public ArrayList<Student> getStudents() {
        return students;
    }
}   
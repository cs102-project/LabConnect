package me.labconnect.webapp.models.users;

import java.util.ArrayList;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import me.labconnect.webapp.models.livesession.Meetable;

/**
 * Class representing a TA, i.e., a User that can grade Attempts, meet with
 * students and manage a meeting queue
 * 
 * @author Borga Haktan Bilen
 * @author Berkan Şahin
 * @version 30.04.2021
 */
@Document(collection = "assistants")
public class TeachingAssistant extends User implements Meetable {

    // Properties
    private String meetingLink;
    @DBRef
    private ArrayList<Student> students;
    private int section;

    // Constructors
    /**
     * Initializes the necessary properties except meeting link.
     * 
     * @param name             Name of the TA.
     * @param institutionId    Unique institution id of the TA.
     * @param department       TA's department
     * @param assignedStudents The students this TA is assigned to
     */
    public TeachingAssistant(String name, long institutionId, String department, ArrayList<Student> assignedStudents,
            int section) {
        super(name, institutionId, department);
        students = assignedStudents;
        this.section = section;
    }

    /**
     * Initializes all properties of the TA object.
     * 
     * @param name             Name of the TA.
     * @param institutionId    Unique institution id of the TA.
     * @param department       TA's department.
     * @param meetingLink      The URL of the online meeting hosted by the TA
     * @param assignedStudents The students this TA is assigned to
     * @param section          The section this TA is assgined to
     */
    public TeachingAssistant(String name, long institutionId, String department, String meetingLink,
            ArrayList<Student> assignedStudents, int section) {
        super(name, institutionId, department);
        this.meetingLink = meetingLink;
        students = assignedStudents;
        this.section = section;
    }

    /**
     * Initializes all properties of the TA object, including the Object ID
     * 
     * @param name          Name of the TA.
     * @param institutionId Unique institution id of the TA.
     * @param department    TA's department.
     * @param meetingLink   The URL of the online meeting hosted by the TA
     * @param students      The students this TA is assigned to
     * @param section       The section this TA is assgined to
     * @param isOnline      The online status of this TA
     * @param objectID      The object ID assigned by the database
     */
    @PersistenceConstructor
    public TeachingAssistant(String name, long institutionId, String department, String meetingLink,
            ArrayList<Student> students, int section, boolean isOnline, String objectID) {
        this(name, institutionId, department, meetingLink, students, section);
        this.isOnline = isOnline;
        this.objectID = objectID;
    }

    // Methods
    /**
     * Gets the meeting link as String.
     * 
     * @return The URL of the online meeting.
     */
    @Override
    public String getMeetingLink() {
        return meetingLink;
    }

    /**
     * Set/update the meeting link for this TA
     * 
     * @param meetingLink The {@code URL} of the online meeting.
     */
    public void setMeetingLink(String meetingLink) {
        this.meetingLink = meetingLink;
    }

    /**
     * Adds the student to the list of assigned students to unique teaching
     * assistant.
     * 
     * @param theStudent Student object which is going to be added.
     */
    public void addStudent(Student theStudent) {
        students.add(theStudent);
    }

    /**
     * Removes the specified student from the list of assigned students to unique
     * teaching assistant.
     * 
     * @param theStudent Student object which is going to be removed.
     */
    public void removeStudent(Student theStudent) {
        students.remove(theStudent);
    }

    /**
     * Gets the students which is assigned to this teaching assistant.
     * 
     * @return ArrayList of assigned Student objects.
     */
    public ArrayList<Student> getStudents() {
        return students;
    }

    /**
     * Return the section this TA is assigned to
     * 
     * @return the section this TA is assigned to
     */
    public int getSection() {
        return section;
    }
}
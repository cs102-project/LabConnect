package me.labconnect.webapp.models;

import java.util.ArrayList;

/**
 * A Live Session Manager is to control the meeting held by
 * teaching assistans and students during the lab hours.
 * 
 * @author Alp Ertan
 * @version 25.04.2021
 */
public class LiveSession {

    // Properties
    private String sessionID;
    private LabAssignment sessionLab;
    private TeachingAssistant[] sessionTAs;
    private ArrayList<Student> studentQueue;

    // Constructors

    /**
     * Creates a new Live Session Manager with a determined 
     * assignment and TAs
     * 
     * @param sessionID  The ID of the session
     * @param sessionLab The assignment of the lab
     * @param sessionTAs TAs participating in the lab
     */
    public LiveSession(String sessionID, LabAssignment sessionLab,
                TeachingAssistant[] sessionTAs) {
        this.sessionID = sessionID;
        this.sessionLab = sessionLab;
        this.sessionTAs = sessionTAs;
        studentQueue = new ArrayList<Student>();
    }

    // Methods
    /**
     * Adds a new student to the live session queue
     * 
     * @param newStudent The student being added to the session queue
     */
    public void addStudent(Student newStudent) {
        studentQueue.add(newStudent);
    }

    /**
     * Returns the next student in the queue
     * 
     * @return next student in queue
     */
    public Student nextStudent() {
        Student nextStudent = studentQueue.get(0);
        studentQueue.remove(0);
        return nextStudent;
    }

    /**
     * Clears all students from the queue 
     */
    public void endSession() {
        studentQueue.clear();
    }



}
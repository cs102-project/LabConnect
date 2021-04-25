package me.labconnect.webapp.livesession;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;

import me.labconnect.webapp.models.Assignment;
import me.labconnect.webapp.models.Student;
import me.labconnect.webapp.models.TeachingAssistant;

/**
 * A Live Session Manager is to control the meeting held by
 * teaching assistans and students during the lab hours.
 * 
 * @author Alp Ertan
 * @author Berkan Şahin
 * @version 25.04.2021
 */
public class LiveSessionManager {

    // Properties
    private String sessionID;
    private Assignment sessionLab;
    private TeachingAssistant[] sessionTAs;
    private Queue<Student> studentQueue;

    // Constructors

    /**
     * Creates a new Live Session Manager with a determined 
     * assignment and TAs
     * 
     * @param sessionID  The ID of the session
     * @param sessionLab The assignment of the lab
     * @param sessionTAs TAs participating in the lab
     */
    public LiveSessionManager(String sessionID, Assignment sessionLab,
                TeachingAssistant[] sessionTAs) {
        this.sessionID = sessionID;
        this.sessionLab = sessionLab;
        this.sessionTAs = sessionTAs;
        studentQueue = new PriorityQueue<>(new AttemptAmountComparator(this.sessionLab));
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
    public Student getNextStudent() {
        return studentQueue.poll();
    }

    /**
     * Clears all students from the queue 
     */
    public void endSession() {
        studentQueue.clear();
    }



}
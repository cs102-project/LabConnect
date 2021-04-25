package me.labconnect.webapp.livesession;

import java.util.Queue;

import me.labconnect.webapp.models.Assignment;
import me.labconnect.webapp.models.Student;

/**
 * A Live Session Manager is to control the meetings held during the lab hours.
 * 
 * @author Alp Ertan
 * @author Berkan Şahin
 * @version 25.04.2021
 */
public abstract class LiveSession {

    // Properties
    protected String sessionID;
    protected Assignment sessionLab;
    protected Queue<Student> studentQueue;

    // Constructors

    /**
     * Creates a new Live Session Manager with a determined assignment
     * 
     * @param sessionID  The ID of the session
     * @param sessionLab The assignment of the lab
     */
    public LiveSession(String sessionID, Assignment sessionLab) {
        this.sessionID = sessionID;
        this.sessionLab = sessionLab;
        this.studentQueue = initQueue();
    }

    // Methods

    /**
     * Initialize the internal student queue
     * 
     * @return the newly created student queue
     */
    protected abstract Queue<Student> initQueue();

    /**
     * Adds a new student to the live session queue
     * 
     * @param newStudent The student being added to the session queue
     * @return {@code true} if the student can attend this session
     * 
     * @implNote The default implementation always returns {@code true}
     */
    public boolean addStudent(Student newStudent) {
        studentQueue.add(newStudent);
        return true;
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

    /**
     * Returns the assignment for this live session
     * @return the assignment for this live session
     */
    public Assignment getSessionAssignment() {
        return sessionLab;
    }

}
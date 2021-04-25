package me.labconnect.webapp.livesession;

import java.util.PriorityQueue;
import java.util.Queue;

import me.labconnect.webapp.models.Assignment;
import me.labconnect.webapp.models.Student;
import me.labconnect.webapp.models.TeachingAssistant;

/**
 * Manages a live session for a singular TA where the students are queued based
 * on their attempt count
 * 
 * @author Berkan Şahin
 * @version 25.04.2021
 */
public class TASessionManager extends LiveSessionManager {

    private TeachingAssistant sessionTA;

    /**
     * Create a new live session for the specified TA and assignment
     * 
     * @param sessionID  The session ID
     * @param sessionLab The assignment for this session
     * @param sessionTA  The TA hosting this session
     */
    public TASessionManager(String sessionID, Assignment sessionLab, TeachingAssistant sessionTA) {
        super(sessionID, sessionLab);
        this.sessionTA = sessionTA;
    }

    /**
     * Initializes a new priority queue where students are ordered based on their
     * attempt count
     * 
     * @return the newly created PriorityQueue instance
     * @see AttemptAmountComparator
     */
    @Override
    protected Queue<Student> initQueue() {
        return new PriorityQueue<>(new AttemptAmountComparator(getSessionAssignment()));
    }

    /**
     * Attempt to add a new student to the queue
     * 
     * @param newStudent The student to add
     * 
     * @return {@code true} if the student has a submission for the assignment and
     *         belongs in this session, otherwise {@code false}
     */
    @Override
    public boolean addStudent(Student newStudent) {
        if (newStudent.getSubmissionFor(getSessionAssignment()) == null
                || sessionTA.getStudents().indexOf(newStudent) == -1) {
            return false;
        } else {
            return super.addStudent(newStudent);
        }
    }

}

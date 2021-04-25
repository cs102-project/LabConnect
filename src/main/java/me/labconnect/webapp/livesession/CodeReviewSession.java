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
public class CodeReviewSession extends LiveSession {

    private TeachingAssistant sessionTA;

    /**
     * Create a new live session for the specified TA and assignment
     * 
     * @param sessionID  The session ID
     * @param sessionLab The assignment for this session
     * @param sessionTA  The TA hosting this session
     */
    public CodeReviewSession(String sessionID, Assignment sessionLab, TeachingAssistant sessionTA) {
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
     * Add a new student to the queue if their last attempt was successful
     * 
     * @return {@code true} if the student is added to the queue, otherwise {@code false}
     */
    @Override
    public boolean addStudent(Student newStudent) {
        if (newStudent.getSubmissionFor(sessionLab).getFinalAttempt().passedAllTests()) {
            return super.addStudent(newStudent);
        } else {
            return false;
        }
    }

    /**
     * Return the teaching assistant hosting this session
     * @return the teaching assistant hosting this session
     */
    public TeachingAssistant getSessionTA() {
        return sessionTA;
    }
}

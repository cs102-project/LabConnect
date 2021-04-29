package me.labconnect.webapp.models.livesession;

import java.util.Collections;

import me.labconnect.webapp.models.data.Assignment;
import me.labconnect.webapp.models.users.Student;
import me.labconnect.webapp.models.users.TeachingAssistant;

/**
 * A live session for a singular TA where the students are queued based on their
 * attempt count
 * 
 * @author Berkan Şahin
 * @version 27.04.2021
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
     * Add a new student to the queue if their last attempt was successful
     * <p>
     * The place of the new student is based on their attempt count
     * 
     * @param newStudent The student to add
     * @return {@code true} if the student is added to the queue, otherwise
     *         {@code false}
     */
    @Override
    public boolean addStudent(Student newStudent) {
        if (newStudent.getSubmissionFor(sessionLab).getFinalAttempt().passedAllTests()) {
            getStudentQueue().add(newStudent);
            Collections.sort(getStudentQueue(), new AttemptAmountComparator(sessionLab)); // This uses TimSort, which
                                                                                          // has near O(n) complexity
                                                                                          // for mostly sorted lists
            return true;
        } else {
            return false;
        }
    }

    /**
     * Return the teaching assistant hosting this session
     * 
     * @return the teaching assistant hosting this session
     */
    public TeachingAssistant getSessionTA() {
        return sessionTA;
    }
}

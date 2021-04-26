package me.labconnect.webapp.livesession;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import me.labconnect.webapp.models.Assignment;
import me.labconnect.webapp.models.Student;
import me.labconnect.webapp.models.Tutor;

/**
 * A live session where Students are tutored by any tutor available
 * 
 * @author Berkan Şahin
 * @version 25.04.2021
 */
public class TutoringSession extends LiveSession {

    // Variables
    private List<Tutor> sessionTutors;

    /**
     * Create a new Tutoring session
     * 
     * @param sessionID
     * @param sessionLab
     * @param sessionTutors
     */
    public TutoringSession(String sessionID, Assignment sessionLab, List<Tutor> sessionTutors) {
        super(sessionID, sessionLab);
        this.sessionTutors = sessionTutors;
    }

    /**
     * Initialize a linked list of students, which acts like a queue
     * 
     * @return The newly created linked list
     */
    @Override
    protected Queue<Student> initQueue() {
        return new LinkedList<>();
    }

    /**
     * Return the tutors for this session
     * 
     * @return the tutors for this session
     */
    public List<Tutor> getTutors() {
        return sessionTutors;
    }

    /**
     * Assign a Tutor to this section, if they aren't already assigned
     * 
     * @param tutor The tutor to assign
     */
    public void addTutor(Tutor tutor) {
        if (!sessionTutors.contains(tutor)) {
            sessionTutors.add(tutor);
        }
    }

    /**
     * Clears the student queue and the tutor list
     */
    @Override
    public void endSession() {
        super.endSession();
        sessionTutors.clear();
    }

}

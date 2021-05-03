package me.labconnect.webapp.models.livesession;

import java.util.List;

import me.labconnect.webapp.models.data.Assignment;
import me.labconnect.webapp.models.users.Tutor;

/**
 * A live session where Students are tutored by any tutor available
 * 
 * @author Berkan Şahin
 * @version 27.04.2021
 */
public class TutoringSession extends LiveSession {

    // Variables
    private List<Tutor> sessionTutors;

    /**
     * Create a new Tutoring session
     * 
     * @param sessionID The unique ID of the Tutoring session
     * @param sessionLab The assignment this session is for
     * @param sessionTutors The tutors participating in the session
     */
    public TutoringSession(String sessionID, Assignment sessionLab, List<Tutor> sessionTutors) {
        super(sessionID, sessionLab);
        this.sessionTutors = sessionTutors;
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

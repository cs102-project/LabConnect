package me.labconnect.webapp.models.livesession;

import java.util.HashMap;
import java.util.List;

import me.labconnect.webapp.models.data.LabAssignment;
import me.labconnect.webapp.models.users.Student;
import me.labconnect.webapp.models.users.TeachingAssistant;
import me.labconnect.webapp.models.users.Tutor;

/**
 * Manages all live sessions (review and tutoring) related to a particular
 * {@link me.labconnect.webapp.models.data.LabAssignment}
 * 
 * @author Berkan Şahin
 * @version 25.04.2021
 */
public class LiveSessionManager {

    // Variables
    private LabAssignment sessionLab;
    private String sessionID;
    private HashMap<TeachingAssistant, CodeReviewSession> reviewSessions;
    private List<TeachingAssistant> sessionTAs;
    private TutoringSession tutoringSession;

    // Constructor

    /**
     * Create a new Live Session Manager for a lab assignment
     * 
     * @param sessionLab    The lab assignment this session is for
     * @param sessionID     The session ID
     * @param sessionTAs    The TAs participating in this session
     * @param sessionTutors The tutors participating in this session
     */
    public LiveSessionManager(LabAssignment sessionLab, String sessionID, List<TeachingAssistant> sessionTAs,
            List<Tutor> sessionTutors) {
        this.sessionLab = sessionLab;
        this.sessionID = sessionID;
        this.sessionTAs = sessionTAs;

        // TODO (somehow) generate unique IDs for sessions
        reviewSessions = new HashMap<>();
        for (TeachingAssistant sessionTA : sessionTAs) {
            reviewSessions.put(sessionTA, new CodeReviewSession(this.sessionID, this.sessionLab, sessionTA));
        }

        tutoringSession = new TutoringSession(sessionID, sessionLab, sessionTutors);
        sessionLab.setLive(true);
    }

    // Methods

    /**
     * Add a student to their corresponding CodeReviewSession if they have one
     * 
     * @param student The student to add
     * @return {@code true} if the student has a submission for the assignment and
     *         is assigned to a TA with a review session
     */
    public boolean addStudentForReview(Student student) {
        for (TeachingAssistant ta : sessionTAs) {
            if (ta.getStudents().contains(student) && student.getSubmissionFor(sessionLab) != null) {
                return reviewSessions.get(ta).addStudent(student);
            }
        }

        return false;
    }

    /**
     * Add a student to the TutoringSession for this lab
     * 
     * @param student the student to add
     */
    public void addStudentForTutoring(Student student) {
        tutoringSession.addStudent(student);
    }

    /**
     * Get a live session hosted by a particular meetable user, if it exists
     * 
     * @param host The user that hosts the session
     * @return The session hosted by the specified user if it exists, otherwise
     *         {@code null}
     */
    public LiveSession getLiveSessionFor(Meetable host) {
        if (host instanceof TeachingAssistant) {
            return reviewSessions.get(host);
        } else if (host instanceof Tutor && tutoringSession.getTutors().contains(host)) {
            return tutoringSession;
        } else {
            return null;
        }
    }

    /**
     * Check whether or not the given meetable user is currently hosting a live
     * session
     * 
     * @param host The user to check
     * @return {@code true} if the user hosts a session, otherwise {@code false}
     */
    public boolean hostsLiveSession(Meetable host) {
        return sessionTAs.contains(host) || tutoringSession.getTutors().contains(host);
    }

    /**
     * End all the live sessions managed by this session
     */
    public void endSession() {
        for (CodeReviewSession reviewSession : reviewSessions.values()) {
            reviewSession.endSession();
        }

        tutoringSession.endSession();
        sessionLab.setLive(false);
    }

    /**
     * Assign a new Tutor to the tutoring session
     * 
     * @param tutor The tutor to add
     */
    public void assignToSession(Tutor tutor) {
        tutoringSession.addTutor(tutor);
    }

    /**
     * Assign a new TA to a review session, if they aren't already hosting one
     * 
     * @param newTA The TA to assign
     */
    public void assignToSession(TeachingAssistant newTA) {
        if (!sessionTAs.contains(newTA)) {
            sessionTAs.add(newTA);
            reviewSessions.put(newTA, new CodeReviewSession(sessionID, sessionLab, newTA));
        }
    }
}
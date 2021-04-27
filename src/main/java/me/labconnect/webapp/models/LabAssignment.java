package me.labconnect.webapp.models;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import me.labconnect.webapp.TARepository;
import me.labconnect.webapp.livesession.LiveSessionManager;
import me.labconnect.webapp.testing.Tester;

/**
 * A Lab Assignment, which is an assignment that is reviewed live by TAs
 * 
 * @see me.labconnect.webapp.livesession.LiveSessionManager
 * @author Berkan Şahin
 * @version 25.04.2021
 */
public class LabAssignment extends Assignment {

    // Variables
    @Autowired
    private TARepository assistantRepository;
    
    boolean isLive;

    // Constructor
    /**
     * Creates an assignment object which contains every property of an assignment
     * 
     * @param title           Title of the assignment
     * @param dueDate         Due date of the assignment in Date type
     * @param visible         Visibility of the assginment
     * @param instructionFile Absolute path of the instruction prompt
     * @param tests           Determined unit or style tests
     * @param sections        Sections that are going to recieve the assignment
     * @throws IOException If copying the instructions fails
     * @implNote The instructions are copied to the assignment directory, therefore
     *           it is safe to delete them afterwards
     */
    public LabAssignment(String title, Date dueDate, boolean visible, Path instructionFile, ArrayList<Tester> tests,
            int[] sections) throws IOException {
        super(title, dueDate, visible, instructionFile, tests, sections);
    }

    /**
     * Start a live session manager for this assignment
     * 
     * @return The newly started live session manager
     */
    public LiveSessionManager startLiveSession() {
        ArrayList<TeachingAssistant> sessionTAs;
        ArrayList<Tutor> sessionTutors;

        sessionTAs = new ArrayList<>();
        for (int section : getSections()) {
            sessionTAs.addAll(assistantRepository.findBySection(section));
        }

        // TODO (maybe) query for available tutors
        sessionTutors = new ArrayList<>();

        // TODO generate an ID for the live session
        return new LiveSessionManager(this, "FIXME", sessionTAs, sessionTutors);

    }

    /**
     * Set whether or not this lab has an active live session
     * 
     * @param live The new live value
     */
    public void setLive(boolean live) {
        isLive = live;
    }

    /**
     * Return whether this lab has an active live session or not
     * 
     * @return {@code true} if there is an active live session for this lab,
     *         otherwise {@code false}
     */
    public boolean isLive() {
        return isLive;
    }

}

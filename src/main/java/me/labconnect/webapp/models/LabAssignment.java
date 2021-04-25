package me.labconnect.webapp.models;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;

import me.labconnect.webapp.testing.Tester;

/**
 * A Lab Assignment, which is an assignment that is reviewed live by TAs
 * 
 * @see me.labconnect.webapp.livesession.LiveSessionManager
 * @author Berkan Şahin
 * @version 25.04.2021
 */
public class LabAssignment extends Assignment {

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



}

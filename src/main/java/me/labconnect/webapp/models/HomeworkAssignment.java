package me.labconnect.webapp.models;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;

import me.labconnect.webapp.testing.Tester;

/**
 * Class representing homework assignments
 * 
 * @author Borga Haktan Bilen
 * @version 23.04.2021
 * @see Assignment
 */
public class HomeworkAssignment extends Assignment {

    // Properties
    ArrayList<Student> groupMembers;

    // Constructor
    /**
     * Initializes every property
     * 
     * @param groupMembers The list containing the group member objects
     * @param title The title of the assignment
     * @param dueDate The due date of the assignment
     * @param isVisible The visibility of the assignment (to students)
     * @param instructionFile Instructions file path
     * @param tests The list of test for this assignment
     * @param sections The list of assigned sections
     * @throws IOException if proccessing the instruction file fails
     */
    public HomeworkAssignment(ArrayList<Student> groupMembers, String title, Date dueDate, boolean isVisible, 
                                Path instructionFile, ArrayList<Tester> tests, int[] sections)  throws IOException {
        super(title, dueDate, isVisible, instructionFile, tests, sections);
        this.groupMembers = groupMembers;
    }

    /**
     * Initializes properties except for the list of group members. Which
     * is going to be initiliazed as blank list
     * 
     * @param title The title of the assignment
     * @param dueDate The due date of the assignment
     * @param isVisible The visibility of the assignment (to students)
     * @param instructionFile Instructions file path
     * @param tests The list of test for this assignment
     * @param sections The list of assigned sections
     * @throws IOException if proccessing the instruction file fails
     */
    public HomeworkAssignment( String title, Date dueDate, boolean isVisible, Path instructionFile, 
                                ArrayList<Tester> tests, int[] sections ) throws IOException {
        super(title, dueDate, isVisible, instructionFile, tests, sections);
        groupMembers = new ArrayList<>();
    }

    // Methods
    /**
     * Gets the list of group members
     * 
     * @return The list of group members
     */
    public ArrayList<Student> getGroupMembers() {
        return groupMembers;
    }

    /**
     * Sets the list of group members
     * 
     * @param groupMembers The list of group members
     */
    public void setGroupMembers(ArrayList<Student> groupMembers) {
        this.groupMembers = groupMembers;
    }

    /**
     * Adds a student to group members list
     * 
     * @param student The student
     */
    public void addGroupMember(Student student) {
        groupMembers.add(student);
    }
}

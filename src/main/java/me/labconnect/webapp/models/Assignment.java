package me.labconnect.webapp.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import me.labconnect.webapp.testing.Tester;

/**
 * A generic assignment model class
 * 
 * @author Berkan Şahin
 * @author Borga Haktan Bilen
 * @version 23.04.2021
 */
@Document(collection = "assignments")
public class Assignment {

    // Constants
    final int MAX_GRADE = 100;
    final int MAX_ATTEMPTS = 5;
    final String ASSIGNMENT_ROOT = "/var/labconnect/assignments";

    // Variables
    @Id
    private String id; // handled by the Mongo backend, do not modify by hand!

    private String assignmentID;
    private boolean isCompleted;
    private boolean isVisible;
    private int[] sections;
    private String instructionFileName;
    private String title;
    private Date dueDate;
    private ArrayList<Tester> tests;

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
    public Assignment(String title, Date dueDate, boolean visible, Path instructionFile, ArrayList<Tester> tests,
            int[] sections) throws IOException {

        Path assignmentDir;

        assignmentDir = Files.createTempDirectory(Paths.get(ASSIGNMENT_ROOT), "");
        instructionFileName = instructionFile.getFileName().toString();
        Files.copy(instructionFile, assignmentDir.resolve(instructionFileName));


        assignmentID = assignmentDir.getFileName().toString();

        this.tests = tests;
        this.title = title;
        this.dueDate = dueDate; // Getting the date as int pairs might be a good idea. However, we need a
                                // Calendar implementation.
        this.sections = sections;
        isVisible = visible;
        isCompleted = false;
    }

    // Methods
    /**
     * Gets the completion status of assignment
     * 
     * @return {@code true} if assigment is completed, {@code false} otherwise
     */
    public boolean isCompleted() {
        return isCompleted;
    }

    /**
     * Sets the completion status of assignment
     * 
     * @param isCompleted The completion status of assignment
     */
    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    /**
     * Gets the visibility of the assinment
     * 
     * @return {@code true} if assigment is visible, {@code false} otherwise
     */
    public boolean isVisible() {
        return isVisible;
    }

    /**
     * Sets the visibility of the assignment
     * 
     * @param isVisible Visibility of the assignment
     */
    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    /**
     * Gets the int array, representing the section numbers that are assigned to the
     * assignment
     * 
     * @return Assigned sections' number
     */
    public int[] getSections() {
        return sections;
    }

    /**
     * Sets the array of section numbers (as integers) that are assigned to the
     * assignment
     * 
     * @param sections {@code int} array of assigned sections' number
     */
    public void setSections(int[] sections) {
        this.sections = sections;
    }

    /**
     * Gets the instructions file in File type
     * 
     * @return The assignment prompt file
     */
    public Path getInstructions() {
        return Paths.get(ASSIGNMENT_ROOT, assignmentID, instructionFileName);
    }

    /**
     * Sets the assignment instructions file
     * 
     * @param instructions The instructions file
     * @throws IOException if copying the instruction file fails
     * @implNote The instruction file is copied to the assignment directory,
     *           therefore it is safe to delete the file afterwards
     */
    public void setInstructions(Path instructions) throws IOException {
        Files.copy(instructions, getAssignmentDir().resolve(instructions.getFileName()));
        instructionFileName = instructions.getFileName().toString();
    }

    /**
     * Gets the name of the assignment
     * 
     * @return Name of the assignment
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the name of the assignment
     * 
     * @param title Name of the assignment
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the due date
     * 
     * @return The due date of the assignment
     */
    public Date getDueDate() {
        return dueDate;
    }

    /**
     * Sets the due date
     * 
     * @param dueDate Due date of the assignment
     */
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Overload of the setDueDate method. Sets the due date with given components
     * 
     * @param day    The day of the due date
     * @param month  The month of the due date
     * @param year   The year of the due date
     * @param hour   Hour of the day (due date)
     * @param minute Minute of the due date
     * @apiNote Must be careful while handling because of the GMT setoff.
     */
    public void setDueDate(int day, int month, int year, int hour, int minute) {
        this.dueDate = new GregorianCalendar(year, month, day, hour, minute).getTime();
    }

    /**
     * Gets the ArrayList of Tester objects
     * 
     * @return The ArrayList of Tester objects
     */
    public ArrayList<Tester> getTests() {
        return tests;
    }

    /**
     * Sets the ArrayList of Tester objects
     * 
     * @param tests the ArrayList of Tester objects
     */
    public void setUnitTests(ArrayList<Tester> tests) {
        this.tests = tests;
    }

    /**
     * Returns the directory assignment-related files are stored
     * 
     * @return the directory assignment-related files are stored
     */
    public Path getAssignmentDir() {
        return Paths.get(ASSIGNMENT_ROOT, assignmentID);
    }

    /**
     * Returns the identifier String for this assignment
     * <p>
     * The identifier is derived from the randomly-created and unique assignment
     * directory name
     * 
     * @return the identifier for this assignment
     */
    public String getAssignmentID() {
        return assignmentID;
    }
}

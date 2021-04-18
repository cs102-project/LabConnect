package me.labconnect.webapp.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.io.File;

import org.springframework.data.annotation.Id;

import me.labconnect.webapp.unittest.UnitTest;

/**
 * A generic assignment model class
 * @author Berkan Şahin
 * @author Borga Haktan Bilen
 * @version 18.04.2021
 */
public class Assignment {

    // Constants
    final int MAX_GRADE = 100;
    final int MAX_ATTEMPTS = 5; // Should we really hard-code these? -Berkan

    // Variables
    @Id
    public Long id; // handled by the Mongo backend, do not modify by hand!

    private boolean isCompleted;
    private boolean isVisible;
    private int[] sections;
    private File instructions;
    private String title;
    private Date dueDate;
    private ArrayList<UnitTest> unitTests;

    // Constructor
    /**
     * Creates an assignment object which contains every property of an
     * assignment
     * @param title Title of the assignment
     * @param dueDate Due date of the assignment in Date type
     * @param visible Visibility of the assginment
     * @param absolutePath Absolute path of the instruction prompt
     * @param unitTests Determined unit tests
     * @param sections Sections that are going to recieve the assignement
     */
    public Assignment ( String title, Date dueDate, boolean visible, 
                        String absolutePath, ArrayList<UnitTest> unitTests,
                        int[] sections ) {
        instructions = new File( absolutePath );
        this.unitTests = unitTests;
        this.title = title;
        this.dueDate = dueDate;         // Getting the date as int pairs might be a good idea. However, we need a Calendar implementation. 
        this.sections = sections;
        isVisible = visible;
        isCompleted = false;
    }

    
    // Methods
    /**
     * Gets the completion status of assignment
     * @return {@code true} if assigment is completed,
     * {@code false} otherwise
     */
    public boolean isCompleted() {
        return isCompleted;
    }
    
    /**
     * Sets the completion status of assignment
     * @param isCompleted The completion status of assignment
     */
    public void setCompleted( boolean isCompleted ) {
        this.isCompleted = isCompleted;
    }
    
    /**
     * Gets the visibility of the assinment
     * @return {@code true} if assigment is visible,
     * {@code false} otherwise
     */
    public boolean isVisible() {
        return isVisible;
    }
    
    /**
     * Sets the visibility of the assignment
     * @param isVisible Visibility of the assignment
     */
    public void setVisible( boolean isVisible ) {
        this.isVisible = isVisible;
    }
    
    /**
     * Gets the int array, representing the section numbers 
     * that are assigned to the assignment
     * @return Assigned sections' number
     */
    public int[] getSections() {
        return sections;
    }
    
    /**
     * Sets the array of section numbers (in int) that are
     * assigned to the assignment
     * @param sections {@code int} array of assigned sections' number
     */
    public void setSections( int[] sections ) {
        this.sections = sections;
    }
    /**
     * Gets the instructions file in File type
     * @return The assignment prompt file
     */
    public File getInstructions() {
        return instructions;
    }
    
    /**
     * Sets the assignment instructions file
     * @param instructions The instructions file
     */
    public void setInstructions( File instructions ) {
        this.instructions = instructions;
    }
    
    /**
     * Gets the name of the assignment
     * @return Name of the assignment
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * Sets the name of the assignment
     * @param title Name of the assignment
     */
    public void setTitle( String title ) {
        this.title = title;
    }
    
    // Might be a solution for default setter method for title
    // public void setTitle() {
    //     title = instructions.getName();
    // }

    /**
     * Gets the due date
     * @return The due date of the assignment
     */
    public Date getDueDate() {
        return dueDate;
    }
    
    /**
     * Sets the due date
     * @param dueDate Due date of the assignment
     */
    public void setDueDate( Date dueDate ) {
        this.dueDate = dueDate;
    }

    /**
     * Overload of the setDueDate method. Sets the due date
     * with given components
     * @param day The day of the due date
     * @param month The month of the due date
     * @param year The year of the due date
     * @param hour Hour of the day (due date)
     * @param minute Minute of the due date
     * @apiNote Must be careful while handling because of the
     * GMT setoff.
     */
    public void setDueDate( int day, int month, 
                            int year, int hour, int minute ) {
        this.dueDate = new GregorianCalendar(year, month, day, hour, minute).getTime();
    }
    
    /**
     * Gets the ArrayList of UnitTest objects
     * @return The ArrayList of UnitTest objects
     */
    public ArrayList<UnitTest> getUnitTests() {
        return unitTests;
    }
    
    /**
     * Sets the ArrayList of UnitTest objects
     * @param unitTests the ArrayList of UnitTest objects
     */
    public void setUnitTests( ArrayList<UnitTest> unitTests ) {
        this.unitTests = unitTests;
    }
    
    /**
     * Deletes the current assignment by clearing the UnitTests,
     * nullifying the id, deleting the instruction file
     */
    public void delete() {
        id = null;      // Can be a good idea to handle this in the db side first
        unitTests.clear();
        instructions.delete();
    }
}

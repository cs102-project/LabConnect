package me.labconnect.webapp.models;

import java.util.ArrayList;

/**
 * Class represents the Students.
 * 
 * @author Borga Haktan Bilen
 * @version 22/04/2021
 */
public class Student extends User {
    
    // Properties
    ArrayList<Assignment> assignments;

    // Constructor
    /**
     * Initializes the necessary properties except meeting link.
     * 
     * @param name Name of the student.
     * @param instutionId Unique instution id of the student.
     * @param department Student's department.
     */
    public Student ( String name, long instutionId, 
                        String department, ArrayList<Assignment> assignments ) {
        super(name, instutionId, department);
        this.assignments = assignments;
    }

    // Methods
    /**
     * Adds an assignment object to the assignments list.
     * 
     * @param assignment The assignment which is going to be added.
     */
    public void giveAssignment( Assignment assignment ) {
        assignments.add( assignment );
    }

    /**
     * Gets the assignments list of a specific Student.
     * 
     * @return ArrayList of assigned Assignments.
     */
    public ArrayList<Assignment> getAssignments() {
        return assignments;
    }

    /**
     * Gets the assignments from the list in the specified index.
     * 
     * @param index The Index which contains the desired assignment.
     * @return The required assignment.
     */
    public Assignment getAssignmentByIndex( int index ) {

        if ( assignments.size() >= index && index < 0 ) {
            return null;
        }
        else {
            return assignments.get( index );
        }
    }
}

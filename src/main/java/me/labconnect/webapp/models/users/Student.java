package me.labconnect.webapp.models.users;

import me.labconnect.webapp.models.data.Assignment;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * A class representing a student, i.e., a User that submits solutions to assignments and waits in
 * queues
 *
 * @author Borga Haktan Bilen
 * @author Berkan Şahin
 * @version 30.04.2021
 */
@Document(collection = "students")
public class Student {

    // Properties
    @Id
    private ObjectId id;
    private List<ObjectId> assignments;

    // Constructor

    /**
     * Initialize a Student object with a list of assignments supplied by the caller
     *
     * @param assignments The list of unique IDs for the assignments this student is responsible for
     */
    public Student(List<ObjectId> assignments) {
        this.assignments = assignments;
    }

    // Methods

    /**
     * Return the unique object ID of this Student
     *
     * @return The unique object ID of this Student
     */
    public ObjectId getId() {
        return id;
    }

    /**
     * Gets the assignments list of a specific Student.
     *
     * @return The unique IDs of the assignments assigned to this student
     */
    public List<ObjectId> getAssignments() {
        return assignments;
    }

    /**
     * Add an assignment to the list of assignments this student is responsible for
     *
     * @param assignment The assignment to add
     */
    public void giveAssignment(Assignment assignment) {
        giveAssignment(assignment.getId());
    }

    /**
     * Private helper method that actually adds the ID of the assignment this student is responsible for
     *
     * @param assignmentId The unique ID of the new assignment
     */
    private void giveAssignment(ObjectId assignmentId) {
        assignments.add(assignmentId);
    }

}

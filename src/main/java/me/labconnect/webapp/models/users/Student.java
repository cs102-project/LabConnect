package me.labconnect.webapp.models.users;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import me.labconnect.webapp.models.data.Assignment;

/**
 * A class representing a student, i.e., a User that submits solutions to
 * assignments and waits in queues
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
    private int section;

    // Constructor

    public Student(List<ObjectId> assignments, int section) {
        this.assignments = assignments;
        this.section = section;
    }

    @PersistenceConstructor
    public Student(ObjectId id, List<ObjectId> assignments, int section) {
        this.assignments = assignments;
        this.id = id;
        this.section = section;
    }

    public int getSection() {
        return section;
    }

    // Methods
    
    public ObjectId getId() {
        return id;
    }
    
    /**
     * Gets the assignments list of a specific Student.
     * 
     * @return ArrayList of assigned Assignments.
     */
    public List<ObjectId> getAssignments() {
        return assignments;
    }

    public void giveAssignment(Assignment assignment) {
        giveAssignment(assignment.getObjectId());
    }

    private void giveAssignment(ObjectId assignmentId) {
        assignments.add(assignmentId);
    }
    
}

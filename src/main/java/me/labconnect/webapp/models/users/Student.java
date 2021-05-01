package me.labconnect.webapp.models.users;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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

    // Constructor

    public Student(List<ObjectId> assignments) {
        this.assignments = assignments;
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
    
}

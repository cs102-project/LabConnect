package me.labconnect.webapp.models.users;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
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
    //@JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    //@JsonSerialize(using = ToStringSerializer.class)
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
    //@JsonSerialize(using = ToStringSerializer.class)
    public List<ObjectId> getAssignments() {
        return assignments;
    }

    public void giveAssignment(Assignment assignment) {
        giveAssignment(assignment.getId());
    }

    private void giveAssignment(ObjectId assignmentId) {
        assignments.add(assignmentId);
    }
    
}

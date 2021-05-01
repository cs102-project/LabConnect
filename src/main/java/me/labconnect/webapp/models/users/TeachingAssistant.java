package me.labconnect.webapp.models.users;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
/**
 * Class representing a TA, i.e., a User that can grade Attempts, meet with
 * students and manage a meeting queue
 * 
 * @author Borga Haktan Bilen
 * @author Berkan Şahin
 * @version 30.04.2021
 */
@Document(collection = "assistants")
public class TeachingAssistant {

    // Properties
    @Id
    private ObjectId id;
    private List<ObjectId> students; // not sure whether to point to a user ref or a student ref. 

    // Constructors
    
    /**
     * Initializes all properties of the TA object.
     * 
     * @param name             Name of the TA.
     * @param institutionId    Unique institution id of the TA.
     * @param department       TA's department.
     * @param meetingLink      The URL of the online meeting hosted by the TA
     * @param assignedStudents The students this TA is assigned to
     * @param section          The section this TA is assgined to
     */
    public TeachingAssistant(List<ObjectId> students) {
        this.students = students;
    }

    // Methods
    
    public ObjectId getId() {
        return id;
    }
    
    /**
     * Gets the students which is assigned to this teaching assistant.
     * 
     * @return ArrayList of assigned Student objects.
     */
    public List<ObjectId> getStudents() {
        return students;
    }
    
}
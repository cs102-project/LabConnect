package me.labconnect.webapp.models.users;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Class representing a Tutor, i.e., a user that can meet with Students during a
 * live session
 * 
 * @author Borga Haktan Bilen
 * @author Vedat Eren Arican
 * @version 22.04.2021
 */
@Document(collection = "tutors")
public class Tutor {

    // Properties
    @Id
    private ObjectId id;

    // Constructors
    
    /**
     * Initialize all properties of a Tutor object, including Object ID. Intended to
     * be used while retrieving entries from the database
     * 
     * @param name          Name of the tutor
     * @param institutionId Unique institution ID of the tutor
     * @param department    Tutor's department
     * @param meetingLink   The URL of the online meeting.
     * @param isOnline      The online status of the tutor
     * @param objectID      The unique object ID assigned to the database entry
     */
    public Tutor() {
        
    }

    public ObjectId getId() {
        return id;
    }

    // Methods
    
}

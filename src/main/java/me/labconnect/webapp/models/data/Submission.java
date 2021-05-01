package me.labconnect.webapp.models.data;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * An aggregation of attempts for a certain assignment
 * 
 * @author Berkan Şahin
 * @version 25.04.2021
 */
@Document(collection = "submissions")
public class Submission {

    @Id
    private ObjectId id;
    private List<Attempt> attempts;
    
    public Submission(List<Attempt> attempts) {
        this.attempts = attempts;
    }
    
    public ObjectId getId() {
        return id;
    }
    
    public List<Attempt> getAttempts() {
        return attempts;
    }
    
}

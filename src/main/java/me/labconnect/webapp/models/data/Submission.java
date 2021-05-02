package me.labconnect.webapp.models.data;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    private ObjectId submitterId;
    private List<Attempt> attempts;
    
    public Submission(List<Attempt> attempts, ObjectId submitterId) {
        this.attempts = attempts;
        this.submitterId = submitterId;
    }
    
    public ObjectId getId() {
        return id;
    }
    
    public List<Attempt> getAttempts() {
        return attempts;
    }

    public ObjectId getSubmitterId() {
        return submitterId;
    }

    public void addAttempt(Attempt attempt) {
        attempts.add(attempt);
    }
    
}

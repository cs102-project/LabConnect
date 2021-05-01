package me.labconnect.webapp.models.data;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import me.labconnect.webapp.models.users.Student;

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
    private ObjectId submitterId;
    private List<Attempt> attempts;
    
    public Submission(List<Attempt> attempts, Student submitter) {
        this.attempts = attempts;
        this.submitterId = submitter.getId();
    }

    @PersistenceConstructor
    public Submission(ObjectId id, ObjectId submitterId, List<Attempt> atttempts) {
        this.id = id;
        this.submitterId = submitterId;
        this.attempts = atttempts;
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

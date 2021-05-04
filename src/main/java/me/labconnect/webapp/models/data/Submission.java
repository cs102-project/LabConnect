package me.labconnect.webapp.models.data;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * An aggregation of attempts for a certain assignment
 *
 * @author Berkan Şahin
 * @version 03.05.2021
 */
@Document(collection = "submissions")
public class Submission {

    @Id
    //@JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    //@JsonSerialize(using = ToStringSerializer.class)
    private ObjectId submitterId;
    private List<Attempt> attempts;

    /**
     * Default constructor for the {@code Submission} class
     *
     * @param attempts    List of attempts of the submission
     * @param submitterId Id of the submitter
     */
    public Submission(List<Attempt> attempts, ObjectId submitterId) {
        this.attempts = attempts;
        this.submitterId = submitterId;
    }

    /**
     * Gets the unique object id of the submission
     *
     * @return Object id of the submission
     */
    public ObjectId getId() {
        return id;
    }

    /**
     * Gets the list of attempts of the submission
     *
     * @return List of attempts
     */
    public List<Attempt> getAttempts() {
        return attempts;
    }

    /**
     * Gets the unique object id of the submitter
     *
     * @return The object id of the submitter
     */
    public ObjectId getSubmitterId() {
        return submitterId;
    }

    /**
     * Adds the attempt to the submission
     *
     * @param attempt The attempt which is going to be added
     */
    public void addAttempt(Attempt attempt) {
        attempts.add(attempt);
    }

}

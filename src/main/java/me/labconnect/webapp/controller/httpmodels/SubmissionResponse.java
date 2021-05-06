package me.labconnect.webapp.controller.httpmodels;

import java.util.List;

import org.bson.types.ObjectId;

import me.labconnect.webapp.models.data.Attempt;
import me.labconnect.webapp.models.data.Submission;

public class SubmissionResponse {
    
    private ObjectId id;
    private String submitterName;
    private List<Attempt> attempts;
    
    public SubmissionResponse(Submission submission, String submitterName) {
        this.id = submission.getId();
        this.submitterName = submitterName;
        this.attempts = submission.getAttempts();
    }
    
    public List<Attempt> getAttempts() {
        return attempts;
    }
    
    public ObjectId getId() {
        return id;
    }
    
    public String getSubmitterName() {
        return submitterName;
    }
    
}

package me.labconnect.webapp.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import me.labconnect.webapp.models.data.Attempt;
import me.labconnect.webapp.models.data.Submission;

@Repository
public interface SubmissionRepository extends MongoRepository<Submission, ObjectId> {
    
     public List<Submission> findBySubmitterId(ObjectId submitterId);
    
     @Query("{ attempts : { $in : ?0 } }")
     public Submission findByAttempt(Attempt attempt);
    
}

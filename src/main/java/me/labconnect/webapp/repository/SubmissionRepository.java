package me.labconnect.webapp.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import me.labconnect.webapp.models.data.Submission;

@Repository
public interface SubmissionRepository extends MongoRepository<Submission, ObjectId> {
    
    
    
}

package me.labconnect.webapp.repository;


import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import me.labconnect.webapp.models.data.Assignment;

/**
 * A repository interface for the assignments stored in the database
 * 
 * @author Berkan Şahin
 * @version 27.04.2021
 */
@Repository
public interface AssignmentRepository extends MongoRepository<Assignment, ObjectId> {
    
    @Query("{ submissions: { $in : ?0 } }")
    Assignment findBySubmissionId(ObjectId submissionId);
    
}

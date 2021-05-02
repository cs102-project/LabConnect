package me.labconnect.webapp.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import me.labconnect.webapp.models.data.Submission;

/**
 * Submission repository interface
 *
 * @author Borga Haktan Bilen
 * @author Vedat Eren Arican
 * @version 22.04.2021
 */
@Repository
public interface SubmissionRepository extends MongoRepository<Submission, ObjectId> {

     List<Submission> findBySubmitterId(ObjectId submitterId);

     @Query("{ attempts : { $elemMatch : { _id: ?0 } } }")
     Submission findByAttemptId(ObjectId attemptId);

}

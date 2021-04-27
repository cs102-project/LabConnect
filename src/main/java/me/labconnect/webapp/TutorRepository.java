package me.labconnect.webapp;

import org.springframework.data.mongodb.repository.MongoRepository;

import me.labconnect.webapp.models.Tutor;

/**
 * A repository for the Tutor data stored in the database
 * 
 */
public interface TutorRepository extends MongoRepository<Tutor, String> {
    // TODO
}
package me.labconnect.webapp.repository;


import org.springframework.data.mongodb.repository.MongoRepository;

import me.labconnect.webapp.models.data.Assignment;

/**
 * A repository interface for the assignments stored in the database
 * 
 * @author Berkan Şahin
 * @version 27.04.2021
 */
public interface AssignmentRepository extends MongoRepository<Assignment, String> {

    
}

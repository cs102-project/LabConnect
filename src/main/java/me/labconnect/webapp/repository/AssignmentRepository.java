package me.labconnect.webapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import me.labconnect.webapp.models.data.Assignment;

/**
 * A repository interface for the assignments stored in the database
 * 
 * @author Berkan Şahin
 * @version 27.04.2021
 */
public interface AssignmentRepository extends MongoRepository<Assignment, String> {

    /**
     * Find an assignment by its unique assignment ID, not to be confused with
     * MongoDB ObjectID
     * 
     * @param assignmentID The assignment ID to query
     * @return The assignment with the given ID
     */
    public Optional<Assignment> findByAssignmentID(String assignmentID);

    /**
     * Find all assignments for the given section
     * @param section The section to query
     * @return All assignments for the given section
     */
    public List<Assignment> findBySectionsContaining(int section);
}

package me.labconnect.webapp.repository;

import java.util.List;

import me.labconnect.webapp.models.users.Student;

/**
 * A repository interface for querying Student objects in the database
 * 
 * @author Berkan Şahin
 * @version 28.04.2021
 */
public interface StudentRepository extends UserRepository<Student> {
    
    /**
     * Retrieve all students in a given section
     * 
     * @param section The section to query
     * @return The list of all students in the given section
     */
    public List<Student> findBySection(int section);

    // TODO the rest
}

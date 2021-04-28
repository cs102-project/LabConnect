package me.labconnect.webapp;

import me.labconnect.webapp.models.Instructor;

/**
 * A repository interface for the Instructor objects stored in the database
 * 
 * @author Berkan Şahin
 * @version 27.04.2021
 */
public interface InstructorRepository extends UserRepository<Instructor> {

    /**
     * Find the instructor teaching a particular section
     * @param section The section to query
     * @return The instructor teaching the given section
     */
    public Instructor findBySectionsContaining(int section);
    
}

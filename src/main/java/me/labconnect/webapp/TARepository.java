package me.labconnect.webapp;

import java.util.List;
import org.springframework.stereotype.Repository;

import me.labconnect.webapp.models.TeachingAssistant;

/**
 * A Repository interface for TeachingAssistant objects in the database
 * 
 * @author Berkan Şahin
 * @version 27.04.2021
 */
@Repository
public interface TARepository extends UserRepository<TeachingAssistant>{
    
 
    /**
     * Retrieve all TAs assigned to a particular section
     * @param section The section to search for
     * @return A list of all TAs assigned to a particular section
     */
    public List<TeachingAssistant> findBySection(int section);
}

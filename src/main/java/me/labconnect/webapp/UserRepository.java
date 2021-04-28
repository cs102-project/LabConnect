package me.labconnect.webapp;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import me.labconnect.webapp.models.User;

/**
 * User repository interface
 * @author Borga Haktan Bilen
 * @version 22.04.2021
 */
@Repository
public interface UserRepository<T extends User> extends MongoRepository<T, String> {
    /**
     * Finds a user or users by name.
     * @param name The name of the user(s).
     * @return List of User objects which has the
     * same required name.
     */
    public List<T> findByName( String name );

    /**
     * Finds a user or users by department
     * @param department The department of the user(s)
     * @return List of User objects which has the same
     * required department property.
     */
    public List<T> findByDepartment( String department );

    /**
     * Finds a user by his/her unique institution ID number.
     * @param institutionId The ID number of the wanted user.
     * @return The user object which has the required ID number.
     */
    public T findByInstitutionID( long institutionId );
}

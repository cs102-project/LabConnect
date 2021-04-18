package me.labconnect.webapp;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import me.labconnect.webapp.models.User;

/**
 * User repository interface
 * @author Borga Haktan Bilen
 * @version 18/04/2021
 */
@Repository
public interface UserRepository extends MongoRepository<User, Long> {
    public List<User> findByName( String name );
    public List<User> findByDepartment( String department );
    public User findByInstitutionId( long institutionId );
}

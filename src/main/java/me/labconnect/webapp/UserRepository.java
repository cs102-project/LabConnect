package me.labconnect.webapp;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import me.labconnect.webapp.models.User;

/**
 * User repository interface
 * @author Borga Haktan Bilen
 * @version 18/04/2021
 * @apiNote There is no additional method. Only the ones that
 * are inherited from MongoRepository
 */
@Repository
public interface UserRepository extends MongoRepository<User, Long> {   
}

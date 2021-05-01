package me.labconnect.webapp.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import me.labconnect.webapp.models.users.User;

/**
 * User repository interface
 * 
 * @author Borga Haktan Bilen
 * @author Vedat Eren Arican
 * @version 22.04.2021
 */
@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {
    
    User findByEmail(String email);
    
    List<User> findByInstitution(String institution);
    
    @Query("{ institution: ?0, courses: { $elemMatch: { course: ?1 } } }")
    List<User> findByCourse(String institution, String course);
    
    @Query("{ institution: ?0, courses: { $elemMatch: { course: ?1, section: ?2 } } }")
    List<User> findByCourseSection(String institution, String course, int section);
    
}

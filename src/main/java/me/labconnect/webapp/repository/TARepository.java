package me.labconnect.webapp.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import me.labconnect.webapp.models.users.TeachingAssistant;

/**
 * Teaching assistant repository interface
 *
 * @author Borga Haktan Bilen
 * @author Vedat Eren Arican
 * @version 22.04.2021
 */
@Repository
public interface TARepository extends MongoRepository<TeachingAssistant, ObjectId> {

    @Query("{ students: ?0 }")
    TeachingAssistant findByStudentId(ObjectId studentId);

}

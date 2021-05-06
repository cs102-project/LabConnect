package me.labconnect.webapp.repository;

import me.labconnect.webapp.models.users.Tutor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Tutor repository interface
 *
 * @author Borga Haktan Bilen
 * @author Vedat Eren Arican
 * @version 22.04.2021
 */
@Repository
public interface TutorRepository extends MongoRepository<Tutor, ObjectId> {

}

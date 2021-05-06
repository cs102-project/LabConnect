package me.labconnect.webapp.repository;

import me.labconnect.webapp.models.users.Student;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Student repository interface
 *
 * @author Borga Haktan Bilen
 * @author Vedat Eren Arican
 * @version 22.04.2021
 */
@Repository
public interface StudentRepository extends MongoRepository<Student, ObjectId> {

    @Query("{ assignments: ?0 }")
    List<Student> findAllByAssignmentId(ObjectId assignmentId);

}

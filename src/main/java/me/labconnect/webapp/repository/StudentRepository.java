package me.labconnect.webapp.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import me.labconnect.webapp.models.users.Student;

@Repository
public interface StudentRepository extends MongoRepository<Student, ObjectId> {
    
    @Query("{ assignments: ?0 }")
    List<Student> findAllByAssignmentId(ObjectId assignmentId);
    

    List<Student> findBySection(int section);
}

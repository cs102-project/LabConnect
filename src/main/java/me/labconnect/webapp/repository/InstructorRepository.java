package me.labconnect.webapp.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import me.labconnect.webapp.models.users.Instructor;

@Repository
public interface InstructorRepository extends MongoRepository<Instructor, ObjectId> {
    
}

package me.labconnect.webapp.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import me.labconnect.webapp.models.users.TeachingAssistant;

@Repository
public interface TARepository extends MongoRepository<TeachingAssistant, ObjectId> {
    
    @Query("{ students: ?0 }")
    TeachingAssistant findByStudentId(ObjectId studentId);
    
}

package me.labconnect.webapp.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import me.labconnect.webapp.models.users.TeachingAssistant;

@Repository
public interface TARepository extends MongoRepository<TeachingAssistant, ObjectId> {
    
    TeachingAssistant findByStudentId(ObjectId studentId);
    
}

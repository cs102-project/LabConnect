package me.labconnect.webapp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import me.labconnect.webapp.models.data.Attempt;

public interface AttemptRepository extends MongoRepository<Attempt, String>{
    
}

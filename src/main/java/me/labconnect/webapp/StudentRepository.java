package me.labconnect.webapp;

import org.springframework.data.mongodb.repository.MongoRepository;

import me.labconnect.webapp.models.Student;

public interface StudentRepository extends MongoRepository<Student, String> {
    // TODO
}

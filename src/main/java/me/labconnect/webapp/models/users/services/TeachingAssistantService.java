package me.labconnect.webapp.models.users.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.labconnect.webapp.models.users.Student;
import me.labconnect.webapp.models.users.TeachingAssistant;
import me.labconnect.webapp.repository.TARepository;

@Service
public class TeachingAssistantService {
    
    @Autowired
    private TARepository taRepository;
    
    public void addStudent(TeachingAssistant assistant, Student student) {
        
        assistant.getStudents().add(student.getId());
        taRepository.save(assistant);
        
    }
    
    
    
}

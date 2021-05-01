package me.labconnect.webapp.models.data.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.labconnect.webapp.models.data.Assignment;
import me.labconnect.webapp.repository.AssignmentRepository;

@Service
public class AssignmentService {
    
    public final String ASSIGNMENT_ROOT = "/var/labconnect/assignments";
    
    @Autowired
    private Assignment assignment;
    @Autowired
    private AssignmentRepository assignmentRepository;
    
    public Assignment getAssignment() {
        return assignment;
    }
    
    public boolean isCompleted() {
        
    }
    
}

package me.labconnect.webapp.models.data.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.labconnect.webapp.models.data.Assignment;
import me.labconnect.webapp.models.users.Student;
import me.labconnect.webapp.repository.AssignmentRepository;
import me.labconnect.webapp.repository.StudentRepository;

@Service
public class AssignmentService {
    
    public final String ASSIGNMENT_ROOT = "/var/labconnect/assignments";
    
    @Autowired
    private Assignment assignment;
    @Autowired
    private AssignmentRepository assignmentRepository;
    @Autowired
    private StudentRepository studentRepository;
    
    public Assignment getAssignment() {
        return assignment;
    }
    
    public boolean isCompleted() {
        return assignment.getDueDate().after(new Date());
    }
    
    public Path getInstructionsPath() {
        return Paths.get(ASSIGNMENT_ROOT, assignment.getId().toString(), assignment.getInstructionFileName());
    }
    
    public void setInstructionsFile(Path currentInstructionPath) {
        Files.move(currentInstructionPath, getInstructionsPath()., );
    }
    
    public Assignment createAssignment(String assignmentName, Path instructionFile, Date dueDate, int[] sections,
            boolean visible) throws IOException {
        Assignment assignment;

        assignment = assignmentRepository
                .save(new Assignment(assignmentName, dueDate, visible, instructionFile, new ArrayList<>(), sections));
        // TODO handle IOException in the controller

        for (int section : sections) {
            for (Student student : studentRepository.findBySection(section)) {
                student.giveAssignment(assignment);
                studentRepository.save(student);
            }
        }

        return assignment;
    }
    
}

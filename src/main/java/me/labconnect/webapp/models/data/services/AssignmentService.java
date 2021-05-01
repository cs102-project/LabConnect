package me.labconnect.webapp.models.data.services;

import java.io.IOException;
import java.nio.file.Path;
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

    @Autowired
    private AssignmentRepository assignmentRepo;
    @Autowired
    private StudentRepository studentRepo;

    public Assignment createAssignment(String assignmentName, Path instructionFile, Date dueDate, int[] sections,
            boolean visible) throws IOException {
        Assignment assignment;

        assignment = assignmentRepo
                .save(new Assignment(assignmentName, dueDate, visible, instructionFile, new ArrayList<>(), sections));
        // TODO handle IOException in the controller

        for (int section : sections) {
            for (Student student : studentRepo.findBySection(section)) {
                student.giveAssignment(assignment);
                studentRepo.save(student);
            }
        }

        return assignment;
    }
}
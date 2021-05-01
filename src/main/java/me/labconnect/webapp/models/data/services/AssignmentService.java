package me.labconnect.webapp.models.data.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import me.labconnect.webapp.models.data.Assignment;
import me.labconnect.webapp.models.data.Course;
import me.labconnect.webapp.models.testing.Tester;
import me.labconnect.webapp.models.users.Student;
import me.labconnect.webapp.models.users.User;
import me.labconnect.webapp.repository.AssignmentRepository;
import me.labconnect.webapp.repository.StudentRepository;
import me.labconnect.webapp.repository.UserRepository;

@Service
public class AssignmentService {

    public final String ASSIGNMENT_ROOT = "/var/labconnect/assignments";

    @Autowired
    private AssignmentRepository assignmentRepository;
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    public boolean isCompleted(Assignment assignment) {
        return assignment.getDueDate().after(new Date());
    }

    private Path getInstructionsPath(Assignment assignment) {
        return Paths.get(assignment.getInstructionPath());
    }


    public Resource getInstructions(Assignment assignment) throws IOException {
        return new UrlResource(getInstructionsPath(assignment).toUri());
    }

    public Assignment createAssignment(String assignmentName, String institution, Path instructionFile, Date dueDate,
            int[] sections, String courseName, String homeworkType, int maxGrade, int maxAttempts, List<Tester> testers)
            throws IOException {
        Assignment assignment;
        ArrayList<Course> courses;
        ArrayList<Student> students;

        courses = new ArrayList<>();
        for (int section : sections) {
            courses.add(new Course(courseName, section));
        }

        instructionFile = Files.copy(instructionFile,
                Files.createTempDirectory(Paths.get(ASSIGNMENT_ROOT), "").resolve(instructionFile.getFileName()));

        assignment = assignmentRepository.save(new Assignment(assignmentName, courses, homeworkType, dueDate, maxGrade,
                maxAttempts, instructionFile.toString(), testers));
        // TODO handle IOException in the controller

        students = new ArrayList<>();

        // TODO identify student auth
        for (int section : sections) {
            students.addAll(userRepository.findByCourseSection(institution, courseName, section).stream()
                    .filter(u -> u.getAuthorities().contains("FIXME")).map(User::getRoleDocumentId)
                    .map(id -> studentRepository.findById(id).orElseThrow()).collect(Collectors.toList()));

        }

        for (Student student : students) {
            student.giveAssignment(assignment);
            studentRepository.save(student);
        }

        return assignment;
    }

    public Assignment getById(ObjectId assignmentId) {
        return assignmentRepository.findById(assignmentId).orElseThrow();
    }

}

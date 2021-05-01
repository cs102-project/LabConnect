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
import me.labconnect.webapp.models.users.services.UserCreatorService.LCUserRoleTypes;
import me.labconnect.webapp.repository.AssignmentRepository;
import me.labconnect.webapp.repository.StudentRepository;
import me.labconnect.webapp.repository.UserRepository;

/**
 * A service that provides assignment creation and retrieval operations
 * 
 * @see me.labconnect.webapp.models.data.Assignment
 * @author Vedat Eren Arican
 * @author Berkan Şahin
 * @version 01.05.2021
 */
@Service
public class AssignmentService {

    public final String ASSIGNMENT_ROOT = "/var/labconnect/assignments";

    @Autowired
    private AssignmentRepository assignmentRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private UserRepository userRepository;

    /**
     * Check if the given assignment is overdue
     * 
     * @param assignment The assignment to check
     * @return {@code true} if the given assignment's due date was before the
     *         current date, otherwise {@code false}
     */
    public boolean isOverdue(Assignment assignment) {
        return assignment.getDueDate().after(new Date());
    }

    /**
     * Return the absolute path to the instructions for the given assignment
     * 
     * @param assignment The assignment to retrieve instructions from
     * @return The absolute path to the instructions for the given assignment
     */
    public Path getInstructionsPath(Assignment assignment) {
        return Paths.get(ASSIGNMENT_ROOT, assignment.getId().toString(), assignment.getInstructionFilename());
    }

    /**
     * Return the instructions for the given assignment as a serveable Resource
     * 
     * @param assignment The assignment to retrieve instructions from
     * @return The instructions as a Resource ready to serve for downloads
     * @throws IOException If retrieving the instructions fails
     */
    public Resource getInstructions(Assignment assignment) throws IOException {
        return new UrlResource(getInstructionsPath(assignment).toUri());
    }

    /**
     * A helper method that moves the instruction file into an appropriate location
     * 
     * @param assignment      The assignment the instructions are for
     * @param instructionPath The absolute path of the instruction file
     * @throws IOException If the instructions cannot be retrieved
     */
    private void moveInstructionsFile(Assignment assignment, Path instructionPath) throws IOException {
        Files.deleteIfExists(getInstructionsPath(assignment));
        Files.move(instructionPath, getInstructionsPath(assignment).resolve(instructionPath.getFileName()));
    }

    /**
     * Creates a new assignment and assigns it to students taking the specified
     * course in the specified sections
     * 
     * @param assignmentName  The name or title of the assignment
     * @param institution     The institution offering the course this assignment is
     *                        a part of
     * @param instructionFile The instruction file
     * @param dueDate         The due date for the assignment
     * @param sections        The sections this assignment is for
     * @param courseName      The name of the course this assignment is a part of
     * @param homeworkType    The type of the assignment
     * @param maxGrade        The maximum grade for this assignment
     * @param maxAttempts     The maximum attempts allowed for this assignment
     * @param testers         A list of the unit tests/style checks applied to the
     *                        submissions
     * @return The newly created assignment instance
     * @throws IOException If processing the instructions fails
     */
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

        assignment = assignmentRepository.save(new Assignment(assignmentName, courses, homeworkType, dueDate, maxGrade,
                maxAttempts, instructionFile.getFileName().toString(), testers, new ArrayList<>()));

        moveInstructionsFile(assignment, instructionFile);

        students = new ArrayList<>();

        for (int section : sections) {
            students.addAll(userRepository.findByCourseSection(institution, courseName, section).stream()
                    .filter(u -> u.getRoleType() == LCUserRoleTypes.STUDENT).map(User::getRoleDocumentId)
                    .map(id -> studentRepository.findById(id).orElseThrow()).collect(Collectors.toList()));

        }

        for (Student student : students) {
            student.giveAssignment(assignment);
            studentRepository.save(student);
        }

        return assignment;
    }

    /**
     * Retrieve a particular assignment with the given Object ID
     * 
     * @param assignmentId The unique identifier of the assignment
     * @return The assignment with the corresponding ID, if it exists
     */
    public Assignment getById(ObjectId assignmentId) {
        return assignmentRepository.findById(assignmentId).orElseThrow();
    }

}

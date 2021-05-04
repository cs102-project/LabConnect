package me.labconnect.webapp.models.data.services;

import me.labconnect.webapp.models.data.Assignment;
import me.labconnect.webapp.models.data.Course;
import me.labconnect.webapp.models.data.Submission;
import me.labconnect.webapp.models.testing.BadExampleException;
import me.labconnect.webapp.models.testing.Tester;
import me.labconnect.webapp.models.testing.Tests;
import me.labconnect.webapp.models.testing.UnitTest;
import me.labconnect.webapp.models.testing.style.*;
import me.labconnect.webapp.models.users.Instructor;
import me.labconnect.webapp.models.users.TeachingAssistant;
import me.labconnect.webapp.models.users.services.UserService;
import me.labconnect.webapp.repository.AssignmentRepository;
import me.labconnect.webapp.repository.InstructorRepository;
import me.labconnect.webapp.repository.StudentRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A service that provides assignment creation and retrieval operations
 *
 * @author Vedat Eren Arican
 * @author Berkan Şahin
 * @version 01.05.2021
 * @see me.labconnect.webapp.models.data.Assignment
 */
@Service
public class AssignmentService {

    public final String ASSIGNMENT_ROOT = "/var/labconnect/assignments";

    @Autowired
    private AssignmentRepository assignmentRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private InstructorRepository instructorRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private SubmissionService submissionService;

    /**
     * Check if the given assignment is overdue
     *
     * @param assignment The assignment to check
     * @return {@code true} if the given assignment's due date was before the current date, otherwise
     * {@code false}
     */
    public boolean isOverdue(Assignment assignment) {
        return assignment.getDueDate().after(new Date());
    }

    /**
     * Return the absolute path to the instructions for the given assignment, creating the parent
     * directory if it doesn't exist
     *
     * @param assignment The assignment to retrieve instructions from
     * @return The absolute path to the instructions for the given assignment
     * @throws IOException If retrieving/creating the instructions path fails
     */
    public Path getInstructionsPath(Assignment assignment) throws IOException {

        return Files.createDirectories(Paths.get(ASSIGNMENT_ROOT, assignment.getId().toString()));
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
    private void moveInstructionsFile(Assignment assignment, Path instructionPath)
            throws IOException {

        Files.move(instructionPath,
                getInstructionsPath(assignment).resolve(instructionPath.getFileName()),
                StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     * Creates a new assignment and assigns it to students taking the specified course in the
     * specified sections
     *
     * @param assignmentName        The name or title of the assignment
     * @param shortDescription      A short description of the assignment
     * @param institution           The institution offering the course this assignment is a part of
     * @param instructionFile       The instruction file
     * @param dueDate               The due date for the assignment
     * @param sections              The sections this assignment is for
     * @param courseName            The name of the course this assignment is a part of
     * @param homeworkType          The type of the assignment
     * @param maxGrade              The maximum grade for this assignment
     * @param maxAttempts           The maximum attempts allowed for this assignment
     * @param testNames             A list of the tests applied to the submissions
     * @param unitTestName          The name of the unit test, if it is applied
     * @param unitTestTimeLimit     The time limit for the unit test in milliseconds. A value of 0
     *                              indicates no time limit
     * @param testerClass           The tester class for the unit test, if there's any
     * @param exampleImplementation The example implementation from which the correct unit test output
     *                              is generated
     * @param forbiddenStatements   The statements to check for in the {@link me.labconnect.webapp.models.testing.style.ForbiddenStatementChecker}
     *                              if applicable
     * @return The newly created assignment instance
     * @throws IOException         If processing the instructions fails
     * @throws BadExampleException If the example implementation or the tester do not compile or
     *                             generate a runtime error (Determined by a non-zero exit code)
     */
    public Assignment createAssignment(String assignmentName, String shortDescription,
                                       String institution, Path instructionFile,
                                       Date dueDate,
                                       int[] sections, String courseName, String homeworkType, int maxGrade, int maxAttempts,
                                       List<Tests> testNames, String unitTestName, Long unitTestTimeLimit,
                                       Path exampleImplementation,
                                       Path testerClass, ArrayList<String> forbiddenStatements)
            throws IOException, BadExampleException {

        Assignment assignment;
        ArrayList<Course> courses;
        Set<Tester> testers = new HashSet<>();

        if (testNames != null) {
            for (Tests testName : testNames) {
                switch (testName) {
                    case INDENTATION:
                        testers.add(new IndentationChecker());
                        break;
                    case METHOD_NAMING:
                        testers.add(new MethodNamingChecker());
                        break;
                    case CONSTANT_NAMING:
                        testers.add(new ConstantNamingChecker());
                        break;
                    case OPERATORS_SPACE:
                        testers.add(new OperatorsSpaceChecker());
                        break;
                    case UNIT_TEST:
                        testers.add(
                                new UnitTest(unitTestName, testerClass, exampleImplementation, unitTestTimeLimit));
                        break;
                    case PARENTHESIS_SPACE:
                        testers.add(new ParenthesisSpaceChecker());
                        break;
                    case FOR_LOOP_SEMICOLON:
                        testers.add(new ForLoopSemicolonChecker());
                        break;
                    case LOOP_CURLY_BRACKETS:
                        testers.add(new LoopCurlyBracketsChecker());
                        break;
                    case FORBIDDEN_STATEMENTS:
                        testers.add(new ForbiddenStatementChecker(forbiddenStatements));
                        break;
                    case CLASS_INTERFACE_NAMING:
                        testers.add(new ClassNInterfaceNamingChecker());
                        break;
                    case PROGRAM_HEADER_JAVADOC:
                        testers.add(new ProgramHeaderJavadocChecker());
                        break;
                    case DECISION_CURLY_BRACKETS:
                        testers.add(new DecisionCurlyBracketsChecker());
                        break;
                    case METHOD_PARENTHESIS_SPACE:
                        testers.add(new MethodParenthesisSpaceChecker());
                        break;
                    case BLANK_LINE_AFTER_CLASS_DECLARATION:
                        testers.add(new BlankLineAfterClassDeclarationChecker());
                }
            }
        }

        courses = new ArrayList<>();
        for (int section : sections) {
            courses.add(new Course(courseName, section));
        }

        assignment = assignmentRepository.save(
                new Assignment(assignmentName, shortDescription, courses, homeworkType, dueDate, maxGrade,
                        maxAttempts, instructionFile.getFileName().toString(), new ArrayList<>(testers),
                        new ArrayList<>()));

        moveInstructionsFile(assignment, instructionFile);

        for (Course course : courses) {
            userService.getStudentsOfCourseSection(institution, course).forEach(student -> {
                student.giveAssignment(assignment);
                studentRepository.save(student);
            });

            Instructor instructor = userService.getInstructorOfCourseSection(institution, course);
            instructor.getAssignments().add(assignment.getId());
            instructorRepository.save(instructor);

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

    /**
     * Finds the assignments of the given course
     *
     * @param course The course to search for Assignments
     * @return Assignments belonging to the given course
     */
    public Stream<Assignment> findByCourse(Course course) {
        return assignmentRepository.findByCourseSection(course.getCourse(), course.getSection())
                .stream();
    }

    /**
     * Finds the assignment submissions of the assignment given by a specific instructor
     *
     * @param instructor   The instructor who gave the specified assignment
     * @param assignmentId Id of the assignment
     * @return Submissions of the assignment given by the instructor
     */
    public List<Submission> getAssignmentSubmissionsForInstructor(Instructor instructor,
                                                                  ObjectId assignmentId) {

        Assignment assignment = assignmentRepository.findById(assignmentId).orElseThrow();
        List<ObjectId> submissionIds = assignment.getSubmissions();

        return submissionIds.stream().flatMap(submissionService::getStreamById).distinct()
                .collect(Collectors.toList());

    }

    /**
     * Finds the assignment submissions assigned to the given teaching assistant
     *
     * @param assistant    The teaching assistant who assigned to the specified assignment
     * @param assignmentId Id of the assignment
     * @return Assignment submissions assigned to the given teaching assistant
     */
    public List<Submission> getAssignmentSubmissionsForTA(TeachingAssistant assistant,
                                                          ObjectId assignmentId) {

        return assistant.getStudents().stream()
                .map(studentId -> submissionService
                        .getAssignmentSubmissionBySubmitter(assignmentId, studentId))
                .filter(Optional::isPresent).map(Optional::orElseThrow).collect(Collectors.toList());

    }

}

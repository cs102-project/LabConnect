package me.labconnect.webapp.controller;

import me.labconnect.webapp.controller.httpmodels.NewAssignment;
import me.labconnect.webapp.controller.httpmodels.NewNote;
import me.labconnect.webapp.models.data.Assignment;
import me.labconnect.webapp.models.data.Attempt;
import me.labconnect.webapp.models.data.Feedback;
import me.labconnect.webapp.models.data.Submission;
import me.labconnect.webapp.models.data.services.AssignmentService;
import me.labconnect.webapp.models.data.services.AttemptService;
import me.labconnect.webapp.models.data.services.SubmissionService;
import me.labconnect.webapp.models.testing.BadExampleException;
import me.labconnect.webapp.models.users.LCUserDetails;
import me.labconnect.webapp.models.users.Student;
import me.labconnect.webapp.models.users.User;
import me.labconnect.webapp.models.users.services.UserService;
import me.labconnect.webapp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for handling {@code HTTP} requests for assignments
 *
 * @author Vedat Eren Arıcan
 * @author Borga Haktan Bilen
 * @author Berk Çakar
 * @author Berkan Şahin
 * @author Alp Ertan
 * @version 02.05.2021
 */
@RestController
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;
    @Autowired
    private AttemptService attemptService;
    @Autowired
    private SubmissionService submissionService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    /*
     *
     * assignment api:
     *
     * /api/assignments/ GET -> assignments of user POST -> create assignment +++
     *
     * /api/assignments/{objectid}/ GET -> data of assignment +++
     *
     * /api/assignments/{objectid}/download GET -> download instructions of
     * assignment ???
     *
     * /api/assignments/{objectid}/submissions/ GET -> get submissions of assignment
     * POST -> add attempt to assignment +++
     *
     * /api/assignments/{objectid}/submissions/{objectid}/attempts/ GET -> get
     * attempts of assignment +++
     *
     * /api/assignments/{objectid}/submissions/{objectid}/attempts/{objectid} GET ->
     * get details of attempt POST -> give feedback +++
     *
     * /api/assignments/{objectid}/submissions/{objectid}/attempts/{objectid}/
     * download GET -> get source code archive of attempt +++
     *
     * /api/assignments/{objectid}/submissions/{objectid}/attempts/{objectid}/notes
     * GET -> get notes POST -> add notes +++
     *
     */

    /**
     * Gets the specified assignment.
     *
     * @param assignmentId   Id of the assignment
     * @param authentication Token for authentication request
     * @return The requested assignment
     */
    @GetMapping("/api/assignments/{assignmentId}")
    public Assignment getAssignment(@PathVariable ObjectId assignmentId,
                                    Authentication authentication) {

        return assignmentService.getById(assignmentId);

    }

    /**
     * Gets the assignments of a student
     *
     * @param authentication Token for authentication request
     * @return List of assignments
     */
    @GetMapping("/api/assignments")
    public List<Assignment> getAssignmentsFor(Authentication authentication) {

        LCUserDetails userDetails = (LCUserDetails) authentication.getPrincipal();
        User user = userRepository.findById(userDetails.getId()).orElseThrow();

        return user.getCourses().stream().flatMap(assignmentService::findByCourse).distinct()
                .collect(Collectors.toList());

    }

    /**
     * Creates/{@code POST} an assignment with given parameters
     *
     * @param authentication        Token for authentication request
     * @param instructions          Instructions file for the assignment
     * @param exampleImplementation The example implementation for the unit test
     * @param testerClass           The tester class for the unit test
     * @param newAssignment         New assignment object
     * @return Constructed assignment object
     * @throws IOException         If processing the instructions fails
     * @throws BadExampleException If the example implementation or the tester do not compile or
     *                             generate a runtime error (Determined by a non-zero exit code)
     */
    @PostMapping("/api/assignments")
    @Secured("ROLE_INSTRUCTOR")
    public Assignment createAssignment(Authentication authentication,
                                       @RequestParam("instructions-file") MultipartFile instructions,
                                       @RequestParam("example-implementation") MultipartFile exampleImplementation,
                                       @RequestParam("tester-class") MultipartFile testerClass,
                                       @RequestBody NewAssignment newAssignment) throws IOException, BadExampleException {

        LCUserDetails userDetails = (LCUserDetails) authentication.getPrincipal();
        User user = userRepository.findById(userDetails.getId()).orElseThrow();

        // TODO Handle BadExampleException

        return assignmentService.createAssignment(
                newAssignment.getAssignmentTitle(),
                newAssignment.getShortDescription(),
                user.getInstitution(),
                instructions.getResource().getFile().toPath(),
                newAssignment.getDueDate(),
                newAssignment.getSections(),
                newAssignment.getCourseName(),
                newAssignment.getHomeworkType(),
                newAssignment.getMaxGrade(),
                newAssignment.getMaxAttempts(),
                newAssignment.getStyleTests(),
                newAssignment.getUnitTestName(),
                newAssignment.getUnitTestTimeLimit(),
                exampleImplementation.getResource().getFile().toPath(),
                testerClass.getResource().getFile().toPath(),
                newAssignment.getForbiddenStatements()
        );

    }

    /**
     * Gets the instructions file of the given assignment
     *
     * @param assignmentId   Id of the assignment
     * @param authentication Token for authentication request
     * @return Instruction file of the assignment
     * @throws IOException If there is not such assignment
     */
    public Resource getInstructionsFile(@PathVariable ObjectId assignmentId,
                                        Authentication authentication) throws IOException {
        Resource instructionsFile = assignmentService
                .getInstructions(assignmentService.getById(assignmentId));

        if (assignmentService.getById(assignmentId) == null) {
            throw new RuntimeException("An assignment with specified assignment ID does not exist");
        }

        return instructionsFile;
    }

    /**
     * Gets the submissions of an assignment
     *
     * @param authentication Token for authentication request
     * @param assignmentId   Id of the assignment
     * @return List of submissions of the specified assignment
     */
    @GetMapping("/api/assignments/{assignmentId}/submissions")
    @Secured({"ROLE_INSTRUCTOR", "ROLE_TEACHING_ASSISTANT", "ROLE_STUDENT"})
    public List<Submission> getSubmissions(Authentication authentication,
                                           @PathVariable ObjectId assignmentId) {

        LCUserDetails userDetail = (LCUserDetails) authentication.getPrincipal();
        User user = userRepository.findById(userDetail.getId()).orElseThrow();

        switch (user.getRoleType()) {

            case INSTRUCTOR:
                return assignmentService
                        .getAssignmentSubmissionsForInstructor(userService.getInstructorDocumentOf(user),
                                assignmentId);

            case TEACHING_ASSISTANT:
                return assignmentService
                        .getAssignmentSubmissionsForTA(userService.getTADocumentOf(user), assignmentId);

            case STUDENT:
                return List.of(submissionService
                        .getAssignmentSubmissionBySubmitter(assignmentId,
                                userService.getStudentDocumentOf(user).getId()).orElseThrow());

            default:
                throw new RuntimeException("Invalid role!");

        }

    }

    /**
     * Creates/{@code POST} an attempt for submission
     *
     * @param authentication Token for authentication request
     * @param assignmentId   Id of the assignment
     * @param attemptArchive The contents of src/ as a ZIP file
     * @return The added attempt
     * @throws IOException If processing the archive fails
     */
    @PostMapping("/api/assignments/{assignmentId}/submissions")
    @Secured({"ROLE_STUDENT"})
    public Attempt addAttempt(
            Authentication authentication,
            @PathVariable ObjectId assignmentId,
            @RequestParam MultipartFile attemptArchive) throws IOException {

        LCUserDetails userDetail = (LCUserDetails) authentication.getPrincipal();
        User user = userRepository.findById(userDetail.getId()).orElseThrow();

        Student student = userService.getStudentDocumentOf(user);

        return submissionService
                .addAttempt(assignmentId, student.getId(), attemptArchive.getResource().getFile().toPath());

    }

    /**
     * Gets the attempts of a submission
     *
     * @param authentication Token for authentication request
     * @param assignmentId   Id of the assignment
     * @param submissionId   Id of the submission
     * @return The list of attempts for the specified assignment and submission
     */
    @GetMapping("/api/assignments/{assignmentId}/submissions/{submissionId}/attempts/")
    public List<Attempt> getAttempts(Authentication authentication,
                                     @PathVariable ObjectId assignmentId,
                                     @PathVariable ObjectId submissionId) {

        return attemptService.getAttemptsFor(submissionId);

    }

    /**
     * Gets the details of the given submission attempt.
     *
     * @param assignmentId Id of the assignment
     * @param submissionId Id of the submission
     * @param attemptId    Id of the attempt
     * @return Details of the specified submission attempt.
     */
    @GetMapping("/api/assignments/{assignmentId}/submissions/{submissionId}/attempts/{attemptId}")
    public Attempt getAttemptDetails(@PathVariable ObjectId assignmentId,
                                     @PathVariable ObjectId submissionId,
                                     @PathVariable int attemptId) {

        return attemptService.getById(attemptId);
    }

    /**
     * Creates/{@code POST} a feedback for the given submission attempt
     *
     * @param assignmentId Id of the assignment
     * @param submissionId Id of the submission
     * @param attemptId    Id of the attempt
     * @param feedback     Feedback which is going to be added to the attempt
     */
    @PostMapping("/api/assignments/{assignmentId}/submissions/{submissionId}/attempts/{attemptId}")
    public void giveFeedbackToAttempt(@PathVariable ObjectId assignmentId,
                                      @PathVariable ObjectId submissionId,
                                      @PathVariable int attemptId, @RequestBody Feedback feedback) {
        if (feedback.getGrade() > assignmentService.getById(assignmentId).getMaxGrade()) {
            throw new RuntimeException(
                    "The grade in the feedback is bigger than maximum allowed grade " + assignmentService
                            .getById(assignmentId)
                            .getMaxGrade());
        } else if (feedback.getGrade() < 0) {
            throw new RuntimeException("The grade in the feedback cannot be less than 0");
        }

        attemptService.getById(attemptId).giveFeedback(feedback);
    }

    /**
     * Retrieves the source code archive of a specific attempt for a specific assignment
     *
     * @param assignmentId Id of the assignment
     * @param submissionId Id of the submission
     * @param attemptId    Id of the attempt
     * @return A ZIP archive of the attempt source code
     * @throws IOException If archiving the attempt fails
     */
    @GetMapping("/api/assignments/{assignmentId}/submissions/{submissionId}/attempts/{attemptId}/download")
    @Secured({"ROLE_TEACHING_ASSISTANT"})
    public Resource getAttemptArchive(@PathVariable ObjectId assignmentId,
                                      @PathVariable ObjectId submissionId,
                                      @PathVariable int attemptId) throws IOException {
        Resource attemptArchive = attemptService.getAttemptArchive(attemptService.getById(attemptId));

        if (assignmentService.getById(assignmentId).getSubmissions().stream()
                .noneMatch(submissionId::equals)) {
            throw new RuntimeException("The submission and the assignment do not match");
        }

        return attemptArchive;
    }

    /**
     * Retrieves instructions for the assignment with the given ID as a downloadable file
     *
     * @param assignmentId The unique ID of the assignment
     * @return The instructions as a ready to download resource
     * @throws IOException If retrieving the assignment fails
     */
    @GetMapping("/api/assignments({assignmentId}/download")
    public Resource downloadAssignmentInstructions(@PathVariable ObjectId assignmentId)
            throws IOException {
        return assignmentService.getInstructions(assignmentService.getById(assignmentId));
    }

    /**
     * Gets the note written for specific attempt
     *
     * @param authentication Token for authentication request
     * @param attemptId      Id of the attempt
     * @return Note content
     */
    @GetMapping("/api/assignments/{assignmentId}/submissions/{submissionId}/attempts/{attemptId}/notes")
    @Secured("ROLE_STUDENT")
    public String getNote(Authentication authentication, @PathVariable int attemptId) {

        return attemptService.getById(attemptId).getNote();

    }

    /**
     * Adds/{@code POST} note to the specified attempt
     *
     * @param authentication Token for authentication request
     * @param attemptId      Id of the attempt
     * @param note           Note which is going to be added to the attempt
     */
    @PostMapping("/api/assignments/{assignmentId}/submissions/{submissionId}/attempts/{attemptId}/notes")
    @Secured("ROLE_STUDENT")
    public void addNote(Authentication authentication, @PathVariable int attemptId,
                        @RequestBody NewNote note) {

        attemptService.setNoteOfAttempt(submissionService.getAttemptById(attemptId), note.getContent());

    }

}

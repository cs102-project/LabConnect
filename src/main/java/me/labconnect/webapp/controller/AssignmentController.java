package me.labconnect.webapp.controller;

import me.labconnect.webapp.controller.httpmodels.NewAssignment;
import me.labconnect.webapp.controller.httpmodels.NewFeedback;
import me.labconnect.webapp.controller.httpmodels.NewNote;
import me.labconnect.webapp.controller.httpmodels.SubmissionResponse;
import me.labconnect.webapp.models.data.Assignment;
import me.labconnect.webapp.models.data.Attempt;
import me.labconnect.webapp.models.data.Feedback;
import me.labconnect.webapp.models.data.Submission;
import me.labconnect.webapp.models.data.services.AssignmentService;
import me.labconnect.webapp.models.data.services.AttemptService;
import me.labconnect.webapp.models.data.services.SubmissionService;
import me.labconnect.webapp.models.testing.BadExampleException;
import me.labconnect.webapp.models.testing.Tests;
import me.labconnect.webapp.models.users.LCUserDetails;
import me.labconnect.webapp.models.users.Student;
import me.labconnect.webapp.models.users.User;
import me.labconnect.webapp.models.users.services.UserService;
import me.labconnect.webapp.repository.UserRepository;

import org.bson.types.ObjectId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
     * Returns the list of tests currently available for new assignments
     *
     * @return A list of all {@link Tests} enums
     */
    @GetMapping("/api/assignments/tests")
    @Secured({"ROLE_INSTRUCTOR"})
    public List<Tests> getAvailableTests() {
        return Arrays.asList(Tests.values());
    }

    /**
     * Creates/{@code POST} an assignment with given parameters
     *
     * @param authentication Token for authentication request
     * @param newAssignment  New assignment object
     * @return Constructed assignment object
     * @throws IOException         If processing the instructions fails
     * @throws BadExampleException If the example implementation or the tester do not compile or
     *                             generate a runtime error (Determined by a non-zero exit code)
     */
    @PostMapping("/api/assignments")
    @Secured({"ROLE_INSTRUCTOR"})
    public Assignment createAssignment(Authentication authentication,
                                       @ModelAttribute NewAssignment newAssignment) throws IOException, BadExampleException {

        LCUserDetails userDetails = (LCUserDetails) authentication.getPrincipal();
        User user = userRepository.findById(userDetails.getId()).orElseThrow();

        Path tmp = Files.createTempDirectory("");
        Path instructions = Files.createFile(Paths.get(tmp.toString(), newAssignment.getInstructionsFile().getOriginalFilename()));
        newAssignment.getInstructionsFile().transferTo(instructions);
        Path exampleImpl = Files.createFile(Paths.get(tmp.toString(), newAssignment.getExampleImplementation().getOriginalFilename()));
        newAssignment.getExampleImplementation().transferTo(exampleImpl);
        Path testerClass = Files.createFile(Paths.get(tmp.toString(), newAssignment.getTesterClass().getOriginalFilename()));
        newAssignment.getTesterClass().transferTo(testerClass);

        return assignmentService.createAssignment(
                newAssignment.getAssignmentTitle(),
                newAssignment.getShortDescription(),
                user.getInstitution(),
                instructions,
                newAssignment.getDueDate(),
                newAssignment.getCourses(),
                newAssignment.getHomeworkType(),
                newAssignment.getMaxGrade(),
                newAssignment.getMaxAttempts(),
                newAssignment.getStyleTests(),
                newAssignment.getUnitTestName(),
                newAssignment.getUnitTestTimeLimit(),
                exampleImpl,
                testerClass,
                newAssignment.getForbiddenStatements()
        );

    }

    /**
     * Gets the instructions file of the given assignment
     *
     * @param assignmentId Id of the assignment
     * @return Instruction file of the assignment
     * @throws IOException If there is not such assignment
     */
    @GetMapping("/api/assignments/{assignmentId}/download")
    public ResponseEntity<Resource> getInstructionsFile(@PathVariable ObjectId assignmentId) throws IOException {
        final String RESPONSE_TEMPLATE = "attachment; filename=\"%s\"";
        Resource instructionsFile = assignmentService
                .getInstructions(assignmentService.getById(assignmentId));

        if (assignmentService.getById(assignmentId) == null) {
            throw new RuntimeException("An assignment with specified assignment ID does not exist");
        }

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, String.format(RESPONSE_TEMPLATE, instructionsFile.getFilename()))
                .body(instructionsFile);
    }

    /**
     * Gets the submissions of an assignment
     *
     * @param authentication Token for authentication request
     * @param assignmentId   Id of the assignment
     * @return List of submissions of the specified assignment
     */
    @GetMapping("/api/assignments/{assignmentId}/submissions/all")
    @Secured({"ROLE_INSTRUCTOR", "ROLE_TEACHING_ASSISTANT"})
    public List<SubmissionResponse> getSubmissions(Authentication authentication,
                                           @PathVariable ObjectId assignmentId) {

        LCUserDetails userDetail = (LCUserDetails) authentication.getPrincipal();
        User user = userRepository.findById(userDetail.getId()).orElseThrow();

        switch (user.getRoleType()) {

            case INSTRUCTOR:
                return assignmentService
                        .getAssignmentSubmissionsForInstructor(userService.getInstructorDocumentOf(user), assignmentId)
                        .stream()
                        .map(submission -> new SubmissionResponse(submission, userRepository.findByRoleDocumentId(submission.getSubmitterId()).getName()))
                        .collect(Collectors.toList());

            case TEACHING_ASSISTANT:
                return assignmentService
                        .getAssignmentSubmissionsForTA(userService.getTADocumentOf(user), assignmentId)
                        .stream()
                        .map(submission -> new SubmissionResponse(submission, userRepository.findByRoleDocumentId(submission.getSubmitterId()).getName()))
                        .collect(Collectors.toList());

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
        Attempt attempt;

        Path tmp = Files.createTempDirectory("");
        Path attemptFile = Files.createFile(Paths.get(tmp.toString(), attemptArchive.getOriginalFilename()));
        attemptArchive.transferTo(attemptFile);

        Student student = userService.getStudentDocumentOf(user);


        attempt = submissionService.addAttempt(assignmentId, student.getId(), attemptFile);
        return attemptService.runTests(attempt);

    }

    /**
     * Gets the attempts of a submission
     *
     * @param authentication Token for authentication request
     * @param assignmentId   Id of the assignment
     * @return The list of attempts for the specified assignment and submission
     */
    @GetMapping("/api/assignments/{assignmentId}/submissions")
    @Secured({ "ROLE_STUDENT" })
    public SubmissionResponse getSubmissionForStudent(Authentication authentication, @PathVariable ObjectId assignmentId) {
        
        LCUserDetails userDetail = (LCUserDetails) authentication.getPrincipal();
        User user = userRepository.findById(userDetail.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        
        return new SubmissionResponse(submissionService
            .getAssignmentSubmissionBySubmitter(
                assignmentId,
                userService.getStudentDocumentOf(user).getId()
            ).orElseThrow(), user.getName());

    }

    @GetMapping("/api/assignments/{assignmentId}/submissions/{submissionId}")
    @Secured({"ROLE_INSTRUCTOR", "ROLE_TEACHING_ASSISTANT"})
    public SubmissionResponse getSubmissionById(Authentication authentication, @PathVariable ObjectId submissionId) {
        Submission submission = submissionService.getById(submissionId);
        return new SubmissionResponse(submission, userRepository.findByRoleDocumentId(submission.getSubmitterId()).getName());
    }

    /**
     * Gets the details of the given submission attempt.
     *
     * @param submissionId Id of the submission
     * @param attemptId    Id of the attempt
     * @return Details of the specified submission attempt.
     */
    @GetMapping("/api/assignments/{assignmentId}/submissions/{submissionId}/attempts/{attemptId}")
    public Attempt getAttemptDetails(@PathVariable ObjectId submissionId,
                                     @PathVariable int attemptId) {

        return attemptService.getById(submissionId, attemptId);
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
                                      @PathVariable int attemptId, @ModelAttribute NewFeedback feedback) {
        if (feedback.getGrade() > assignmentService.getById(assignmentId).getMaxGrade()) {
            throw new RuntimeException(
                    "The grade in the feedback is bigger than maximum allowed grade " + assignmentService
                            .getById(assignmentId)
                            .getMaxGrade());
        } else if (feedback.getGrade() < 0) {
            throw new RuntimeException("The grade in the feedback cannot be less than 0");
        }

        attemptService.giveFeedback(attemptService.getById(submissionId, attemptId), new Feedback(feedback.getGrade(), feedback.getContent(), new Date()));
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
    public ResponseEntity<Resource> getAttemptArchive(@PathVariable ObjectId assignmentId,
                                                      @PathVariable ObjectId submissionId,
                                                      @PathVariable int attemptId) throws IOException {
        final String RESPONSE_TEMPLATE = "attachment; filename=\"%s\"";
        Resource attemptArchive = attemptService.getAttemptArchive(attemptService.getById(submissionId, attemptId));


        if (assignmentService.getById(assignmentId).getSubmissions().stream()
                .noneMatch(submissionId::equals)) {
            throw new RuntimeException("The submission and the assignment do not match");
        }

        return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/zip"))
                .header(HttpHeaders.CONTENT_DISPOSITION, String.format(RESPONSE_TEMPLATE, attemptArchive.getFilename()))
                .body(attemptArchive);
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
    public String getNote(Authentication authentication, @PathVariable ObjectId submissionId,
                          @PathVariable int attemptId) {

        return attemptService.getById(submissionId, attemptId).getNote();

    }

    /**
     * Adds/{@code POST} note to the specified attempt
     *
     * @param authentication Token for authentication request
     * @param attemptId      Id of the attempt
     * @param note           Notes to be added to this attempt
     */
    @PostMapping("/api/assignments/{assignmentId}/submissions/{submissionId}/attempts/{attemptId}/notes")
    @Secured("ROLE_STUDENT")
    public void addNote(Authentication authentication, @PathVariable ObjectId submissionId, @PathVariable int attemptId,
                        @ModelAttribute NewNote note) {

        attemptService.setNoteOfAttempt(attemptService.getById(submissionId, attemptId), note.getContent());

    }

    /**
     * Get the "file map", i.e, a mapping of filenames to file contents for all the java files in a particular attempt.
     * Useful for live review scenarios.
     *
     * @param submissionId The unique identifier for the submission this attempt is a part of
     * @param attemptId    The (unique for the submission) ID of the attempt
     * @return A mapping of filenames to file contents for all java files in the attempt
     * @throws IOException If processing the attempt content fails
     */
    @GetMapping("/api/assignments/{assignmnetId}/submissions/{submissionId}/attempts/{attemptId}/filemap")
    @Secured({"ROLE_TEACHING_ASSISTANT"})
    public Map<String, List<String>> getFileMap(@PathVariable ObjectId submissionId, @PathVariable int attemptId) throws IOException {
        return attemptService.getAttemptContents(attemptService.getById(submissionId, attemptId));
    }

}

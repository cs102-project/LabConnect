package me.labconnect.webapp.controller;

import me.labconnect.webapp.controller.httpmodels.NewAssignment;
import me.labconnect.webapp.controller.httpmodels.Note;
import me.labconnect.webapp.models.data.Assignment;
import me.labconnect.webapp.models.data.Attempt;
import me.labconnect.webapp.models.data.Submission;
import me.labconnect.webapp.models.data.services.AssignmentService;
import me.labconnect.webapp.models.data.services.AttemptService;
import me.labconnect.webapp.models.data.services.SubmissionService;
import me.labconnect.webapp.models.users.LCUserDetails;
import me.labconnect.webapp.models.users.TeachingAssistant;
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
import java.util.*;
import java.util.stream.Collectors;

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
     * /api/assignments/ GET -> assignments of user POST -> create assignment
     *
     * /api/assignments/{objectid}/ GET -> data of assignment
     *
     * /api/assignments/{objectid}/download GET -> download instructions of
     * assignment
     *
     * /api/assignments/{objectid}/submissions/ GET -> get submissions of assignment
     * POST -> add attempt to assignment
     *
     * /api/assignments/{objectid}/submissions/{objectid}/attempts/ GET -> get
     * attempts of assignment
     *
     * /api/assignments/{objectid}/submissions/{objectid}/attempts/{objectid} GET ->
     * get details of attempt POST -> give feedback
     *
     * /api/assignments/{objectid}/submissions/{objectid}/attempts/{objectid}/
     * download GET -> get source code archive of attempt
     *
     * /api/assignments/{objectid}/submissions/{objectid}/attempts/{objectid}/notes
     * GET -> get notes POST -> add notes
     *
     */

    @GetMapping("/api/assignments/{assignmentId}/submissions/{submissionId}/attempts/{attemptId}/notes")
    @Secured("ROLE_STUDENT")
    public String getNote(Authentication authentication, @PathVariable ObjectId attemptId) {

        return attemptService.getById(attemptId).getNote();

    }

    @PostMapping("/api/assignments/{assignmentId}/submissions/{submissionId}/attempts/{attemptId}/notes")
    @Secured("ROLE_STUDENT")
    public void addNote(Authentication authentication, @PathVariable ObjectId attemptId, @RequestBody Note note) {

        attemptService.setNoteOfAttempt(submissionService.getAttemptById(attemptId), note.getContent());

    }

    @GetMapping("/api/assignments")
    public List<Assignment> getAssignmentsFor(Authentication authentication) {

        LCUserDetails userDetails = (LCUserDetails) authentication.getPrincipal();
        User user = userRepository.findById(userDetails.getId()).orElseThrow();

        return user.getCourses().stream().flatMap(assignmentService::findByCourse).distinct()
                .collect(Collectors.toList());

    }

    @PostMapping("/api/assignments")
    @Secured("ROLE_INSTRUCTOR")
    public Assignment createAssignment(Authentication authentication,
                                       @RequestParam("uploaded-file") MultipartFile instructions,
                                       @RequestBody NewAssignment newAssignment)
            throws IOException {

        LCUserDetails userDetails = (LCUserDetails) authentication.getPrincipal();
        User user = userRepository.findById(userDetails.getId()).orElseThrow();

        // TODO Find a way to add tests

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
                new ArrayList<>()
        );

    }

    @GetMapping("/api/assignments/{assignmentId}")
    public Assignment getAssignment(@PathVariable ObjectId assignmentId, Authentication authentication) {

        return assignmentService.getById(assignmentId);

    }

    @GetMapping("/api/assignments/{assignmentID}/submissions")
    @Secured({"ROLE_INSTRUCTOR", "ROLE_TEACHING_ASSISTANT", "ROLE_STUDENT"})
    public List<Submission> getSubmissions(Authentication authentication, @PathVariable ObjectId assignmentID) {

        List<Submission> submissions;
        Assignment assignment = assignmentService.getById(assignmentID);

        LCUserDetails userDetail = (LCUserDetails) authentication.getPrincipal();
        User user = userRepository.findById(userDetail.getId()).orElseThrow();

        switch (user.getRoleType()) {
            case INSTRUCTOR:
                List<ObjectId> submissionIds = assignmentService.getById(assignmentID).getSubmissions();
                submissions = submissionIds.stream().flatMap(submissionService::getStreamById).distinct()
                        .collect(Collectors.toList());
                break;
            case TEACHING_ASSISTANT:
                TeachingAssistant teachingAssistant = userService.getTADocumentOf(user);
                submissions = teachingAssistant.getStudents().stream()
                        .map(studentId -> submissionService.getAssignmentSubmissionBySubmitter(assignmentID, studentId))
                        .filter(Optional::isPresent).map(Optional::orElseThrow).collect(Collectors.toList());
                break;
            case STUDENT:
                ObjectId submissionId = assignment.getSubmissions().stream()
                        .filter(s -> submissionService.getById(s).getSubmitterId().equals(user.getId())).findAny()
                        .orElseThrow();
                submissions = List.of(submissionService.getById(submissionId));
                break;
            default:
                throw new RuntimeException("Invalid role!");
        }

        return submissions;

    }

    @GetMapping("/api/assignments/{assignmentId}/submissions/{submissionId}/attempts/")
    public List<Attempt> getAttempts(Authentication authentication, @PathVariable ObjectId assignmentId,
                                     @PathVariable ObjectId submissionId) {

        return attemptService.getAttemptsFor(submissionId);

    }

    @PostMapping("/api/assignments/{assignmentId}/submissions")
    public Attempt addAttempt(Authentication authentication,
                              @PathVariable ObjectId assignmentId,
                              @RequestParam MultipartFile attemptArchive) throws IOException {
        LCUserDetails userDetail = (LCUserDetails) authentication.getPrincipal();
        User user = userRepository.findById(userDetail.getId()).orElseThrow();

        return submissionService.addAttempt(assignmentId, user.getId(), attemptArchive.getResource().getFile().toPath());
    }

    @GetMapping("/api/assignments/{assignmentId}/submissions/{submissionId}/attempts/{attemptId}/download")
    @Secured({"ROLE_TEACHING_ASSISTANT"})
    public Resource getAttemptArchive(@PathVariable ObjectId assignmentId, @PathVariable ObjectId submissionId,
                                      @PathVariable ObjectId attemptId) throws IOException {
        Resource attemptArchive = attemptService.getAttemptArchive(attemptService.getById(attemptId));

        if (assignmentService.getById(assignmentId).getSubmissions().stream().noneMatch(submissionId::equals)) {
            throw new RuntimeException("The submission and the assignment do not match");
        }

        return attemptArchive;
    }

}

package me.labconnect.webapp.controller;

import me.labconnect.webapp.models.data.Assignment;
import me.labconnect.webapp.models.data.Attempt;
import me.labconnect.webapp.models.data.Submission;
import me.labconnect.webapp.models.data.services.AssignmentService;
import me.labconnect.webapp.models.data.services.AttemptService;
import me.labconnect.webapp.models.data.services.SubmissionService;
import me.labconnect.webapp.models.users.LCUserDetails;
import me.labconnect.webapp.models.users.TeachingAssistant;
import me.labconnect.webapp.models.users.User;
import me.labconnect.webapp.models.users.services.UserCreatorService.LCUserRoleTypes;
import me.labconnect.webapp.models.users.services.UserService;
import me.labconnect.webapp.repository.TARepository;
import me.labconnect.webapp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private TARepository teachingAssistantRepository;

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
            @RequestParam(name = "name") String assignmentName,
            @RequestParam(name = "description") String shortDescription, @RequestParam MultipartFile instructions,
            @RequestParam(name = "due-date") Date dueDate, @RequestParam(name = "course") String courseName,
            @RequestParam(name = "type") String homeworkType, @RequestParam int[] sections,
            @RequestParam(name = "max-grade") int maxGrade, @RequestParam(name = "max-attempts") int maxAttempts)
            throws IOException {
        LCUserDetails userDetails = (LCUserDetails) authentication.getPrincipal();
        User user = userRepository.findById(userDetails.getId()).orElseThrow();

        // TODO Find a way to add tests
        return assignmentService.createAssignment(assignmentName, shortDescription, user.getInstitution(),
                instructions.getResource().getFile().toPath(), dueDate, sections, courseName, homeworkType, maxGrade,
                maxAttempts, new ArrayList<>());
    }

    @GetMapping("/api/assignments/{assignmentId}")
    public Assignment getAssignment(@PathVariable ObjectId assignmentId, Authentication authentication) {

        return assignmentService.getById(assignmentId);
    }

    @GetMapping("/api/assignments/{assignmentID}/submissions")
    @Secured({ "ROLE_INSTRUCTOR", "ROLE_TEACHING_ASSISTANT", "ROLE_STUDENT" })
    public List<Submission> getSubmissions(Authentication authentication, @PathVariable ObjectId assignmentID) {
        List<Submission> submissions;
        Assignment assignment = assignmentService.getById(assignmentID);
        LCUserDetails userDetail = (LCUserDetails) authentication.getPrincipal();
        User user = userRepository.findById(userDetail.getId()).orElseThrow();
        switch (user.getRoleType()) {
            case INSTRUCTOR:
                List<ObjectId> submissionIds = assignmentService.getById(assignmentID).getSubmissions();
                submissions = submissionIds.stream().flatMap(submissionService::getStreamById).distinct().collect(Collectors.toList());
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
                submissions = List.of( submissionService.getById(submissionId) );
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
}
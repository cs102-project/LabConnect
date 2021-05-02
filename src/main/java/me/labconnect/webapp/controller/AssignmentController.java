package me.labconnect.webapp.controller;

import me.labconnect.webapp.models.data.Assignment;
import me.labconnect.webapp.models.data.services.AssignmentService;
import me.labconnect.webapp.models.data.services.AttemptService;
import me.labconnect.webapp.models.data.services.SubmissionService;
import me.labconnect.webapp.models.users.LCUserDetails;
import me.labconnect.webapp.models.users.User;
import me.labconnect.webapp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    /* 
    
    assignment api:
    
    /api/assignments/
    GET -> assignments of user
    POST -> create assignment
    
    /api/assignments/{objectid}/
    GET -> data of assignment
    
    /api/assignments/{objectid}/download
    GET -> download instructions of assignment
    
    /api/assignments/{objectid}/submissions/
    GET -> get submissions of assignment
    POST -> add attempt to assignment
    
    /api/assignments/{objectid}/submissions/{objectid}/attempts/
    GET -> get attempts of assignment
    
    /api/assignments/{objectid}/submissions/{objectid}/attempts/{objectid}
    GET -> get details of attempt
    POST -> give feedback
    
    /api/assignments/{objectid}/submissions/{objectid}/attempts/{objectid}/download
    GET -> get source code archive of attempt
    
    /api/assignments/{objectid}/submissions/{objectid}/attempts/{objectid}/notes
    GET -> get notes
    POST -> add notes
    
    */

    @GetMapping("/api/assignments")
    public List<Assignment> getAssignmentsFor(Authentication authentication) {
        LCUserDetails userDetails = (LCUserDetails) authentication.getPrincipal();
        User user = userRepository.findById(userDetails.getId()).orElseThrow();

        return user.getCourses().stream().flatMap(assignmentService::findByCourse).distinct().collect(Collectors.toList());
    }

    @PostMapping("/api/assignments")
    @Secured("ROLE_INSTRUCTOR")
    public Assignment createAssignment(Authentication authentication, @RequestParam(name = "name") String assignmentName,
                                       @RequestParam(name = "description") String shortDescription,
                                       @RequestParam MultipartFile instructions, @RequestParam(name = "due-date") Date dueDate,
                                       @RequestParam(name = "course") String courseName, @RequestParam(name = "type") String homeworkType,
                                       @RequestParam int[] sections, @RequestParam(name = "max-grade") int maxGrade,
                                       @RequestParam(name = "max-attempts") int maxAttempts) throws IOException {
        LCUserDetails userDetails = (LCUserDetails) authentication.getPrincipal();
        User user = userRepository.findById(userDetails.getId()).orElseThrow();

        // TODO Find a way to add tests
        return assignmentService.createAssignment(assignmentName, shortDescription, user.getInstitution(),
                instructions.getResource().getFile().toPath(), dueDate, sections, courseName,
                homeworkType, maxGrade, maxAttempts, new ArrayList<>());
    }

    @GetMapping("/api/assignments/{assignmentId}")
    public Assignment getAssignment(@PathVariable ObjectId assignmentId, Authentication authentication) {

        return assignmentService.getById(assignmentId);
    }
}

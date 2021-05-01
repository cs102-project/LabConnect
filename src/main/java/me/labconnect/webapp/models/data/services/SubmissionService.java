package me.labconnect.webapp.models.data.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.labconnect.webapp.models.data.Assignment;
import me.labconnect.webapp.models.data.Attempt;
import me.labconnect.webapp.models.data.Submission;
import me.labconnect.webapp.models.users.Student;
import me.labconnect.webapp.repository.AssignmentRepository;
import me.labconnect.webapp.repository.StudentRepository;
import me.labconnect.webapp.repository.SubmissionRepository;

@Service
public class SubmissionService {

    @Autowired
    private AssignmentRepository assignmentRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private SubmissionRepository submissionRepository;
    @Autowired
    private AssignmentService assignmentService;

    private Submission addSubmission(ObjectId assignmentId, ObjectId submitterId) {
        Submission submission;
        Assignment assignment;

        if (!studentRepository.findAllByAssignmentId(assignmentId).stream()
                .anyMatch(s -> s.getId().equals(submitterId))) {
            throw new NoSuchElementException("The assignment is not assigned to this student");
        }

        assignment = assignmentRepository.findById(assignmentId).orElseThrow();

        submission = new Submission(new ArrayList<>(), submitterId);

        assignment.addSubmission(submission);
        assignmentRepository.save(assignment);
        
        return submission;

    }
    
    public Attempt addAttempt(ObjectId assignmentId, ObjectId submitterId, Path attemptArchive) throws IOException {
        
        Submission submission = getAssignmentSubmissionBySubmitter(assignmentId, submitterId);
        Assignment assignment = assignmentRepository.findBySubmissionId(submission.getId());
        
        // If the assignment doesn't have a submission by this student yet, make one
        if (submission == null) {
            submission = addSubmission(assignmentId, submitterId);
        }
        
        Attempt attempt;
        
        attempt = new Attempt(new ObjectId(), attemptArchive.getFileName().toString(), 0, "", new ArrayList<>());

        submission.addAttempt(attempt);
        submissionRepository.save(submission);
        
        // Move the user's zip archive to storage
        Path assignmentDir = assignmentService.getInstructionsPath(assignment).getParent();
        Files.move(
            attemptArchive,
            assignmentDir
            .resolve(submission.getId().toString())
            .resolve(attempt.getId().toString())
            .resolve(attempt.getAttemptFilename())
        );

        return attempt;
        
    }
    
    public Submission getAssignmentSubmissionBySubmitter(ObjectId assignmentId, ObjectId submitterId) {
        
        Assignment assignment;
        List<Submission> submissionsOfAssignment;
        
        submissionsOfAssignment = new ArrayList<>();
        assignment = assignmentRepository.findById(assignmentId).get();
        
        submissionRepository.findAllById(assignment.getSubmissions()).forEach(submission -> submissionsOfAssignment.add(submission));
        
        return submissionsOfAssignment.stream().filter(submission -> submission.getSubmitterId().equals(submitterId)).findAny().orElse(null);
        
    }
    
    public Submission getById(ObjectId submissionId) {
        return submissionRepository.findById(submissionId).orElseThrow();
    }

    public List<Submission> getSubmissionsBy(ObjectId studentId) {
        return submissionRepository.findBySubmitterId(studentId);
    }

    public List<Submission> getSubmissionsBy(Student student) {
        return getSubmissionsBy(student.getId());
    }

}

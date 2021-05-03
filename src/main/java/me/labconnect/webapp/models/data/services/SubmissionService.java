package me.labconnect.webapp.models.data.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Stream;

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

/**
 * A service class that provides creation and retrieval operations for
 * Submissions
 *
 * @author Berkan Şahin
 * @version 01.05.2021
 */
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

    /**
     * Add a submission for a given assignment
     *
     * @param assignmentId The unique ID of the assignment this submission is for
     * @param submitterId  The unique ID of the student this submission belongs to
     * @return The newly created submission instance
     */
    private Submission addSubmission(ObjectId assignmentId, ObjectId submitterId) {
        Submission submission;
        Assignment assignment;

        if (studentRepository.findAllByAssignmentId(assignmentId).stream()
                .noneMatch(s -> s.getId().equals(submitterId))) {
            throw new NoSuchElementException("The assignment is not assigned to this student");
        }

        assignment = assignmentRepository.findById(assignmentId).orElseThrow();

        submission = new Submission(new ArrayList<>(), submitterId);

        assignment.addSubmission(submission);
        assignmentRepository.save(assignment);

        return submission;

    }

    /**
     * A method that adds an attempt for the given assignment by the given student
     *
     * @param assignmentId   The assignment that is attempted
     * @param submitterId    The submitter of this attempt
     * @param attemptArchive A ZIP archive of the attempt (contents of the src
     *                       directory)
     * @return The newly created Attempt
     * @throws IOException If processing the archive fails
     */
    public Attempt addAttempt(ObjectId assignmentId, ObjectId submitterId, Path attemptArchive) throws IOException {

        Submission submission = getAssignmentSubmissionBySubmitter(assignmentId, submitterId)
                .orElse(addSubmission(assignmentId, submitterId));
        Assignment assignment = assignmentRepository.findBySubmissionId(submission.getId());

        Attempt attempt;

        attempt = new Attempt(submission.getAttempts().size(), attemptArchive.getFileName().toString(), "", null, new ArrayList<>());

        submission.addAttempt(attempt);
        submissionRepository.save(submission);

        // Move the user's zip archive to storage
        Path assignmentDir = assignmentService.getInstructionsPath(assignment).getParent();
        Path attemptDir = Files.createDirectories(assignmentDir.resolve(submission.getId().toString()).resolve(String.valueOf(attempt.getId())));
        Files.move(attemptArchive, attemptDir.resolve(attempt.getAttemptFilename()));

        return attempt;

    }
    
    /**
     * Retrieve the submission attempt with the given unique ID
     *
     * @param attemptId The unique submission attempt ID
     * @return The submission attempt with the given ID if it exists
     */
    public Attempt getAttemptById(int attemptId) {

        return submissionRepository.findByAttemptId(attemptId).getAttempts().stream().filter(attempt -> attempt.getId() == attemptId)
                .findAny().orElseThrow();

    }

    /**
     * Retrieve the assignment submissions with the given unique assignment and submitter ID
     *
     * @param assignmentId The unique assignment ID
     * @param submitterId The unique ID of the submitter of the specified assignment submission
     * @return The submissions of assignment with the given assignment and submitter ID if it exists
     */
    public Optional<Submission> getAssignmentSubmissionBySubmitter(ObjectId assignmentId, ObjectId submitterId) {

        Assignment assignment;
        List<Submission> submissionsOfAssignment;

        submissionsOfAssignment = new ArrayList<>();
        assignment = assignmentRepository.findById(assignmentId).orElseThrow();

        submissionRepository.findAllById(assignment.getSubmissions()).forEach(submissionsOfAssignment::add);

        return submissionsOfAssignment.stream().filter(submission -> submission.getSubmitterId().equals(submitterId)).findAny();

    }

    /**
     * Retrieve the submission with the given unique ID
     *
     * @param submissionId The unique submission ID
     * @return The submission with the given ID if it exists
     */
    public Submission getById(ObjectId submissionId) {
        return submissionRepository.findById(submissionId).orElseThrow();
    }

    /**
     * Retrieve the submission with the given unique ID
     *
     * @param submissionId The unique submission ID
     * @return The submission with the given ID if it exists
     */
    public Stream<Submission> getStreamById(ObjectId submissionId) {
        return submissionRepository.findById(submissionId).stream();
    }

    /**
     * Get all the submissions authored by the student with the given ID
     *
     * @param studentId The ID of the student
     * @return The submissions belonging to the student with the given ID
     */
    public List<Submission> getSubmissionsBy(ObjectId studentId) {
        return submissionRepository.findBySubmitterId(studentId);
    }

    /**
     * Get all the submissions authored by the given student
     *
     * @param student The student to retrieve submissions authored by
     * @return The submissions belonging to the given student
     */
    public List<Submission> getSubmissionsBy(Student student) {
        return getSubmissionsBy(student.getId());
    }

}

package me.labconnect.webapp.models.data.services;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.labconnect.webapp.models.data.Assignment;
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

    /**
     * Add a submission for a given assignment
     * 
     * @param assignmentId The unique ID of the asignment this submission is for
     * @param submitterId  The unique ID of the student this submission belongs to
     * @return The newly created submission instance
     */
    public Submission addSubmission(ObjectId assignmentId, ObjectId submitterId) {
        Submission submission;
        Assignment assignment;

        if (!studentRepository.findAllByAssignmentId(assignmentId).stream()
                .anyMatch(s -> s.getId().equals(submitterId))) {
            throw new NoSuchElementException("The assignment is not assigned to this student");
        }

        assignment = assignmentRepository.findById(assignmentId).orElseThrow();

        // Create a new submission if one doesn't exist
        submission = assignment.getSubmissions().stream().map(id -> submissionRepository.findById(id).orElseThrow())
                .filter(s -> s.getSubmitterId().equals(submitterId)).findFirst()
                .orElse(submissionRepository.save(new Submission(new ArrayList<>(), submitterId)));

        assignment.addSubmission(submission);
        assignmentRepository.save(assignment);

        return submission;

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

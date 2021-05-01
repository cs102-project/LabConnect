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

@Service
public class SubmissionService {

    @Autowired
    private AssignmentRepository assignmentRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private SubmissionRepository submissionRepository;

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

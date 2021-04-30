package me.labconnect.webapp.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import me.labconnect.webapp.models.data.Assignment;
import me.labconnect.webapp.models.users.Student;
import me.labconnect.webapp.repository.AssignmentRepository;
import me.labconnect.webapp.repository.StudentRepository;

/**
 * Part of the API that exposes student-related operations
 * 
 * @author Berkan Şahin
 * @version 30.04.2021
 */
@RestController
public class StudentAPI {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private AssignmentRepository assignmentRepository;

    @GetMapping("/api/student/{studentID}/submissions/{assignmentID}/get_final")
    public ResponseEntity<Resource> downloadFinalAttempt(@PathVariable Long studentID,
            @PathVariable String assignmentID) throws IOException {
        final String fileNameTemplate = "%d_%s_%s.zip";
        final String responseTemplate = "attachment; filename=\"%s\"";
        final String archiveMimeType = "application/zip";

        String meaningfulFileName;
        Student student;
        Assignment assignment;
        Resource finalArchive;

        student = studentRepository.findByInstitutionId(studentID)
                .orElseThrow(() -> new UserNotFoundException(studentID));
        assignment = assignmentRepository.findByAssignmentID(assignmentID)
                .orElseThrow(() -> new AssignmentNotFoundException(assignmentID));
        finalArchive = student.getSubmissionFor(assignment).getFinalCodeArchive();
        meaningfulFileName = String.format(fileNameTemplate, student.getSection(), assignment.getTitle(),
                student.getName());

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(archiveMimeType))
                .header(HttpHeaders.CONTENT_DISPOSITION, String.format(responseTemplate, meaningfulFileName))
                .body(finalArchive);

    }
}

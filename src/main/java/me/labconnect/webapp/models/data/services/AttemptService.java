package me.labconnect.webapp.models.data.services;

import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import me.labconnect.webapp.models.data.Assignment;
import me.labconnect.webapp.models.data.Attempt;
import me.labconnect.webapp.models.data.Submission;
import me.labconnect.webapp.models.users.Student;
import me.labconnect.webapp.models.users.User;
import me.labconnect.webapp.models.users.services.UserCreatorService.LCUserRoleTypes;
import me.labconnect.webapp.repository.AssignmentRepository;
import me.labconnect.webapp.repository.SubmissionRepository;
import me.labconnect.webapp.repository.UserRepository;

/**
 * A service that provides operations for creation, modification and retrieval
 * of Attempts
 * 
 * @author Berkan Şahin
 * @author Vedat Eren Arican
 * @version 01.05.2021
 */
@Service
public class AttemptService {

    private final String ATTEMPT_ROOT = "/var/labconnect/submissions";
    private final String ARCHIVE_NAME_TEMPLATE = "%s_%s.zip";

    @Autowired
    private AssignmentRepository assignmentRepository;
    @Autowired
    private SubmissionRepository submissionRepository;
    @Autowired
    private UserRepository userRepository;

    /**
     * A helper method that extracts the attempt archive
     * 
     * @param attemptArchive The attempt as a ZIP file
     * @return The directory the archive is extracted to
     * @throws IOException If extracting the attempt fails
     */
    private Path extractAttempt(Path attemptArchive) throws IOException {
        Path extractionDir;
        ArrayList<String> extractorArgs;
        ProcessBuilder extractorBuilder;
        Process extractor;

        if (Files.isDirectory(attemptArchive)) {
            return attemptArchive;
        }
        if (!attemptArchive.isAbsolute() || !attemptArchive.toString().endsWith(".zip")) {
            throw new IOException("Invalid archive");
        }

        // Create extraction dir
        extractionDir = Files.createTempDirectory(ATTEMPT_ROOT);

        // Unzip example
        extractorArgs = new ArrayList<>();
        extractorArgs.add("unzip");

        // Rest of the args are derived from unzip manpage
        extractorArgs.add("-oqq"); // Overwrite existing files and suppress output

        extractorArgs.add(attemptArchive.toString());

        // Extract to the newly created dir
        extractorArgs.add("-d");
        extractorArgs.add(extractionDir.toString());

        extractorBuilder = new ProcessBuilder(extractorArgs);
        extractorBuilder.redirectOutput(Redirect.DISCARD);
        extractor = extractorBuilder.start();

        // Wait for extraction to end, then determine success from exit code
        try {
            if (extractor.waitFor() != 0) {
                throw new IOException("Extraction error");
            }
        } catch (InterruptedException e) {
            throw new IOException("unzip was interrupted");
        }

        return extractionDir;
    }

    /**
     * A method that adds an attempt for the given assignment by the given student
     * 
     * @param student        The student submitting the attempt
     * @param assignment     The assignment that is attempted
     * @param attemptArchive A ZIP archive of the attempt (contents of the src
     *                       directory)
     * @return The newly created Attempt
     * @throws IOException If processing the archive fails
     */
    public Attempt addAttempt(Student student, Assignment assignment, Path attemptArchive) throws IOException {
        Submission submission;
        Path attemptDir;
        Attempt attempt;

        submission = assignment.getSubmissions().stream().map(id -> submissionRepository.findById(id).orElseThrow())
                .filter(sub -> sub.getSubmitterId().equals(student.getId())).findAny().orElseThrow();

        attemptDir = extractAttempt(attemptArchive);
        attempt = new Attempt(attemptDir, assignment);

        submission.addAttempt(attempt);
        submissionRepository.save(submission);

        return attempt;
    }

    /**
     * Archive the source code for this attempt and return it as a serveable
     * Resource
     * 
     * @param attempt The attempt to archive
     * @return The ZIP archive of the attempt as a Resource ready for download
     * @throws IOException If archiving the attempt fails
     */
    public Resource getCodeArchive(Attempt attempt) throws IOException {
        Submission parent;
        User submitter;
        Assignment assignment;

        parent = submissionRepository.findByAttemptId(attempt.getID());

        assignment = assignmentRepository.findBySubmissionId(parent.getId());

        submitter = userRepository.findAll().stream().filter(u -> u.getRoleType() == LCUserRoleTypes.STUDENT)
                .filter(u -> u.getRoleDocumentId().equals(parent.getSubmitterId())).findAny().orElseThrow();

        return buildCodeArchive(attempt, assignment.getTitle(), submitter.getName());
    }

    /**
     * Archive the source code submitted for this attempt as a ZIP file
     * 
     * @return The resource corresponding to the newly created archive
     * @throws IOException If archiving the source code fails
     */
    private Resource buildCodeArchive(Attempt attempt, String assignmentName, String submitterName) throws IOException {
        ProcessBuilder archiverBuilder;
        Process archiver;
        List<String> archiverArgs;
        Path attemptPath;
        Path archiveFile;
        String archiveFileName;

        attemptPath = attempt.getAttemptPath();

        archiverArgs = new ArrayList<>();
        archiverArgs.add("zip");
        archiverArgs.add("-qr"); // Suppress output and recurse into directories

        archiveFileName = String.format(ARCHIVE_NAME_TEMPLATE, assignmentName, submitterName);
        archiverArgs.add(archiveFileName);
        archiverArgs.add("."); // Archive everything

        archiverBuilder = new ProcessBuilder(archiverArgs);
        archiverBuilder.directory(attemptPath.toFile());
        archiverBuilder.redirectOutput(Redirect.DISCARD);
        archiver = archiverBuilder.start();

        // Wait for compression to end, then determine success from exit code
        try {
            if (archiver.waitFor() != 0) {
                throw new IOException("Compression error");
            }
        } catch (InterruptedException e) {
            throw new IOException("zip was interrupted");
        }

        archiveFile = attemptPath.resolve(archiveFileName);

        // Move the file to a temprorary place, then return it
        archiveFile = Files.move(archiveFile, Files.createTempDirectory("").resolve(archiveFileName),
                StandardCopyOption.REPLACE_EXISTING);

        return new UrlResource(archiveFile.toUri());
    }

    /**
     * Give an attempt feedback and update its database entry accordingly
     * 
     * @param attempt  The attempt to give feedback to
     * @param feedback The feedback as a list of lines
     * @return The attempt with the feedback added
     */
    public Attempt giveFeedback(Attempt attempt, List<String> feedback) {
        attempt.giveFeedback(feedback);
        update(attempt);
        return attempt;
    }

    /**
     * Give an attempt a grade and update its database entry accordingly
     * 
     * @param attempt The attempt to grade
     * @param grade   The new grade
     * @return {@code true} if the grade was within assignment bounds, otherwise
     *         {@code false}
     */
    public boolean grade(Attempt attempt, int grade) {
        Assignment assignment;

        assignment = assignmentRepository.findByAttemptId(attempt.getID());

        if (grade > assignment.getMaxGrade()) {
            return false;
        }

        attempt.setGrade(grade);
        update(attempt);
        return true;
    }

    /**
     * A helper method that replaces a "stale" attempt in a submission with an
     * updated one
     * 
     * @param attempt The attempt to update
     */
    private void update(Attempt attempt) {
        Submission parent;
        int index;
        parent = submissionRepository.findByAttemptId(attempt.getID());

        index = parent.getAttempts().indexOf(attempt);
        parent.getAttempts().set(index, attempt);

        submissionRepository.save(parent);
    }

    /**
     * Retrieve an attempt by its unique identifier
     * 
     * @param attemptId The unique ID of the attempt
     * @return The corresponding attempt if it exists
     */
    public Attempt getById(ObjectId attemptId) {
        return submissionRepository.findByAttemptId(attemptId).getAttempts().stream()
                .filter(a -> a.getID().equals(attemptId)).findAny().orElseThrow();
    }

    /**
     * Get all the attempts for a submission
     * 
     * @param submissionId The unique ID of the submission
     * @return The attempts for the submission if the submission exists
     */
    public List<Attempt> getAttemptsFor(ObjectId submissionId) {
        return submissionRepository.findById(submissionId).orElseThrow().getAttempts();
    }
}

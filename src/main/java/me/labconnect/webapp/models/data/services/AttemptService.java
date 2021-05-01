package me.labconnect.webapp.models.data.services;

import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import me.labconnect.webapp.models.data.Assignment;
import me.labconnect.webapp.models.data.Attempt;
import me.labconnect.webapp.models.data.Submission;
import me.labconnect.webapp.models.users.Student;
import me.labconnect.webapp.models.users.User;
import me.labconnect.webapp.repository.AssignmentRepository;
import me.labconnect.webapp.repository.SubmissionRepository;
import me.labconnect.webapp.repository.UserRepository;

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

    public Resource getCodeArchive(Attempt attempt) throws IOException {
        Submission parent;
        User submitter;
        Assignment assignment;

        parent = submissionRepository.findByAttempt(attempt);

        assignment = assignmentRepository.findBySubmissionId(parent.getId());

        submitter = userRepository.findAll().stream().filter(u -> u.getAuthorities().contains("FIXME"))
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
}

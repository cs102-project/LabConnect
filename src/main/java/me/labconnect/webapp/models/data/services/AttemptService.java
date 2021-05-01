package me.labconnect.webapp.models.data.services;

import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.nio.file.Files;
import java.nio.file.Path;
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
import me.labconnect.webapp.repository.AssignmentRepository;
import me.labconnect.webapp.repository.SubmissionRepository;

@Service
public class AttemptService {

    private final String ATTEMPT_ROOT = "/var/labconnect/submissions";

    @Autowired
    private AssignmentRepository assignmentRepository;
    @Autowired
    private SubmissionRepository submissionRepository;
    @Autowired
    private AssignmentService assignmentService;

    /**
     * A helper method that extracts the attempt archive
     * 
     * @param attemptArchive The attempt as a ZIP file
     * @return The directory the archive is extracted to
     * @throws IOException If extracting the attempt fails
     */
    public Path extractAttempt(Path attemptArchive) throws IOException {
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

    public Resource getAttemptArchive(Attempt attempt) throws IOException {
        
        Assignment assignment = assignmentRepository.findByAttemptId(attempt.getId());
        Submission submission = submissionRepository.findByAttemptId(attempt.getId());
        Path assignmentDir = assignmentService.getInstructionsPath(assignment).getParent();
        
        return new UrlResource(
            assignmentDir
            .resolve(submission.getId().toString())
            .resolve(attempt.getId().toString())
            .resolve(attempt.getAttemptFilename()).toUri()
        );
        
    }

    public Attempt giveFeedback(Attempt attempt, String feedback) {
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

        assignment = assignmentRepository.findByAttemptId(attempt.getId());

        if (grade > assignment.getMaxGrade()) {
            return false;
        }

        attempt.setGrade(grade);
        update(attempt);
        return true;
    }

    private void update(Attempt attempt) {
        Submission parent;
        int index;
        parent = submissionRepository.findByAttemptId(attempt.getId());

        index = parent.getAttempts().indexOf(attempt);
        parent.getAttempts().set(index, attempt);

        submissionRepository.save(parent);
    }

    public Attempt getById(ObjectId attemptId) {
        return submissionRepository.findByAttemptId(attemptId).getAttempts().stream()
                .filter(a -> a.getId().equals(attemptId)).findAny().orElseThrow();
    }

    public List<Attempt> getAttemptsFor(ObjectId submissionId) {
        return submissionRepository.findById(submissionId).orElseThrow().getAttempts();
    }
}

package me.labconnect.webapp.models.data.services;

import me.labconnect.webapp.models.data.Assignment;
import me.labconnect.webapp.models.data.Attempt;
import me.labconnect.webapp.models.data.Feedback;
import me.labconnect.webapp.models.data.Submission;
import me.labconnect.webapp.models.testing.TestResult;
import me.labconnect.webapp.models.testing.Tester;
import me.labconnect.webapp.repository.AssignmentRepository;
import me.labconnect.webapp.repository.SubmissionRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * A service that provides operations for creation, modification and retrieval of Attempts
 *
 * @author Berkan Şahin
 * @author Vedat Eren Arican
 * @version 01.05.2021
 */
@Service
public class AttemptService {

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
        extractionDir = Files.createTempDirectory("");

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
     * Run the unit and style tests for this attempt and return the attempt with the results added
     *
     * @param attempt The attempt to test
     * @return The attempt with the test results added
     * @throws IOException If extracting the attempt archive fails
     */
    public Attempt runTests(Attempt attempt) throws IOException {
        List<Tester> tests = assignmentRepository.findBySubmissionId(attempt.getParentId()).getTests();
        Path extractedAttempt = extractAttempt(getAttemptArchive(attempt).getFile().toPath());

        List<TestResult> results = new ArrayList<>();

        for (Tester test : tests) {
            results.add(test.runTest(extractedAttempt));
        }

        attempt.setTestResults(results);
        updateSubmissionOf(attempt);

        return attempt;
    }

    /**
     * Return the contents of each java file in a given attempt mapped to the file name
     *
     * @param attempt The attempt to retrieve contents from
     * @return A mapping of file names to source code
     * @throws IOException If extracting the attempt archive fails
     */
    public Map<String, List<String>> getAttemptContents(Attempt attempt) throws IOException {
        Path extractedAttempt = extractAttempt(getAttemptArchive(attempt).getFile().toPath());
        Map<String, List<String>> sourceCodeMap = new HashMap<>();

        Files.walk(extractedAttempt).filter(f -> f.toString().endsWith(".java")).forEach(javaFile -> {
            List<String> fileContents = new ArrayList<>();
            String fileName = extractedAttempt.relativize(javaFile).toString();
            try (Scanner scan = new Scanner(javaFile.toFile())) {
                while (scan.hasNextLine()) {
                    fileContents.add(scan.nextLine());
                }
                sourceCodeMap.put(fileName, fileContents);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        return sourceCodeMap;
    }

    /**
     * Get the source code archive for this attempt and return it as a serveable Resource
     *
     * @param attempt The attempt
     * @return The ZIP archive of the attempt as a Resource ready for download
     * @throws IOException If archiving the attempt fails
     */
    public Resource getAttemptArchive(Attempt attempt) throws IOException {

        Assignment assignment = assignmentRepository.findBySubmissionId(attempt.getParentId());
        Submission submission = submissionRepository.findById(attempt.getParentId()).orElseThrow();
        Path assignmentDir = assignmentService.getInstructionsPath(assignment).getParent();

        return new UrlResource(
                assignmentDir
                        .resolve(submission.getId().toString())
                        .resolve(String.valueOf(attempt.getId()))
                        .resolve(attempt.getAttemptFilename()).toUri()
        );

    }

    /**
     * Give an attempt feedback and update its database entry accordingly
     *
     * @param attempt  The attempt to give feedback to
     * @param feedback The feedback to give
     * @return The attempt with the feedback added
     */
    public Attempt giveFeedback(Attempt attempt, Feedback feedback) {
        attempt.giveFeedback(feedback);
        updateSubmissionOf(attempt);
        return attempt;
    }

    /**
     * Set the note for the attempt
     *
     * @param attempt The attempt to add a note to
     * @param note The note to add
     */
    public void setNoteOfAttempt(Attempt attempt, String note) {
        attempt.setNote(note);
        updateSubmissionOf(attempt);
    }

    /**
     * A helper method that replaces a "stale" attempt in a submission with an updated one
     *
     * @param attempt The attempt to update
     */
    private void updateSubmissionOf(Attempt attempt) {
        Submission parent;
        int index;
        parent = submissionRepository.findById(attempt.getParentId()).orElseThrow();

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
    public Attempt getById(ObjectId submissionId, int attemptId) {
        return submissionRepository.findById(submissionId).orElseThrow().getAttempts().stream()
                .filter(a -> a.getId() == attemptId).findAny().orElseThrow();
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

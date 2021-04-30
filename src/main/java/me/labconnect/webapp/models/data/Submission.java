package me.labconnect.webapp.models.data;

import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;

import me.labconnect.webapp.models.users.Student;

/**
 * An aggregation of attempts for a certain assignment
 * 
 * @author Berkan Şahin
 * @version 25.04.2021
 */
public class Submission {

    // Variables
    @Id
    private String objectID;
    private long submitterID;
    private int submitterSection;
    private String submitterName;
    @DBRef
    private Assignment assignment;
    @DBRef
    private List<Attempt> attempts;

    // Constructors

    @PersistenceConstructor
    public Submission(String objectID, long submitterID, int submitterSection, String submitterName,
            Assignment assignment, List<Attempt> attempts) {
        this.submitterID = submitterID;
        this.assignment = assignment;
        this.attempts = attempts;
        this.objectID = objectID;
        this.submitterName = submitterName;
        this.submitterSection = submitterSection;
    }

    /**
     * Create a new submission
     * 
     * @param submitter  The student the submission belongs to
     * @param assignment The assignment this submission is for
     */
    public Submission(Student submitter, Assignment assignment) {
        this.submitterID = submitter.getInstitutionId();
        this.submitterName = submitter.getName();
        this.submitterSection = submitter.getSection();

        this.assignment = assignment;
        attempts = new ArrayList<>();
    }

    // Methods

    /**
     * Returns the ID of the student this submission belongs to
     * 
     * @return the student this submission belongs to
     */
    public long getSubmitterID() {
        return submitterID;
    }

    /**
     * Returns the assignment this submission is for
     * 
     * @return the assignment this submission is for
     */
    public Assignment getAssignment() {
        return assignment;
    }

    /**
     * Add an attempt to this submission
     * 
     * @param attempt The attempt to add
     */
    public Attempt addAttempt(Attempt attempt) {
        attempts.add(attempt);
        return attempt;
    }

    /**
     * Returns the list of attempts
     * 
     * @return the list of attempts
     */
    public List<Attempt> getAttempts() {
        return attempts;
    }

    /**
     * Returns the grade for the final attempt
     * 
     * @return the grade for the final attempt
     */
    public int getFinalGrade() {
        if (attempts.size() > 0) {
            return attempts.get(attempts.size() - 1).getGrade();
        } else {
            return 0;
        }
    }

    /**
     * Get the final attempt for this submission
     * 
     * @return the final attempt for this submission if it exists
     */
    public Attempt getFinalAttempt() {
        if (attempts.size() > 0) {
            return attempts.get(attempts.size() - 1);
        } else {
            return null;
        }
    }

    /**
     * Returns the amount of attempts for this submission
     * 
     * @return the amount of attempts for this submission
     */
    public int getAttemptCount() {
        return attempts.size();
    }

    /**
     * Archive the source code submitted for a given attempt as a ZIP file
     * 
     * @param attempt The attempt to archive
     * @return The path corresponding to the newly created archive
     * @throws IOException If archiving the source code fails
     */
    public Path getCodeArchiveFor(Attempt attempt) throws IOException {
        final String fileNameTemplate = "%d_%s_%s.zip";
        ProcessBuilder archiverBuilder;
        Process archiver;
        List<String> archiverArgs;
        Path attemptPath;
        Path archiveFile;
        String archiveFileName;

        attemptPath = attempt.getAttemptDir();

        archiverArgs = new ArrayList<>();
        archiverArgs.add("zip");
        archiverArgs.add("-qr"); // Suppress output and recurse into directories

        archiveFileName = String.format(fileNameTemplate, submitterSection, assignment.getTitle(), submitterName);
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
        return Files.move(archiveFile, Files.createTempDirectory("").resolve(archiveFileName),
                StandardCopyOption.REPLACE_EXISTING);
    }
}

package me.labconnect.webapp.models.data;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import me.labconnect.webapp.models.testing.Tester;

/**
 * A generic assignment model class
 * 
 * @author Berkan Şahin
 * @author Borga Haktan Bilen
 * @version 03.05.2021
 */
@Document(collection = "assignments")
public class Assignment {

    // Variables
    @Id
    //@JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    private String title;
    private String shortDescription;
    private String homeworkType;
    private List<Course> courses;
    private Date dueDate;

    private int maxGrade;
    private int maxAttempts;
    private String instructionFilename;
    private List<Tester> tests;
    //@JsonSerialize(using = ToStringSerializer.class)
    private List<ObjectId> submissions;

    // Constructor

    /**
     * Default constructor for the {@code Assignment} class
     * 
     * @param title               The title of the assignment
     * @param shortDescription    Description of the assignment
     * @param courses             Assigned courses for the assignment
     * @param homeworkType        Type of the assignment
     * @param dueDate             Due date of the assignment
     * @param maxGrade            Maximum grade possible for the assignment
     * @param maxAttempts         Maximum attempt number for the assignment
     * @param instructionFilename Name of the instruction file for the assignment
     * @param tests               List of test which is going to be applied to the
     *                            assignment
     * @param submissions         List of submission to the assignment
     */
    public Assignment(String title, String shortDescription, List<Course> courses, String homeworkType, Date dueDate,
            int maxGrade, int maxAttempts, String instructionFilename, List<Tester> tests, List<ObjectId> submissions) {
        this.title = title;
        this.shortDescription = shortDescription;
        this.homeworkType = homeworkType;
        this.courses = courses;
        this.maxGrade = maxGrade;
        this.maxAttempts = maxAttempts;
        this.instructionFilename = instructionFilename;
        this.tests = tests;
        this.submissions = submissions;
        this.dueDate = dueDate;
    }

    // Methods

    /**
     * Gets the unique id of the assignment
     * 
     * @return Unique object id of the assignment
     */
    public ObjectId getId() {
        return id;
    }

    /**
     * Gets the title of the assignment
     * 
     * @return Title of the assignment
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the short description of the assignment
     * 
     * @return Short description of the assignment
     */
    public String getShortDescription() {
        return shortDescription;
    }

    /**
     * Gets the type of the homework
     * 
     * @return Type of the homework
     */
    public String getHomeworkType() {
        return homeworkType;
    }

    /**
     * Gets the list of assigned courses
     * 
     * @return List of assigned courses
     */
    public List<Course> getCourses() {
        return courses;
    }

    /**
     * Gets the due date of the assignment
     * 
     * @return Due date of the assignment
     */
    public Date getDueDate() {
        return dueDate;
    }

    /**
     * Gets the maximum possible grade
     * 
     * @return Maximum possible grade
     */
    public int getMaxGrade() {
        return maxGrade;
    }

    /**
     * Gets the maximum attempt number
     * 
     * @return Number of maximum attempts
     */
    public int getMaxAttempts() {
        return maxAttempts;
    }

    /**
     * Gets the name of the instructions file
     * 
     * @return Name of the instructions file
     */
    public String getInstructionFilename() {
        return instructionFilename;
    }

    /**
     * Gets the list of tests which is going to be applied to the assignment
     * 
     * @return List of tests which is going to be applied to the assignment
     */
    public List<Tester> getTests() {
        return tests;
    }

    /**
     * Gets the list of unique ids of submissions for this assignment
     * 
     * @return List of unique ids of submissions for this assignment
     */
    //@JsonSerialize(using = ToStringSerializer.class)
    public List<ObjectId> getSubmissions() {
        return submissions;
    }

    /**
     * Add a submission to the list <b>if it doesn't exist</b>
     * 
     * @param submission The submission to add
     */
    public void addSubmission(Submission submission) {
        addSubmission(submission.getId());
    }

    /**
     * Adds a submission with id to the list <b>if it doesn't exist</b>
     * 
     * @param submissionId The id of the submission
     */
    private void addSubmission(ObjectId submissionId) {
        if (!submissions.contains(submissionId))
            submissions.add(submissionId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Assignment that = (Assignment) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

package me.labconnect.webapp.controller.httpmodels;

import me.labconnect.webapp.models.data.Course;
import me.labconnect.webapp.models.testing.Tests;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

/**
 * HTTP model class for {@code NewAssignment} objects
 *
 * @author Vedat Eren Arican
 * @author Berk Çakar
 * @version 03.05.2021
 */
public class NewAssignment {

    private String assignmentTitle;
    private String shortDescription;
    private String homeworkType;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dueDate;
    private List<Course> courses;
    private int maxGrade;
    private int maxAttempts;
    private List<Tests> styleTests;
    private String unitTestName;
    private Long unitTestTimeLimit;
    private ArrayList<String> forbiddenStatements;
    private MultipartFile instructionsFile;
    private MultipartFile exampleImplementation;
    private MultipartFile testerClass;
    
    /**
     * Default constructor for {@code NewAssignment} takes the required information for an assignment
     *
     * @param assignmentTitle     The title of assignment
     * @param shortDescription    A short description for the assignment
     * @param homeworkType        Type of the homework
     * @param dueDate             Due date of the assignment
     * @param courseName          The course name which the assignment belongs to
     * @param sections            Section number which the assignment belongs to
     * @param maxGrade            Maximum possible grade for the assignment
     * @param maxAttempts         Maximum possible attempt count for the assignment
     * @param styleTests          Style tests to be applied
     * @param unitTestName        Name of the unit test to be applied
     * @param unitTestTimeLimit   Time limit for the unit test
     * @param forbiddenStatements Statements not allowed for use in the assignment
     */
    public NewAssignment(String assignmentTitle, String shortDescription, String homeworkType,
                         Date dueDate, List<Course> courses, int maxGrade, int maxAttempts, List<Tests> styleTests, String unitTestName,
                         Long unitTestTimeLimit, ArrayList<String> forbiddenStatements, 
                         MultipartFile instructionsFile, MultipartFile exampleImplementation, MultipartFile testerClass) {
        this.assignmentTitle = assignmentTitle;
        this.shortDescription = shortDescription;
        this.homeworkType = homeworkType;
        this.dueDate = dueDate;
        this.courses = courses;
        this.maxGrade = maxGrade;
        this.maxAttempts = maxAttempts;
        this.styleTests = styleTests;
        this.unitTestName = unitTestName;
        this.unitTestTimeLimit = unitTestTimeLimit;
        this.forbiddenStatements = forbiddenStatements;
        this.instructionsFile = instructionsFile;
        this.exampleImplementation = exampleImplementation;
        this.testerClass = testerClass;
    }

    /**
     * Gets the title of the {@code NewAssignment} object
     *
     * @return Title of the {@code NewAssignment} object
     */
    public String getAssignmentTitle() {
        return assignmentTitle;
    }

    /**
     * Gets the course name which {@code NewAssignment} object belongs to
     *
     * @return Course name which {@code NewAssignment} object belongs to
     */
    public List<Course> getCourses() {
        return courses;
    }

    /**
     * Gets the due date of the {@code NewAssignment} object
     *
     * @return Due date of the {@code NewAssignment} object
     */
    public Date getDueDate() {
        return dueDate;
    }

    /**
     * Gets the maximum possible attempt count for the {@code NewAssignment} object
     *
     * @return Maximum possible attempt count for the {@code NewAssignment} object
     */
    public int getMaxAttempts() {
        return maxAttempts;
    }

    /**
     * Gets the maximum possible grade for the assignment for the {@code NewAssignment} object
     *
     * @return Maximum possible grade for the assignment for the {@code NewAssignment} object
     */
    public int getMaxGrade() {
        return maxGrade;
    }

    /**
     * Gets the short description of the {@code NewAssignment} object
     *
     * @return Short description of the {@code NewAssignment} object
     */
    public String getShortDescription() {
        return shortDescription;
    }

    /**
     * Gets the homework type of the {@code NewAssignment} object
     *
     * @return Homework type of the {@code NewAssignment} object
     */
    public String getHomeworkType() {
        return homeworkType;
    }

    /**
     * Gets the style tests of the {@code NewAssignment} object
     *
     * @return Style tests of the {@code NewAssignment} object
     */
    public List<Tests> getStyleTests() {
        return styleTests;
    }

    /**
     * Gets the name of the unit test to be applied
     *
     * @return Name of the unit test to be applied
     */
    public String getUnitTestName() {
        return unitTestName;
    }

    /**
     * Gets the time limit for the unit test
     *
     * @return Time limit for the unit test
     */
    public Long getUnitTestTimeLimit() {
        return unitTestTimeLimit;
    }

    /**
     * Gets the statements not allowed for use in the {@code NewAssignment} object
     *
     * @return Statements not allowed for use in the {@code NewAssignment} object
     */
    public ArrayList<String> getForbiddenStatements() {
        return forbiddenStatements;
    }
    
    public MultipartFile getExampleImplementation() {
        return exampleImplementation;
    }
    
    public MultipartFile getInstructionsFile() {
        return instructionsFile;
    }
    
    public MultipartFile getTesterClass() {
        return testerClass;
    }
    
}

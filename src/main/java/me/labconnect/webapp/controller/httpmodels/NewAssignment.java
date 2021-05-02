package me.labconnect.webapp.controller.httpmodels;

import me.labconnect.webapp.models.testing.Tests;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NewAssignment {

    private String assignmentTitle;
    private String shortDescription;
    private String homeworkType;
    private Date dueDate;
    private String courseName;
    private int[] sections;
    private int maxGrade;
    private int maxAttempts;
    private List<Tests> styleTests;
    private String unitTestName;
    private Long unitTestTimeLimit;
    private ArrayList<String> forbiddenStatements;

    public NewAssignment(String assignmentTitle, String shortDescription, String homeworkType, Date dueDate, String courseName,
                         int[] sections, int maxGrade, int maxAttempts, List<Tests> styleTests, String unitTestName, Long unitTestTimeLimit, ArrayList<String> forbiddenStatements) {
        this.assignmentTitle = assignmentTitle;
        this.shortDescription = shortDescription;
        this.homeworkType = homeworkType;
        this.dueDate = dueDate;
        this.courseName = courseName;
        this.sections = sections;
        this.maxGrade = maxGrade;
        this.maxAttempts = maxAttempts;
        this.styleTests = styleTests;
        this.unitTestName = unitTestName;
        this.unitTestTimeLimit = unitTestTimeLimit;
        this.forbiddenStatements = forbiddenStatements;
    }

    public String getAssignmentTitle() {
        return assignmentTitle;
    }

    public String getCourseName() {
        return courseName;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

    public int getMaxGrade() {
        return maxGrade;
    }

    public int[] getSections() {
        return sections;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getHomeworkType() {
        return homeworkType;
    }

    public List<Tests> getStyleTests() {
        return styleTests;
    }

    public String getUnitTestName() {
        return unitTestName;
    }

    public Long getUnitTestTimeLimit() {
        return unitTestTimeLimit;
    }

    public ArrayList<String> getForbiddenStatements() {
        return forbiddenStatements;
    }
}

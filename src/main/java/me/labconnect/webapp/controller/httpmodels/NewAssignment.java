package me.labconnect.webapp.controller.httpmodels;

import java.util.Date;

public class NewAssignment {
    
    private String assignmentTitle;
    private String shortDescription;
    private String homeworkType;
    private Date dueDate;
    private String courseName;
    private int[] sections;
    private int maxGrade;
    private int maxAttempts;
    
    public NewAssignment(String assignmentTitle, String shortDescription, String homeworkType, Date dueDate, String courseName, int[] sections, int maxGrade, int maxAttempts) {
        this.assignmentTitle = assignmentTitle;
        this.shortDescription = shortDescription;
        this.homeworkType = homeworkType;
        this.dueDate = dueDate;
        this.courseName = courseName;
        this.sections = sections;
        this.maxGrade = maxGrade;
        this.maxAttempts = maxAttempts;
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
    
}

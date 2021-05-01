package me.labconnect.webapp.models.data;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import me.labconnect.webapp.models.testing.Tester;
import me.labconnect.webapp.models.testing.Tests;

/**
 * A generic assignment model class
 * 
 * @author Berkan Şahin
 * @author Borga Haktan Bilen
 * @version 30.04.2021
 */
@Document(collection = "assignments")
public class Assignment {

    // Variables
    @Id
    private ObjectId id;
    private String title;
    private String homeworkType;
    private List<Course> courses;
    private Date dueDate;
    
    private int maxGrade;
    private int maxAttempts;
    private String instructionFileName;
    private List<Tester> tests;

    // Constructor
    
    public Assignment(String title, List<Course> courses, String homeworkType, Date dueDate, int maxGrade, int maxAttempts, String instructionFileName, List<Tester> tests) {

        // TODO make sure that instructions are stored permanently
        this.title = title;
        this.homeworkType = homeworkType;
        this.courses = courses;
        this.maxGrade = maxGrade;
        this.maxAttempts = maxAttempts;
        this.instructionFileName = instructionFileName;
        this.tests = tests;
        
    }

    // Methods
    
    public ObjectId getId() {
        return id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getHomeworkType() {
        return homeworkType;
    }
    
    public List<Course> getCourses() {
        return courses;
    }
    
    public Date getDueDate() {
        return dueDate;
    }
    
    public int getMaxGrade() {
        return maxGrade;
    }
    
    public int getMaxAttempts() {
        return maxAttempts;
    }
    
    public String getInstructionFileName() {
        return instructionFileName;
    }
    
    public List<Tester> getTests() {
        return tests;
    }
    
}

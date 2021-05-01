package me.labconnect.webapp.models.data;

public class Course {
    
    private String course;
    private String section;
    
    public Course(String course, String section) {
        this.course = course;
        this.section = section;
    }
    
    public String getCourse() {
        return course;
    }
    
    public String getSection() {
        return section;
    }
    
}

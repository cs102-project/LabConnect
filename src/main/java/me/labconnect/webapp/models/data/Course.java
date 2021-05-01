package me.labconnect.webapp.models.data;

public class Course {
    
    private String course;
    private int section;
    
    public Course(String course, int section) {
        this.course = course;
        this.section = section;
    }
    
    public String getCourse() {
        return course;
    }
    
    public int getSection() {
        return section;
    }
    
}

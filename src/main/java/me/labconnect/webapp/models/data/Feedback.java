package me.labconnect.webapp.models.data;

import java.util.Date;

public class Feedback {
    
    private int grade;
    private String content;
    private Date date;
    
    public Feedback(int grade, String content, Date date) {
        this.grade = grade;
        this.content = content;
        this.date = date;
    }
    
    public String getContent() {
        return content;
    }
    
    public Date getDate() {
        return date;
    }
    
    public int getGrade() {
        return grade;
    }
    
}

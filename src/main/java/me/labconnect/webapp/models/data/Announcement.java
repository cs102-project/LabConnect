package me.labconnect.webapp.models.data;

import java.util.Date;

public class Announcement {
    
    private String content;
    private Date date;
    
    public Announcement(String content, Date date) {
        this.content = content;
        this.date = date;
    }
    
    public String getContent() {
        return content;
    }
    
    public Date getDate() {
        return date;
    }
    
}

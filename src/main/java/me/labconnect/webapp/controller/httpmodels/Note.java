package me.labconnect.webapp.controller.httpmodels;

public class Note {
    
    String content;
    
    public Note(String content) {
        this.content = content;
    }
    
    public String getContent() {
        return content;
    }
    
}

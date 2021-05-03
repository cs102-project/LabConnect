package me.labconnect.webapp.controller.httpmodels;

public class NewNote {
    
    private String content;
    
    public NewNote(String content) {
        this.content = content;
    }
    
    public String getContent() {
        return content;
    }
    
}

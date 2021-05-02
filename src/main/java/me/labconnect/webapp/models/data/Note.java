package me.labconnect.webapp.models.data;

public class Note {
    
    private String content;
    
    public Note(String content) {
        this.content = content;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent( String content ) {
        this.content = content;
    }
}

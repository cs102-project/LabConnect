package me.labconnect.webapp.controller.httpmodels;

/**
 * HTTP model class for {@code Note} objects
 *
 * @author Vedat Eren Arican
 * @author Berk Çakar
 * @version 03.05.2021
 */
public class Note {

    String content;

    /**
     * Default constructor for the {@code Note} class takes content of the note as parameter
     *
     * @param content String content of the {@code Note} object
     */
    public Note(String content) {
        this.content = content;
    }

    /**
     * Gets the content of the {@code Note} object
     *
     * @return String content of the {@code Note} object
     */
    public String getContent() {
        return content;
    }

}

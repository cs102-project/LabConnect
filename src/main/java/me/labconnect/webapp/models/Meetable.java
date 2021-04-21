package me.labconnect.webapp.models;

/**
 * Meetable interface.
 * <p>
 * User types who can be meetable in an
 * online setting.
 * @author Borga Haktan Bilen
 * @version 22/04/2021
 */
public interface Meetable {
    /**
     * Gets the meeting link of Tutor or Teaching Assitant.
     * 
     * @return The meeting link in String.
     */
    String getMeetingLink();
}

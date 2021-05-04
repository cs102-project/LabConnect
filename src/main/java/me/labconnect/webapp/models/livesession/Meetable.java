package me.labconnect.webapp.models.livesession;

/**
 * A user that has a personal meeting room accessible by visiting an external website (e.g. Zoom)
 *
 * @author Borga Haktan Bilen
 * @version 22.04.2021
 */
public interface Meetable {

    /**
     * Gets the meeting link for this user.
     *
     * @return The meeting link as a String.
     */
    String getMeetingLink();
}

package me.labconnect.webapp.models.users.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.labconnect.webapp.models.data.Announcement;
import me.labconnect.webapp.models.users.Instructor;
import me.labconnect.webapp.repository.InstructorRepository;

/**
 * Serves different instructor functions for controllers
 *
 * @author Vedat Eren Arican
 * @version 03.05.2021
 */
@Service
public class InstructorService {

    @Autowired
    private InstructorRepository instructorRepository;

    /**
     * Adds an announcement from the given instructor
     *
     * @param instructor The instructor who gives the announcemet
     * @param announcement The announcement which is going to be declared
     */
    public void addAnnouncementFrom(Instructor instructor, Announcement announcement) {

        instructor.getAnnouncements().add(announcement);
        instructorRepository.save(instructor);

    }

}

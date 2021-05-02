package me.labconnect.webapp.models.users.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.labconnect.webapp.models.data.Announcement;
import me.labconnect.webapp.models.users.Instructor;
import me.labconnect.webapp.repository.InstructorRepository;

@Service
public class InstructorService {
    
    @Autowired
    private InstructorRepository instructorRepository;
    
    public void addAnnouncementFrom(Instructor instructor, Announcement announcement) {
        
        instructor.getAnnouncements().add(announcement);
        instructorRepository.save(instructor);
        
    }
    
}

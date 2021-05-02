package me.labconnect.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import me.labconnect.webapp.models.data.Announcement;
import me.labconnect.webapp.models.users.Instructor;
import me.labconnect.webapp.models.users.LCUserDetails;
import me.labconnect.webapp.models.users.User;
import me.labconnect.webapp.models.users.services.InstructorService;
import me.labconnect.webapp.models.users.services.UserService;
import me.labconnect.webapp.models.users.services.UserCreatorService.LCUserRoleTypes;
import me.labconnect.webapp.repository.UserRepository;

public class InstructorController {
    
    // /api/instructor/announcements/
    // POST -> add announcement
    
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private InstructorService instructorService;
    
    @PostMapping("/api/instructor/announcements")
    public void addAnnouncement(Authentication authentication, @RequestBody Announcement announcement) {
        
        if (authentication == null || !authentication.isAuthenticated()) {
            return;
        }

        LCUserDetails userDetails = (LCUserDetails) authentication.getPrincipal();
        User user = userRepository.findById(userDetails.getId()).orElseThrow();
        
        if (user.getRoleType() != LCUserRoleTypes.INSTRUCTOR) {
            return;
        }
        
        Instructor instructor = userService.getInstructorDocumentOf(user);
        
        instructorService.addAnnouncementFrom(instructor, announcement);
        
    }
    
}

package me.labconnect.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import me.labconnect.webapp.models.users.LCUserDetails;
import me.labconnect.webapp.models.users.User;
import me.labconnect.webapp.repository.UserRepository;

@RestController
public class SelfController {
    
    // Dashboard api (/api/self/)
    // announcements page api (/api/self/announcements)
    // my notes page api (/api/self/notes)
    
    @Autowired
    private UserRepository userRepository;
    
    // The below example demonstrates the usage of sessions
    
    @GetMapping("/api/self")
    public User selfData(Authentication authentication) {
        
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        
        LCUserDetails userDetails = (LCUserDetails) authentication.getPrincipal();
        User user = userRepository.findById(userDetails.getId()).get();
        
        return user;
        
    }
    
}

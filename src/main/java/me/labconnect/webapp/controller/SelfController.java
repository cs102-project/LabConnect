package me.labconnect.webapp.controller;

import me.labconnect.webapp.models.data.Announcement;
import me.labconnect.webapp.models.data.Course;
import me.labconnect.webapp.models.users.Instructor;
import me.labconnect.webapp.models.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import me.labconnect.webapp.models.users.LCUserDetails;
import me.labconnect.webapp.models.users.User;
import me.labconnect.webapp.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SelfController {

    // Dashboard api (/api/self/)
    // announcements page api (/api/self/announcements)
    // my notes page api (/api/self/notes)

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    // The below example demonstrates the usage of sessions

    @GetMapping("/api/self")
    public User selfData(Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        LCUserDetails userDetails = (LCUserDetails) authentication.getPrincipal();
        User user = userRepository.findById(userDetails.getId()).orElseThrow();

        return user;

    }

    @GetMapping("/api/self/announcements")
    public List<Announcement> getAnnouncements(Authentication authentication) {
        List<Announcement> announcements = new ArrayList<>();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        LCUserDetails userDetails = (LCUserDetails) authentication.getPrincipal();
        User user = userRepository.findById(userDetails.getId()).orElseThrow();


        user.getCourses().stream()
                .map(c -> userService.getInstructorOfCourseSection(user.getInstitution(), c))
                .distinct()
                .forEach(instructor -> announcements.addAll(instructor.getAnnouncements()));

        return announcements;
    }

}

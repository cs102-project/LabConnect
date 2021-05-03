package me.labconnect.webapp.controller;

import me.labconnect.webapp.controller.httpmodels.Note;
import me.labconnect.webapp.models.data.Announcement;
import me.labconnect.webapp.models.users.services.UserService;
import me.labconnect.webapp.models.users.services.UserCreatorService.LCUserRoleTypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import me.labconnect.webapp.models.users.LCUserDetails;
import me.labconnect.webapp.models.users.User;
import me.labconnect.webapp.repository.UserRepository;

import java.util.List;

/**
 * @author Vedat Eren Arıcan
 * @author Berkan Şahin
 * @author Borga Haktan Bilen
 * @version 02.05.2021
 */
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

    @GetMapping("/api/self/notes")
    public List<?> getNotes(Authentication authentication) {
        LCUserDetails userDetails = (LCUserDetails) authentication.getPrincipal();
        User user = userRepository.findById(userDetails.getId()).orElseThrow();

        if ( ( user.getRoleType() != LCUserRoleTypes.STUDENT ) && ( authentication == null || !authentication.isAuthenticated() ) ) {
            return null;
        }

        return userService.getNotesForStudent(userService.getStudentDocumentOf(user), Note.class);
    }

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

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        LCUserDetails userDetails = (LCUserDetails) authentication.getPrincipal();
        User user = userRepository.findById(userDetails.getId()).orElseThrow();

        return userService.getAnnouncementsOfUser(user);

    }

}

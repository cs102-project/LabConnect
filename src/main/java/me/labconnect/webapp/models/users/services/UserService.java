package me.labconnect.webapp.models.users.services;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.labconnect.webapp.controller.httpmodels.AssignmentNotes;
import me.labconnect.webapp.controller.httpmodels.AssignmentNotes.AttemptNote;
import me.labconnect.webapp.models.data.Announcement;
import me.labconnect.webapp.models.data.Attempt;
import me.labconnect.webapp.models.data.Course;
import me.labconnect.webapp.models.data.services.SubmissionService;
import me.labconnect.webapp.models.users.Instructor;
import me.labconnect.webapp.models.users.Student;
import me.labconnect.webapp.models.users.TeachingAssistant;
import me.labconnect.webapp.models.users.Tutor;
import me.labconnect.webapp.models.users.User;
import me.labconnect.webapp.models.users.services.UserCreatorService.LCUserRoleTypes;
import me.labconnect.webapp.repository.AssignmentRepository;
import me.labconnect.webapp.repository.InstructorRepository;
import me.labconnect.webapp.repository.StudentRepository;
import me.labconnect.webapp.repository.TARepository;
import me.labconnect.webapp.repository.TutorRepository;
import me.labconnect.webapp.repository.UserRepository;

/**
 * Serves different user functions for controllers
 * 
 * @author Vedat Eren Arıcan
 * @author Berkan Şahin
 * @author Borga Haktan Bilen
 * @version 02.05.2021
 */
@Service
public class UserService {

    @Autowired
    private SubmissionService submissionService;
    @Autowired
    private TARepository taRepository;
    @Autowired
    private TutorRepository tutorRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private InstructorRepository instructorRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AssignmentRepository assignmentRepository;

    public TeachingAssistant getTADocumentOf(User user) {
        if (user.getRoleType() != LCUserRoleTypes.TEACHING_ASSISTANT) {
            throw new NoSuchElementException("Mismatch in requested user role document.");
        }
        return taRepository.findById(user.getRoleDocumentId()).orElseThrow();
    }

    public Tutor getTutorDocumentOf(User user) {
        if (user.getRoleType() != LCUserRoleTypes.TUTOR) {
            throw new NoSuchElementException("Mismatch in requested user role document.");
        }
        return tutorRepository.findById(user.getRoleDocumentId()).orElseThrow();
    }

    public Student getStudentDocumentOf(User user) {
        if (user.getRoleType() != LCUserRoleTypes.STUDENT) {
            throw new NoSuchElementException("Mismatch in requested user role document.");
        }
        return studentRepository.findById(user.getRoleDocumentId()).orElseThrow();
    }

    public Instructor getInstructorDocumentOf(User user) {
        if (user.getRoleType() != LCUserRoleTypes.INSTRUCTOR) {
            throw new NoSuchElementException("Mismatch in requested user role document.");
        }
        return instructorRepository.findById(user.getRoleDocumentId()).orElseThrow();
    }

    public List<Student> getStudentsOfCourseSection(String institution, Course course) {

        return userRepository.findByCourseSection(institution, course.getCourse(), course.getSection()).stream()
                .filter(user -> user.getRoleType() == LCUserRoleTypes.STUDENT).map(this::getStudentDocumentOf)
                .collect(Collectors.toList());

    }

    public Instructor getInstructorOfCourseSection(String institution, Course course) {

        return userRepository.findByCourseSection(institution, course.getCourse(), course.getSection()).stream()
                .filter(user -> user.getRoleType() == LCUserRoleTypes.INSTRUCTOR).map(this::getInstructorDocumentOf)
                .findAny().orElseThrow();

    }

    public List<Announcement> getAnnouncementsOfUser(User user) {

        List<Announcement> announcements = new ArrayList<>();

        user.getCourses().stream().map(course -> getInstructorOfCourseSection(user.getInstitution(), course)).distinct()
                .forEach(instructor -> announcements.addAll(instructor.getAnnouncements()));

        return announcements;

    }

    public List<AssignmentNotes> getNotesForStudent(Student student) {
        
        List<AssignmentNotes> result = new ArrayList<>();
        List<ObjectId> assignmentIds = student.getAssignments();
        
        for (ObjectId assignmentId : assignmentIds) {
            
            List<AttemptNote> attemptNotes = new ArrayList<>();
            
            for (Attempt attempt : submissionService.getAssignmentSubmissionBySubmitter(assignmentId, student.getId()).orElseThrow().getAttempts()) {
                attemptNotes.add(new AttemptNote(attempt.getNote(), attempt.getId()));
            }
            
            result.add(new AssignmentNotes(assignmentRepository.findById(assignmentId).get().getTitle(), assignmentId, attemptNotes));
            
        }
        
        return result;
        
    }
    
}

package me.labconnect.webapp.models.users.services;

import me.labconnect.webapp.controller.httpmodels.AssignmentNotes;
import me.labconnect.webapp.controller.httpmodels.AssignmentNotes.AttemptNote;
import me.labconnect.webapp.models.data.Announcement;
import me.labconnect.webapp.models.data.Attempt;
import me.labconnect.webapp.models.data.Course;
import me.labconnect.webapp.models.data.services.SubmissionService;
import me.labconnect.webapp.models.users.*;
import me.labconnect.webapp.models.users.services.UserCreatorService.LCUserRoleTypes;
import me.labconnect.webapp.repository.*;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

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

    /**
     * Get the corresponding TeachingAssistant document for this user
     *
     * @param user The user to retrieve its document from
     * @return The TeachingAssistant document if the user has a Teaching Assistant role
     */
    public TeachingAssistant getTADocumentOf(User user) {
        if (user.getRoleType() != LCUserRoleTypes.TEACHING_ASSISTANT) {
            throw new NoSuchElementException("Mismatch in requested user role document.");
        }
        return taRepository.findById(user.getRoleDocumentId()).orElseThrow();
    }

    /**
     * Get the corresponding Tutor document for this user
     *
     * @param user The user to retrieve its document from
     * @return The Tutor document if the user has a tutor role
     */
    public Tutor getTutorDocumentOf(User user) {
        if (user.getRoleType() != LCUserRoleTypes.TUTOR) {
            throw new NoSuchElementException("Mismatch in requested user role document.");
        }
        return tutorRepository.findById(user.getRoleDocumentId()).orElseThrow();
    }

    /**
     * Get the corresponding Student document for this user
     *
     * @param user The user to retrieve its document from
     * @return The student document if the user has a student role
     */
    public Student getStudentDocumentOf(User user) {
        if (user.getRoleType() != LCUserRoleTypes.STUDENT) {
            throw new NoSuchElementException("Mismatch in requested user role document.");
        }
        return studentRepository.findById(user.getRoleDocumentId()).orElseThrow();
    }

    /**
     * Get the corresponding Instructor document for this user
     *
     * @param user The user to retrieve its document from
     * @return The Instructor document if the user has an instructor role
     */
    public Instructor getInstructorDocumentOf(User user) {
        if (user.getRoleType() != LCUserRoleTypes.INSTRUCTOR) {
            throw new NoSuchElementException("Mismatch in requested user role document.");
        }
        return instructorRepository.findById(user.getRoleDocumentId()).orElseThrow();
    }

    /**
     * Retrieves all the students from a particular institution taking a particular course
     *
     * @param institution The <b>case-sensitive</b> name of the institution
     * @param course      The course object denoting the course name and the section
     * @return The list of the students taking said course
     */
    public List<Student> getStudentsOfCourseSection(String institution, Course course) {

        return userRepository.findByCourseSection(institution, course.getCourse(), course.getSection())
                .stream()
                .filter(user -> user.getRoleType() == LCUserRoleTypes.STUDENT)
                .map(this::getStudentDocumentOf)
                .collect(Collectors.toList());

    }

    /**
     * Retrieve the instructor of the given course in the given institution
     *
     * @param institution The <b>case-sensitive</b> name of the institution
     * @param course      The course object denoting the course name and the section
     * @return The instructor teaching said course
     */
    public Instructor getInstructorOfCourseSection(String institution, Course course) {

        return userRepository.findByCourseSection(institution, course.getCourse(), course.getSection())
                .stream()
                .filter(user -> user.getRoleType() == LCUserRoleTypes.INSTRUCTOR)
                .map(this::getInstructorDocumentOf)
                .findAny().orElseThrow();

    }

    /**
     * Retrieve the announcements that are relevant to the specified user
     *
     * @param user The user to retrieve announcements for
     * @return The list of announcements for the courses this user attends/teaches
     */
    public List<Announcement> getAnnouncementsOfUser(User user) {

        List<Announcement> announcements = new ArrayList<>();

        user.getCourses().stream()
                .map(course -> getInstructorOfCourseSection(user.getInstitution(), course)).distinct()
                .forEach(instructor -> announcements.addAll(instructor.getAnnouncements()));

        return announcements;

    }

    /**
     * Get the list of self-notes authored by the given student
     *
     * @param student The student to retrieve notes of
     * @return The list of assignment notes authored by the given student
     */
    public List<AssignmentNotes> getNotesForStudent(Student student) {

        List<AssignmentNotes> result = new ArrayList<>();
        List<ObjectId> assignmentIds = student.getAssignments();

        for (ObjectId assignmentId : assignmentIds) {

            List<AttemptNote> attemptNotes = new ArrayList<>();

            for (Attempt attempt : submissionService
                    .getAssignmentSubmissionBySubmitter(assignmentId, student.getId()).orElseThrow()
                    .getAttempts()) {
                attemptNotes.add(new AttemptNote(attempt.getNote(), attempt.getId()));
            }

            result.add(new AssignmentNotes(assignmentRepository.findById(assignmentId).get().getTitle(),
                    assignmentId, attemptNotes));

        }

        return result;

    }

}

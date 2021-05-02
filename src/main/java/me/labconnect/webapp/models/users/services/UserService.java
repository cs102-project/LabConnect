package me.labconnect.webapp.models.users.services;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.labconnect.webapp.controller.httpmodels.Note;
import me.labconnect.webapp.models.data.Announcement;
import me.labconnect.webapp.models.data.Assignment;
import me.labconnect.webapp.models.data.Attempt;
import me.labconnect.webapp.models.data.Course;
import me.labconnect.webapp.models.data.Submission;
import me.labconnect.webapp.models.data.services.AssignmentService;
import me.labconnect.webapp.models.data.services.AttemptService;
import me.labconnect.webapp.models.data.services.SubmissionService;
import me.labconnect.webapp.models.users.Instructor;
import me.labconnect.webapp.models.users.Student;
import me.labconnect.webapp.models.users.TeachingAssistant;
import me.labconnect.webapp.models.users.Tutor;
import me.labconnect.webapp.models.users.User;
import me.labconnect.webapp.models.users.services.UserCreatorService.LCUserRoleTypes;
import me.labconnect.webapp.repository.InstructorRepository;
import me.labconnect.webapp.repository.StudentRepository;
import me.labconnect.webapp.repository.TARepository;
import me.labconnect.webapp.repository.TutorRepository;
import me.labconnect.webapp.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private AssignmentService assignmentService;
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
    private AttemptService attemptService;

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

    public List<Note> getNotesForUser(User user) {
        List<Note> notes = new ArrayList<>();
        List<Submission> submissions = new ArrayList();
        List<Attempt> attempts = new ArrayList();
        List<Assignment> assignemnts;

        assignemnts = user.getCourses().stream().flatMap(assignmentService::findByCourse).distinct()
                .collect(Collectors.toList());

        for (Assignment assignment : assignemnts) {
            ObjectId tempAssignmentId = assignment.getId();
            submissions.add(submissionService
                    .getAssignmentSubmissionBySubmitter(tempAssignmentId, getStudentDocumentOf(user).getId())
                    .orElseThrow());
        }

        for ( Submission submission : submissions ) {
            attempts.add(attemptService.getById(submission.getId()));
        }

        for ( Attempt attempt : attempts ) {
            notes.add( new Note( attempt.getNote() ) );
        }

        return notes;
    }
}

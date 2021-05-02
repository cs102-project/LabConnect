package me.labconnect.webapp.models.users.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.labconnect.webapp.models.data.Course;
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
    private TARepository taRepository;
    @Autowired
    private TutorRepository tutorRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private InstructorRepository instructorRepository;
    @Autowired
    private UserRepository userRepository;
    
    public TeachingAssistant getTADocumentOf(User user) throws Exception {
        if (user.getRoleType() != LCUserRoleTypes.TEACHING_ASSISTANT) {
            throw new Exception("Mismatch in requested user role document.");
        }
        return taRepository.findById(user.getRoleDocumentId()).orElseThrow();
    }
    
    public Tutor getTutorDocumentOf(User user) throws Exception {
        if (user.getRoleType() != LCUserRoleTypes.TUTOR) {
            throw new Exception("Mismatch in requested user role document.");
        }
        return tutorRepository.findById(user.getRoleDocumentId()).orElseThrow();
    }
    
    public Student getStudentDocumentOf(User user) throws Exception {
        if (user.getRoleType() != LCUserRoleTypes.STUDENT) {
            throw new Exception("Mismatch in requested user role document.");
        }
        return studentRepository.findById(user.getRoleDocumentId()).orElseThrow();
    }
    
    public Instructor getInstructorDocumentOf(User user) throws Exception {
        if (user.getRoleType() != LCUserRoleTypes.INSTRUCTOR) {
            throw new Exception("Mismatch in requested user role document.");
        }
        return instructorRepository.findById(user.getRoleDocumentId()).orElseThrow();
    }
    
    public List<Student> getStudentsOfCourseSection(String institution, Course course) {
        
        return userRepository
            .findByCourseSection(institution, course.getCourse(), course.getSection())
            .stream()
            .filter(user -> user.getRoleType() == LCUserRoleTypes.STUDENT)
            .map(studentUser -> studentRepository.findById(studentUser.getRoleDocumentId()).get())
            .collect(Collectors.toList());
        
    }
    
}

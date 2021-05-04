package me.labconnect.webapp.models.users.services;

import me.labconnect.webapp.models.data.Course;
import me.labconnect.webapp.models.users.*;
import me.labconnect.webapp.repository.*;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserCreatorService {

    @Autowired
    private TARepository taRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TutorRepository tutorRepository;
    @Autowired
    private InstructorRepository instructorRepository;
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public enum LCUserRoleTypes {
        TEACHING_ASSISTANT, TUTOR, INSTRUCTOR, STUDENT
    }

    private LCUserRoleTypes roleType;

    private ObjectId roleDocumentId;
    private String institution;
    private String institutionId;
    private List<Course> courses;
    private String name;
    private String email;
    private String password;

    public UserCreatorService() {
        this.courses = new ArrayList<>();
    }

    public UserCreatorService setRoleType(LCUserRoleTypes roleType) {
        this.roleType = roleType;
        return this;
    }

    public UserCreatorService setInstitution(String institution) {
        this.institution = institution;
        return this;
    }

    public UserCreatorService setInstitutionId(String institutionId) {
        this.institutionId = institutionId;
        return this;
    }

    public UserCreatorService setCourses(Course... courses) {
        this.courses = Arrays.asList(courses);
        return this;
    }

    public UserCreatorService setName(String name) {
        this.name = name;
        return this;
    }

    public UserCreatorService setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserCreatorService setPassword(String password) {
        this.password = password;
        return this;
    }

    public User create() throws Exception {

        if (roleType == null) {
            throw new Exception("Role type must be defined.");
        }

        switch (roleType) {
            case TUTOR:
                roleDocumentId = tutorRepository.save(new Tutor("")).getId();
                break;
            case STUDENT:
                roleDocumentId = studentRepository.save(new Student(new ArrayList<>())).getId();
                break;
            case INSTRUCTOR:
                roleDocumentId = instructorRepository
                        .save(new Instructor(new ArrayList<>(), new ArrayList<>())).getId();
                break;
            case TEACHING_ASSISTANT:
                roleDocumentId = taRepository.save(new TeachingAssistant("", new ArrayList<>())).getId();
                break;
        }

        return userRepository.save(
                new User(roleDocumentId, roleType, institution, institutionId, courses, name, email,
                        passwordEncoder.encode(password)));

    }

}

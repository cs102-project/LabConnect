package me.labconnect.webapp;

import com.mongodb.client.MongoClient;

import me.labconnect.webapp.models.data.services.AssignmentService;
import me.labconnect.webapp.models.users.services.TeachingAssistantService;
import me.labconnect.webapp.models.users.services.UserService;
import me.labconnect.webapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import me.labconnect.webapp.models.data.Course;
import me.labconnect.webapp.models.users.services.UserCreatorService;
import me.labconnect.webapp.models.users.services.UserCreatorService.LCUserRoleTypes;

import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

@SpringBootApplication
public class WebappApplication implements CommandLineRunner {

    @Autowired
    private UserCreatorService userCreatorService;
    @Autowired
    private AssignmentService assignmentService;
    @Autowired
    private TeachingAssistantService teachingAssistantService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MongoClient mongoClient;

    public static void main(String[] args) {
        SpringApplication.run(WebappApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        mongoClient.getDatabase("labconnect").drop();
        userCreatorService
                .setRoleType(LCUserRoleTypes.STUDENT)
                .setName("Vedat Eren Arican")
                .setInstitution("Bilkent University")
                .setInstitutionId("22002643")
                .setCourses(new Course("CS102", 2))
                .setEmail("dev@vedat.xyz")
                .setPassword("myPasswd")
                .create();

        userCreatorService.setRoleType(LCUserRoleTypes.INSTRUCTOR).setName("David Davenport").setInstitution("Bilkent University")
                .setInstitutionId("1234")
                .setCourses(new Course("CS102", 2),
                        new Course("CS102", 1),
                        new Course("CS102", 3),
                        new Course("CS101", 3))
                .setEmail("david@cs.bilkent.edu.tr").setPassword("DBRefsBadRoboGood").create();

        userCreatorService.setRoleType(LCUserRoleTypes.TEACHING_ASSISTANT).setName("Teaching Assistant").setInstitution("Bilkent University")
                .setInstitutionId("321").setCourses(new Course("CS102", 2)).setEmail("ta@bilkent.edu.tr").setPassword("passwd").create();

        teachingAssistantService.addStudent(userService.getTADocumentOf(userRepository.findByEmail("mogus@mogus.com")),
                userService.getStudentDocumentOf(userRepository.findByEmail("dev@vedat.xyz")));

        assignmentService.createAssignment("Lab03", "Dummy Lab", "Bilkent University",
                Files.createTempFile("lab_", ".pdf"),
                new GregorianCalendar(2021, Calendar.MAY, 4).getTime(),
                new int[]{1, 2, 3}, "CS102", "Lab", 80, 3, new ArrayList<>(), null, null,
                null, null, null);
    }

}

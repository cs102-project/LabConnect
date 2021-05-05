package me.labconnect.webapp;

import com.mongodb.client.MongoClient;
import me.labconnect.webapp.models.data.Course;
import me.labconnect.webapp.models.data.services.AssignmentService;
import me.labconnect.webapp.models.users.User;
import me.labconnect.webapp.models.users.services.TeachingAssistantService;
import me.labconnect.webapp.models.users.services.UserCreatorService;
import me.labconnect.webapp.models.users.services.UserCreatorService.LCUserRoleTypes;
import me.labconnect.webapp.models.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
    private MongoClient mongoClient;

    public static void main(String[] args) {
        SpringApplication.run(WebappApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        User student;
        User teachingAssistant;

        mongoClient.getDatabase("labconnect").drop();

        student = userCreatorService.setRoleType(LCUserRoleTypes.STUDENT)
                .setName("Vedat Eren Arıcan")
                .setInstitution("Bilkent University")
                .setInstitutionId("22002643")
                .setCourses(new Course("CS102", 2))
                .setEmail("student@bilkent.edu.tr")
                .setPassword("Aa123456")
                .create();

        userCreatorService.setRoleType(LCUserRoleTypes.INSTRUCTOR).setName("Aynur Dayanık")
                .setInstitution("Bilkent University")
                .setInstitutionId("1234")
                .setCourses(new Course("CS102", 2),
                        new Course("CS102", 1),
                        new Course("CS102", 3),
                        new Course("CS101", 3))
                .setEmail("instructor@bilkent.edu.tr").setPassword("Aa123456").create();

        teachingAssistant = userCreatorService.setRoleType(LCUserRoleTypes.TEACHING_ASSISTANT).setName("Haya Shamim Khan Khattak")
                .setInstitution("Bilkent University")
                .setInstitutionId("321").setCourses(new Course("CS102", 2)).setEmail("ta@bilkent.edu.tr")
                .setPassword("Aa123456").create();

        teachingAssistantService.addStudent(userService.getTADocumentOf(teachingAssistant),
                userService.getStudentDocumentOf(student));

        assignmentService.createAssignment("Lab03 | Working with big numbers", "Big numbers are sometimes dreaded by new programmers due to their initial lack of an understanding of how data types work in the world of digital computation. This lab will get you up to speed in no time though. At least, that's the story that has been conjured just now.", "Bilkent University",
                Files.createTempFile("lab_", ".pdf"),
                new GregorianCalendar(2021, Calendar.MAY, 1).getTime(),
                new int[]{1, 2, 3}, "CS102", "Lab", 80, 3, new ArrayList<>(), null, null,
                null, null, null);
                
        assignmentService.createAssignment("Lab04 | Learning how to make pretty GUI's", "Dummy Lab Dummy Dummy Dummy Lab", "Bilkent University",
                Files.createTempFile("lab_", ".pdf"),
                new GregorianCalendar(2021, Calendar.MAY, 7).getTime(),
                new int[]{1, 2, 3}, "CS102", "Lab", 100, 5, new ArrayList<>(), null, null,
                null, null, null);
                
        assignmentService.createAssignment("Lab05 | Create your first Spring project", "And regret what you have unleashed upon yourself once you realize that Spring is not nearly as pleasant as its name would suggest.", "Bilkent University",
                Files.createTempFile("lab_", ".pdf"),
                new GregorianCalendar(2021, Calendar.MAY, 15).getTime(),
                new int[]{1, 2, 3}, "CS102", "Lab", 80, 3, new ArrayList<>(), null, null,
                null, null, null);
    }

}

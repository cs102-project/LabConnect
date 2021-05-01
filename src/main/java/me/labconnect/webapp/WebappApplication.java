package me.labconnect.webapp;

import com.mongodb.client.MongoClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import me.labconnect.webapp.models.data.Course;
import me.labconnect.webapp.models.users.services.UserCreatorService;
import me.labconnect.webapp.models.users.services.UserCreatorService.LCUserRoleTypes;

@SpringBootApplication
public class WebappApplication implements CommandLineRunner {
	
	@Autowired
	private UserCreatorService userCreatorService;
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
	}
	
}

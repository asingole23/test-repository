package com.cybage.gms.app;

import java.time.Duration;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.cybage.gms.app.dao.RoleRepository;
import com.cybage.gms.app.exception.InvalidUsernameException;
import com.cybage.gms.app.model.Address;
import com.cybage.gms.app.model.City;
import com.cybage.gms.app.model.Gender;
import com.cybage.gms.app.model.Role;
import com.cybage.gms.app.model.UserType;
import com.cybage.gms.app.payload.request.SignupRequest;
import com.cybage.gms.app.service.UserDetailsServiceImpl;

@SpringBootApplication
public class GmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(GmsApplication.class, args);
		

	}

	// startup scripts and schedulers
	@Bean
	public CommandLineRunner insertRolesData(RoleRepository roleRepository, UserDetailsServiceImpl userService) {
		return (args) -> {
			if (roleRepository.count() != 4) {
				roleRepository.save(new Role(UserType.ADMIN));
				roleRepository.save(new Role(UserType.DEPARTMENT));
				roleRepository.save(new Role(UserType.CITIZEN));
				roleRepository.save(new Role(UserType.USER_DEPT));
			}
			boolean adminExists= true;
			try {
				userService.findByUsername("admin");
			}catch (InvalidUsernameException e) {
				adminExists = false;
			}
			if(!adminExists) {
				userService.createUser(new SignupRequest("admin", "admin1@cybage.com",
						new HashSet<>(List.of("admin")), "admin", LocalDate.now(), "admin", "123312311", Gender.MALE,
						new Address("100", "home st", City.PUNE), "admin@123"));
				userService.setUnlock("admin");
			}
			
			
			
		};		
		
	}
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {

		return builder.setConnectTimeout(Duration.ofMillis(3000))
						.setReadTimeout(Duration.ofMillis(3000))
						.build();
	}


}

package com.cybage.gms.app.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.management.relation.RoleNotFoundException;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.cybage.gms.app.model.Address;
import com.cybage.gms.app.model.City;
import com.cybage.gms.app.model.Gender;
import com.cybage.gms.app.model.User;
import com.cybage.gms.app.model.UserType;
import com.cybage.gms.app.payload.request.SignupRequest;
/**
 * @Date: 18/11/21
 * @author anmolm
 *
 */
@SpringBootTest
class IUserServiceTest {
	@Autowired
	IUserService userService;

	private static final Logger LOG = Logger.getLogger(IUserServiceTest.class);

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@Test
	void testCreateUser() throws RoleNotFoundException {
//		userService.deleteUser(2);
		LOG.info("in create user test");
		var user = userService.createUser(new SignupRequest("anupam33541", "anupam33541@email.com",
				new HashSet<>(List.of("admin", "citizen", "department")), "anupam", LocalDate.now(), "singh", "123312311", Gender.MALE,
				new Address("100", "home st", City.AHMEDABAD), "password"));

		assertNotNull(user, "some error occured");
		assertNotNull(user.getUserId());
		userService.deleteUser(user.getUserId());
	}

	@Test
	void testSetLock() {
		assertTrue(userService.setLock("anupam3354"));
	}

	@Test
	void testSetUnlock() {
		assertTrue(!userService.setUnlock("anupam3354"));
	}

	@Test
	void testDeleteUser() throws RoleNotFoundException {
		LOG.info("In delete test");
		var user = userService.createUser(new SignupRequest("anupam33541", "anupam33541@email.com",
				new HashSet<>(List.of("admin")), "anupam", LocalDate.now(), "singh", "123312311", Gender.MALE,
				new Address("100", "home st", City.AHMEDABAD), "password"));

		System.out.println("deleting" + user.toString());
		LOG.info("Deleting -> " + userService.deleteUser(user.getUserId()));

	}
	
	@Test 
	void testFindByRole() {
		LOG.info("in test find by role");
		var users = userService.findByRole(UserType.ADMIN);
		assertNotNull(users);
	}

}

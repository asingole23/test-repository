package com.cybage.gms.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cybage.gms.app.dto.ChangePasswordDTO;
import com.cybage.gms.app.dto.UserDTO;
import com.cybage.gms.app.model.UserType;
import com.cybage.gms.app.service.UserDetailsServiceImpl;

@CrossOrigin("http://localhost:4200") // CORS -> prevents web browser from producing and consuming request on any
										// other port
@RestController // Work as a @Controller as well as @ResponseBody
@RequestMapping("/user") // used for mapping web request in different methods like POST, GET,PUT, DELETE
public class UserController {
	@Autowired // used for spring bean dependecy injection at runtime
	private UserDetailsServiceImpl userService;
//	@Autowired
//	private LogService logService;
	public UserController() {
		System.out.println("in constructor of " + getClass().getName());
//		logService.log("in constructor of " + getClass().getName());
	}

	// returns the list of users by user role
	@GetMapping("/list/{type}")
	public ResponseEntity<?> getUsersListByType(@PathVariable String type) {
		System.out.println("in get User list");
		List<UserDTO> userList = userService.getAllUser(UserType.valueOf(type.toUpperCase()));
		if (userList.isEmpty()) {
			System.out.println("Empty User List");
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(userList, HttpStatus.OK);
	}

	// returns user details by id
	@GetMapping("/details/{UserId}")
	public ResponseEntity<?> getUserDetails(@PathVariable Integer UserId) {
		System.out.println("in get User details " + UserId);
		return ResponseEntity.ok(userService.findById(UserId));
	}
	
	//return list of all users
	@GetMapping("/allUserDetails")
	public ResponseEntity<?> getAllUserDetails() {
		return ResponseEntity.ok(userService.findAllUser());
	}

	// returns deleted user by id
	@DeleteMapping("/delete/{UserId}")
	public ResponseEntity<?> deleteUser(@PathVariable Integer UserId) {
		System.out.println("in delete User " + UserId);
		return ResponseEntity.ok(userService.deleteUser(UserId));
	}

	// return updated user details by id
	@PutMapping("/update/{UserId}")
	public ResponseEntity<?> updateUser(@RequestBody UserDTO user,@PathVariable Integer UserId) {
		return ResponseEntity.ok(userService.updateUserDetails(UserId,user));
	}
	
	//change password
	@PutMapping("/changePassword")
	public boolean changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) {
		return userService.changePassword(changePasswordDTO);
	}

}
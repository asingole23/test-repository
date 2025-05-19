package com.cybage.gms.app.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cybage.gms.app.dto.ComplainDTO;
import com.cybage.gms.app.dto.OpenComplainDTO;
import com.cybage.gms.app.dto.UserDTO;
import com.cybage.gms.app.model.Complain;
import com.cybage.gms.app.model.Department;
import com.cybage.gms.app.model.Feedback;
import com.cybage.gms.app.model.OpenComplain;
import com.cybage.gms.app.model.User;
import com.cybage.gms.app.service.IComplainService;
import com.cybage.gms.app.service.IDepartmentService;
import com.cybage.gms.app.service.IFeedbackService;
import com.cybage.gms.app.service.IOpenComplainService;
import com.cybage.gms.app.service.UserDetailsServiceImpl;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/citizen")
public class CitizenController {
	/*
	 * create complain
	 * view list of complains
	 * open complain
	 */
	
	@Autowired
	IComplainService complainService;
	
	@Autowired
	UserDetailsServiceImpl userService;
	
	@Autowired
	IDepartmentService departmentService;
	
	@Autowired
	IOpenComplainService openComplainService;
	
	@Autowired
	IFeedbackService feedbackService;
		
	
	@PostMapping("/complain/create/{deptName}/{citizenId}")
	public ComplainDTO createComplain(@RequestBody Complain complain,@PathVariable Integer citizenId,@PathVariable String deptName) {
		User citizen=userService.findByIds(citizenId);
		Department departmentDTO=departmentService.getDepartmentByNames(deptName);
		return complainService.createComplain(citizen, complain,departmentDTO);
	}
	
	@GetMapping("/complain/list/{citizenId}")
	public List<ComplainDTO> viewAllComplain(@PathVariable Integer citizenId) {	
		User citizen=userService.findByIds(citizenId);
		return complainService.findByCitizen(citizen);
	}
	
	@GetMapping("/complain/view/{complainId}")
	public ComplainDTO viewComplain(@PathVariable Integer complainId) {	
		return complainService.findById(complainId);
	}
	
	@PostMapping("/complain/reopenComplain/{complainId}")
	public OpenComplainDTO createOpenComplain(@PathVariable Integer complainId,@RequestBody OpenComplain openComplain) {	
		Complain complain=complainService.findByIds(complainId);
		return openComplainService.createOpenComplain(complain, openComplain);
	}
	
	@PutMapping("/complain/sendReminder/{complainId}")
	public boolean sendReminder(@PathVariable Integer complainId) {
		return complainService.sendReminderToDepartment(complainId);
	}
	
	@GetMapping("/userDetails/{username}")
	public UserDTO findUserDetailsByUsername(@PathVariable String username) {
		return userService.findByUsername(username);
	}
	
	@GetMapping("/openComplain/{citizenId}")
	public List<OpenComplainDTO> allOpenComplain(@PathVariable Integer citizenId){
		return openComplainService.findByCitizenId(citizenId);
	}
	
	@PostMapping("/giveFeedback")
	public ResponseEntity<?> addFeedback(@Valid @RequestBody Feedback feedback) {
		feedback.setTimestamp(LocalDateTime.now());
		return feedbackService.save(feedback);
	}
}

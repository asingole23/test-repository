package com.cybage.gms.app.controller;

import java.util.HashSet;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cybage.gms.app.dto.AssignDepartmentDTO;
import com.cybage.gms.app.dto.ComplainDTO;
import com.cybage.gms.app.dto.DepartmentDTO;
import com.cybage.gms.app.dto.DepartmentHeadDTO;
import com.cybage.gms.app.dto.OpenComplainDTO;
import com.cybage.gms.app.dto.UserDTO;
import com.cybage.gms.app.model.Department;
import com.cybage.gms.app.model.Faq;
import com.cybage.gms.app.model.Log;
import com.cybage.gms.app.model.OpenComplain;
import com.cybage.gms.app.model.Topic;
import com.cybage.gms.app.model.UserType;
import com.cybage.gms.app.payload.request.SignupRequest;
import com.cybage.gms.app.service.IComplainService;
import com.cybage.gms.app.service.IDepartmentService;
import com.cybage.gms.app.service.IFaqService;
import com.cybage.gms.app.service.IFeedbackService;
import com.cybage.gms.app.service.ILogService;
import com.cybage.gms.app.service.IOpenComplainService;
import com.cybage.gms.app.service.ITopicService;
import com.cybage.gms.app.service.UserDetailsServiceImpl;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	public IDepartmentService departmentService;
	
	@Autowired
	private IFaqService faqService;
	
	@Autowired
	private ITopicService topicService;
	
	@Autowired
	private IComplainService complainService;
	
	@Autowired
	private IOpenComplainService openComplainService;
	
	@Autowired
	private UserDetailsServiceImpl userService;
	
	@Autowired
	private ILogService logService;
	
	@Autowired
	private IFeedbackService feedbackService;
	
	//check done
	@GetMapping("/department/all")
	public List<DepartmentDTO> allDepartments(){
		return departmentService.getAllDepartments();
	}
	
	//check done
	@GetMapping("/department/{id}")
	public DepartmentDTO byId(@PathVariable Integer id) {
		return departmentService.findById(id);
	}
	
	//check done
	//create department
	@PostMapping("/department/add")
	public DepartmentDTO addDepartment(@Valid @RequestBody Department department) {
		return departmentService.addDepartment(department);
	}
	
	
	// create department head
	// give user_dept
	@PostMapping("/department/head/signup")		
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if(signUpRequest.getRole() == null) {
			signUpRequest.setRole(new HashSet<>());
		}
		if(!signUpRequest.getRole().contains("user_dept")) {
			signUpRequest.getRole().add("user_dept");
		}
		return userService.createUserResponse(signUpRequest);
	}
	// all unassigned department heads
	@GetMapping("/department/head/unassigned")
	public List<UserDTO> allUnassignedDepartmentHeads(){
		return userService.findByRole(UserType.USER_DEPT);
	}
	//all unassigned departments 
	@GetMapping("/department/unassigned")
	public List<DepartmentDTO> allUnassignedDepartments(){
		return departmentService.getAllUnassignedDepartments();
	}
	//assign department to department head
//	@PostMapping("/department/assign")
//	public AssignDepartmentDTO assignDepartment(@RequestBody @Valid AssignDepartmentDTO dto) {
//		return departmentService.assignDepartment(dto);
//	}
	
	@PostMapping("/department/assign/{userId}/{deptId}")
	public AssignDepartmentDTO assignDepartmentById(@PathVariable Integer userId, @PathVariable Integer deptId) {
		return departmentService.assignDepartment(deptId,userId);
	}
	
	@PostMapping("/department/unassign/{departmentId}")
	public boolean unassignDepartmentFromDeptHead(@PathVariable Integer departmentId) {
		return departmentService.unassignDepartment(departmentId);
	}
	
	
	@PostMapping("/department/update")
	public DepartmentDTO updateDepartment(@Valid @RequestBody Department department) {
		return departmentService.updateDepartment(department, department.getDepartmentHead());
	}
	
	@DeleteMapping("/department/delete/{departmentId}")
	public String deleteDepartment(@Valid @PathVariable Integer departmentId) {
		if(departmentService.deleteDepartmentById(departmentId)) {
			return "{\"message\":\"Department deleted.\"}";
		}
		return "{\"message\":\"Department not deleted.\"}";
	}
	
	@GetMapping("/departmant/head/all")
	public List<DepartmentHeadDTO> allDepartmentHead(){
		return departmentService.findAllDepartmentHeads();
	}
	@GetMapping("/department/head/{id}")
	public UserDTO departmentHeadById(@PathVariable Integer id){
		return userService.findById(id);
	}
	
	@PutMapping("/user/unlock/{username}")
	public String unlockAccount(@PathVariable String username) {
		if(!userService.setUnlock(username)){
			return "{\"message\":\"user "+username+" is now unlocked.\"}";
		}
		return "{\"message\":\"user "+username+" is locked.\"}";
	}
	
	
	@PutMapping("/user/lock/{userId}")
	public String lockAccount(@PathVariable Integer userId) {
		if(!userService.setLock(userId)){
			return "{\"message\":\"user "+userId+" is now unlocked.\"}";
		}
		return "{\"message\":\"user "+userId+" is locked.\"}";
	}	
	
	@GetMapping("/complains/list")
	public List<ComplainDTO> allComplains(){
		return complainService.findAllComplains();
	}

	@GetMapping("openComplains/list")
	public List<OpenComplainDTO> allOpenComplains(){
		return openComplainService.findAllOpenComplain();
	}
	
	@GetMapping("/openComplain/{openComplainId}")
	public OpenComplainDTO openComplainById(@PathVariable Integer openComplainId) {
		return openComplainService.findById(openComplainId);
	}	
		
	
	@PutMapping("/openComplain/takeAction/{openComplainId}")
	public OpenComplainDTO updateOpenComplain(@PathVariable Integer openComplainId,@RequestBody OpenComplain openComplain) {
		return openComplainService.updateOpenComplain(openComplainId,openComplain);
	}
	@GetMapping("/logs")
	public List<Log> getAllLogs(){
		return logService.getAllLogs();
	} 
	
	@PostMapping("/addfAQ")
	public ResponseEntity<?> saveFaq(@RequestBody Faq faq) {
		System.out.println(faq);
		return faqService.save(faq);
	}
	@DeleteMapping("/deletefAQ/{id}")
	public ResponseEntity<?> deleteFaq(@PathVariable("id")Integer id) {
		return faqService.deleteById(id);
	}
	
	@GetMapping("/allfAQ")
	public ResponseEntity<?> allFaq(){
		return faqService.findAll(); 
	}
	
	@PostMapping("/addFaqTopic")
	public ResponseEntity<?> addTopic(@Valid @RequestBody Topic topic) {
		return topicService.save(topic);
	}
	
	@GetMapping("/allFaqTopics")
	public ResponseEntity<?> allTopics(){
		return topicService.findAll();
	}
	
	@GetMapping("/allFeedbacks")
	public ResponseEntity<?> allFeedbacks(){
		return feedbackService.findAll();
	}
	
}
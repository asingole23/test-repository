package com.cybage.gms.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cybage.gms.app.dto.ComplainDTO;
import com.cybage.gms.app.dto.DepartmentDTO;
import com.cybage.gms.app.dto.OpenComplainDTO;
import com.cybage.gms.app.model.Department;
import com.cybage.gms.app.model.User;
import com.cybage.gms.app.service.IComplainService;
import com.cybage.gms.app.service.IDepartmentService;
import com.cybage.gms.app.service.IOpenComplainService;
import com.cybage.gms.app.service.UserDetailsServiceImpl;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/reports")
public class ReportController {
	@Autowired
	public IDepartmentService departmentService;
	
	@Autowired
	public IComplainService complainService;
	
	@Autowired
	public IOpenComplainService openComplainService;
	
	@Autowired
	public UserDetailsServiceImpl userService;
	
	// For Admin
//	1. all users
//	2. all departments
//	3. all complains
//	4. all Reopen complains
//	5. all resolved complains
//	6. all unresolved complains
	
	//All Users
	@GetMapping("/userReport")
	public ResponseEntity<?> getAllUserDetails() {
		return ResponseEntity.ok(userService.findAllUser());
	}
	
	//All Department
	@GetMapping("/department/all")
	public List<DepartmentDTO> allDepartments(){
		return departmentService.getAllDepartments();
	}
	
	@GetMapping("/departmentReport")
	public List<DepartmentDTO> allUnassignedDepartments(){
		return departmentService.getAllUnassignedDepartments();
	}
	
	//All Complains
	@GetMapping("/complainReport")
	public List<ComplainDTO> allComplains(){
		return complainService.findAllComplains();
	}
	
	//All Reopen Complains
	@GetMapping("openComplainsReport")
	public List<OpenComplainDTO> allOpenComplains(){
		return openComplainService.findAllOpenComplain();
	}
	
	@GetMapping("/resolvedComplainsReport")
	public List<ComplainDTO> resolvedComplains(){
		return complainService.findByResolved();
	}
	
	@GetMapping("/unresolvedComplainsReport")
	public List<ComplainDTO> unresolvedComplains(){
		return complainService.findByUnResolved();
	}
	
	// Department Head 
	/*
	 * 1. All complains 2. All resolved complains 3. All unsolved complains
	 */
	
	// All complains by Department
	@GetMapping("/complainsByDepartment/{departmentHeadId}")
	public List<ComplainDTO> findByDepartment(@PathVariable Integer departmentHeadId) {
		User departmentHead = userService.findByIds(departmentHeadId);
		Department department = departmentHead.getDepartment();
		return complainService.findByDepartment(department);
	}
	
	@GetMapping("/complainsByDepartmentResolved/{departmentHeadId}")
	public List<ComplainDTO> findByDepartmentResolved(@PathVariable Integer departmentHeadId) {
		User departmentHead = userService.findByIds(departmentHeadId);
		Department department = departmentHead.getDepartment();
		return complainService.findByDepartmentResolved(department);
	}
	
	@GetMapping("/complainsByDepartmentUnresolved/{departmentHeadId}")
	public List<ComplainDTO> findByDepartmentUnresolved(@PathVariable Integer departmentHeadId) {
		User departmentHead = userService.findByIds(departmentHeadId);
		Department department = departmentHead.getDepartment();
		return complainService.findByDepartmentUnresolved(department);
	}
	

}

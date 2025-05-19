package com.cybage.gms.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cybage.gms.app.dto.ComplainDTO;
import com.cybage.gms.app.dto.DepartmentDTO;
import com.cybage.gms.app.model.Department;
import com.cybage.gms.app.model.User;
import com.cybage.gms.app.service.IComplainService;
import com.cybage.gms.app.service.IDepartmentService;
import com.cybage.gms.app.service.IOpenComplainService;
import com.cybage.gms.app.service.UserDetailsServiceImpl;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/department")
public class DepartmentContoller {

	@Autowired
	IComplainService complainService;

	@Autowired
	UserDetailsServiceImpl userService;

	@Autowired
	IDepartmentService departmentService;

	@Autowired
	IOpenComplainService openComplainService;

	@GetMapping("/complains/head/{departmentHeadId}")
	public List<ComplainDTO> findByDepartment(@PathVariable Integer departmentHeadId) {
		User departmentHead = userService.findByIds(departmentHeadId);
		Department department = departmentHead.getDepartment();
		return complainService.findByDepartment(department);
	}

	@PutMapping("/complains/update/head/{departmentHeadId}")
	public ComplainDTO updateComplain(@RequestBody ComplainDTO complain, @PathVariable Integer departmentHeadId) {
		return complainService.updateComplain(departmentHeadId, complain);
	}

	@GetMapping("/complains/{complainId}")
	public ComplainDTO getComlainById(@PathVariable Integer complainId) {
		return complainService.findById(complainId);
	}
	
	@PutMapping("/complains/update/department/{departmentId}")
	public ComplainDTO changeDepartmentForComplain(@RequestBody ComplainDTO complainDTO, @PathVariable Integer departmentId) {
		return complainService.updateComplainDepartment(departmentId, complainDTO);
	}
	
	@GetMapping("/allDepartment")
	public List<DepartmentDTO> allDepartments(){
		return departmentService.getAllDepartments();
	}
	
	@GetMapping("/allReminders/{departmentHeadId}")
	public List<ComplainDTO> viewAllReminders(@PathVariable Integer departmentHeadId){
		User departmentHead = userService.findByIds(departmentHeadId);
		Department department = departmentHead.getDepartment();
		return complainService.getAllReminders(department);
	}
	

}

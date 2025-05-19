package com.cybage.gms.app.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cybage.gms.app.dao.DepartmentRepository;
import com.cybage.gms.app.dao.UserRepository;
import com.cybage.gms.app.dto.AssignDepartmentDTO;
import com.cybage.gms.app.dto.DepartmentDTO;
import com.cybage.gms.app.dto.DepartmentHeadDTO;
import com.cybage.gms.app.exception.InvalidDepartmentException;
import com.cybage.gms.app.exception.InvalidEmailException;
import com.cybage.gms.app.exception.InvalidUsernameException;
import com.cybage.gms.app.model.Department;
import com.cybage.gms.app.model.User;

@Service
public class DepartmentSeviceImpl implements IDepartmentService {
	@Autowired
	DepartmentRepository departmentRepository;

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private IUserService userService;

	private static DepartmentDTO departmentToDepartmentDto(Department department) {
		DepartmentDTO departmentDTO = new DepartmentDTO();
		BeanUtils.copyProperties(department, departmentDTO);
		return departmentDTO;
	}

	@Override
	public DepartmentDTO findById(Integer id) {
		Department department = departmentRepository.findById(id)
				.orElseThrow(() -> new InvalidDepartmentException("Department Not Present"));
		return departmentToDepartmentDto(department);

	}

	@Override
	public DepartmentDTO getDepartmentByName(String name) {
		Department department = departmentRepository.findByDepartmentName(name)
				.orElseThrow(() -> new InvalidDepartmentException("The department name you provided is invalid."));
		return departmentToDepartmentDto(department);

	}

	@Override
	public List<DepartmentDTO> getAllDepartments() {
		List<DepartmentDTO> departmentsDTOs = new ArrayList<>();
		departmentRepository.findAll().forEach(allDepartment -> {
			DepartmentDTO departmentDTO = new DepartmentDTO();
			BeanUtils.copyProperties(allDepartment, departmentDTO);
			departmentsDTOs.add(departmentDTO);
		});
		return departmentsDTOs;
	}

	@Override
	public DepartmentDTO addDepartment(Department department) {
		departmentRepository.save(department);
		return departmentToDepartmentDto(department);
	}

	@Override
	public DepartmentDTO updateDepartment(Department department, User departmentHead) {
		Optional<Department> department2 = departmentRepository.findById(department.getId());
		if (department2.isEmpty()) {
			throw new InvalidDepartmentException("Department Not Present");
		} else {
			department.setDepartmentHead(departmentHead);
		}

		departmentRepository.save(department);
		return departmentToDepartmentDto(department);
	}

	@Transactional
	@Override
	public boolean deleteDepartmentById(Integer departmentId) {
		Optional<Department> deparment2 = departmentRepository.findById(departmentId);
		if (deparment2.isEmpty()) {
			return false;
		} else {
			departmentRepository.deleteById(departmentId);
			return true;

		}

	}

	@Override
	public List<DepartmentDTO> getAllUnassignedDepartments() {
		return departmentRepository.findAll().stream().filter(dept -> dept.getDepartmentHead() == null)
				.map(Utility::departmentToDepartmentDto).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public AssignDepartmentDTO assignDepartment(AssignDepartmentDTO dto) {

		Department department = departmentRepository.findByDepartmentName(dto.getDepartmentDTO().getDepartmentName())
				.orElseThrow(() -> new InvalidDepartmentException("The department name you provided is invalid."));
		User deptHead = userRepository.findByEmail(dto.getUserDto().getEmail()).orElseThrow(InvalidEmailException::new);
		userService.removeRoles(deptHead.getUsername(), new HashSet<>(List.of("user_dept")));
		userService.addRoles(deptHead.getUsername(), new HashSet<>(List.of("department")));
		deptHead.setDepartment(department);
		AssignDepartmentDTO assignDepartmentDTO = new AssignDepartmentDTO();
		assignDepartmentDTO.setDepartmentDTO(Utility.departmentToDepartmentDto(department));
		assignDepartmentDTO.setUserDto(Utility.userToUserDto(deptHead));
		return assignDepartmentDTO;
	}

	@Override
	public Department getDepartmentByNames(String name) {
		Department department = departmentRepository.findByDepartmentName(name)
				.orElseThrow(() -> new InvalidDepartmentException("The department name you provided is invalid."));
		return department;
	}

	@Transactional
	@Override
	public AssignDepartmentDTO assignDepartment(int deptId, int userId) {
		Department department = departmentRepository.findById(deptId)
				.orElseThrow(() -> new InvalidDepartmentException("The department id you provided is invalid."));
		User deptHead = userRepository.findById(userId).orElseThrow(InvalidUsernameException::new);
		userService.removeRoles(deptHead.getUsername(), new HashSet<>(List.of("user_dept")));
		userService.addRoles(deptHead.getUsername(), new HashSet<>(List.of("department")));
		deptHead.setDepartment(department);
		department.setDepartmentHead(deptHead);
		AssignDepartmentDTO assignDepartmentDTO = new AssignDepartmentDTO();
		assignDepartmentDTO.setDepartmentDTO(Utility.departmentToDepartmentDto(department));
		assignDepartmentDTO.setUserDto(Utility.userToUserDto(deptHead));
		return assignDepartmentDTO;
	}
	
	@Transactional
	@Override
	public List<DepartmentHeadDTO> findAllDepartmentHeads(){
		return departmentRepository.findAll().stream().filter(dept->
			dept.getDepartmentHead()!=null
		).map(Utility::departmentToDepartmentHeadDTO
		).collect(Collectors.toList());
	}

	@Transactional
	@Override
	public boolean unassignDepartment(Integer departmentId) {
		Department department=departmentRepository.findById(departmentId).orElseThrow(()->new InvalidDepartmentException("Invalid Department ID"));
		User user=department.getDepartmentHead();
		department.setDepartmentHead(null);
		userService.removeRoles(user.getUsername(), new HashSet<String>(List.of("department")));
		userService.addRoles(user.getUsername(), new HashSet<String>(List.of("user_dept")));		
		return true;
	}

	
}

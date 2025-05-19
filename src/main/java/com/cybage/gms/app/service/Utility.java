package com.cybage.gms.app.service;

import org.springframework.beans.BeanUtils;

import com.cybage.gms.app.dto.ComplainDTO;
import com.cybage.gms.app.dto.DepartmentDTO;
import com.cybage.gms.app.dto.DepartmentHeadDTO;
import com.cybage.gms.app.dto.UserDTO;
import com.cybage.gms.app.model.Complain;
import com.cybage.gms.app.model.Department;
import com.cybage.gms.app.model.User;

public interface Utility {
	static DepartmentDTO departmentToDepartmentDto(Department department) {
		DepartmentDTO departmentDto = new DepartmentDTO();
		BeanUtils.copyProperties(department, departmentDto);
		return departmentDto;
	}
	static UserDTO userToUserDto(User user) {
		UserDTO userDto = new UserDTO();
		BeanUtils.copyProperties(user, userDto);
		return userDto;
	}
	static DepartmentHeadDTO departmentToDepartmentHeadDTO(Department dept) {
		DepartmentHeadDTO departmentHeadDTO=new DepartmentHeadDTO();
		departmentHeadDTO.setDepartmentDTO(departmentToDepartmentDto(dept));
		departmentHeadDTO.setUserDTO(userToUserDto(dept.getDepartmentHead()));
		return departmentHeadDTO;
	}

	static ComplainDTO complainToComplainDto(Complain complain) {
		ComplainDTO complainDTO = new ComplainDTO();
		BeanUtils.copyProperties(complain, complainDTO);
		return complainDTO;
	}
}
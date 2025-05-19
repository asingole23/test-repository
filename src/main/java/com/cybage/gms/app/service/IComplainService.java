package com.cybage.gms.app.service;

import java.util.List;

import com.cybage.gms.app.dto.ComplainDTO;
import com.cybage.gms.app.model.Complain;
import com.cybage.gms.app.model.Department;
import com.cybage.gms.app.model.User;

public interface IComplainService {
	ComplainDTO findById(Integer id);
	Complain findByIds(Integer id);
	List<ComplainDTO> findByCitizen(User citizen);
	List<ComplainDTO> findByDepartment(Department department);
	
	//For ADMIN ONLY
	List<ComplainDTO> findAllComplains();
		
	ComplainDTO createComplain(User citizenId,Complain complain, Department department);	
	
	boolean deleteComplain(Complain complain);
	
	boolean sendReminderToDepartment(Integer complainId);
	
	ComplainDTO updateComplain(Integer departmentHeadId, ComplainDTO complain);
	ComplainDTO updateComplainDepartment(Integer departmentId, ComplainDTO complain);
	
	List<ComplainDTO> findByResolved();
	
	List<ComplainDTO> findByUnResolved();
	
	List<ComplainDTO> findByDepartmentResolved(Department department);
	
	List<ComplainDTO> findByDepartmentUnresolved(Department department);
	
	List<ComplainDTO> getAllReminders(Department department);
	
	
}

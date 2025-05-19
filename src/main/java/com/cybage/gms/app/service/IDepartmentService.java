package com.cybage.gms.app.service;

import java.util.List;

import javax.validation.Valid;

import com.cybage.gms.app.dto.AssignDepartmentDTO;
import com.cybage.gms.app.dto.DepartmentDTO;
import com.cybage.gms.app.dto.DepartmentHeadDTO;
import com.cybage.gms.app.model.Department;
import com.cybage.gms.app.model.User;

public interface IDepartmentService {
public List<DepartmentDTO> getAllDepartments();
public DepartmentDTO addDepartment(Department department);
public DepartmentDTO updateDepartment(Department department, User departmentHead);
public boolean deleteDepartmentById(Integer  departmentId);
public DepartmentDTO getDepartmentByName(String name);
public Department getDepartmentByNames(String name);
public DepartmentDTO findById(Integer id);
public List<DepartmentDTO> getAllUnassignedDepartments();
public AssignDepartmentDTO assignDepartment(@Valid AssignDepartmentDTO dto);
//public DepartmentDTO findByName(String deptName);
public AssignDepartmentDTO assignDepartment(int deptId, int userId);
List<DepartmentHeadDTO> findAllDepartmentHeads();
public boolean unassignDepartment(Integer departmentId);
}

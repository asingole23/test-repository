package com.cybage.gms.app.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cybage.gms.app.model.Department;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
	Optional<Department> findByDepartmentName(String departmentName);
}

package com.cybage.gms.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cybage.gms.app.model.Complain;

@Repository
public interface ComplainRepository extends JpaRepository<Complain, Integer> {
	
	@Query(value = "select * from complain_table where citizen_id= :userId",nativeQuery = true)
	List<Complain> findByCitizen(Integer userId);
	
	@Query(value = "select *from complain_table where department_id= :departmentId",nativeQuery = true)
	List<Complain> findByDepartmentId(Integer departmentId);
	
	@Query(value = "select *from complain_table where resolved = true",nativeQuery = true)
	List<Complain> findByResolved();
	
	@Query(value = "select *from complain_table where resolved = false",nativeQuery = true)
	List<Complain> findByUnResolved();
	
	@Query(value = "select *from complain_table where department_id= :departmentId and resolved = true",nativeQuery = true)
	List<Complain> findByDepartmentResolved(Integer departmentId);
	
	@Query(value = "select *from complain_table where department_id= :departmentId and resolved = false",nativeQuery = true)
	List<Complain> findByDepartmentUnresolved(Integer departmentId);
	
	@Query(value = "select *from complain_table where department_id= :departmentId and remainder = true",nativeQuery = true)
	List<Complain> findByDepartmentRemainder(Integer departmentId);
	
}

package com.cybage.gms.app.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cybage.gms.app.model.User;
import com.cybage.gms.app.model.UserType;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByUsername(String username);
	Optional<User> findByEmail(String email);
	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);

	Boolean existsByPhoneNo(String phoneNo);
	
	@Query("select u from User u left outer join u.roles ar where ar.name = :name")
	List<User> findByType(UserType name);
	
	@Query("select u from User u left outer join u.roles ar where ar.name = :name and u.userId = :id")
	Optional<User> findByTypeId(UserType name,Integer id);
}

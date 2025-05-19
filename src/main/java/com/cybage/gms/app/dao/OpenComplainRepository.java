package com.cybage.gms.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cybage.gms.app.dto.OpenComplainDTO;
import com.cybage.gms.app.model.OpenComplain;
import com.cybage.gms.app.model.User;

public interface OpenComplainRepository extends JpaRepository<OpenComplain, Integer> {

//	@Query(value="select * from complain c INNER JOIN open_complain o on c.id=o.id")
//	select o from OpenComplain left outer join fetch o.complain where o.complain.id= :citizenId
	@Query(value = "select o from OpenComplain o left outer join fetch o.complain where o.complain.citizen= :citizenId")
	List<OpenComplain> findByCitizenId(User citizenId);
}

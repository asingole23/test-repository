package com.cybage.gms.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cybage.gms.app.model.Log;

public interface LogRepository extends JpaRepository<Log, Integer> {

}

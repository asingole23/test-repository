package com.cybage.gms.app.service;

import org.springframework.http.ResponseEntity;

import com.cybage.gms.app.model.Topic;

public interface ITopicService {
	ResponseEntity<?> findAll();
	ResponseEntity<?> save(Topic topic);
	ResponseEntity<?> update(Integer id, Topic topic);

	ResponseEntity<?> findByName(String name);
	ResponseEntity<?> findById(Integer id);

	ResponseEntity<?> delete(Integer id);
}
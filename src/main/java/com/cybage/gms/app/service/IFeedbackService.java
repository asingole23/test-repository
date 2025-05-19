package com.cybage.gms.app.service;

import java.util.List;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import com.cybage.gms.app.model.Feedback;

public interface IFeedbackService {
	ResponseEntity<?> findAll();
	ResponseEntity<?> save(Feedback feedback);
	ResponseEntity<?> update(Integer id, Feedback feedback);
	ResponseEntity<?> deleteById(Integer feedbackId);
	ResponseEntity<?> findByTopicName(String topicName);
	ResponseEntity<?> findById(Integer id);
}
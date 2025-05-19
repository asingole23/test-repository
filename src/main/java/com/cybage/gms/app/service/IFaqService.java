package com.cybage.gms.app.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.cybage.gms.app.model.Faq;
import com.cybage.gms.app.model.Topic;

public interface IFaqService {
	ResponseEntity<?> findAll();
	ResponseEntity<?> findById(Integer id);
	ResponseEntity<?> save(Faq faq);
	ResponseEntity<?> deleteById(Integer faqId);

	ResponseEntity<?> findByTopicName(String topicName);
	ResponseEntity<?> update(Integer id, Faq faq);
}
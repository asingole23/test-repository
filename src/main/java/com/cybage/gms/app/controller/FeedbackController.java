package com.cybage.gms.app.controller;

import java.time.LocalDateTime;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cybage.gms.app.model.Feedback;
import com.cybage.gms.app.service.IFeedbackService;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {
	@Autowired
	private IFeedbackService feedbackService;
	
	@GetMapping("/all")
	public ResponseEntity<?> allFeedbacks(){
		return feedbackService.findAll();
	}
	@GetMapping("/topic/{topic}")
	public ResponseEntity<?> allByTopic(@PathVariable("topic") String topic){
		return feedbackService.findByTopicName(topic);
	}
	@GetMapping("/id/{id}")
	public ResponseEntity<?> byId(@PathVariable("id") Integer id){
		return feedbackService.findById(id);
	}
	@PostMapping("/add")
	public ResponseEntity<?> addFeedback(@Valid @RequestBody Feedback feedback) {
		feedback.setTimestamp(LocalDateTime.now());
		return feedbackService.save(feedback);
	}
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateFeedback(@Valid @RequestBody Feedback feedback, @PathVariable("id")Integer id) {
		feedback.setTimestamp(LocalDateTime.now());
		return feedbackService.update(id, feedback);
	}
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteFeedback(@PathVariable("id") Integer id) {
		return feedbackService.deleteById(id);
	}
}

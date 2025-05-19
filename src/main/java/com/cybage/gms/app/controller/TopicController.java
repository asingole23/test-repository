package com.cybage.gms.app.controller;

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

import com.cybage.gms.app.model.Topic;
import com.cybage.gms.app.service.ITopicService;

@RestController
@RequestMapping("/topic")
public class TopicController {
	@Autowired
	private ITopicService topicService;
	
	@GetMapping("/all")
	public ResponseEntity<?> allTopics(){
		return topicService.findAll();
	}
	
	@PostMapping("/add")
	public ResponseEntity<?> addTopic(@Valid @RequestBody Topic topic) {
		return topicService.save(topic);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateTopic(@Valid @RequestBody Topic topic, @PathVariable("id")Integer id) {
		return topicService.update(id, topic);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteTopic(@PathVariable("id")Integer id) {
		return topicService.delete(id);
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<?> byId(@PathVariable("id")Integer id) {
		return topicService.findById(id);
	}
}

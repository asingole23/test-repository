package com.cybage.gms.app.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cybage.gms.app.model.Faq;
import com.cybage.gms.app.model.Feedback;
import com.cybage.gms.app.model.Topic;
@SpringBootTest
class MethodTest {
	@Autowired
	ITopicService topicService;
	
	@Autowired
	IFaqService faqService;
	
	@Autowired
	IFeedbackService feedbackService;
	
	@Test
	void testAddTopic() {
		//topicService.save(new Topic("test for check"));
	}
	
	@Test
	void testAllTopic() {
		//System.out.println(topicService.findAll());
	}

	@Test
	void testAllFaq() {
		//faqService.findAll();
	}	
	
}

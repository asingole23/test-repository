package com.cybage.gms.app.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cybage.gms.app.model.Feedback;

@Service
public class FeedbackServiceImpl implements IFeedbackService {
	@Value("${gms.feedback.url}")
	private String feedbackUrl;
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public ResponseEntity<?> findAll() {
		return restTemplate.getForEntity(feedbackUrl + "/all", Feedback[].class);
	}

	@Override
	public ResponseEntity<?> save(Feedback feedback) {
		return restTemplate.postForEntity(feedbackUrl + "/add", feedback, Feedback.class);
	}

	@Override
	public ResponseEntity<?> update(Integer id, Feedback feedback) {
		RequestEntity<Feedback> request = null;
		try {
			request = RequestEntity.put(new URI(feedbackUrl+"/update/"+id))
					.accept(MediaType.APPLICATION_JSON).body(feedback);
		} catch (URISyntaxException e) {
			//request =
			return restTemplate.exchange(request, Feedback.class);
		}
		return restTemplate.exchange(request, Feedback.class);
	}

	@Override
	public ResponseEntity<?> deleteById(Integer feedbackId) {
		RequestEntity<?> request = null;
		try {
			request = RequestEntity.delete(new URI(feedbackUrl+"/delete/"+feedbackId))
					.accept(MediaType.APPLICATION_JSON).build();
		} catch (URISyntaxException e) {
			//request =
			return restTemplate.exchange(request, Feedback.class);
		}
		return restTemplate.exchange(request, Feedback.class);
	}

	@Override
	public ResponseEntity<?> findByTopicName(String topicName) {
		RequestEntity<?> request = null;
		try {
			request = RequestEntity.get(new URI(feedbackUrl+"/topic/"+topicName))
					.accept(MediaType.APPLICATION_JSON).build();
		} catch (URISyntaxException e) {
			//request =
			return restTemplate.exchange(request, Feedback.class);
		}
		return restTemplate.exchange(request, Feedback.class);
	}

	@Override
	public ResponseEntity<?> findById(Integer id) {
		RequestEntity<?> request = null;
		try {
			request = RequestEntity.get(new URI(feedbackUrl+"/id/"+id))
					.accept(MediaType.APPLICATION_JSON).build();
		} catch (URISyntaxException e) {
			//request =
			return restTemplate.exchange(request, Feedback.class);
		}
		return restTemplate.exchange(request, Feedback.class);
	}

}
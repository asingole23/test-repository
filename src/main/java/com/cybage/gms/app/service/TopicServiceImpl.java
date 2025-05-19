package com.cybage.gms.app.service;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cybage.gms.app.model.Topic;

@Service
public class TopicServiceImpl implements ITopicService{
	@Value("${gms.topic.url}")
	private String topicUrl;
	@Autowired
	private RestTemplate restTemplate;
	@Override
	public ResponseEntity<?> findAll() {
//		RequestEntity<?> request = null;
//		try {
//			request = RequestEntity.get(new URI(topicUrl+"/all"))
//					.accept(MediaType.APPLICATION_JSON).build();
//		} catch (URISyntaxException e) {
//			//request =
//			return restTemplate.exchange(request, Topic[].class);
//		}
//		return restTemplate.exchange(request, Topic[].class);
		return restTemplate.getForEntity(topicUrl+"/all", Topic[].class);
	}

	@Override
	public ResponseEntity<?> save(Topic topic) {		
		return restTemplate.postForEntity(topicUrl+"/add", topic, Topic.class);
	}

	@Override
	public ResponseEntity<?> update(Integer id, Topic topic) {
		RequestEntity<?> request = null;
		try {
			request = RequestEntity.put(new URI(topicUrl+"/update/"+id))
					.accept(MediaType.APPLICATION_JSON).body(topic);
		} catch (URISyntaxException e) {
			//request =
			return restTemplate.exchange(request, Topic.class);
		}
		return restTemplate.exchange(request, Topic.class);
	}

	@Override
	public ResponseEntity<?> delete(Integer id) {
		RequestEntity<?> request = null;
		try {
			request = RequestEntity.delete(new URI(topicUrl+"/delete/"+id))
					.accept(MediaType.APPLICATION_JSON).build();
		} catch (URISyntaxException e) {
			//request =
			return restTemplate.exchange(request, Topic.class);
		}
		return restTemplate.exchange(request, Topic.class);
	}

	@Override
	public ResponseEntity<?> findByName(String name) {
		RequestEntity<?> request = null;
		try {
			request = RequestEntity.get(new URI(topicUrl+"/name/"+name))
					.accept(MediaType.APPLICATION_JSON).build();
		} catch (URISyntaxException e) {
			//request =
			return restTemplate.exchange(request, Topic.class);
		}
		return restTemplate.exchange(request, Topic.class);
	}

	@Override
	public ResponseEntity<?> findById(Integer id) {
		RequestEntity<?> request = null;
		try {
			request = RequestEntity.get(new URI(topicUrl+"/id/"+id))
					.accept(MediaType.APPLICATION_JSON).build();
		} catch (URISyntaxException e) {
			//request =
			return restTemplate.exchange(request, Topic.class);
		}
		return restTemplate.exchange(request, Topic.class);
	}



}
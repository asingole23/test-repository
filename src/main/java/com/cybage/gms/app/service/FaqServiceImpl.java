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

import com.cybage.gms.app.model.Faq;
import com.cybage.gms.app.model.Feedback;
import com.cybage.gms.app.model.Topic;

@Service
public class FaqServiceImpl implements IFaqService{
	@Value("${gms.faq.url}")
	private String faqUrl;
	@Autowired
	private RestTemplate restTemplate;
	@Override
	public ResponseEntity<?> findAll() {
		RequestEntity<?> request = null;
		try {
			request = RequestEntity.get(new URI(faqUrl+"/all"))
					.accept(MediaType.APPLICATION_JSON).build();
		} catch (URISyntaxException e) {
			//request =
			return restTemplate.exchange(request, Faq[].class);
		}
		return restTemplate.exchange(request, Faq[].class);
	}

	@Override
	public ResponseEntity<?> findById(Integer id) {
		RequestEntity<?> request = null;
		try {
			request = RequestEntity.get(new URI(faqUrl+"/id/"+id))
					.accept(MediaType.APPLICATION_JSON).build();
		} catch (URISyntaxException e) {
			//request =
			return restTemplate.exchange(request, Faq.class);
		}
		return restTemplate.exchange(request, Faq.class);
	}

	@Override
	public ResponseEntity<?> save(Faq faq) {
		RequestEntity<?> request = null;
		try {
			request = RequestEntity.post(new URI(faqUrl+"/add"))
					.accept(MediaType.APPLICATION_JSON).body(faq);
		} catch (URISyntaxException e) {
			//request =
			return restTemplate.exchange(request, Faq.class);
		}
		return restTemplate.exchange(request, Faq.class);
	}

	@Override
	public ResponseEntity<?> deleteById(Integer faqId) {
		RequestEntity<?> request = null;
		try {
			request = RequestEntity.delete(new URI(faqUrl+"/delete/"+faqId))
					.accept(MediaType.APPLICATION_JSON).build();
		} catch (URISyntaxException e) {
			//request =
			return restTemplate.exchange(request, Faq.class);
		}
		return restTemplate.exchange(request, Faq.class);
	}



	@Override
	public ResponseEntity<?> findByTopicName(String topicName) {
		RequestEntity<?> request = null;
		try {
			request = RequestEntity.get(new URI(faqUrl+"/topic/"+topicName))
					.accept(MediaType.APPLICATION_JSON).build();
		} catch (URISyntaxException e) {
			//request =
			return restTemplate.exchange(request, Faq[].class);
		}
		return restTemplate.exchange(request, Faq[].class);
	}

	@Override
	public ResponseEntity<?> update(Integer id, Faq faq) {
		RequestEntity<?> request = null;
		try {
			request = RequestEntity.put(new URI(faqUrl+"/update/"+id))
					.accept(MediaType.APPLICATION_JSON).body(faq);
		} catch (URISyntaxException e) {
			//request =
			return restTemplate.exchange(request, Faq.class);
		}
		return restTemplate.exchange(request, Faq.class);
	}

}
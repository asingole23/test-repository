package com.cybage.gms.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cybage.gms.app.model.Faq;
import com.cybage.gms.app.service.IFaqService;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/faq")
public class FaqController {
	@Autowired
	private IFaqService faqService;
	@GetMapping("/all")
	public ResponseEntity<?> allFaq(){
		return faqService.findAll(); 
	}
	@GetMapping("/id/{id}")
	public ResponseEntity<?> byId(@PathVariable("id") Integer id) {
		System.out.println(id);
		return faqService.findById(id);
	}
	@GetMapping("/topic/{name}")
	public ResponseEntity<?> byTopicName(@PathVariable("name") String name){
		System.out.println(name);
		return faqService.findByTopicName(name);
	}
	@PostMapping("/add")
	public ResponseEntity<?> saveFaq(@RequestBody Faq faq) {
		return faqService.save(faq);
	}
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteFaq(@PathVariable("id")Integer id) {
		return faqService.deleteById(id);
	}
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateFaq(@PathVariable("id")Integer id, @RequestBody Faq faq) {
		return faqService.update(id,faq);
	}
	
}

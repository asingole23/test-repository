package com.cybage.gms.app.dto;

import java.time.LocalDate;

import com.cybage.gms.app.model.Address;
import com.cybage.gms.app.model.Gender;

import lombok.Data;

@Data
public class UserDTO {
	
	private Integer userId;
	private String firstName;
	private String lastName;
	private String email;
	private LocalDate dob;
	private String phoneNo;
	private Gender gender;
	private Address address;
	private String password;
	private String username;
	private Boolean isEnabled;
		
	public UserDTO() {
		System.out.println("in user dto constructor");
	}	

}

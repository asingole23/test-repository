package com.cybage.gms.app.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;

import lombok.Data;

//Address of User 
@Data

//value type -> separate table will not be created
@Embeddable
public class Address {
	@Column(name = "house_no", length = 20)
	@NotBlank(message = "House number can not be empty")
	private String houseNo;

	@Column(length = 50)
	@NotBlank(message = "Street name can not be empty")
	private String street;

	@Enumerated(EnumType.STRING)
	private City city;

	public Address() {
		System.out.println("in address constructor");
	}
	
	//Constructor
	public Address(String houseNo, String street, City city) {
		super();
		this.houseNo = houseNo;
		this.street = street;
		this.city = city;
	}
}

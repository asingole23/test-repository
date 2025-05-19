package com.cybage.gms.app.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

// Complain
@Data
@Entity
@Table(name = "complain_table")
public class Complain {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(length = 50)
	@NotBlank(message = "Complain Subject can not be empty")
	private String complainSubject;

	@Column(length = 50)
	@NotBlank(message = "Complain Information can not be empty")
	private String complainInformation;

	@Enumerated(EnumType.STRING)
	private Status complainStatus;

	private LocalDateTime createdOn;

	private LocalDateTime accepetdOn;

	private LocalDateTime openedOn;

	private LocalDate expectedToResolve;

	private LocalDateTime closedOn;

	private Boolean resolved = false;

	private Boolean remainder = false;

	// User mapping i.e. citizen many to one relation
	@ManyToOne
	@JoinColumn(name = "citizen_id")
	@JsonIgnore
	@JsonIgnoreProperties("complains")
	private User citizen;

	// Department mapping many to one relation
	@ManyToOne
	@JoinColumn(name = "department_id")
	@JsonIgnore
	@JsonIgnoreProperties("complains")
	private Department department;

	// OpenComplain mapping one to one relation
	@JsonIgnore
	@JsonIgnoreProperties
	@OneToOne(mappedBy = "complain", cascade = CascadeType.ALL, orphanRemoval = true)
	private OpenComplain openComplain;

}

package com.cybage.gms.app.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "open_complain_table")
public class OpenComplain {
	
	@Id
	@GeneratedValue
	
	private Integer id;
		
	private LocalDateTime createdOn;
	
	private String reason;
	
	private String actionTaken;
	
	private String actionDescription;
	
	private LocalDateTime actionTakenDate;
		
	@OneToOne
	@JoinColumn(name = "complain_id")
	private Complain complain;

}

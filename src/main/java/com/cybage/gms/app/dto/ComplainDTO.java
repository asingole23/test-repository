package com.cybage.gms.app.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.cybage.gms.app.model.Status;

import lombok.Data;

@Data
public class ComplainDTO {
	private Integer id;
	private String complainSubject;
	private String complainInformation;
	private Status complainStatus;
	private LocalDateTime createdOn;
	private LocalDateTime accepetdOn;
	private LocalDateTime openedOn;
	private LocalDate expectedToResolve;
	private LocalDateTime closedOn;
	private Boolean resolved;
	private Boolean remainder;	
}

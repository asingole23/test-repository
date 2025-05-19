package com.cybage.gms.app.dto;

import java.time.LocalDateTime;

import com.cybage.gms.app.model.Complain;

import lombok.Data;

@Data
public class OpenComplainDTO {
	private Integer id;
	private LocalDateTime createdOn;
	private String reason;
	private String actionTaken;
	private String actionDescription;
	private LocalDateTime actionTakenDate;
	private Complain complain;
}

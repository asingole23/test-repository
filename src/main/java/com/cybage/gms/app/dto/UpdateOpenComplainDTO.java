package com.cybage.gms.app.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UpdateOpenComplainDTO {
	private Integer id;
	private LocalDateTime createdOn;
	private String reason;
	private String actionTaken;
	private String actionDescription;
	private LocalDateTime actionTakenDate;
}

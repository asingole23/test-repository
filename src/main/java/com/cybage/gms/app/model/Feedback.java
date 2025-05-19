package com.cybage.gms.app.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Feedback extends BaseEntity {
	@NotNull
	private String name;
	@NotNull
	private String phoneNumber;
	@NotNull
	private String email;
	@ManyToOne
	@JoinColumn(
			name = "topic_id"
			)
	@NotNull
	private Topic topic;
	@NotNull
	private String description;
	
	private LocalDateTime timestamp;
}
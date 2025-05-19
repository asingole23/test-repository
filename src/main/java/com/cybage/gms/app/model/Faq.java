package com.cybage.gms.app.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Faq extends BaseEntity {
	@ManyToOne
	@JoinColumn(
			name = "topic_id"
			)
	@NotNull
	private Topic topic;
	@NotNull
	private String question;
	@NotNull
	private String answer;
}
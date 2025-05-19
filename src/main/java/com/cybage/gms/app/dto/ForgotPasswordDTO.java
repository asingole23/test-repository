package com.cybage.gms.app.dto;

import lombok.Data;

@Data
public class ForgotPasswordDTO {
	private Integer otp;
	private String newPassword;
}

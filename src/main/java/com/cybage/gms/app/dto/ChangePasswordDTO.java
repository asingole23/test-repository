package com.cybage.gms.app.dto;

import lombok.Data;

@Data
public class ChangePasswordDTO {
	private UserDTO userDTO;
	private PasswordDTO passwordDTO;
}

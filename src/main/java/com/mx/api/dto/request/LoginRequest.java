package com.mx.api.dto.request;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class LoginRequest {
	@NotBlank
	private String username;

	@NotBlank
	private String password;
}

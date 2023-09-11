package com.mx.api.dto.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private Long id;
	private String username;
	private String claveEstatusUsuario;
	private List<String> roles;
	private String timeToken;
	
	public JwtResponse(String accessToken, Long id, String username, List<String> roles, String claveEstatusUsuario, String timeToken) {
		this.token = accessToken;
		this.id = id;
		this.username = username;
		this.roles = roles;
		this.claveEstatusUsuario = claveEstatusUsuario;
		this.timeToken = timeToken;
	}
}

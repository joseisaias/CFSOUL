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
	private LoginResponse info;
	
	public JwtResponse(String accessToken, Long id, String username, List<String> roles, String claveEstatusUsuario, String timeToken, LoginResponse info) {
		this.token = accessToken;
		this.id = id;
		this.username = username;
		this.roles = roles;
		this.claveEstatusUsuario = claveEstatusUsuario;
		this.timeToken = timeToken;
		this.info = info;
	}
}

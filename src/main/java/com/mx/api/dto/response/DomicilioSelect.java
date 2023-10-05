package com.mx.api.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DomicilioSelect {

	private Long idCatDomicilio;
	
	private String cp;
	
	private String estado;
	
	private String municipio;
}

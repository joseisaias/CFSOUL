package com.mx.api.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InputSelectResponse {

	private Long idCat;
	
	private String clave;
	
	private String descripcion;
	
	private Integer indEstatus;
}

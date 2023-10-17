package com.mx.api.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClienteEmpleadoLoginResponse {
	
	private Long idCliente;
	private String nombreCliente;
	private Long idEmpleado;
	
}

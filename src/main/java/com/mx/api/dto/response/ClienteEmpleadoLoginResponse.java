package com.mx.api.dto.response;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClienteEmpleadoLoginResponse {
	
	private Long idCliente;
	private String nombreCliente;
	private Long idEmpleado;
	private BigDecimal montoMaximoPrestamo;
	
}

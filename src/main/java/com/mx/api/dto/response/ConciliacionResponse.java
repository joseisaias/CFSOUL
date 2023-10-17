package com.mx.api.dto.response;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ConciliacionResponse {

	private String nombreCompleto;
	private Long idCliente;
	private BigDecimal monto;
	private BigDecimal totalCredito;
	
	private Long idConciliacion;
	private String status;
	private String fechaPago;
}

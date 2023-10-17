package com.mx.api.dto.response;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreditosEmpleado {

	private Long idCredito;
	
	private BigDecimal totalCredito;
	
	private BigDecimal montoSolicitado;
	
	private String fechaPrimerPago;
	
	private String fechaUltimoPago;
	
	private String totalPagos;
	
}

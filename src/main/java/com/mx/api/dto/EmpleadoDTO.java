package com.mx.api.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpleadoDTO {
	
	private Long idEmpleado;
	
	private Long idPersona;
	
	private Long idCliente;
	
	private Long idCredito;
	
	private Long idCuentaBancaria;
	
	private Long idDomicilio;

	private String nombreCompleto;
	
	private String rfc;
	
	private String montoMaximoPrestamo;
	
	private String puesto;
	
	private String email;
	
	private String telefono;
	
	private String totalCredito;
	
	private String montoTotalPrestamo;
	
	private String indStatusString;
	
	private Integer numPago;
	private Integer numPagos;
	private BigDecimal salario;
	private BigDecimal cuota;
	private BigDecimal pagoTotal;
	private String fechaPago;
}

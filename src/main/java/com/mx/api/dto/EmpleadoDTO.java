package com.mx.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpleadoDTO {
	
	private Long idEmpleado;
	
	private Long idPersona;
	
	private Long idCliente;
	
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
}

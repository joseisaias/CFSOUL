package com.mx.api.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Empleado extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7344648638434421330L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long idEmpleado;
	
	@Column
	private Long idPersona;
	
	@Column
	private Long idCliente;
	
	@Column
	private Long idEstatusEmpleado;
	
	@Column
	private String puesto;
	
	@Column
	private BigDecimal salario;
	
	@Column
	private BigDecimal montoMaximoPrestamo;
	

}

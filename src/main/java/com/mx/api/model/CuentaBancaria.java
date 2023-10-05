package com.mx.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
public class CuentaBancaria extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1604846890310207488L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long idCuentaBancaria;
	
	@Column
	private Long idEmpleado;
	
	@Column
	private String clabeInterbancaria;
	
	@Column
	private String numeroCuenta;
	
	
}

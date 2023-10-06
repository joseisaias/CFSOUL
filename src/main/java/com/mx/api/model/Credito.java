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
public class Credito extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5677996750530296780L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long idCredito;
	@Column
	private Long idEmpleado;
	@Column
	private Long idEstatusCredito;
	@Column
	private Long idTipoPago;
	
	@Column
	private Long numPagos;
	
	@Column
	private BigDecimal montoSolicitado;
	@Column
	private BigDecimal intereses;
	@Column
	private BigDecimal pagoTotal;
	
}

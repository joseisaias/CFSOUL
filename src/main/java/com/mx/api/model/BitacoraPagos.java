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
public class BitacoraPagos extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5677996750530296780L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long idBitacoraPagos;
	
	@Column
	private Long idCredito;
	
	@Column
	private Long idEstatusPago;
	
	@Column
	private Long numPago;
	
	@Column
	private Long totalDias;
	
	@Column(name = "fecha_pago")
	private String fechaPago;
	
	@Column
	private BigDecimal pagoCapital;
	
	@Column
	private BigDecimal cuota;
	
	@Column
	private BigDecimal pagoInteres;
	
	@Column
	private BigDecimal saldoFinal;
	
	@Column
	private BigDecimal saldoInicial;
	
	
}

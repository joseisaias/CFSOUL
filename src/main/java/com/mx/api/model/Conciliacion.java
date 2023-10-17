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
@Setter
@Getter
public class Conciliacion extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 351768318819802254L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long idConciliacion;
	
	@Column
	private String fecha_conciliacion;
	
	@Column
	private BigDecimal monto_conciliacion;
	
}

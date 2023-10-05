package com.mx.api.model;

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
public class Domicilio extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2148552677248486605L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_domicilio")
	private Long idDomicilio;

    @Column(name = "id_tipo_domicilio")
	private Long idTipoDomicilio;

    @Column(name = "id_cat_domicilio")
	private Long idCatDomicilio;

    @Column(name = "cp")
	private String cp;
    
    @Column(name = "calle")
	private String calle;

    @Column(name = "numero_exterior")
	private String numeroExterior;

    @Column(name = "numero_interior")
	private String numeroInterior;

    @Column(name = "colonia")
	private String colonia;
    
}

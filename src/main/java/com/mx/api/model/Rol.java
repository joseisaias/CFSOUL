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
public class Rol extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4471259767718586019L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private Long idRol;
    
    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "clave")
    private String claveRol;
    
}

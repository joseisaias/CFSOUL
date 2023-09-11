package com.mx.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Usuario extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5545075528272675740L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_usuario")
	private Long idUsuario;
	
	private String usuario;
	
	private String password;
	
	@ManyToOne
	@JoinColumn(name="id_estatus_usuario")
	private CatDetalle estatus;
	
	
}

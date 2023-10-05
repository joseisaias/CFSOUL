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
public class Usuario {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_usuario")
	private Long idUsuario;
	
	private String usuario;
	
	private String password;
	
	@Column(name="token_password")
	private String tokenPassword;
	
	@Column(name="fecha_password")
	private String fechaPassword;
	
	@ManyToOne
	@JoinColumn(name="id_persona")
	private Persona persona;
	
	@ManyToOne
	@JoinColumn(name="id_estatus_usuario")
	private CatDetalle estatus;
	
	
}

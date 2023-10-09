package com.mx.api.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "usuario_rol")
public class UsuarioRol implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario_rol")
    private Integer idUsuarioRol;
    
    @JoinColumn(name = "id_usuario")
    @ManyToOne
    private Usuario idUsuario;
    
    @ManyToOne
	@JoinColumn(name="id_rol")
    private Rol idRol;

	public UsuarioRol(Usuario idUsuario, Rol idRol) {
		super();
		this.idUsuario = idUsuario;
		this.idRol = idRol;
	}
    
    
}

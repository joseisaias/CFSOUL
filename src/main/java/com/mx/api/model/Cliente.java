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
public class Cliente extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
	private Long idCliente;
	
	@Column(name = "id_usuario")
	private Long idUsuario;
	
	@Column(name = "id_tipo_persona")
	private Long idTipoPersona;
	
	@Column(name = "id_tipo_sociedad")
	private Long idTipoSociedad;
	
	@Column(name = "id_actividad")
	private Long idActividad;
	
	@Column(name = "id_establecimiento")
	private Long idEstablecimiento;
	
	@Column(name = "id_giro")
	private Long idGiro;
	
	private String rfc;
	
	private String telefono;
	
	private String email;
	
	@Column(name = "razon_social")
	private String razonSocial;
	
	private String nombre;
	
	@Column(name = "apellido_paterno")
	private String apellidoPaterno;
	
	@Column(name = "apellido_materno")
	private String apellidoMaterno;
	
	@Column(name = "fecha_inicio_operacion")
	private String fechaInicioOperacion;
	
	@Column(name = "fecha_inicio_registro")
	private String fechaInicioRegistro;
	
	@Column(name = "pagina_web")
	private String paginaWeb;
}

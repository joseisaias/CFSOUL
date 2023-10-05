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

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
public class PersonaDomicilio {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_persona_domicilio")
	private Long idPersonaDomicilio;
	
	@Column(name ="id_domicilio")
	private Long idDomicilio;
	
	@Column(name ="id_persona")
	private Long idPersona;

	public PersonaDomicilio(Long idDomicilio, Long idPersona) {
		this.idDomicilio = idDomicilio;
		this.idPersona = idPersona;
	}
}

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

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDomicilio {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente_domicilio")
	private Long idClienteDomicilio;
	
	@Column(name = "id_domicilio")
	private Long idDomicilio;
	
	@Column(name = "id_cliente")
	private Long idCliente;

	public ClienteDomicilio(Long idDomicilio, Long idCliente) {
		super();
		this.idDomicilio = idDomicilio;
		this.idCliente = idCliente;
	}
	
	
}

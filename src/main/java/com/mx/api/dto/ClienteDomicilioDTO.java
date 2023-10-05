package com.mx.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteDomicilioDTO {

	private Long idClienteDomicilio;
	private Long idDomicilio;
	private Long idTipoDomicilio;
	private String claveTipoDomicilio;
	private String descTipoDomicilio;
	private Long idCliente;

	
}

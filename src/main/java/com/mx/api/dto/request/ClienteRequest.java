package com.mx.api.dto.request;

import com.mx.api.model.Cliente;
import com.mx.api.model.Domicilio;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteRequest {

	private Cliente cliente;
	
	private Domicilio domicilioFiscal;
	
	private Domicilio domicilioComercial;
}

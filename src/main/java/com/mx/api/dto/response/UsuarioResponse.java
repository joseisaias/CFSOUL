package com.mx.api.dto.response;

import com.mx.api.model.CuentaBancaria;
import com.mx.api.model.Domicilio;
import com.mx.api.model.Persona;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponse {
	private Long idUsuario;
	private Persona persona;
	private Domicilio domicilio;
	private CuentaBancaria cuentaBancaria;
	
}

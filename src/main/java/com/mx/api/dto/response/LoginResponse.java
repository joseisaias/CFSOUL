package com.mx.api.dto.response;

import java.util.List;

import com.mx.api.model.Empleado;
import com.mx.api.model.Persona;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginResponse {

	private Long idCliente;
	
	private List<Empleado> empleadoCientes;
	
	private Persona persona;
}

package com.mx.api.dto.response;

import java.util.List;

import com.mx.api.model.Persona;
import com.mx.api.model.Rol;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginResponse {

	private Long idCliente;
	private Persona persona;
	private List<ClienteEmpleadoLoginResponse> clientes;
	private List<ClienteEmpleadoLoginResponse> empleados;
	private ClienteEmpleadoLoginResponse empleadoSelect;
	private ClienteEmpleadoLoginResponse clienteSelect;
	private Rol rolSelect;
}

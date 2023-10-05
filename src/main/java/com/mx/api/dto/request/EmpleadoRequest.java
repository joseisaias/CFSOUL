package com.mx.api.dto.request;

import com.mx.api.model.CuentaBancaria;
import com.mx.api.model.Domicilio;
import com.mx.api.model.Empleado;
import com.mx.api.model.Persona;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmpleadoRequest {

	private Persona persona;
	
	private Empleado empleado;
	
	
	private Domicilio domicilio;
	
	
	
	
	
	private CuentaBancaria cuentaBancaria;
	
	
}

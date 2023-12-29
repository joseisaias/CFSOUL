package com.mx.api.dto.request;

import com.mx.api.model.Cliente;
import com.mx.api.model.Rol;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRequest {

	private Cliente cliente;
	private Rol rol;
}

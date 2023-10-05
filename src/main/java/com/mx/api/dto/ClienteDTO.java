package com.mx.api.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO {

	private Long idCliente;
	private Long idTipoPersona;
	private String razonSocial;
	private String nombreCompleto;
	private String rfc;
	private String tipoSociedad;
	private String indStatusString;
	private List<ClienteDomicilioDTO> clienteDomicilio;
}

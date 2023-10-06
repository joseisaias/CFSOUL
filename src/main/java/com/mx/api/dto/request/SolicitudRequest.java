package com.mx.api.dto.request;

import java.util.List;

import com.mx.api.model.BitacoraPagos;
import com.mx.api.model.Credito;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SolicitudRequest {

	private Credito credito;
	
	private List<BitacoraPagos> tablaAmortizacion;
}

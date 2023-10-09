package com.mx.api.dto.response;

import java.math.BigDecimal;
import java.util.List;

import com.mx.api.dto.EmpleadoDTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmpleadoSeguimientoResponse {

	private List<EmpleadoDTO> empleados;
	
	private BigDecimal montoDeudaTotal;
	
	
}

package com.mx.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mx.api.dao.EmpleadoDao;
import com.mx.api.dto.commons.GenericResponseDTO;
import com.mx.api.dto.request.SolicitudRequest;
import com.mx.api.model.BitacoraPagos;
import com.mx.api.repository.BitacoraPagosRepository;
import com.mx.api.repository.CreditoRepository;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Api(value = "Servicios para Solicitud de credito", tags = "CFSOUL API Rest")
@RequestMapping("/api/solicitud")
@Slf4j
public class SolicitudController extends BaseController {
	
	@Autowired
	private CreditoRepository creditoRepository;
	
	@Autowired
	private BitacoraPagosRepository bitacoraPagosRepository;
	
	@Autowired
	private EmpleadoDao empleadoDao;

	@PostMapping("/guardarSolicitud")
	public ResponseEntity<?> guardarSolicitud(@RequestBody SolicitudRequest dto) {
		creditoRepository.save(dto.getCredito());
		for (BitacoraPagos item : dto.getTablaAmortizacion()) {
			item.setIdCredito(dto.getCredito().getIdCredito());
			bitacoraPagosRepository.save(item);
		}
		return ResponseEntity.ok(new GenericResponseDTO<>(SUCCESS, HTTP_SUCCESS, null, null, SUCCESS_MESSAGE, "Exito"));
	}
	
	@GetMapping("/{idEmpleado}/obtenerMontoMaximo")
	public ResponseEntity<?> obtenerMontoMaximo(@PathVariable Long idEmpleado){
		try {
			return ResponseEntity.ok(new GenericResponseDTO<>(SUCCESS, HTTP_SUCCESS, null, null, SUCCESS_MESSAGE, empleadoDao.obtenerMontoMaximo(idEmpleado)));
		}catch(Exception ex) {
			log.error("Ocurrio un error en el registro", ex);
			return ResponseEntity.ok(new GenericResponseDTO<>(ERROR, HTTP_NOT_FOUND, null, ERROR_MESSAGE, null, null));
		}
	}
	
	
}

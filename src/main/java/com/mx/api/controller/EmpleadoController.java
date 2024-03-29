package com.mx.api.controller;

import java.util.List;

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
import com.mx.api.dto.request.EmpleadoRequest;
import com.mx.api.repository.BitacoraPagosRepository;
import com.mx.api.service.EmpleadoService;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/empleado")
@Slf4j
public class EmpleadoController extends BaseController{

	@Autowired
	private EmpleadoDao empleadoDao;
	
	@Autowired
	private EmpleadoService empleadoService;
	
	@Autowired
	private BitacoraPagosRepository amortizacionRepository;
	
	@GetMapping("/{idCliente}/getEmpleadosByIdCliente")
	public ResponseEntity<?> getEmpleadosByIdCliente(@PathVariable Long idCliente){
		try {
			return ResponseEntity.ok(new GenericResponseDTO<>(SUCCESS, HTTP_SUCCESS, null, null, SUCCESS_MESSAGE, empleadoDao.getEmpleadosByIdCliente(idCliente)));
		}catch(Exception ex) {
			log.error("Ocurrio un error en el registro", ex);
			return ResponseEntity.ok(new GenericResponseDTO<>(ERROR, HTTP_NOT_FOUND, null, ERROR_MESSAGE, null, null));
		}
	}
	
	@GetMapping("/{idEmpleado}/getCreditosByEmpleado")
	public ResponseEntity<?> getCreditosByEmpleado(@PathVariable Long idEmpleado){
		try {
			return ResponseEntity.ok(new GenericResponseDTO<>(SUCCESS, HTTP_SUCCESS, null, null, SUCCESS_MESSAGE, empleadoService.getCreditosByEmpleado(idEmpleado)));
		}catch(Exception ex) {
			log.error("Ocurrio un error en el registro", ex);
			return ResponseEntity.ok(new GenericResponseDTO<>(ERROR, HTTP_NOT_FOUND, null, ERROR_MESSAGE, null, null));
		}
	}
	
	@GetMapping("/{idCliente}/{fechaPago}/getEmpleadosByIdClienteSeguimiento")
	public ResponseEntity<?> getEmpleadosByIdClienteSeguimiento(@PathVariable Long idCliente, @PathVariable String fechaPago){
		try {
			return ResponseEntity.ok(new GenericResponseDTO<>(SUCCESS, HTTP_SUCCESS, null, null, SUCCESS_MESSAGE, 
					empleadoDao.getEmpleadosByIdClienteSeguimiento(idCliente, 124L, fechaPago.replace("-", "/"))));
		}catch(Exception ex) {
			log.error("Ocurrio un error en el registro", ex);
			return ResponseEntity.ok(new GenericResponseDTO<>(ERROR, HTTP_NOT_FOUND, null, ERROR_MESSAGE, null, null));
		}
	}
	
	@GetMapping("/{idCredito}/getTablaAmortizacion")
	public ResponseEntity<?> getTablaAmortizacion(@PathVariable Long idCredito) {
		return ResponseEntity.ok(new GenericResponseDTO<>(SUCCESS, HTTP_SUCCESS, null, null, SUCCESS_MESSAGE, 
				amortizacionRepository.findByidCredito(idCredito)));
	}

	@GetMapping("/{idPersona}/getEmpleadoById")
	public ResponseEntity<?> getEmpleadoById(@PathVariable Long idPersona) {
		return ResponseEntity.ok(new GenericResponseDTO<>(SUCCESS, HTTP_SUCCESS, null, null, SUCCESS_MESSAGE, empleadoService.getEmpleadoById(idPersona)));
	}

	
	@GetMapping("/{idEmpleado}/activaInactivaEmpleado")
	public ResponseEntity<?> activaInactivaEmpleado(@PathVariable Long idEmpleado) {
		empleadoService.activaInactivaEmpleado(idEmpleado);
		return ResponseEntity.ok(new GenericResponseDTO<>(SUCCESS, HTTP_SUCCESS, null, null, SUCCESS_MESSAGE, "Exito"));
	}
	
	@PostMapping("/saveEmpleado")
	public ResponseEntity<?> save(@RequestBody EmpleadoRequest r){
		empleadoService.guardar(r);
		return ResponseEntity.ok(new GenericResponseDTO<>(SUCCESS, HTTP_SUCCESS, null, null, SUCCESS_MESSAGE, "GUARDADO EXITOSAMENTE"));
	}
	
	@PostMapping("/guardarEmpleadosExcel")
	public ResponseEntity<?> save(@RequestBody List<EmpleadoRequest>  r){
		return ResponseEntity.ok(new GenericResponseDTO<>(SUCCESS, HTTP_SUCCESS, null, null, SUCCESS_MESSAGE, empleadoService.guardarExcel(r)));
	}
 }

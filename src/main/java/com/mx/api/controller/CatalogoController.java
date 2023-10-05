package com.mx.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mx.api.dto.commons.GenericResponseDTO;
import com.mx.api.service.CatalogoService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/catalogo")
public class CatalogoController extends BaseController {

	@Autowired
	private CatalogoService catalogoService;
	
	@GetMapping("/{clave}/getCatSelect")
	public ResponseEntity<?> getCatSelect(@PathVariable String clave){
		return ResponseEntity.ok(new GenericResponseDTO<>(SUCCESS, HTTP_SUCCESS, null, null, SUCCESS_MESSAGE, catalogoService.getCatSelect(clave)));
	}
	
	@GetMapping("/{cp}/getCatDomicilioByCp")
	public ResponseEntity<?> getCatDomicilioByCp(@PathVariable String cp){
		return ResponseEntity.ok(new GenericResponseDTO<>(SUCCESS, HTTP_SUCCESS, null, null, SUCCESS_MESSAGE, catalogoService.getCatDomicilioByCp(cp)));
	}
	
	@GetMapping("/{clave}/getCatDetalleByClave")
	public ResponseEntity<?> getCatDetalleByClave(@PathVariable String clave){
		return ResponseEntity.ok(new GenericResponseDTO<>(SUCCESS, HTTP_SUCCESS, null, null, SUCCESS_MESSAGE, catalogoService.getCatDetalleByClave(clave)));
	}
}

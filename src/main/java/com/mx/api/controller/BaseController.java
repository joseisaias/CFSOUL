package com.mx.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.mx.api.dto.commons.GenericResponseDTO;
import com.mx.api.jwt.services.*;
public class BaseController {
	
	public static final boolean SUCCESS = true;
	
	public static final boolean ERROR = false;
	
	public static final int ACTIVO = 1;
	public static final int INACTIVO = 0;
	
	public static final int HTTP_SUCCESS = 200;
	
	public static final int HTTP_BAD_REQUEST = 400;
	public static final int HTTP_AUTH_FAILURE = 401;
	public static final int HTTP_CONFLICT = 409;
	public static final int HTTP_NOT_FOUND = 409;
	public static final int HTTP_APP_FAILURE = 409;
	public static final int HTTP_APP_NOT_IMPL = 409;
	
	public static final String SUCCESS_MESSAGE = "SERVICIO EJECUTADO EXITOSAMENTE.";
	public static final String ERROR_MESSAGE = "Ocurrió un error al realizar la petición";
	
	public UserDetailsSegImpl getUsuario() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsSegImpl userDetails = (UserDetailsSegImpl) authentication.getPrincipal();
		return userDetails;
	}
	
	public Long getIdUsuario() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsSegImpl userDetails = (UserDetailsSegImpl) authentication.getPrincipal();
		return userDetails.getId();
	}
	
	public ResponseEntity<?> notFoundError(String message){
		return new ResponseEntity<>(new GenericResponseDTO<>(ERROR, HTTP_NOT_FOUND, null,
				message, null, null),HttpStatus.OK);
	}
	
	public ResponseEntity<?> internalServerError(Exception e){
		return new ResponseEntity<>(new GenericResponseDTO<>(ERROR, HTTP_APP_FAILURE, null,
				e.getMessage(), null, null),HttpStatus.OK);
	}

}

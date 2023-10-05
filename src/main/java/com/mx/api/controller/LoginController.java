package com.mx.api.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mx.api.dto.commons.GenericResponseDTO;
import com.mx.api.dto.request.LoginRequest;
import com.mx.api.dto.response.JwtResponse;
import com.mx.api.jwt.JwtUtils;
import com.mx.api.jwt.services.UserDetailsSegImpl;
import com.mx.api.model.Usuario;
import com.mx.api.repository.EmpleadoRepository;
import com.mx.api.repository.RolRepository;
import com.mx.api.repository.UserRepository;
import com.mx.api.util.cons.EstatusUsuarioEnum;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Api(value = "Servicios para Login", tags = "CFSOUL LOGIN API Rest", description = "Servicios para login ")
@RequestMapping("/api/login")
@Slf4j
public class LoginController extends BaseController{
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RolRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;
	
	@Autowired
	private EmpleadoRepository empleadoRepository;
	
	@Value("${app.security.expiration}")
	private String jwtExpirationMs;
	
	
	@ApiOperation(value = "Servicio de autenticación")
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(
			@ApiParam(value =  "usuario email  (formato: mail@mail.com), password String (Contraseña con la que ingresas al sistema)", required = true, name = "user")
			@Valid @RequestBody LoginRequest loginRequest) {
		String password = encoder.encode(loginRequest.getPassword());
		System.out.println(password);
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
				);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsSegImpl userDetails = (UserDetailsSegImpl) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());
		if (userDetails.getClave().equals(EstatusUsuarioEnum.EST_US_ACTIVO.name()) 
				|| userDetails.getClave().equals(EstatusUsuarioEnum.EST_US_NUEVO.name())
		) {
			
			
			return ResponseEntity.ok(new JwtResponse(userDetails.getPersona(), jwt, userDetails.getId(), userDetails.getUsername(), roles,
					userDetails.getClave(), jwtExpirationMs, empleadoRepository.findByIdPersonaAndIndStatus(userDetails.getPersona().getIdPersona(), ACTIVO)));
		} else {
			return new ResponseEntity<>(ERROR_MESSAGE, HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@PostMapping("/actualizaContrasenia")
	public ResponseEntity<?> actualizaContrasenia(@RequestBody LoginRequest dto) {
		try {
		Usuario u = userRepository.findById(dto.getIdUsuario())
				.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
		String fecha = new SimpleDateFormat("ddMMyyyy").format(new Date());
		if(u.getFechaPassword().equals(fecha) && u.getTokenPassword().equals(dto.getTokenPassword())) {
			String password = encoder.encode(dto.getPassword());
			u.setPassword(password);
			u.setTokenPassword("");
			u.setFechaPassword("");
			userRepository.save(u);
			return ResponseEntity.ok(new GenericResponseDTO<>(SUCCESS, HTTP_SUCCESS, null, null, SUCCESS_MESSAGE, "Contraseña modificada"));
		}
		return ResponseEntity.ok(new GenericResponseDTO<>(ERROR, HTTP_BAD_REQUEST, null, ERROR_MESSAGE, "", "Token invalido"));
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResponseEntity.ok(new GenericResponseDTO<>(ERROR, HTTP_BAD_REQUEST, null, ERROR_MESSAGE, "", "Token invalido"));
		}
		
	}
	
	
}

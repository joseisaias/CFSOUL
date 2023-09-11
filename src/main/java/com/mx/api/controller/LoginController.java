package com.mx.api.controller;

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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mx.api.dto.request.LoginRequest;
import com.mx.api.dto.response.JwtResponse;
import com.mx.api.jwt.JwtUtils;
import com.mx.api.jwt.services.UserDetailsSegImpl;
import com.mx.api.repository.RolRepository;
import com.mx.api.repository.UserRepository;
import com.mx.api.util.cons.EstatusUsuarioEnum;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/login")
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
	
	@Value("${app.jwtExpirationMs}")
	private String jwtExpirationMs;
	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
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
			return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), roles,
					userDetails.getClave(),jwtExpirationMs));
		} else {
			return new ResponseEntity<>(ERROR_MESSAGE, HttpStatus.BAD_REQUEST);
		}
		
	}
}

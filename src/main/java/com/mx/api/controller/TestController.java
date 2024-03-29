package com.mx.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mx.api.dto.TestDTO;
import com.mx.api.dto.commons.GenericResponseDTO;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController extends BaseController{

	@Autowired
	PasswordEncoder encoder;
	
	@Value("${spring.datasource.url}")
	private String var1;
	@Value("${spring.datasource.username}")
	private String var2;
	@Value("${spring.datasource.password}")
	private String var3;
	@Value("${app.security.secret}")
	private String var4;
	
	@GetMapping(value="/getObtenerPassword")
	public ResponseEntity<?> password() {
		String password = encoder.encode("1234");
		System.out.println(password);
		
		return new ResponseEntity<>(new GenericResponseDTO<>(
				SUCCESS, 
				HTTP_SUCCESS, 
				null,
				null, 
				"Prueba exitosa \n " + var1 + " \n " + var2 + " \n " + var3 + " var4 " + var4, 
				password) , HttpStatus.OK);
	}
	
	@PostMapping(value="/postObtenerPassword")
	public ResponseEntity<?> password(@RequestBody TestDTO dto) {
		String password = encoder.encode(dto.getPassword());
		System.out.println(password);
		
		return new ResponseEntity<>(new GenericResponseDTO<>(
				SUCCESS, 
				HTTP_SUCCESS, 
				null,
				null, 
				"Prueba exitosa", 
				password) , HttpStatus.OK);
	}
}

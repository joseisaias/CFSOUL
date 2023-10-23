package com.mx.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mx.api.dao.LoginDao;
import com.mx.api.dto.commons.GenericResponseDTO;
import com.mx.api.dto.response.JwtResponse;
import com.mx.api.dto.response.LoginResponse;
import com.mx.api.dto.response.UsuarioResponse;
import com.mx.api.jwt.JwtUtils;
import com.mx.api.jwt.services.UserDetailsSegImpl;
import com.mx.api.model.Rol;
import com.mx.api.model.Usuario;
import com.mx.api.repository.RolRepository;
import com.mx.api.service.UsuarioService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Api(value = "Servicios para Usuario", tags = "CFSOUL Usuario API Rest", description = "Servicios para usuario ")
@RequestMapping("/api/usuario")
@Slf4j
public class UsuarioController extends BaseController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	JwtUtils jwtUtils;
	
	
	@Value("${app.security.expiration}")
	private String jwtExpirationMs;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	RolRepository roleRepository;
	
	@Autowired
	private LoginDao loginDao;

	@GetMapping("/{idUsuario}/obtenerUsuario")
	public ResponseEntity<?> obtenerUsuario(@PathVariable Long idUsuario){
		try {
			return ResponseEntity.ok(new GenericResponseDTO<>(SUCCESS, HTTP_SUCCESS, null, null, SUCCESS_MESSAGE, usuarioService.obtenerUsuario(idUsuario)));
		}catch(Exception ex) {
			log.error("Ocurrio un error en el registro", ex);
			return ResponseEntity.ok(new GenericResponseDTO<>(ERROR, HTTP_NOT_FOUND, null, ERROR_MESSAGE, null, null));
		}
	}
	
	@ PostMapping("/saveUsuario")
	public ResponseEntity<?> saveUsuario(@RequestBody UsuarioResponse usuarioResponse){
		try {
			usuarioService.guardar(usuarioResponse);
			return ResponseEntity.ok(new GenericResponseDTO<>(SUCCESS, HTTP_SUCCESS, null, null, SUCCESS_MESSAGE, "Usuario Guardado"));
		}catch(Exception ex) {
			log.error("Ocurrio un error en el registro", ex);
			return ResponseEntity.ok(new GenericResponseDTO<>(ERROR, HTTP_NOT_FOUND, null, ERROR_MESSAGE, null, null));
		}
	}
	
	@PostMapping("/editaUsuarioClientePass")
	public ResponseEntity<?> editaUsuarioClientePass(@RequestBody Usuario request) {
		try {
			String password = request.getPassword();
			request = usuarioService.editaUsuarioClientePass(request);
			if(request!=null) {
				Authentication authentication = authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(request.getUsuario(), password)
						);
				SecurityContextHolder.getContext().setAuthentication(authentication);
				String jwt = jwtUtils.generateJwtToken(authentication);
				UserDetailsSegImpl userDetails = (UserDetailsSegImpl) authentication.getPrincipal();		
				List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());
				List<Rol> listRoles = new ArrayList<>();
				for (String r : roles) {
					listRoles.add(roleRepository.findByClaveRol(r).get());
				}
				
				LoginResponse info = new LoginResponse();
				info.setClientes(loginDao.clienteLogin(userDetails.getId()));
				info.setEmpleados(loginDao.empleadoLogin(userDetails.getId()));
				if(!info.getClientes().isEmpty()) {
					info.setClienteSelect(info.getClientes().get(0));
				}
				if(!info.getEmpleados().isEmpty()) {
					info.setEmpleadoSelect(info.getEmpleados().get(0));
				}
				info.setRolSelect(listRoles.get(0));
				
				info.setPersona(userDetails.getPersona());
				return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), listRoles,
						userDetails.getClave(), jwtExpirationMs, info));
			}else {
				return ResponseEntity.ok(request);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>( "Ocurrió un error al realizar la petición", HttpStatus.BAD_REQUEST);
		}
	}
}

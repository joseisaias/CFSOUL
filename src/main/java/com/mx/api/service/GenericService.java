package com.mx.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mx.api.dto.response.CatDetalleResponse;
import com.mx.api.model.CatDetalle;
import com.mx.api.model.Persona;
import com.mx.api.model.Rol;
import com.mx.api.model.Usuario;
import com.mx.api.model.UsuarioRol;
import com.mx.api.repository.RolRepository;
import com.mx.api.repository.UserRepository;
import com.mx.api.repository.UsuarioRolRepository;
import com.mx.api.util.cons.CatDetalleEnum;

@Service
public class GenericService {

	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	public UserRepository userRepository;
	@Autowired
	private CatalogoService catalogoService;
	@Autowired
	private RolRepository rolRepository;
	@Autowired
	private UsuarioRolRepository usuarioRolRepository;
	
	private static String NUMEROS = "0123456789";

	private static String MAYUSCULAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	private static String MINUSCULAS = "abcdefghijklmnopqrstuvwxyz";

	private static String ESPECIALES = "ñÑ@$%&#?¡¿!";
	
	private String generarContrasenia() {
		String key = NUMEROS + MAYUSCULAS + MINUSCULAS + ESPECIALES;
		String contrasenia = "";
		for (int i = 0; i < 10; i++) {
			contrasenia += (key.charAt((int) (Math.random() * key.length())));
		}
		return contrasenia;
	}
	
	public String generaPassword() {
		return encoder.encode(generarContrasenia());
	}
	public String generaPasswordTemp() {
		return encoder.encode("Temp-1");
	}
	
	public Usuario registraUsuarioRol(Persona p, String claveRol){
		Usuario u = new Usuario();
		CatDetalleResponse usNuevo = catalogoService.getCatDetalleByClave(CatDetalleEnum.EST_US_NUEVO.name()).get(0);
		u.setEstatus(new CatDetalle(usNuevo.getIdCatDetalle()));
		u.setUsuario(p.getCorreoElectronico());
		u.setPersona(p);
		u.setPassword(generaPasswordTemp());
		userRepository.save(u);
		
		Rol rol = rolRepository.findByClaveRol(claveRol).get();
		usuarioRolRepository.save(new UsuarioRol(u,rol));
		return u;
	}
	
	public Usuario registraNuevoUsuarioRol(Persona p, Long claveRol){
		Usuario u = new Usuario();
		CatDetalleResponse usNuevo = catalogoService.getCatDetalleByClave(CatDetalleEnum.EST_US_NUEVO.name()).get(0);
		u.setEstatus(new CatDetalle(usNuevo.getIdCatDetalle()));
		u.setUsuario(p.getCorreoElectronico());
		u.setPersona(p);
		u.setPassword(generaPasswordTemp());
		userRepository.save(u);
		
		Rol rol = rolRepository.findById(claveRol).get();
		usuarioRolRepository.save(new UsuarioRol(u,rol));
		return u;
	}


	public Usuario obtenUsuarioById(Long idUsuario){
		return userRepository.findById(idUsuario).get();
	}
	
	public Usuario activaUsuarioById(Long idUsuario) {
		CatDetalleResponse usNuevo = catalogoService.getCatDetalleByClave(CatDetalleEnum.EST_US_ACTIVO.name()).get(0);
		Usuario u = userRepository.findById(idUsuario).get();
		u.setEstatus(new CatDetalle(usNuevo.getIdCatDetalle()));
		return userRepository.save(u);
	}
}

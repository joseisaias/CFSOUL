package com.mx.api.jwt.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mx.api.model.Usuario;
import com.mx.api.model.UsuarioRol;
import com.mx.api.repository.PersonaRepository;
import com.mx.api.repository.UserRepository;
import com.mx.api.repository.UsuarioRolRepository;



@Service
public class UserDetailsSegServiceImpl implements UserDetailsService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UsuarioRolRepository usuarioRolRepository;
	
	@Autowired
	PersonaRepository personaRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario user = ( userRepository.findByUsuario(username))
				.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con nombre de usuario: " + username));
		List<UsuarioRol> usRol = usuarioRolRepository.findByIdUsuario(user);
		return UserDetailsSegImpl.build(user,usRol);
	}

}

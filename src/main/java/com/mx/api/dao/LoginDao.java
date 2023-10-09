package com.mx.api.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mx.api.dto.response.LoginResponse;
import com.mx.api.model.Cliente;
import com.mx.api.model.Persona;
import com.mx.api.repository.ClienteRepository;
import com.mx.api.repository.EmpleadoRepository;

@Service
public class LoginDao {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EmpleadoRepository empleadoRepository;
	
	private static Integer ACTIVO = 1;
	
	public LoginResponse parametrosLogin(Long idUsuario, Persona p) {
		LoginResponse r = new LoginResponse();
		Cliente c = clienteRepository.findByIdUsuario(idUsuario).orElse(new Cliente());
		if(c.getIdCliente() != null) {
			r.setIdCliente(c.getIdCliente());
		}
		r.setEmpleadoCientes(empleadoRepository.findByIdPersonaAndIndStatus(p.getIdPersona(), ACTIVO));
		r.setPersona(p);
		return r;
	}
}

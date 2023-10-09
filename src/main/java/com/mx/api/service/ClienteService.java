package com.mx.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mx.api.dto.request.ClienteRequest;
import com.mx.api.model.ClienteDomicilio;
import com.mx.api.model.Persona;
import com.mx.api.model.Usuario;
import com.mx.api.repository.ClienteDomicilioRepository;
import com.mx.api.repository.ClienteRepository;
import com.mx.api.repository.DomicilioRepository;
import com.mx.api.repository.PersonaRepository;
import com.mx.api.util.cons.RolEnum;

@Service
public class ClienteService extends GenericService {

	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private DomicilioRepository domicilioRepository;
	@Autowired
	private ClienteDomicilioRepository clienteDomicilioRepository;
	@Autowired
	private PersonaRepository personaRepository;
	
	public void guardarCliente(ClienteRequest e){
		boolean isNewDomicilioF = e.getDomicilioFiscal().getIdDomicilio() == null;
		boolean isNewDomicilioC = e.getDomicilioComercial().getIdDomicilio() == null;
		boolean isNewUsuario = e.getCliente().getIdUsuario()==null;
		
		Persona p = new Persona();
		if(e.getCliente().getNombre() != null) {
			p.setNombre(e.getCliente().getNombre());
			p.setApellidoPaterno(e.getCliente().getApellidoPaterno());
			p.setApellidoMaterno(e.getCliente().getApellidoMaterno());
		} else {
			p.setNombre(e.getCliente().getRazonSocial());
			p.setApellidoPaterno(e.getCliente().getRazonSocial());
		}
		
		p.setCorreoElectronico(e.getCliente().getEmail());
		p.setRfc(e.getCliente().getRfc());
		p.setTelefono(e.getCliente().getTelefono());
		personaRepository.save(p);
		if(isNewUsuario) {
			Usuario u =  registraUsuarioRol(p, RolEnum.ROL_CLIE.name());
			e.getCliente().setIdUsuario(u.getIdUsuario());
		}
		
		clienteRepository.save(e.getCliente());
		domicilioRepository.save(e.getDomicilioComercial());
		domicilioRepository.save(e.getDomicilioFiscal());
		
		if(isNewDomicilioC) {
			clienteDomicilioRepository.save(new ClienteDomicilio(e.getDomicilioComercial().getIdDomicilio(), e.getCliente().getIdCliente()));
		}
		if(isNewDomicilioF) {
			clienteDomicilioRepository.save(new ClienteDomicilio(e.getDomicilioFiscal().getIdDomicilio(), e.getCliente().getIdCliente()));
		}
	}
}

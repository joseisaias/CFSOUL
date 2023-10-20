package com.mx.api.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.mx.api.dto.response.CatDetalleResponse;
import com.mx.api.dto.response.UsuarioResponse;
import com.mx.api.model.CuentaBancaria;
import com.mx.api.model.Domicilio;
import com.mx.api.model.PersonaDomicilio;
import com.mx.api.model.Usuario;
import com.mx.api.repository.CuentaBancariaRepository;
import com.mx.api.repository.DomicilioRepository;
import com.mx.api.repository.PersonaDomicilioRepository;
import com.mx.api.repository.PersonaRepository;
import com.mx.api.util.cons.CatDetalleEnum;

@Service
public class UsuarioService extends GenericService {

	@Autowired
	private DomicilioRepository domicilioRepository;
	
	@Autowired
	private PersonaDomicilioRepository personaDomicilioRepository;
	
	@Autowired
	private CuentaBancariaRepository cuentaBancariaRepository;
	
	@Autowired
	private PersonaRepository personaRepository;
	
	@Autowired
	private CatalogoService catalogoService;
	@Autowired
	private PasswordEncoder encoder;
	
	public UsuarioResponse obtenerUsuario(Long idUsuario) {
		UsuarioResponse r = new UsuarioResponse();
		Usuario u = obtenUsuarioById(idUsuario);
		r.setPersona(u.getPersona());
		r.setCuentaBancaria(cuentaBancariaRepository.findByIdPersona(r.getPersona().getIdPersona()).orElse(new CuentaBancaria())); 
		List<PersonaDomicilio> listD =  personaDomicilioRepository.findByIdPersona(r.getPersona().getIdPersona());
		if(listD.isEmpty()) {
			r.setDomicilio(new Domicilio());
		}
		for (PersonaDomicilio ed : listD) {
			Domicilio domicilio = domicilioRepository.findById(ed.getIdDomicilio()).get();
			if(domicilio.getIndStatus() == 1) {
				r.setDomicilio(domicilio);
			}
		}
		return r; 
	}
	
	public void guardar(UsuarioResponse r) {
		boolean isNuevo = true;
		CatDetalleResponse cd = catalogoService.getCatDetalleByClave(CatDetalleEnum.TIP_DOM_EMPL.name()).get(0);
		
		if(r.getDomicilio()!=null && r.getDomicilio().getIdDomicilio() != null) {
			isNuevo=false;
		}
		personaRepository.save(r.getPersona());
		r.getDomicilio().setIdTipoDomicilio(cd.getIdCatDetalle());
		domicilioRepository.save(r.getDomicilio());
		r.getCuentaBancaria().setIdPersona(r.getPersona().getIdPersona());
		cuentaBancariaRepository.save(r.getCuentaBancaria());
		if(isNuevo)
			personaDomicilioRepository.save(new PersonaDomicilio(r.getDomicilio().getIdDomicilio(), r.getPersona().getIdPersona()));
		
		
	}
	
	public Usuario editaUsuarioClientePass(Usuario u) throws Exception {
		Usuario us = activaUsuarioById(u.getIdUsuario());
		us.setPassword(encoder.encode(u.getPassword()));
		userRepository.save(us);
		return us;
	}
}

package com.mx.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mx.api.dto.request.EmpleadoRequest;
import com.mx.api.dto.response.CatDetalleResponse;
import com.mx.api.model.CuentaBancaria;
import com.mx.api.model.Domicilio;
import com.mx.api.model.Empleado;
import com.mx.api.model.Persona;
import com.mx.api.model.PersonaDomicilio;
import com.mx.api.repository.CuentaBancariaRepository;
import com.mx.api.repository.DomicilioRepository;
import com.mx.api.repository.EmpleadoRepository;
import com.mx.api.repository.PersonaDomicilioRepository;
import com.mx.api.repository.PersonaRepository;
import com.mx.api.util.cons.CatDetalleEnum;

@Service
public class EmpleadoService {

	@Autowired
	private EmpleadoRepository empleadoRepository;
	
	@Autowired
	private DomicilioRepository domicilioRepository;
	
	@Autowired
	private PersonaRepository personaRepository;
	
	@Autowired
	private PersonaDomicilioRepository personaDomicilioRepository;
	
	@Autowired
	private CuentaBancariaRepository cuentaBancariaRepository;
	
	@Autowired
	private CatalogoService catalogoService;
	
	public void guardar(EmpleadoRequest r) {
		boolean isNuevo = true;
		CatDetalleResponse cd = catalogoService.getCatDetalleByClave(CatDetalleEnum.TIP_DOM_EMPL.name()).get(0);
		if(r.getEmpleado()!=null && r.getEmpleado().getIdEmpleado() != null
				&& r.getDomicilio()!=null && r.getDomicilio().getIdDomicilio() != null) {
			isNuevo=false;
		}
		personaRepository.save(r.getPersona());
		
		r.getEmpleado().setIdPersona(r.getPersona().getIdPersona());
		empleadoRepository.save(r.getEmpleado());
		
		r.getDomicilio().setIdTipoDomicilio(cd.getIdCatDetalle());
		domicilioRepository.save(r.getDomicilio());
		
		r.getCuentaBancaria().setIdEmpleado(r.getEmpleado().getIdEmpleado());
		cuentaBancariaRepository.save(r.getCuentaBancaria());
		if(isNuevo)
			personaDomicilioRepository.save(new PersonaDomicilio(r.getDomicilio().getIdDomicilio(), r.getPersona().getIdPersona()));
	}
	
	public void excel() {
		
		/**
		 * preguntar si existe la persona por el RFC
		 * 
		 * si existe{
		 * r.setPersona(consulta del RFC)
		 * preguntar si existe empleado con el id del cliente y el id de la persona
		 * si existe{
		 * r.setEmpleado(resultado de la busqueda por idCliente y idPersona)
		 * recuperacuenta bancaria
		 * recupera domicilio
		 * }
		 * }else{
		 * 
		 * }
		 * 
		 * 
		 * 
		 */
		
		/* recorrer el excel se contemplan validaciones */
	}
	
	public EmpleadoRequest getEmpleadoById(Long idEmpleado) {
		EmpleadoRequest r = new EmpleadoRequest();
		r.setEmpleado(empleadoRepository.findById(idEmpleado).orElse(new Empleado()));
		Long idPersona = r.getEmpleado().getIdPersona()!= null ?r.getEmpleado().getIdPersona() : 0;
		r.setPersona(personaRepository.findById(idPersona).orElse(new Persona()));
		r.setCuentaBancaria(cuentaBancariaRepository.findByIdEmpleado(idEmpleado).orElse(new CuentaBancaria())); 
		List<PersonaDomicilio> listD =  personaDomicilioRepository.findByIdPersona(idPersona);
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
	
	
	public void activaInactivaEmpleado(Long idEmpleado) {
		Empleado e = empleadoRepository.findById(idEmpleado).get();
		e.setIndStatus(e.getIndStatus()==1 ? 0:1);
		empleadoRepository.save(e);
	}
		
}

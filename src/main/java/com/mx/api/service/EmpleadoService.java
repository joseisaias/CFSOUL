package com.mx.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mx.api.dao.EmpleadoDao;
import com.mx.api.dto.EmpleadoDTO;
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
import com.mx.api.util.cons.RolEnum;

@Service
public class EmpleadoService extends GenericService {

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
	
	@Autowired
	private EmpleadoDao empleadoDao;
	
	
	
	
	public List<EmpleadoRequest> guardarExcel(List<EmpleadoRequest> r) {
		List<EmpleadoRequest> error = new ArrayList<>();
		for (EmpleadoRequest e : r) {
			List<EmpleadoDTO> emp =  empleadoDao.getEmpleadoRegistrado(e.getPersona().getRfc(), e.getPersona().getCorreoElectronico());
			if(emp.isEmpty()) {
				guardar(e);
			}else {
				EmpleadoDTO empl = emp.get(0);
				if(empl.getIdCliente() != e.getEmpleado().getIdCliente()) {
					error.add(e);
				}else {
					Empleado empleado = empleadoRepository.findById(empl.getIdEmpleado()).orElse(new Empleado());
					empleado.setMontoMaximoPrestamo(e.getEmpleado().getMontoMaximoPrestamo());
					empleado.setSalario(e.getEmpleado().getSalario());
					e.setEmpleado(empleado);
					CuentaBancaria cb = cuentaBancariaRepository.findByIdPersona(empl.getIdPersona()).orElse(new CuentaBancaria());
					cb.setClabeInterbancaria(e.getCuentaBancaria().getClabeInterbancaria());
					cb.setNumeroCuenta(e.getCuentaBancaria().getNumeroCuenta());
					e.setCuentaBancaria(cb);
					e.setPersona(personaRepository.findById(empl.getIdPersona()).orElse(new Persona()));
					
					List<PersonaDomicilio> listD =  personaDomicilioRepository.findByIdPersona(empl.getIdPersona());
					if(listD.isEmpty()) {
						e.setDomicilio(new Domicilio());
					}
					for (PersonaDomicilio ed : listD) {
						Domicilio domicilio = domicilioRepository.findById(ed.getIdDomicilio()).get();
						if(domicilio.getIndStatus() == 1) {
							e.setDomicilio(domicilio);
						}
					}
					guardar(e);
				}
				
			}
			
		}
		return error;
	}
	
	public void guardar(EmpleadoRequest r) {
		boolean isNuevo = true;
		boolean registraUsuario = false;
		CatDetalleResponse cd = catalogoService.getCatDetalleByClave(CatDetalleEnum.TIP_DOM_EMPL.name()).get(0);
		if(r.getEmpleado()!=null && r.getEmpleado().getIdEmpleado() != null
				&& r.getDomicilio()!=null && r.getDomicilio().getIdDomicilio() != null) {
			isNuevo=false;
		}
		if(r.getPersona().getIdPersona() == null) {
			registraUsuario = true;
		}
		personaRepository.save(r.getPersona());
		
		if(registraUsuario) {
			registraUsuarioRol(r.getPersona(),RolEnum.ROL_EMPL.name());
		}
		r.getEmpleado().setIdPersona(r.getPersona().getIdPersona());
		empleadoRepository.save(r.getEmpleado());
		
		r.getDomicilio().setIdTipoDomicilio(cd.getIdCatDetalle());
		domicilioRepository.save(r.getDomicilio());
		
		r.getCuentaBancaria().setIdPersona(r.getPersona().getIdPersona());
		cuentaBancariaRepository.save(r.getCuentaBancaria());
		if(isNuevo)
			personaDomicilioRepository.save(new PersonaDomicilio(r.getDomicilio().getIdDomicilio(), r.getPersona().getIdPersona()));
	}
	
	
	public EmpleadoRequest getEmpleadoById(Long idEmpleado) {
		EmpleadoRequest r = new EmpleadoRequest();
		r.setEmpleado(empleadoRepository.findById(idEmpleado).orElse(new Empleado()));
		Long idPersona = r.getEmpleado().getIdPersona()!= null ?r.getEmpleado().getIdPersona() : 0;
		r.setPersona(personaRepository.findById(idPersona).orElse(new Persona()));
		r.setCuentaBancaria(cuentaBancariaRepository.findByIdPersona(idPersona).orElse(new CuentaBancaria())); 
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

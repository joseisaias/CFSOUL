package com.mx.api.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mx.api.dto.UsuarioDTO;
import com.mx.api.dto.request.ClienteRequest;
import com.mx.api.dto.request.UsuarioRequest;
import com.mx.api.dto.response.CatDetalleResponse;
import com.mx.api.dto.response.UsuarioResponse;
import com.mx.api.model.ClienteDomicilio;
import com.mx.api.model.CuentaBancaria;
import com.mx.api.model.Domicilio;
import com.mx.api.model.Persona;
import com.mx.api.model.PersonaDomicilio;
import com.mx.api.model.Usuario;
import com.mx.api.repository.ClienteRepository;
import com.mx.api.repository.CuentaBancariaRepository;
import com.mx.api.repository.DomicilioRepository;
import com.mx.api.repository.PersonaDomicilioRepository;
import com.mx.api.repository.PersonaRepository;
import com.mx.api.util.cons.CatDetalleEnum;
import com.mx.api.util.cons.RolEnum;

@Service
public class UsuarioService extends GenericService {

	@Autowired
	private ClienteRepository usuarioRepository;

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
	
	public List<UsuarioDTO> findDetalleUsuario(Long idUsuario) {
		//System.out.println("llegamos hasta service autoevaluación");
		List<UsuarioDTO> lsCat = new ArrayList<UsuarioDTO>();
		List<Object[]> ls = new ArrayList<Object[]>();
		
		try {
			if(idUsuario == 0){
				ls = this.userRepository.findUsers();
			}else{
				ls = this.userRepository.findIdUser(idUsuario);

			}
		} catch (Exception e) {
			System.out.println(" error tal "+ e.getMessage());
		}
            
		for (Object[] objects : ls) {
			
			UsuarioDTO objDeCat  = new UsuarioDTO();
			
			
			objDeCat.setIdUsuario(!Objects.isNull(objects[0])?(Integer)objects[0]:0);
			objDeCat.setIdEstatusUsuario(!Objects.isNull(objects[1])?(Integer)objects[1]:0);
			objDeCat.setIdPersona(!Objects.isNull(objects[2])?(Integer)objects[2]:0);
			objDeCat.setUsuario(!Objects.isNull(objects[3])?(String)objects[3]:"");
			objDeCat.setFechaAlta(!Objects.isNull(objects[4])?(String)objects[4]:"");
			objDeCat.setNombre(!Objects.isNull(objects[5])?(String)objects[5]:"");
			objDeCat.setApellidoPaterno(!Objects.isNull(objects[6])?(String)objects[6]:"");
			objDeCat.setApellidoMaterno(!Objects.isNull(objects[7])?(String)objects[7]:"");
			objDeCat.setRfc(!Objects.isNull(objects[8])?(String)objects[8]:"");
			objDeCat.setCurp(!Objects.isNull(objects[9])?(String)objects[9]:"");
			objDeCat.setEmail(!Objects.isNull(objects[10])?(String)objects[10]:"");
			objDeCat.setTelefono(!Objects.isNull(objects[11])?(String)objects[11]:"");
			objDeCat.setEstatusPersona(!Objects.isNull(objects[12])?(Integer)objects[12]:0);
			objDeCat.setNombreCompleto(!Objects.isNull(objects[13])?(String)objects[13]:"");
			objDeCat.setIndStatusString(!Objects.isNull(objects[14])?(String)objects[14]:"");
			objDeCat.setIdRol(!Objects.isNull(objects[15])?(Integer)objects[15]:0);
			objDeCat.setIndStatus(!Objects.isNull(objects[16])?(Integer)objects[16]:0);
			//objDeCat.setEstatus(!Objects.isNull(objects[9])?((Integer)objects[9]==1?Boolean.TRUE:Boolean.FALSE):Boolean.FALSE);
			
			lsCat.add(objDeCat);
			
		}
		
		
		return lsCat;
	}

	public void nuevoUsuario(UsuarioRequest e){

		boolean isNewUsuario = e.getCliente().getIdUsuario()==null;
		Long idRol =  e.getRol().getIdRol();
		Persona p = new Persona();

		p.setNombre(e.getCliente().getNombre());
		p.setApellidoPaterno(e.getCliente().getApellidoPaterno());
		p.setApellidoMaterno(e.getCliente().getApellidoMaterno());
		
		
		p.setCorreoElectronico(e.getCliente().getEmail());
		p.setRfc(e.getCliente().getRfc());
		p.setTelefono(e.getCliente().getTelefono());
		personaRepository.save(p);
	
		//personaRepository.
		if(isNewUsuario) {
			Usuario u =  registraNuevoUsuarioRol(p, idRol);
			e.getCliente().setIdUsuario(u.getIdUsuario());
		}
		
		usuarioRepository.save(e.getCliente());	

	}


	public String cambiarEstatusUsuario(Long idUsuario) {
		// Si el ID del usuario no es nulo
		if (idUsuario != null) {
			List<UsuarioDTO> resultado;
			resultado = this.findDetalleUsuario(idUsuario);

			// Verifica si se encontraron resultados
			if (!resultado.isEmpty()) {
			
				// Suponiendo que el índice correcto de ind_estatus es el último en el array
				int indiceIndEstatus = resultado.get(0).getIndStatus();
				// Obtener el valor actual de ind_estatus
				Integer indEstatusNuevo = ( (Integer)indiceIndEstatus == 1 )? 0 :1;
				
				// Actualizar en la base de datos (asumiendo que hay un método de actualización)
				try {
					int update; 
					update = this.userRepository.actualizarIndEstatus(idUsuario, (Integer) indEstatusNuevo);
					resultado = this.findDetalleUsuario(idUsuario);
					return "ok";
				} catch (Exception e) {
					System.out.println("ERRROR AL ACTUALIZAR" + e.getMessage());
					return "no";
				}
				
			}
		}
		return "no";
	}


}

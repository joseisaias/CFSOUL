package com.mx.api.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mx.api.dao.ClientesDao;
import com.mx.api.dto.ClienteDTO;
import com.mx.api.dto.ClienteDomicilioDTO;
import com.mx.api.dto.commons.GenericResponseDTO;
import com.mx.api.dto.request.ClienteRequest;
import com.mx.api.model.Cliente;
import com.mx.api.model.ClienteDomicilio;
import com.mx.api.model.Domicilio;
import com.mx.api.repository.ClienteDomicilioRepository;
import com.mx.api.repository.ClienteRepository;
import com.mx.api.repository.DomicilioRepository;
import com.mx.api.util.cons.CatDetalleEnum;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/cliente")
@Slf4j
public class ClienteController extends BaseController {

	/*@SuppressWarnings("unused")
	private ObjectMapper mapper = new ObjectMapper();*/
	
	@Autowired
	private ClientesDao clientDao;
	
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private DomicilioRepository domicilioRepository;
	@Autowired
	private ClienteDomicilioRepository clienteDomicilioRepository;
	
	@GetMapping("/getClientes")
	public ResponseEntity<?> getClientes() {
		return ResponseEntity.ok(new GenericResponseDTO<>(SUCCESS, HTTP_SUCCESS, null, null, SUCCESS_MESSAGE, clientDao.getClientes()));
	}
	
	@PostMapping("/getClienteById")
	public ResponseEntity<?> getClienteById(@RequestBody ClienteDTO dto) {
		ClienteRequest req = new ClienteRequest();
		Optional<Cliente> c = clienteRepository.findById(dto.getIdCliente());
		req.setCliente(c.get());
		
		for (ClienteDomicilioDTO d : dto.getClienteDomicilio()) {
			if(CatDetalleEnum.TIP_DOM_FIS.name().equals(d.getClaveTipoDomicilio())) {
				Optional<Domicilio> df = domicilioRepository.findById(d.getIdDomicilio());
				req.setDomicilioFiscal(df.get());
			}else {
				Optional<Domicilio> dc = domicilioRepository.findById(d.getIdDomicilio());
				req.setDomicilioComercial(dc.get());
			}
		}
		if(req.getDomicilioComercial() == null) {
			req.setDomicilioComercial(new Domicilio());
		}
		if(req.getDomicilioFiscal() == null) {
			req.setDomicilioFiscal(new Domicilio());
		}
		return ResponseEntity.ok(new GenericResponseDTO<>(SUCCESS, HTTP_SUCCESS, null, null, SUCCESS_MESSAGE, req));
	}
	
	@PostMapping("/borradoLogico")
	public ResponseEntity<?> borradoLogico(@RequestBody ClienteDTO dto){
		Cliente c = clienteRepository.findById(dto.getIdCliente()).get();
		c.setIndStatus(c.getIndStatus() == ACTIVO ?INACTIVO : ACTIVO);
		clienteRepository.save(c);
		return ResponseEntity.ok(new GenericResponseDTO<>(SUCCESS, HTTP_SUCCESS, null, null, SUCCESS_MESSAGE, clientDao.getClientes()));
	}
	
	@PostMapping("/guardarCliente")
	public ResponseEntity<?> guardarCliente(@RequestBody ClienteRequest e) {
		boolean isNewDomicilioF = e.getDomicilioFiscal().getIdDomicilio() == null;
		boolean isNewDomicilioC = e.getDomicilioComercial().getIdDomicilio() == null;
		try {
			clienteRepository.save(e.getCliente());
			domicilioRepository.save(e.getDomicilioComercial());
			domicilioRepository.save(e.getDomicilioFiscal());
			if(isNewDomicilioC) {
				clienteDomicilioRepository.save(new ClienteDomicilio(e.getDomicilioComercial().getIdDomicilio(), e.getCliente().getIdCliente()));
			}
			if(isNewDomicilioF) {
				clienteDomicilioRepository.save(new ClienteDomicilio(e.getDomicilioFiscal().getIdDomicilio(), e.getCliente().getIdCliente()));
			}
			return ResponseEntity.ok(new GenericResponseDTO<>(SUCCESS, HTTP_SUCCESS, null, null, SUCCESS_MESSAGE, clientDao.getClientes()));
		}catch(Exception ex) {
			log.error("Ocurrio un error en el registro", ex);
			return ResponseEntity.ok(new GenericResponseDTO<>(ERROR, HTTP_NOT_FOUND, null, ERROR_MESSAGE, null, null));
		}
	}
}

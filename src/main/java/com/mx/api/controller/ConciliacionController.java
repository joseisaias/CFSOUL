package com.mx.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mx.api.dao.ConciliacionDao;
import com.mx.api.dto.commons.GenericResponseDTO;
import com.mx.api.dto.response.ConciliacionResponse;
import com.mx.api.model.BitacoraPagos;
import com.mx.api.model.Conciliacion;
import com.mx.api.model.Credito;
import com.mx.api.repository.BitacoraPagosRepository;
import com.mx.api.repository.ConciliacionRepository;
import com.mx.api.repository.CreditoRepository;
import com.mx.api.service.CatalogoService;
import com.mx.api.util.cons.CatDetalleEnum;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/conciliacion")
@Slf4j
public class ConciliacionController extends BaseController {
	
	@Autowired
	private ConciliacionDao conciliacionDao;
	
	@Autowired
	private BitacoraPagosRepository bitPagRepository;
	
	@Autowired
	private CatalogoService catalogoService;
	
	@Autowired
	private BitacoraPagosRepository amortizacionRepository;
	
	@Autowired
	private CreditoRepository creditoRepository;
	
	@Autowired
	private ConciliacionRepository conciliacionRepository;
	
	
	@GetMapping("/{fechaPago}/getConciliacion")
	public ResponseEntity<?> getConciliacion(@PathVariable String fechaPago){
		try {
			return ResponseEntity.ok(new GenericResponseDTO<>(SUCCESS, HTTP_SUCCESS, null, null, SUCCESS_MESSAGE, 
					conciliacionDao.getConciliacion(fechaPago.replace("-", "/"))));
		}catch(Exception ex) {
			log.error("Ocurrio un error en el registro", ex);
			return ResponseEntity.ok(new GenericResponseDTO<>(ERROR, HTTP_NOT_FOUND, null, ERROR_MESSAGE, null, null));
		}
	}
	
	@PostMapping("/conciliaPago")
	public ResponseEntity<?> conciliaPago (@RequestBody List<ConciliacionResponse> list){
		try {
			Long idEstatusPago = catalogoService.getCatDetalleByClave(CatDetalleEnum.ESTATUS_BIT_PAGADO.name()).get(0).getIdCatDetalle();
			Long idEstatusCredito = catalogoService.getCatDetalleByClave(CatDetalleEnum.ESTATUS_PAGADO.name()).get(0).getIdCatDetalle();
			Conciliacion conciliacion = new Conciliacion();
			conciliacion.setFecha_conciliacion(list.get(0).getFechaPago());
			conciliacionRepository.save(conciliacion);
			for (ConciliacionResponse item : list) {
				List<BitacoraPagos> pagos =  amortizacionRepository.findByFechaPagoIdCliente(item.getFechaPago(), item.getIdCliente());
				for (BitacoraPagos bp : pagos) {
					List<BitacoraPagos> pagosCredito = amortizacionRepository.findByidCredito(bp.getIdBitacoraPagos());
					bp.setIdEstatusPago(idEstatusPago);
					bp.setIdConciliacion(conciliacion.getIdConciliacion());
					bitPagRepository.save(bp);
					if(pagosCredito.size() == bp.getNumPago()) {
						Credito c = creditoRepository.findById(bp.getIdCredito()).get();
						c.setIdEstatusCredito(idEstatusCredito);
						creditoRepository.save(c);
					}
				}
			}
			return ResponseEntity.ok(new GenericResponseDTO<>(SUCCESS, HTTP_SUCCESS, null, null, SUCCESS_MESSAGE, 
					""));
		} catch(Exception ex) {
			log.error("Ocurrio un error en el registro", ex);
			return ResponseEntity.ok(new GenericResponseDTO<>(ERROR, HTTP_NOT_FOUND, null, ERROR_MESSAGE, null, null));
		}
	}
	
}

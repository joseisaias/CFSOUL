package com.mx.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mx.api.dao.CatalogoDao;
import com.mx.api.dto.response.CatDetalleResponse;
import com.mx.api.dto.response.DomicilioSelect;
import com.mx.api.dto.response.InputSelectResponse;

@Service
public class CatalogoService {
	@Autowired
	private CatalogoDao catalogoDao;
	
	public List<InputSelectResponse> getCatSelect(String clave) {
		return catalogoDao.getCatSelect(clave);
	}
	
	public List<InputSelectResponse> getCatSelectIdCatPadre(String clave, Long idPadre) {
		return catalogoDao.getCatSelectIdCatPadre(clave, idPadre);
	}
	
	public List<DomicilioSelect> getCatDomicilioByCp(String cp) {
		return catalogoDao.getCatDomicilioByCp(cp);
	}
	
	public List<CatDetalleResponse> getCatDetalleByClave(String clave){
		return catalogoDao.getCatDetalleByClave(clave);
	}
	
	public List<InputSelectResponse> getCatRol(){
		return catalogoDao.getCatRol();
	}
}

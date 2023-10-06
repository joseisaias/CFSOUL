package com.mx.api.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mx.api.dto.response.CatDetalleResponse;
import com.mx.api.dto.response.DomicilioSelect;
import com.mx.api.dto.response.InputSelectResponse;

@Repository
public class CatalogoDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@SuppressWarnings("deprecation")
	public List<InputSelectResponse> getCatSelect(String clave) {
		String sql = "SELECT \r\n"
				+ "id_cat_detalle as idCat, \r\n"
				+ "descripcion, \r\n"
				+ "clave  \r\n"
				+ "FROM cat_detalle cd \r\n"
				+ "where id_cat_maestro = (SELECT id_cat_maestro FROM cat_maestro cm where cm.clave = ?)";
		return jdbcTemplate.query(sql, new Object[] { clave }, (rs, rowNum) -> {
			InputSelectResponse dto = new InputSelectResponse();
			dto.setIdCat(rs.getLong("idCat"));
			dto.setClave(rs.getString("clave"));
			dto.setDescripcion(rs.getString("descripcion"));
			return dto;
		});
	}
	
	@SuppressWarnings("deprecation")
	public List<DomicilioSelect> getCatDomicilioByCp(String cp) {
		if(cp.length() < 5) {
			cp = "0" + cp;
		}
		String sql = "SELECT \r\n "
				+ " id_cat_domicilio, \r\n "
				+ " cp, \r\n "
				+ " estado, \r\n "
				+ " municipio  \r\n "
				+ " from cat_domicilio cd \r\n "
				+ " where cp = ?";
		return jdbcTemplate.query(sql, new Object[] { cp }, (rs, rowNum) -> {
			DomicilioSelect dto = new DomicilioSelect();
			dto.setIdCatDomicilio(rs.getLong("id_cat_domicilio"));
			dto.setCp(rs.getString("cp"));
			dto.setEstado(rs.getString("estado"));
			dto.setMunicipio(rs.getString("municipio"));
			return dto;
		});
	}
	
	@SuppressWarnings("deprecation")
	public List<CatDetalleResponse> getCatDetalleByClave(String clave) {
		
		String sql = " SELECT \r\n "
				+ " id_cat_detalle,\r\n "
				+ " descripcion,\r\n "
				+ " clave \r\n "
				+ " from cat_detalle cd \r\n "
				+ " WHERE clave = ? ";
		return jdbcTemplate.query(sql, new Object[] { clave }, (rs, rowNum) -> {
			CatDetalleResponse dto = new CatDetalleResponse();
			dto.setIdCatDetalle(rs.getLong("id_cat_detalle"));
			dto.setClave(rs.getString("clave"));
			dto.setDescripcion(rs.getString("descripcion"));
			return dto;
		});
	}
	
	
}

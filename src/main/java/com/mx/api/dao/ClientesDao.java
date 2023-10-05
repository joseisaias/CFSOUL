package com.mx.api.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.mx.api.dto.ClienteDTO;
import com.mx.api.dto.ClienteDomicilioDTO;

@Service
public class ClientesDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@SuppressWarnings("deprecation")
	public List<ClienteDTO> getClientes(){
		String sql = "SELECT \r\n"
				+ "id_cliente, \r\n"
				+ "rfc, \r\n"
				+ "cd.descripcion,\r\n"
				+ "CASE WHEN razon_social IS NOT NULL THEN razon_social \r\n"
				+ "    ELSE CONCAT(nombre,' ',apellido_paterno,' ',apellido_materno) \r\n"
				+ "    END\r\n"
				+ "as nombreCompleto,\r\n"
				+ "CASE WHEN c.ind_status = 1 THEN 'Activo' \r\n"
				+ "    ELSE 'Inactivo' \r\n"
				+ "    END\r\n"
				+ "as indStatusString\r\n"
				+ "from cliente c \r\n"
				+ "join cat_detalle cd on c.id_tipo_sociedad = cd.id_cat_detalle";
		return jdbcTemplate.query(sql, new Object[] { }, (rs, rowNum) -> {
			ClienteDTO dto = new ClienteDTO();
			dto.setIdCliente(rs.getLong("id_cliente"));
			dto.setNombreCompleto(rs.getString("nombreCompleto"));
			dto.setRfc(rs.getString("rfc"));
			dto.setTipoSociedad(rs.getString("descripcion"));
			dto.setIndStatusString(rs.getString("indStatusString"));
			dto.setClienteDomicilio(getClienteDomicilio(dto.getIdCliente()));
			return dto;
		});
	}
	
	@SuppressWarnings("deprecation")
	public List<ClienteDomicilioDTO> getClienteDomicilio(Long id){
		String sql = "SELECT \r\n"
				+ "	cd.id_cliente_domicilio, \r\n"
				+ "	cd.id_cliente, \r\n"
				+ "	cd.id_domicilio, \r\n"
				+ "	d.id_tipo_domicilio,\r\n"
				+ "	cd2.clave,\r\n"
				+ "	cd2.descripcion \r\n"
				+ "from cliente_domicilio cd\r\n"
				+ "join domicilio d on d.id_domicilio = cd.id_domicilio \r\n"
				+ "join cat_detalle cd2 on cd2.id_cat_detalle = d.id_tipo_domicilio  where cd.id_cliente = ?";
		return jdbcTemplate.query(sql, new Object[] { id }, (rs, rowNum) -> {
			ClienteDomicilioDTO dto = new ClienteDomicilioDTO();
			dto.setIdCliente(rs.getLong("id_cliente"));
			dto.setIdClienteDomicilio(rs.getLong("id_cliente_domicilio"));
			dto.setIdDomicilio(rs.getLong("id_domicilio"));
			dto.setIdTipoDomicilio(rs.getLong("id_tipo_domicilio"));
			dto.setClaveTipoDomicilio(rs.getString("clave"));
			dto.setDescTipoDomicilio(rs.getString("descripcion"));
			return dto;
		});
	}
}

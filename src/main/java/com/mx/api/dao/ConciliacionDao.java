package com.mx.api.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.mx.api.dto.response.ConciliacionResponse;
import com.mx.api.util.cons.CatDetalleEnum;

@Service
public class ConciliacionDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private CatalogoDao catDao;
	
	@SuppressWarnings("deprecation")
	public List<ConciliacionResponse> getConciliacion(String fechaPago){
		String sql = "select ifnull(c.razon_social, concat(c.nombre,' ',c.apellido_paterno)) as nombreCompleto, \r\n"
				+ "	c.id_cliente, \r\n"
				+ "	max(bp.cuota) as monto, \r\n"
				+ "	count(bp.id_bitacora_pagos) as totalCreditos ,\r\n"
				+ "	bp.id_conciliacion \r\n"
				+ "from cliente c\r\n"
				+ "join empleado e on e.id_cliente = c.id_cliente\r\n"
				+ "join credito cr on cr.id_empleado = e.id_empleado \r\n"
				+ "join bitacora_pagos bp on bp.id_credito = cr.id_credito and bp.fecha_pago = ?\r\n"
				+ "left join conciliacion co on co.id_conciliacion = bp.id_conciliacion \r\n"
				+ "where c.ind_status = 1 \r\n"
				+ "group by c.id_cliente, bp.id_conciliacion";
		return jdbcTemplate.query(sql, new Object[] {fechaPago }, (rs, rowNum) -> {
			ConciliacionResponse r = new ConciliacionResponse();
			r.setIdCliente(rs.getLong("id_cliente"));
			r.setMonto(rs.getBigDecimal("monto"));
			r.setNombreCompleto(rs.getString("nombreCompleto"));
			r.setTotalCredito(rs.getBigDecimal("totalCreditos"));
			r.setIdConciliacion(rs.getLong("id_conciliacion"));
			if(r.getIdConciliacion() != null && r.getIdConciliacion() > 0) {
				r.setStatus(catDao.getCatDetalleByClave(CatDetalleEnum.ESTATUS_BIT_PAGADO.name()).get(0).getDescripcion());
			} else {
				r.setStatus(catDao.getCatDetalleByClave(CatDetalleEnum.ESTATUS_BIT_ADEUDO.name()).get(0).getDescripcion());
			}
			r.setFechaPago(fechaPago);
			return r;
		});
	}
}

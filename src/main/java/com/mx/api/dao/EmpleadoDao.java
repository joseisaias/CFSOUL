package com.mx.api.dao;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.mx.api.dto.EmpleadoDTO;
import com.mx.api.dto.response.CreditosEmpleado;
import com.mx.api.dto.response.EmpleadoSeguimientoResponse;

@Service
public class EmpleadoDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@SuppressWarnings("deprecation")
	public List<EmpleadoDTO> getEmpleadosByIdCliente(Long id) {
		String sql = "SELECT \r\n"
				+ "e.id_empleado as idEmpleado, CONCAT(p.nombre,' ',p.apellido_paterno,' ', p.apellido_materno ) as nombreCompleto,\r\n"
				+ "p.rfc,\r\n"
				+ "e.monto_maximo_prestamo,\r\n"
				+ "e.puesto,\r\n"
				+ "p.correo_electronico,\r\n"
				+ "p.telefono ,\r\n"
				+ "(SELECT count(*) from credito cred where cred.id_empleado= e.id_empleado) as totalCreditos,\r\n"
				+ "(SELECT IFNULL(max(monto_solicitado), 0) from credito cred  where cred.id_empleado= e.id_empleado) as montoTotalPrestamo,\r\n"
				+ " CASE WHEN e.ind_status = 1 THEN 'Activo' \r\n"
				+ "    ELSE 'Inactivo' \r\n"
				+ "    END\r\n"
				+ "as indStatusString "
				+ "FROM\r\n"
				+ "persona p \r\n"
				+ "join empleado e on p.id_persona = e.id_persona  \r\n"
				+ "join cliente c on e.id_cliente = c.id_cliente where c.id_cliente = ?";
		return jdbcTemplate.query(sql, new Object[]{id}, (rs, index) ->{
			EmpleadoDTO dto = new EmpleadoDTO();
			dto.setIdEmpleado(rs.getLong("idEmpleado"));
			dto.setNombreCompleto(rs.getString("nombreCompleto"));
			dto.setRfc(rs.getString("rfc"));
			dto.setMontoMaximoPrestamo(rs.getString("monto_maximo_prestamo"));
			dto.setPuesto(rs.getString("puesto"));
			dto.setEmail(rs.getString("correo_electronico"));
			dto.setTelefono(rs.getString("telefono"));
			dto.setTotalCredito(rs.getString("totalCreditos"));
			dto.setMontoTotalPrestamo(rs.getString("montoTotalPrestamo"));
			dto.setIndStatusString(rs.getString("indStatusString"));
			return dto;
		});
	}
	
	@SuppressWarnings("deprecation")
	public EmpleadoSeguimientoResponse getEmpleadosByIdClienteSeguimiento(Long id, Long idEstatus, String fechaPago) {
		EmpleadoSeguimientoResponse resp = new EmpleadoSeguimientoResponse();
		String sql = "select bp.id_credito,\r\n"
				+ "e.id_empleado as idEmpleado, \r\n"
				+ "bp.num_pago, \r\n"
				+ "bp.pago_capital + bp.pago_interes as cuota,\r\n "
				+ "bp.fecha_pago,\r\n"
				+ "c.num_pagos,\r\n"
				+ "c.pago_total,\r\n"
				+ "e.monto_maximo_prestamo,\r\n"
				+ "e.salario,\r\n"
				+ "concat(p.nombre,' ',p.apellido_paterno) as nombreCompleto,\r\n"
				+ "p.correo_electronico \r\n"
				+ "from bitacora_pagos bp\r\n"
				+ "join credito c on bp.id_credito = c.id_credito \r\n"
				+ "join empleado e on e.id_empleado = c.id_empleado \r\n"
				+ "join persona p on p.id_persona = e.id_persona \r\n"
				+ "where \r\n"
				+ "bp.fecha_pago = ? \r\n"
				+ "and bp.id_estatus_pago = ? \r\n"
				+ "and bp.ind_status = 1 \r\n"
				+ "and e.id_cliente = ?";
		resp.setEmpleados(
		jdbcTemplate.query(sql, new Object[]{fechaPago, idEstatus, id}, (rs, index) ->{
			EmpleadoDTO dto = new EmpleadoDTO();
			dto.setIdEmpleado(rs.getLong("idEmpleado"));
			dto.setNombreCompleto(rs.getString("nombreCompleto"));
			dto.setMontoMaximoPrestamo(rs.getString("monto_maximo_prestamo"));
			dto.setEmail(rs.getString("correo_electronico"));
			
			dto.setIdCredito(rs.getLong("id_credito"));
			
			dto.setNumPago(rs.getInt("num_pago"));
			dto.setNumPagos(rs.getInt("num_pagos"));
			dto.setSalario(rs.getBigDecimal("salario"));
			dto.setCuota(rs.getBigDecimal("cuota"));
			dto.setPagoTotal(rs.getBigDecimal("pago_total"));
			dto.setFechaPago(rs.getString("fecha_pago"));
			return dto;
		})
		);
		BigDecimal montoDeudaTotal = new BigDecimal(0);
		for (EmpleadoDTO item : resp.getEmpleados()) {
			montoDeudaTotal = montoDeudaTotal.add(item.getCuota());
		}
		resp.setMontoDeudaTotal(montoDeudaTotal);
		
		return resp;
	}
	
	@SuppressWarnings("deprecation")
	public List<EmpleadoDTO> getEmpleadoRegistrado(String rfc, String correo) {
		String sql = "select \r\n"
				+ "p.id_persona, e.id_cliente, e.id_empleado, cb.id_cuenta_bancaria, d.id_domicilio \r\n"
				+ "from persona p \r\n"
				+ "join empleado e ON p.id_persona = e.id_persona \r\n"
				+ "left join cuenta_bancaria cb on cb.id_persona = p.id_persona and cb.ind_status = 1 \r\n"
				+ "left join persona_domicilio pd ON pd.id_persona = p.id_persona \r\n"
				+ "left join domicilio d on d.id_domicilio = pd.id_domicilio and d.ind_status = 1 \r\n"
				+ "where \r\n"
				+ "(UPPER(rfc) = UPPER(?) \r\n"
				+ "or UCASE(correo_electronico) = UCASE(?)) \r\n"
				+ "and p.ind_status = 1 \r\n"
				+ "and e.ind_status = 1 ";
		
		return jdbcTemplate.query(sql, new Object[]{rfc, correo}, (rs, index) ->{
			EmpleadoDTO dto = new EmpleadoDTO();
			dto.setIdPersona(rs.getLong("id_persona"));
			dto.setIdCliente(rs.getLong("id_cliente"));
			dto.setIdEmpleado(rs.getLong("id_empleado"));
			dto.setIdCuentaBancaria(rs.getLong("id_cuenta_bancaria"));
			dto.setIdDomicilio(rs.getLong("id_domicilio"));
			return dto;
		});
	}
	
	@SuppressWarnings("deprecation")
	public List<EmpleadoDTO> getEmpleadoCliente(Long idPersona, Long idCliente) {
		String sql = "select \r\n"
				+ "p.id_persona  \r\n"
				+ "from persona p \r\n"
				+ "where \r\n"
				+ "(UPPER(rfc) = UPPER(?) \r\n"
				+ "or UCASE(correo_electronico) = UCASE(?)) \r\n"
				+ "and ind_status = 1";
		
		return jdbcTemplate.query(sql, new Object[]{idPersona, idCliente}, (rs, index) ->{
			EmpleadoDTO dto = new EmpleadoDTO();
			dto.setIdPersona(rs.getLong("id_persona"));
			return dto;
		});
	}
	
	@SuppressWarnings("deprecation")
	public List<CreditosEmpleado> getCreditosByEmpleado(Long idEmpleado){
		String sql = "select \r\n"
				+ "c.id_credito, \r\n"
				+ "c.pago_total,\r\n"
				+ "c.monto_solicitado,\r\n"
				+ "concat(ifnull((select num_pago  from bitacora_pagos bp where bp.id_credito  = c.id_credito and bp.id_conciliacion is not null order by  num_pago  asc LIMIT 1),0), '/',c.num_pagos) as totalPagos,\r\n"
				+ "(select fecha_pago from bitacora_pagos bp where bp.id_credito  = c.id_credito order by  num_pago  asc LIMIT 1) as fechaInicio,\r\n"
				+ "(select fecha_pago from bitacora_pagos bp where bp.id_credito  = c.id_credito order by  num_pago  desc LIMIT 1) as fechaFin\r\n"
				+ "from empleado e\r\n"
				+ "join credito c on c.id_empleado = e.id_empleado where e.id_empleado = ? ";
		
		return jdbcTemplate.query(sql, new Object[]{idEmpleado}, (rs, index) -> {
			CreditosEmpleado dto = new CreditosEmpleado();
			dto.setIdCredito(rs.getLong("id_credito"));
			dto.setTotalCredito(rs.getBigDecimal("pago_total"));
			dto.setMontoSolicitado(rs.getBigDecimal("monto_solicitado"));
			dto.setFechaPrimerPago(rs.getString("fechaInicio"));
			dto.setFechaUltimoPago(rs.getString("fechaFin"));
			dto.setTotalPagos(rs.getString("totalPagos"));
			return dto;
		});
	}
	
 }

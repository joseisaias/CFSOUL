package com.mx.api.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.mx.api.dto.EmpleadoDTO;

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
 }

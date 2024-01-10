package com.mx.api.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.mx.api.dto.response.ClienteEmpleadoLoginResponse;

@Service
public class LoginDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private static Integer ACTIVO = 1;
	
	@SuppressWarnings("deprecation")
	public List<ClienteEmpleadoLoginResponse> empleadoLogin(Long idUsuario) {
		String sql = "select \r\n"
				+ "c.id_cliente, ifnull(c.razon_social, concat(c.nombre,' ',c.apellido_paterno)) nombreCliente, e.id_empleado, e.monto_maximo_prestamo as montoMaximoPrestamo, salario \r\n"
				+ "from usuario u \r\n"
				+ "join persona p on u.id_persona  = p.id_persona  \r\n"
				+ "join  empleado e on e.id_persona = p.id_persona  \r\n"
				+ "join cliente c on c.id_cliente  = e.id_cliente \r\n"
				+ "where u.id_usuario = ? and e.ind_status = ? and c.ind_status = ?";
		
		return jdbcTemplate.query(sql, new Object[]{ idUsuario, ACTIVO, ACTIVO }, (rs, index) ->{
			ClienteEmpleadoLoginResponse dto = new ClienteEmpleadoLoginResponse();
			dto.setIdCliente(rs.getLong("id_cliente"));
			dto.setIdEmpleado(rs.getLong("id_empleado"));
			dto.setNombreCliente(rs.getString("nombreCliente"));
			dto.setMontoMaximoPrestamo(rs.getBigDecimal("montoMaximoPrestamo"));
			dto.setSalario(rs.getBigDecimal("salario"));
			return dto;
		});
	}
	
	@SuppressWarnings("deprecation")
	public List<ClienteEmpleadoLoginResponse> clienteLogin(Long idUsuario) {
		String sql = "select \r\n"
				+ "c.id_cliente, ifnull(c.razon_social, concat(c.nombre,' ',c.apellido_paterno)) nombreCliente\r\n"
				+ "from usuario u \r\n"
				+ "join cliente c on c.id_usuario  = u.id_usuario \r\n"
				+ "where u.id_usuario = ? and c.ind_status = ?";
		
		return jdbcTemplate.query(sql, new Object[]{ idUsuario, ACTIVO }, (rs, index) ->{
			ClienteEmpleadoLoginResponse dto = new ClienteEmpleadoLoginResponse();
			dto.setIdCliente(rs.getLong("id_cliente"));
			dto.setNombreCliente(rs.getString("nombreCliente"));
			return dto;
		});
	}
	
}

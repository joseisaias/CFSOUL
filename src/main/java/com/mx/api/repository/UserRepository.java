package com.mx.api.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mx.api.model.Usuario;

@Repository
public interface UserRepository extends JpaRepository<Usuario, Long> {

	@Transactional
	@Query(nativeQuery = true,  value = "UPDATE usuario u SET u.ind_status = :nuevoEstatus WHERE u.id_usuario = :idUsuario")
	@Modifying
    int  actualizarIndEstatus(@Param("idUsuario") Long idUsuario, @Param("nuevoEstatus") Integer nuevoEstatus);


	Optional<Usuario> findByUsuario(String usuario);

	@Query(nativeQuery = true, value = "select \r\n"
		+	"u.id_usuario,\r\n"
		+	"u.id_estatus_usuario,\r\n"
		+	"u.id_persona,\r\n"
		+	"u.usuario,\r\n"
		+	"\tDATE_FORMAT(u.fecha_alta, '%Y-%m-%d') AS fecha_alta,\r\n"
		+	"p.nombre,\r\n"
		+	"p.apellido_paterno,\r\n"
		+	"p.apellido_materno,\r\n"
		+	"p.rfc,\r\n"
		+	"p.curp,\r\n"
		+	"p.correo_electronico AS email,\r\n"
		+	"p.telefono,\r\n"
		+	"p.ind_status AS estatus_persona,\r\n"
		+	"\tCONCAT(p.nombre,' ',p.apellido_paterno,' ',p.apellido_materno) AS nombre_completo,\r\n"
		+ 	"\tCASE WHEN u.ind_status = 1 THEN 'Activo' \r\n"
				+ "    ELSE 'Inactivo' \r\n"
				+ "    END\r\n"
				+ "as indStatusString, \r\n"
		+	"r.id_rol,\r\n"
		+	"u.ind_status\r\n"
		+	"FROM usuario u\r\n"
		+ "LEFT JOIN persona p ON u.id_persona = p.id_persona \r\n"
		+ "LEFT JOIN usuario_rol r ON u.id_usuario = r.id_usuario  \r\n")
		List<Object[]> findUsers();
	
		
			@Query(nativeQuery = true, value = "select \r\n"
			+	"u.id_usuario,\r\n"
			+	"u.id_estatus_usuario,\r\n"
			+	"u.id_persona,\r\n"
			+	"u.usuario,\r\n"
			+	"\tDATE_FORMAT(u.fecha_alta, '%Y-%m-%d') AS fecha_alta,\r\n"
			+	"p.nombre,\r\n"
			+	"p.apellido_paterno,\r\n"
			+	"p.apellido_materno,\r\n"
			+	"p.rfc,\r\n"
			+	"p.curp,\r\n"
			+	"p.correo_electronico AS email,\r\n"
			+	"p.telefono,\r\n"
			+	"p.ind_status AS estatus_persona,\r\n"
			+	"\tCONCAT(p.nombre,' ',p.apellido_paterno,' ',p.apellido_materno) AS nombre_completo,\r\n"
			+ 	"\tCASE WHEN u.ind_status = 1 THEN 'Activo' \r\n"
					+ "    ELSE 'Inactivo' \r\n"
					+ "    END\r\n"
					+ "as indStatusString, \r\n"
			+	"r.id_rol,\r\n"
			+	"u.ind_status\r\n"
			+	"FROM usuario u\r\n"
			+ "LEFT JOIN persona p ON u.id_persona = p.id_persona  \r\n"
			+ "LEFT JOIN usuario_rol r ON u.id_usuario = r.id_usuario  \r\n"
			+ " WHERE u.id_usuario = ?1 "
			)
			List<Object[]> findIdUser(Long id_usuario);


}
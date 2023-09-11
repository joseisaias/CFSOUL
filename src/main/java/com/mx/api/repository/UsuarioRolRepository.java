package com.mx.api.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mx.api.model.Rol;
import com.mx.api.model.Usuario;
import com.mx.api.model.UsuarioRol;


public interface UsuarioRolRepository  extends JpaRepository<UsuarioRol, Long>{

	List<UsuarioRol> findByIdUsuario(Usuario idUsuario);
	List<UsuarioRol> findByIdRolAndIdUsuario(Rol rol,Usuario usuario);
}

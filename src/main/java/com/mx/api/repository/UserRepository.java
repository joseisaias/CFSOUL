package com.mx.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.mx.api.model.Usuario;

@Repository
public interface UserRepository extends JpaRepository<Usuario, Long> {

	Optional<Usuario> findByUsuario(String usuario);

}
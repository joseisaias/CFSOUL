package com.mx.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mx.api.model.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {
	Optional<Rol> findByClaveRol(String claveRol);

}

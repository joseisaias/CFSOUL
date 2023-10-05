package com.mx.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mx.api.model.Empleado;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

	List<Empleado> findByIdPersonaAndIndStatus(Long idPersona, Integer indStatus);
}

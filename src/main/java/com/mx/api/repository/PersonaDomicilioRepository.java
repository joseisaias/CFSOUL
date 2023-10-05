package com.mx.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mx.api.model.PersonaDomicilio;

public interface PersonaDomicilioRepository extends JpaRepository<PersonaDomicilio, Long> {

	List<PersonaDomicilio> findByIdPersona(Long idPersona);
}

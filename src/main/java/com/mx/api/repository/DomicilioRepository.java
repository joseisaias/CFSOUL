package com.mx.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mx.api.model.Domicilio;

public interface DomicilioRepository extends JpaRepository<Domicilio, Long> {

	Optional<Domicilio> findByIdDomicilioAndIdTipoDomicilio(Long idDomicilio, Long idTipoDomicilio);
	
}

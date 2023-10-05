package com.mx.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.mx.api.model.Persona;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {
	
	
}

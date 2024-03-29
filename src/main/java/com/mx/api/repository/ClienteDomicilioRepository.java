package com.mx.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mx.api.model.ClienteDomicilio;

@Repository
public interface ClienteDomicilioRepository extends JpaRepository<ClienteDomicilio, Long> {

}

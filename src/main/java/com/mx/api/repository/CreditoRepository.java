package com.mx.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mx.api.model.Credito;

@Repository
public interface CreditoRepository extends JpaRepository<Credito, Long> {

}

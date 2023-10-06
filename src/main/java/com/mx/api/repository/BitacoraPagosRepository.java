package com.mx.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mx.api.model.BitacoraPagos;

@Repository
public interface BitacoraPagosRepository extends JpaRepository<BitacoraPagos, Long>{

	List<BitacoraPagos> findByFechaPagoAndIndStatus(String fechaPago, Integer indEstatus );
}

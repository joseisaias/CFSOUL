package com.mx.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mx.api.model.BitacoraPagos;

@Repository
public interface BitacoraPagosRepository extends JpaRepository<BitacoraPagos, Long>{

	List<BitacoraPagos> findByFechaPagoAndIndStatus(String fechaPago, Integer indEstatus );
	
	@Query("select bp from "
			+ " BitacoraPagos bp "
			+ " join Credito c on bp.idCredito = c.idCredito "
			+ " join Empleado e on e.idEmpleado = c.idEmpleado"
			+ " where bp.fechaPago = :fechaPago and e.idCliente = :idCliente and bp.idConciliacion is null")
	List<BitacoraPagos> findByFechaPagoIdCliente(@Param("fechaPago") String fechaPago, @Param("idCliente") Long idCliente );
	
	List<BitacoraPagos> findByidCredito(Long idCredito);
	
}

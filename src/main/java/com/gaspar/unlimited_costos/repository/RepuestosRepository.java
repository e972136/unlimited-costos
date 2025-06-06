package com.gaspar.unlimited_costos.repository;

import com.gaspar.unlimited_costos.entity.Repuestos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RepuestosRepository extends JpaRepository<Repuestos,Integer> {
    List<Repuestos> findAllByIdTransaccion(Integer idTransaccion);

    @Query(value = "SELECT * from repuestos where date_part('year', fecha_factura) = :y and date_part('month', fecha_factura) = :m", nativeQuery = true)
    List<Repuestos> findByYearMonth(Double y, Double m);
}

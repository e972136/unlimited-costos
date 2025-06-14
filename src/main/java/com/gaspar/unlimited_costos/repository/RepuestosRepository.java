package com.gaspar.unlimited_costos.repository;

import com.gaspar.unlimited_costos.entity.Repuestos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RepuestosRepository extends JpaRepository<Repuestos,Integer> {
    List<Repuestos> findAllByIdTransaccion(Integer idTransaccion);

    @Query(value = "SELECT * from repuestos where date_part('year', fecha_factura) = :y and date_part('month', fecha_factura) = :m", nativeQuery = true)
    List<Repuestos> findByYearMonth(Double y, Double m);


    @Query(value = "select DISTINCT proveedor from repuestos order by proveedor", nativeQuery = true)
    List<String> findAllProveedores();

    @Query(value = "select DISTINCT tipo_gasto from repuestos order by tipo_gasto",nativeQuery = true)
    List<String> findAllRepuestos();
}

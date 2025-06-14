package com.gaspar.unlimited_costos.repository;

import com.gaspar.unlimited_costos.entity.Pintura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PinturaRepository extends JpaRepository<Pintura,Integer> {
    List<Pintura> findAllByIdTransaccion(Integer idTransaccion);

    @Query(value = "select distinct tipo from Pintura order by tipo",nativeQuery = true)
    List<String> findDistinctByTipo();
}

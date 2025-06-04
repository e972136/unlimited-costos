package com.gaspar.unlimited_costos.repository;

import com.gaspar.unlimited_costos.entity.Transaccion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransaccionRepository extends JpaRepository<Transaccion,Integer> {
    List<Transaccion> findAllByEstadoContrato(String estado);

    Page<Transaccion> findAllByEstadoContrato(String activo, Pageable page);

    Page<Transaccion> findAllByPlacaContainingIgnoreCaseOrClienteContainingIgnoreCase(String busqueda, String busqueda1, Pageable page);
}

package com.gaspar.unlimited_costos.repository;

import com.gaspar.unlimited_costos.entity.SolicitudRepuestos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SolicitudRepuestosRepository  extends JpaRepository<SolicitudRepuestos,Integer> {
    List<SolicitudRepuestos> findAllByIdTransaccion(Integer idTransaccion);
}

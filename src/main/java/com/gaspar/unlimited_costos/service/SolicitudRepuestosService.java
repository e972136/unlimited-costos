package com.gaspar.unlimited_costos.service;

import com.gaspar.unlimited_costos.entity.SolicitudRepuestos;
import com.gaspar.unlimited_costos.repository.SolicitudRepuestosRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SolicitudRepuestosService {
    private final SolicitudRepuestosRepository solicitudRepuestosRepository;

    public SolicitudRepuestosService(SolicitudRepuestosRepository solicitudRepuestosRepository) {
        this.solicitudRepuestosRepository = solicitudRepuestosRepository;
    }

    public List<SolicitudRepuestos> findAllByIdTransaccion(Integer idTransaccion) {
        return solicitudRepuestosRepository.findAllByIdTransaccion(idTransaccion);
    }
}

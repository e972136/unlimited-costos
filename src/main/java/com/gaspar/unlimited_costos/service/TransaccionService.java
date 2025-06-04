package com.gaspar.unlimited_costos.service;

import com.gaspar.unlimited_costos.entity.Transaccion;
import com.gaspar.unlimited_costos.repository.TransaccionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransaccionService {

    private final TransaccionRepository transaccionRepository;

    public TransaccionService(TransaccionRepository transaccionRepository) {
        this.transaccionRepository = transaccionRepository;
    }

    public List<Transaccion> findAllEstado(String activo) {
        return transaccionRepository.findAllByEstadoContrato(activo);
    }


    public Page<Transaccion> findAllEstadoPage(String activo, Pageable page) {
        return transaccionRepository.findAllByEstadoContrato(activo,page);
    }
}

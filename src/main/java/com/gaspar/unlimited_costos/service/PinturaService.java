package com.gaspar.unlimited_costos.service;

import com.gaspar.unlimited_costos.entity.Pintura;
import com.gaspar.unlimited_costos.repository.PinturaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PinturaService {

    private final PinturaRepository otrosMaterialesRepository;

    public PinturaService(PinturaRepository otrosMaterialesRepository) {
        this.otrosMaterialesRepository = otrosMaterialesRepository;
    }

    public List<Pintura> findAllByIdTransaccion(Integer idTransaccion) {
        return otrosMaterialesRepository.findAllByIdTransaccion(idTransaccion);
    }

    public Pintura save(Pintura solicitud) {
        return otrosMaterialesRepository.save(solicitud);
    }

    public List<String> findAllTipo() {
        return otrosMaterialesRepository.findDistinctByTipo();
    }

    public Pintura deleteRegistro(Integer id) {
        Pintura byId = otrosMaterialesRepository.findById(id).get();
        otrosMaterialesRepository.delete(byId);
        return byId;
    }
}

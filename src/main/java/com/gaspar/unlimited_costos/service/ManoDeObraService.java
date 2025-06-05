package com.gaspar.unlimited_costos.service;

import com.gaspar.unlimited_costos.entity.ManoDeObra;
import com.gaspar.unlimited_costos.repository.ManoDeObraRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManoDeObraService {
    private final ManoDeObraRepository manoDeObraRepository;

    public ManoDeObraService(ManoDeObraRepository manoDeObraRepository) {
        this.manoDeObraRepository = manoDeObraRepository;
    }

    public List<ManoDeObra> findAllByIdTransaccion(Integer idTransaccion) {
        return manoDeObraRepository.findAllByIdTransaccion(idTransaccion);
    }
}

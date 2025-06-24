package com.gaspar.unlimited_costos.service;

import com.gaspar.unlimited_costos.dto.ManoDeObraReporte;
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

    public ManoDeObra save(ManoDeObra solicitud) {
        return manoDeObraRepository.save(solicitud);
    }

    public List<ManoDeObraReporte> findAllByMonth(String periodo) {
        String[] split = periodo.split("-");
        Double year = Double.parseDouble(split[0]);
        Double month = Double.parseDouble(split[1]);
        return manoDeObraRepository.findAllByYearMonto(year,month);
    }

    public List<String> findAllPintores() {
        return manoDeObraRepository.findAllPintores();
    }

    public ManoDeObra deleteRegistro(Integer id) {
        ManoDeObra manoDeObra = manoDeObraRepository.findById(id).get();
        manoDeObraRepository.delete(manoDeObra);
        return manoDeObra;
    }
}

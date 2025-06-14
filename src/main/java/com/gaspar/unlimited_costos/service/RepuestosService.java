package com.gaspar.unlimited_costos.service;

import com.gaspar.unlimited_costos.entity.Repuestos;
import com.gaspar.unlimited_costos.repository.RepuestosRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepuestosService {
    private final RepuestosRepository repuestosRepository;

    public RepuestosService(RepuestosRepository repuestosRepository) {
        this.repuestosRepository = repuestosRepository;
    }

    public List<Repuestos> findAllByIdTransaccion(Integer idTransaccion) {
        return repuestosRepository.findAllByIdTransaccion(idTransaccion);
    }

    public List<Repuestos> findAllByMonth(String periodo) {
        String[] split = periodo.split("-");
        Double year = Double.parseDouble(split[0]);
        Double month = Double.parseDouble(split[1]);
        return repuestosRepository.findByYearMonth(year,month);
    }

    public Repuestos save(Repuestos solicitud) {
        return repuestosRepository.save(solicitud);
    }

    public List<String> findAllProveedores() {
        return repuestosRepository.findAllProveedores();
    }

    public List<String> findAllRepuestos() {
        return repuestosRepository.findAllRepuestos();
    }
}

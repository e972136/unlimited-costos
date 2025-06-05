package com.gaspar.unlimited_costos.repository;

import com.gaspar.unlimited_costos.entity.ManoDeObra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ManoDeObraRepository extends JpaRepository<ManoDeObra,Integer> {
    List<ManoDeObra> findAllByIdTransaccion(Integer idTransaccion);
}

package com.gaspar.unlimited_costos.repository;

import com.gaspar.unlimited_costos.dto.ManoDeObraReporte;
import com.gaspar.unlimited_costos.entity.ManoDeObra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface ManoDeObraRepository extends JpaRepository<ManoDeObra,Integer> {
    List<ManoDeObra> findAllByIdTransaccion(Integer idTransaccion);

    @Query(value = "Select new com.gaspar.unlimited_costos.dto.ManoDeObraReporte(m.pintor, m.fechaDePago, m.montoDePago, t.placa, t.id) " +
            "from ManoDeObra m, Transaccion t " +
            "where m.idTransaccion = t.id "+
            "and date_part('year', m.fechaDePago) = :yy and date_part('month', m.fechaDePago) = :mm"
    )
    List<ManoDeObraReporte> findAllByYearMonto(@Param("yy") Double yy, @Param("mm") Double mm);

    /*
    update mano_de_obra
set pintor = tabla_b.pintor_encargado
from (select id,pintor_encargado  from transaccion ) as tabla_b
where mano_de_obra.id_transaccion = tabla_b.id;

     */


}

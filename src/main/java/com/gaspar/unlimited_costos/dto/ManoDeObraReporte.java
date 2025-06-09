package com.gaspar.unlimited_costos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.gaspar.unlimited_costos.util.MetodosGenerales.cambioFormatoAEstandar;

@Data
public class ManoDeObraReporte{
        String pintor;
        LocalDate fechaDePago;
        String montoDePagoStr;
        BigDecimal montoDePago;
        String placa;
        Integer idTransaccion;

    public ManoDeObraReporte(String pintor, LocalDate fechaDePago, BigDecimal montoDePago, String placa, Integer idTransaccion) {
        this.pintor = pintor;
        this.fechaDePago = fechaDePago;
        this.montoDePago = montoDePago;
        this.montoDePagoStr = cambioFormatoAEstandar(montoDePago.toString());
        this.placa = placa;
        this.idTransaccion = idTransaccion;
    }
}

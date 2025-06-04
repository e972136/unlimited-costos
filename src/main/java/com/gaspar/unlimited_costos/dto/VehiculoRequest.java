package com.gaspar.unlimited_costos.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class VehiculoRequest {
    Integer id;

    String numeroSiniestro;

    @JsonFormat(pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate fechaIngreso;

    String cliente;
    String placa;
    String marca;
    String anio;
    String color;
    String tipoDeVehiculo;
    BigDecimal planificadoManoDeObra;
    BigDecimal planificadoMateriales;
    String pintorEncargado;

}

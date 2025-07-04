package com.gaspar.unlimited_costos.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class Transaccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String cobradoSegunFactura;


    @JsonFormat(pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate deFecha;

    BigDecimal valorFacturado;

    String placa;
    String cliente;
    String marca;
    String tipoDeVehiculo;
    String color;
    String anio;

    @JsonFormat(pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate fechaIngreso;

    @JsonFormat(pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate fechaDeEntrega;

    String pintorEncargado;
    BigDecimal planificadoMateriales;
    BigDecimal planificadoManoDeObra;
    String estadoContrato;
    String numeroSiniestro;
    String aseguradora;

    @JsonFormat(pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate fechaSistema;
}

package com.gaspar.unlimited_costos.controller;

import com.gaspar.unlimited_costos.dto.VehiculoRequest;
import com.gaspar.unlimited_costos.entity.SolicitudRepuestos;
import com.gaspar.unlimited_costos.service.SolicitudRepuestosService;
import com.gaspar.unlimited_costos.service.TransaccionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import static com.gaspar.unlimited_costos.util.MetodosGenerales.cambioFormatoAEstandar;

@Controller
@RequestMapping("/solicitud-repuesto")
public class SolicitudRepuestosController {

    private final TransaccionService transaccionService;
    private final SolicitudRepuestosService solicitudRepuestosService;

    public SolicitudRepuestosController(TransaccionService transaccionService, SolicitudRepuestosService solicitudRepuestosService) {
        this.transaccionService = transaccionService;
        this.solicitudRepuestosService = solicitudRepuestosService;
    }

    @GetMapping("/pedido/{idTransaccion}")
    public ModelAndView pedido(
        @PathVariable Integer idTransaccion
    ){
        VehiculoRequest vehiculo = transaccionService.findById(idTransaccion).map(
                i-> VehiculoRequest.of(
                        i.getId(),
                        i.getNumeroSiniestro(),
                        i.getFechaIngreso(),
                        i.getCliente(),
                        i.getPlaca(),
                        i.getMarca(),
                        i.getAnio(),
                        i.getColor(),
                        i.getTipoDeVehiculo(),
                        i.getPlanificadoManoDeObra(),
                        i.getPlanificadoMateriales(),
                        i.getPintorEncargado(),
                        i.getAseguradora()
                )
        ).get();

        List<SolicitudRepuestos> solicitudRepuestosList = solicitudRepuestosService.findAllByIdTransaccion(idTransaccion);

        BigDecimal calculado = solicitudRepuestosList.stream().map(SolicitudRepuestos::getValorPrevisto).reduce(BigDecimal.ZERO, (subtotal, element) -> subtotal.add(element));

        solicitudRepuestosList.sort(Comparator.comparing(SolicitudRepuestos::getTipoGasto));

        ModelAndView mav = new ModelAndView("./page/repuestos-solicitud");
        mav.addObject("solicitudRepuestosList", solicitudRepuestosList);
        mav.addObject("vehiculo", vehiculo);
        mav.addObject("solicitud",new SolicitudRepuestos());
        mav.addObject("calculado",cambioFormatoAEstandar(calculado.toString()));


        return mav;
    }

    @PostMapping("/guardar/{idTransaccion}")
    public ModelAndView salvar(
            @PathVariable Integer idTransaccion,
            @ModelAttribute SolicitudRepuestos solicitud
    ){
        solicitud.setIdTransaccion(idTransaccion);
        solicitud.setEntregado(false);
        solicitud.setFechaSistema(LocalDate.now());
        SolicitudRepuestos nuevo = solicitudRepuestosService.save(solicitud);
        return new ModelAndView("redirect:/solicitud-repuesto/pedido/"+idTransaccion);
    }
}

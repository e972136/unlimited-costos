package com.gaspar.unlimited_costos.controller;

import com.gaspar.unlimited_costos.dto.VehiculoRequest;
import com.gaspar.unlimited_costos.entity.Pintura;
import com.gaspar.unlimited_costos.service.PinturaService;
import com.gaspar.unlimited_costos.service.TransaccionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static com.gaspar.unlimited_costos.util.MetodosGenerales.cambioFormatoAEstandar;

@Controller
@RequestMapping("/otros-materiales")
public class OtrosMaterialesController {
    //cargar-otros-materiales

    private final TransaccionService transaccionService;
    private final PinturaService otrosMaterialesService;

    public OtrosMaterialesController(TransaccionService transaccionService, PinturaService otrosMaterialesService) {
        this.transaccionService = transaccionService;
        this.otrosMaterialesService = otrosMaterialesService;
    }

    @GetMapping("/cargar/{idTransaccion}")
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

        List<Pintura> otrosMaterialesList =  otrosMaterialesService.findAllByIdTransaccion(idTransaccion);
        BigDecimal calculado = otrosMaterialesList.stream().map(Pintura::getValorTotal).reduce(BigDecimal.ZERO, BigDecimal::add);

        ModelAndView mav = new ModelAndView("./page/cargar-otros-materiales");
        mav.addObject("vehiculo", vehiculo);
        mav.addObject("solicitud", new Pintura());
        mav.addObject("otrosMaterialesList", otrosMaterialesList);
        mav.addObject("calculado",cambioFormatoAEstandar(calculado.toString()));
        mav.addObject("planificado",cambioFormatoAEstandar(vehiculo.getPlanificadoMateriales().toString()));
        return mav;
    }

    @PostMapping("/guardar/{idTransaccion}")
    public ModelAndView pedido(
            @PathVariable Integer idTransaccion,
            @ModelAttribute Pintura solicitud
    ){
        solicitud.setIdTransaccion(idTransaccion);
        solicitud.setFechaSistema(LocalDate.now());
        Pintura otrosMateriales = otrosMaterialesService.save(solicitud);
        return new ModelAndView("redirect:/otros-materiales/cargar/"+idTransaccion);
    }

}

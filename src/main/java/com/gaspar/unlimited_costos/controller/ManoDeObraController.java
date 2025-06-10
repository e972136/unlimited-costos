package com.gaspar.unlimited_costos.controller;

import com.gaspar.unlimited_costos.dto.VehiculoRequest;
import com.gaspar.unlimited_costos.entity.ManoDeObra;
import com.gaspar.unlimited_costos.service.ManoDeObraService;
import com.gaspar.unlimited_costos.service.TransaccionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static com.gaspar.unlimited_costos.util.MetodosGenerales.cambioFormatoAEstandar;


@Controller
@RequestMapping("/mano-de-obra")
public class ManoDeObraController {

    private final TransaccionService transaccionService;
    private final ManoDeObraService manoDeObraService;


    public ManoDeObraController(TransaccionService transaccionService, ManoDeObraService manoDeObraService) {
        this.transaccionService = transaccionService;
        this.manoDeObraService = manoDeObraService;
    }

    @GetMapping("/cargar/{idTransaccion}")
    public ModelAndView cargar(
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

        List<ManoDeObra> manoDeObraList = manoDeObraService.findAllByIdTransaccion(idTransaccion);

        BigDecimal calculado = manoDeObraList.stream().map(ManoDeObra::getMontoDePago).reduce(BigDecimal.ZERO, BigDecimal::add);


        ModelAndView mav = new ModelAndView("./page/cargar-mano-de-obra");

        ManoDeObra manoDeObra = new ManoDeObra();
        manoDeObra.setPintor(vehiculo.getPintorEncargado());

        mav.addObject("solicitud", manoDeObra);
        mav.addObject("vehiculo", vehiculo);
        mav.addObject("manoDeObraList", manoDeObraList);
        mav.addObject("calculado",cambioFormatoAEstandar(calculado.toString()));
        mav.addObject("planificado",cambioFormatoAEstandar(vehiculo.getPlanificadoManoDeObra().toString()));
        return mav;
    }

    @PostMapping("/guardar/{idTransaccion}")
    public ModelAndView guardar(
            @PathVariable Integer idTransaccion,
            @ModelAttribute ManoDeObra solicitud
    ){
        System.out.println("patito");
        solicitud.setIdTransaccion(idTransaccion);
        solicitud.setFechaSistema(LocalDate.now());
        ManoDeObra bd = manoDeObraService.save(solicitud);
        return new ModelAndView("redirect:/mano-de-obra/cargar/"+idTransaccion);
    }
}

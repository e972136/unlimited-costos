package com.gaspar.unlimited_costos.controller;

import com.gaspar.unlimited_costos.dto.VehiculoRequest;
import com.gaspar.unlimited_costos.entity.Repuestos;
import com.gaspar.unlimited_costos.entity.SolicitudRepuestos;
import com.gaspar.unlimited_costos.service.RepuestosService;
import com.gaspar.unlimited_costos.service.SolicitudRepuestosService;
import com.gaspar.unlimited_costos.service.TransaccionService;
import com.gaspar.unlimited_costos.util.ComboItem;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static com.gaspar.unlimited_costos.util.MetodosGenerales.cambioFormatoAEstandar;

@CrossOrigin
@Controller
@RequestMapping("/repuestos")
public class RepuestosController {
    private final TransaccionService transaccionService;
    private final RepuestosService repuestosService;
    private final SolicitudRepuestosService solicitudRepuestosService;


    public RepuestosController(TransaccionService transaccionService, RepuestosService repuestosService, SolicitudRepuestosService solicitudRepuestosService) {
        this.transaccionService = transaccionService;
        this.repuestosService = repuestosService;
        this.solicitudRepuestosService = solicitudRepuestosService;
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
        List<Repuestos> repuestosList = repuestosService.findAllByIdTransaccion(idTransaccion);
        BigDecimal calculado = repuestosList.stream().map(Repuestos::getValorDelGasto).reduce(BigDecimal.ZERO,BigDecimal::add);

        List<SolicitudRepuestos> solicitudRepuestosList = solicitudRepuestosService.findAllByIdTransaccion(idTransaccion);
        BigDecimal planificado = solicitudRepuestosList.stream().map(SolicitudRepuestos::getValorPrevisto).reduce(BigDecimal.ZERO,BigDecimal::add);

        List<ComboItem> solicitudlist = solicitudRepuestosList.stream().map(e ->
                new ComboItem(e.getTipoGasto(), e.getTipoGasto())
        ).toList();

        List<String> proveedores = repuestosService.findAllProveedores();
        List<String> repuestos = repuestosService.findAllRepuestos();

        ModelAndView mav = new ModelAndView("./page/repuestos-compra");

        mav.addObject("vehiculo", vehiculo);
        mav.addObject("proveedores", proveedores);
        mav.addObject("repuestos", repuestos);
        mav.addObject("solicitud", new Repuestos());
        mav.addObject("repuestosList", repuestosList);
        mav.addObject("solicitudlist", solicitudlist);
        mav.addObject("calculado",cambioFormatoAEstandar(calculado.toString()));
        mav.addObject("planificado",cambioFormatoAEstandar(planificado.toString()));
        return mav;
    }

    @PostMapping("/guardar/{idTransaccion}")
    public ModelAndView guardar(
            @PathVariable Integer idTransaccion,
            @ModelAttribute Repuestos solicitud
    ){
        solicitud.setIdTransaccion(idTransaccion);
        solicitud.setEstadoGasto("USADO");
        solicitud.setFechaSistema(LocalDate.now());
        Repuestos bd = repuestosService.save(solicitud);
        return new ModelAndView("redirect:/repuestos/cargar/"+idTransaccion);
    }


    @GetMapping("/borrar")
    public ModelAndView eliminarRepuesto(
            @RequestParam Integer id
    ){
        Repuestos bd = repuestosService.deleteRegistro(id);
        return new ModelAndView("redirect:/repuestos/cargar/"+bd.getIdTransaccion());
    }
}

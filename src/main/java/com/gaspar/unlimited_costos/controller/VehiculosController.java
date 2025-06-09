package com.gaspar.unlimited_costos.controller;

import com.gaspar.unlimited_costos.dto.ManoDeObraReporte;
import com.gaspar.unlimited_costos.dto.VehiculoRequest;
import com.gaspar.unlimited_costos.entity.ManoDeObra;
import com.gaspar.unlimited_costos.entity.Pintura;
import com.gaspar.unlimited_costos.entity.Repuestos;
import com.gaspar.unlimited_costos.entity.Transaccion;
import com.gaspar.unlimited_costos.service.ManoDeObraService;
import com.gaspar.unlimited_costos.service.PinturaService;
import com.gaspar.unlimited_costos.service.RepuestosService;
import com.gaspar.unlimited_costos.service.TransaccionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static com.gaspar.unlimited_costos.util.MetodosGenerales.cambioFormatoAEstandar;
import static java.util.Objects.isNull;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/vehiculo")
public class VehiculosController {

    private final TransaccionService transaccionService;
    private final RepuestosService repuestosService;
    private final ManoDeObraService manoDeObraService;
    private final PinturaService otrosGastosService;

    public VehiculosController(TransaccionService transaccionService, RepuestosService repuestosService, ManoDeObraService manoDeObraService, PinturaService otrosGastosService) {
        this.transaccionService = transaccionService;
        this.repuestosService = repuestosService;
        this.manoDeObraService = manoDeObraService;
        this.otrosGastosService = otrosGastosService;
    }

    @GetMapping("/listado")
    public ModelAndView inicio(
            @PageableDefault(size = 11)
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "fechaIngreso", direction = Sort.Direction.DESC),
                    @SortDefault(sort = "cliente", direction = Sort.Direction.ASC),
                    @SortDefault(sort = "placa", direction = Sort.Direction.ASC)
            }) Pageable page,
            @RequestParam(required = false) String busqueda
    )
    {
        Page<Transaccion> vehiculos = null;

        if(isNull(busqueda) || busqueda.isEmpty()){
            vehiculos = transaccionService.findAllEstadoPage("ACTIVO",page);
        }else{
            vehiculos = transaccionService.findAllEstadoBusquedaPage("ACTIVO",busqueda,page);
        }


        ModelAndView mav = new ModelAndView("./page/vehiculos");
        mav.addObject("vehiculos", vehiculos);

        return mav;
    }

    @GetMapping("/ingreso")
    public ModelAndView ingresarNuevo(

    )
    {
        ModelAndView mav = new ModelAndView("./page/vehiculos-crear");
        mav.addObject("vehiculo", new VehiculoRequest());
        return mav;
    }

    @PostMapping("/ingreso")
    public ModelAndView ingresarNuevoBd(
            @ModelAttribute VehiculoRequest vehiculo
    ){
        System.out.println("patito");
        System.out.println(vehiculo);
        Optional<Transaccion> vehiculoBD = transaccionService.save(vehiculo);
        if(!vehiculoBD.isPresent()){
            return new ModelAndView(".redirect:/vehiculo/ingreso");
        }
        return new ModelAndView("redirect:/vehiculo/editar/" + vehiculoBD.get().getId());
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editarVehiculo(
            @PathVariable Integer id
    ){
        ModelAndView mav = new ModelAndView("./page/vehiculos-editar");

        VehiculoRequest vehiculo = transaccionService.findById(id).map(
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

        mav.addObject("vehiculo", vehiculo);
        return mav;
    }

    @GetMapping("control-costos/{id}")
    public ModelAndView controlCostos(
            @PathVariable Integer id
    ) {
        ModelAndView mav = new ModelAndView("./page/control-costos");
        Transaccion vehiculo = transaccionService.findById(id).get();
        List<Repuestos> repuestosComprados = repuestosService.findAllByIdTransaccion(id);
        List<ManoDeObra> manoDeObra = manoDeObraService.findAllByIdTransaccion(id);
        List<Pintura> otrosGastos = otrosGastosService.findAllByIdTransaccion(id);






        BigDecimal totalRepuestos = repuestosComprados.stream().map(e->e.getValorDelGasto()).reduce(BigDecimal.ZERO,BigDecimal::add);
        BigDecimal totalPinturas = otrosGastos.stream().map(e->e.getValorTotal()).reduce(BigDecimal.ZERO,BigDecimal::add);
        BigDecimal totalManoObra = manoDeObra.stream().map(e->e.getMontoDePago()).reduce(BigDecimal.ZERO,BigDecimal::add);

        mav.addObject("vehiculo", vehiculo);
        mav.addObject("repuestosComprados", repuestosComprados);
        mav.addObject("manoDeObra", manoDeObra);
        mav.addObject("otrosGastos", otrosGastos);

        mav.addObject("totalRepuestos",cambioFormatoAEstandar(totalRepuestos.toString()));
        mav.addObject("totalPinturas", cambioFormatoAEstandar(totalPinturas.toString()));
        mav.addObject("totalManoObra", cambioFormatoAEstandar(totalManoObra.toString()));

        BigDecimal totalGastado = totalRepuestos.add(totalPinturas).add(totalManoObra);


        mav.addObject("totalMateriales", cambioFormatoAEstandar(totalPinturas.toString()));
        mav.addObject("totalGastado", cambioFormatoAEstandar(totalGastado.toString()));

        return mav;
    }

    @GetMapping("/resumen-gastos")
    public ModelAndView resumenGastos()
    {
        return new ModelAndView("./page/resumen-gastos");
    }

    @GetMapping("/resumen-gastos/repuestos/{periodo}")
    public ModelAndView resumenGastosRepuestos(
            @PathVariable String periodo
    ){
        ModelAndView mav = new ModelAndView("./page/resumen-gastos-repuestos");


        List<Repuestos> repuestosList = repuestosService.findAllByMonth(periodo);
        List<Integer> idTransaccion = repuestosList.stream().map(Repuestos::getIdTransaccion).toList();
        Map<Integer, Transaccion> transaccionMap = transaccionService.findAllById(idTransaccion).stream().collect(Collectors.toMap(e -> e.getId(), Function.identity()));

        Map<Transaccion, List<Repuestos>> collect = repuestosList.stream().collect(Collectors.groupingBy(e -> transaccionMap.get(e.getIdTransaccion())));

        List<RepuestosMensual> lista = new ArrayList<>();
        final BigDecimal[] totalMes = {BigDecimal.ZERO};
        collect.forEach((i,v)-> {
            BigDecimal totalCarro =  v.stream().map(Repuestos::getValorDelGasto).reduce(BigDecimal.ZERO,BigDecimal::add);
            totalMes[0] = totalMes[0].add(totalCarro);
            lista.add(new RepuestosMensual(i, v, cambioFormatoAEstandar(totalCarro.toString())));
        });

        String mensaje = "Del periodo: " + periodo;

        mav.addObject("mensaje",mensaje);
        mav.addObject("lista", lista);
        mav.addObject("totalMes", cambioFormatoAEstandar(totalMes[0].toString()));

        return mav;
    }

    @GetMapping("/resumen-gastos/mano-obra/{periodo}")
    public ModelAndView resumenGastosManoObra(
            @PathVariable String periodo
    ){
        ModelAndView mav = new ModelAndView("./page/resumen-gastos-mano-de-obra");

        List<ManoDeObraReporte> manoDeObra = manoDeObraService.findAllByMonth(periodo);
        Map<String, List<ManoDeObraReporte>> collect = manoDeObra.stream()
                .collect(Collectors.groupingBy(ManoDeObraReporte::getPintor,TreeMap::new,Collectors.toList()));
        List<ManoObraMensual> lista = new ArrayList<>();
        final BigDecimal[] totalMes = {BigDecimal.ZERO};
        collect.forEach((i,v)->{
            v.sort(Comparator.comparing(ManoDeObraReporte::getFechaDePago)
                    .thenComparing(ManoDeObraReporte::getPlaca));

            BigDecimal totalPintor =  v.stream().map(ManoDeObraReporte::getMontoDePago).reduce(BigDecimal.ZERO,BigDecimal::add);
            totalMes[0] = totalMes[0].add(totalPintor);

            lista.add(new ManoObraMensual(i,cambioFormatoAEstandar(totalPintor.toString()),v));
                });

        String mensaje = "Del periodo: " + periodo;

        mav.addObject("mensaje",mensaje);
        mav.addObject("lista", lista);
        mav.addObject("totalMes", cambioFormatoAEstandar(totalMes[0].toString()));
        return mav;
    }

    record ManoObraMensual(
            String pintor,
            String totalPintor,
            List<ManoDeObraReporte> list
    )
    {}

    record RepuestosMensual(
            Transaccion vehiculo,
            List<Repuestos> repuestosList,
            String totalPorCarro
    ){}
}

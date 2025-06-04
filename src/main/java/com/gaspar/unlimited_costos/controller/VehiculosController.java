package com.gaspar.unlimited_costos.controller;

import com.gaspar.unlimited_costos.dto.VehiculoRequest;
import com.gaspar.unlimited_costos.entity.Transaccion;
import com.gaspar.unlimited_costos.service.TransaccionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import static java.util.Objects.isNull;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/vehiculo")
public class VehiculosController {

    private final TransaccionService transaccionService;

    public VehiculosController(TransaccionService transaccionService) {
        this.transaccionService = transaccionService;
    }

    @GetMapping("/listado")
    public ModelAndView inicio(
            @PageableDefault(size = 15)
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "fechaIngreso", direction = Sort.Direction.DESC),
                    @SortDefault(sort = "cliente", direction = Sort.Direction.ASC)
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

    @GetMapping("/editar/{id}")
    public ModelAndView editarVehiculo(
            @PathVariable Integer id
    ){
        ModelAndView mav = new ModelAndView("./page/vehiculos-editar");

        Optional<VehiculoRequest> vehiculo = transaccionService.findById(id).map(
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
                        i.getPintorEncargado()
                )
        );

        mav.addObject("vehiculo", vehiculo.get());
        return mav;
    }
}

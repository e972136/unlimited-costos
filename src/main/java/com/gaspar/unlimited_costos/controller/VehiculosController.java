package com.gaspar.unlimited_costos.controller;

import com.gaspar.unlimited_costos.entity.Transaccion;
import com.gaspar.unlimited_costos.service.TransaccionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

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
            }) Pageable page
    )
    {
        Page<Transaccion> vehiculos = transaccionService.findAllEstadoPage("ACTIVO",page);

        ModelAndView mav = new ModelAndView("./page/vehiculos");
        mav.addObject("vehiculos", vehiculos);

        return mav;
    }
}

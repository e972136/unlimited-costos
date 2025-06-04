package com.gaspar.unlimited_costos.controller;

import com.gaspar.unlimited_costos.repository.*;
import com.gaspar.unlimited_costos.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CostosController {

//    private final ManoDeObraService manoDeObraService;
//    private final MaterialesYPinturaService materialesYPinturaService;
//    private final PinturaService pinturaService;
//    private final RepuestosService repuestosService;
//    private final SolicitudRepuestosService solicitudRepuestosService;
//    private final TransaccionService trnsaccionService;



    @GetMapping()
    public ModelAndView inicio(
    )
    {
        ModelAndView mav = new ModelAndView("./principal");


        return mav;
    }

}

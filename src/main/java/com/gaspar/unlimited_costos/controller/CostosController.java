package com.gaspar.unlimited_costos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@CrossOrigin
@Controller
public class CostosController {


    @GetMapping()
    public ModelAndView inicio(
    )
    {
        ModelAndView mav = new ModelAndView("./principal");


        return mav;
    }

}

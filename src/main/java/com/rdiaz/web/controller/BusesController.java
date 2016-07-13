package com.rdiaz.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/vehiculos/buses")
public class BusesController extends BaseController
{
    @RequestMapping
    public String buses(ModelMap model)
    {
        model.addAttribute("titulo", "Buses");
        model.addAttribute("marcas", marcas.getMarcas());
        model.addAttribute("vehiculos", vehiculos.listaDeBuses());
        return "vehiculos";
    }
}

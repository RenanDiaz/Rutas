package com.rdiaz.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class BusesController extends BaseController
{
    @RequestMapping(value = "/buses", method = RequestMethod.GET)
    public String buses(ModelMap model)
    {
        model.addAttribute("titulo", "Buses");
        model.addAttribute("marcas", marcas.getMarcas());
        model.addAttribute("vehiculos", vehiculos.getBuses());
        return "vehiculos";
    }
}

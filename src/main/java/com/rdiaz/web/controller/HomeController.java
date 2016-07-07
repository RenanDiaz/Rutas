package com.rdiaz.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController extends BaseController
{
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String inicio(ModelMap model)
    {
        model.addAttribute("buses", vehiculos.getBuses().size());
        model.addAttribute("taxis", vehiculos.getTaxis().size());
        model.addAttribute("particulares", vehiculos.getParticulares().size());
        model.addAttribute("rutas", rutas.size());
        model.addAttribute("rutasAsignadas", rutasAsignadas.size());
        model.addAttribute("ubicaciones", ubicaciones.size());
        return "home";
    }
}
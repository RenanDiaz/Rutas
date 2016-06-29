package com.rdiaz.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ParticularesController extends BaseController
{
    @RequestMapping(value = "/particulares", method = RequestMethod.GET)
    public String particulares(ModelMap model)
    {
        model.addAttribute("titulo", "Particulares");
        model.addAttribute("marcas", marcas.lista());
        model.addAttribute("vehiculos", vehiculos.particulares());
        return "vehiculos";
    }
    
    @RequestMapping(value = "/particulares/{placa}", method = RequestMethod.GET)
    public String particular(ModelMap model, @PathVariable("placa") String placa)
    {
        model.addAttribute("vehiculo", vehiculos.get(placa));
        model.addAttribute("marcas", marcas.lista());
        return "vehiculo";
    }
}

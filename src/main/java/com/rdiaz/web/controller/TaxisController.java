package com.rdiaz.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TaxisController extends BaseController
{
    @RequestMapping(value = "/taxis", method = RequestMethod.GET)
    public String taxis(ModelMap model)
    {
        model.addAttribute("titulo", "Taxis");
        model.addAttribute("marcas", marcas.lista());
        model.addAttribute("vehiculos", vehiculos.taxis());
        return "vehiculos";
    }
    
    @RequestMapping(value = "/taxis/{placa}", method = RequestMethod.GET)
    public String taxi(ModelMap model, @PathVariable("placa") String placa)
    {
        model.addAttribute("vehiculo", vehiculos.get(placa));
        model.addAttribute("marcas", marcas.lista());
        return "vehiculo";
    }
}

package com.rdiaz.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TaxisController extends BaseController
{
    @RequestMapping(value = "/taxis", method = RequestMethod.GET)
    public String taxis(ModelMap model)
    {
        model.addAttribute("titulo", "Taxis");
        model.addAttribute("marcas", marcas.getMarcas());
        model.addAttribute("vehiculos", vehiculos.listaDeTaxis());
        return "vehiculos";
    }
}

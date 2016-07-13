package com.rdiaz.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/vehiculos/taxis")
public class TaxisController extends BaseController
{
    @RequestMapping
    public String taxisView(ModelMap model)
    {
        model.addAttribute("titulo", "Taxis");
        model.addAttribute("marcas", marcas.getMarcas());
        model.addAttribute("vehiculos", vehiculos.listaDeTaxis());
        return "vehiculos";
    }
}

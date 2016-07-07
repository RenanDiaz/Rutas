package com.rdiaz.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/mapa")
public class MapaController extends BaseController
{
    @RequestMapping(value = "{placa}")
    public String mapa(ModelMap model, @PathVariable String placa)
    {
        model.addAttribute("vehiculo", vehiculos.get(placa));
        return "mapa";
    }
}

package com.rdiaz.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/vehiculos/particulares")
public class ParticularesController extends BaseController
{
    @RequestMapping
    public String particularesView(ModelMap model)
    {
        model.addAttribute("titulo", "Particulares");
        model.addAttribute("marcas", marcas.getMarcas());
        model.addAttribute("vehiculos", vehiculos.listaDeParticulares());
        return "vehiculos";
    }
}

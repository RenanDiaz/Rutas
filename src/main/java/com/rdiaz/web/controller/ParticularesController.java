package com.rdiaz.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ParticularesController extends BaseController
{
    @RequestMapping(value = "/particulares", method = RequestMethod.GET)
    public String particulares(ModelMap model)
    {
        model.addAttribute("titulo", "Particulares");
        model.addAttribute("marcas", marcas.getMarcas());
        model.addAttribute("vehiculos", vehiculos.listaDeParticulares());
        return "vehiculos";
    }
}

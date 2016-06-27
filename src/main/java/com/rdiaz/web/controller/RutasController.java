package com.rdiaz.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RutasController extends BaseController
{
    @RequestMapping(value = "/obtener/ubicacion/{placa}/", method = RequestMethod.GET)
    public String printWelcome(ModelMap model, @PathVariable("placa") String placa)
    {
        model.addAttribute("vehiculo", vehiculos.get(placa));
        return "vehiculo";
    }
}
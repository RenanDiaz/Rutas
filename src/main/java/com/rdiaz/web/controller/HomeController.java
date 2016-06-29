package com.rdiaz.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.rdiaz.model.Ubicacion;

@Controller
public class HomeController extends BaseController
{
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String printWelcome(ModelMap model)
    {
        model.addAttribute("buses", vehiculos.buses().size());
        model.addAttribute("taxis", vehiculos.taxis().size());
        model.addAttribute("particulares", vehiculos.particulares().size());
        model.addAttribute("ubicaciones", Ubicacion.todas().size());
        return "home";
    }
}
package com.rdiaz.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/mapa")
public class MapaController extends BaseController
{
    @RequestMapping(value = "ruta/{placa}")
    public String rutaMapaView(ModelMap model, @PathVariable String placa, @RequestParam(value = "inicio", defaultValue = "1") int inicio, @RequestParam(value = "fin", defaultValue = "0") int fin)
    {
        model.addAttribute("vehiculo", vehiculos.get(placa));
        model.addAttribute("inicio", inicio);
        model.addAttribute("fin", fin);
        return "mapa";
    }
    
    @RequestMapping(value = "punto/{id}")
    public String puntoMapaView(ModelMap model, @PathVariable int id)
    {
        model.addAttribute("vehiculo", ubicaciones.get(id).getVehiculo());
        model.addAttribute("inicio", id);
        model.addAttribute("fin", id);
        return "mapa";
    }
}

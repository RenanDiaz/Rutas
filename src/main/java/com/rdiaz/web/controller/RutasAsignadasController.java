package com.rdiaz.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rdiaz.model.RutaAsignada;

@Controller
@RequestMapping(value = "/asignacion")
public class RutasAsignadasController extends BaseController
{
    @RequestMapping(value = "agregar", method = RequestMethod.POST)
    @ResponseBody
    public String agregarAsignacion(@RequestParam(value = "placa") String placa, @RequestParam(value = "ruta") int ruta, @RequestParam(value = "inicio") long inicio, @RequestParam(value = "fin") long fin)
    {
        rutasAsignadas.add(new RutaAsignada(vehiculos.get(placa), rutas.get(ruta), inicio, fin));
        return "success";
    }
    
    @RequestMapping
    public String getAsignaciones(ModelMap model)
    {
        model.addAttribute("rutasAsignadas", rutasAsignadas.getRutasAsignadas());
        model.addAttribute("vehiculos", vehiculos.getVehiculos());
        model.addAttribute("rutas", rutas.getRutas());
        return "rutasAsignadas";
    }
}

package com.rdiaz.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rdiaz.model.RutaAsignada;

@Controller
@RequestMapping(value = "/asignacion")
public class RutaAsignadaController extends BaseController
{
    @RequestMapping(value = "agregar", method = RequestMethod.POST)
    @ResponseBody
    public RutaAsignada agregarAsignacion(@RequestParam(value = "placa") String placa, @RequestParam(value = "ruta") int ruta, @RequestParam(value = "partida") long partida, @RequestParam(value = "llegada") long llegada)
    {
        RutaAsignada rutaAsignada = new RutaAsignada(vehiculos.get(placa), rutas.get(ruta), partida, llegada);
        rutasAsignadas.add(rutaAsignada);
        return rutaAsignada;
    }
    @RequestMapping(value = "editar", method = RequestMethod.POST)
    @ResponseBody
    public RutaAsignada editarAsignacion(@RequestParam(value = "id") int id, @RequestParam(value = "placa") String placa, @RequestParam(value = "ruta") int ruta, @RequestParam(value = "partida") long partida, @RequestParam(value = "llegada") long llegada)
    {
        RutaAsignada rutaAsignada = rutasAsignadas.get(id);
        rutaAsignada.editar(vehiculos.get(placa), rutas.get(ruta), partida, llegada);
        return rutaAsignada;
    }
    
    @RequestMapping
    public String asignacionesView(ModelMap model)
    {
        model.addAttribute("rutasAsignadas", rutasAsignadas.getRutasAsignadas());
        model.addAttribute("vehiculos", vehiculos.getVehiculos());
        model.addAttribute("rutas", rutas.getRutas());
        return "rutasAsignadas";
    }
    
    @RequestMapping(value = "{id}")
    public String asignacionView(ModelMap model, @PathVariable int id)
    {
        model.addAttribute("rutaAsignada", rutasAsignadas.get(id));
        model.addAttribute("vehiculos", vehiculos.getVehiculos());
        model.addAttribute("rutas", rutas.getRutas());
        model.addAttribute("total", rutasAsignadas.size());
        return "rutaAsignada";
    }
}

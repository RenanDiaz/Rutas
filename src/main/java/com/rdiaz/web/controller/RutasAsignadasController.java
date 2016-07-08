package com.rdiaz.web.controller;

import java.sql.Timestamp;

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
public class RutasAsignadasController extends BaseController
{
    @RequestMapping(value = "agregar", method = RequestMethod.POST)
    @ResponseBody
    public String agregarAsignacion(@RequestParam(value = "placa") String placa, @RequestParam(value = "ruta") int ruta, @RequestParam(value = "partida") long partida, @RequestParam(value = "llegada") long llegada)
    {
        rutasAsignadas.add(new RutaAsignada(vehiculos.get(placa), rutas.get(ruta), partida, llegada));
        return "success";
    }
    @RequestMapping(value = "editar", method = RequestMethod.POST)
    @ResponseBody
    public String editarAsignacion(@RequestParam(value = "id") int id, @RequestParam(value = "placa") String placa, @RequestParam(value = "ruta") int ruta, @RequestParam(value = "partida") long partida, @RequestParam(value = "llegada") long llegada)
    {
        RutaAsignada rutaAsignada = rutasAsignadas.get(id);
        rutaAsignada.editar(vehiculos.get(placa), rutas.get(ruta), partida, llegada);
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
    
    @RequestMapping(value = "{id}")
    public String getAsignacion(ModelMap model, @PathVariable int id)
    {
        model.addAttribute("rutaAsignada", rutasAsignadas.get(id));
        model.addAttribute("vehiculos", vehiculos.getVehiculos());
        model.addAttribute("rutas", rutas.getRutas());
        return "rutaAsignada";
    }
}

package com.rdiaz.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rdiaz.model.Asignacion;

@Controller
@RequestMapping(value = "/asignacion")
public class AsignacionesController extends BaseController
{
    @RequestMapping(value = "agregar", method = RequestMethod.POST)
    @ResponseBody
    public Asignacion agregarAsignacion(@RequestParam(value = "placa") String placa, @RequestParam(value = "ruta") int ruta, @RequestParam(value = "partida") long partida, @RequestParam(value = "llegada") long llegada)
    {
        Asignacion asignacion = new Asignacion(vehiculos.get(placa), rutas.get(ruta), partida, llegada);
        asignaciones.add(asignacion);
        return asignacion;
    }
    @RequestMapping(value = "editar", method = RequestMethod.POST)
    @ResponseBody
    public Asignacion editarAsignacion(@RequestParam(value = "id") int id, @RequestParam(value = "placa") String placa, @RequestParam(value = "ruta") int ruta, @RequestParam(value = "partida") long partida, @RequestParam(value = "llegada") long llegada)
    {
        Asignacion asignacion = asignaciones.get(id);
        asignacion.editar(vehiculos.get(placa), rutas.get(ruta), partida, llegada);
        return asignacion;
    }
    
    @RequestMapping(value = "calcular", method = RequestMethod.POST)
    @ResponseBody
    public String calcularDistancias(@RequestParam(value = "asignacion", required = true) int idAsignacion)
    {
        return String.valueOf(ubicaciones.getUbicacionesDeLaAsignacion(asignaciones.get(idAsignacion)).calcularDistanciaTotal());
    }
    
    @RequestMapping
    public String asignacionesView(ModelMap model)
    {
        model.addAttribute("asignaciones", asignaciones.getAsignaciones());
        model.addAttribute("vehiculos", vehiculos.getVehiculos());
        model.addAttribute("rutas", rutas.getRutas());
        return "asignaciones";
    }
    
    @RequestMapping(value = "{id}")
    public String asignacionView(ModelMap model, @PathVariable int id)
    {
        model.addAttribute("asignacion", asignaciones.get(id));
        model.addAttribute("vehiculos", vehiculos.getVehiculos());
        model.addAttribute("rutas", rutas.getRutas());
        model.addAttribute("total", asignaciones.size());
        return "asignacion";
    }
}

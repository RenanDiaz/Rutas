package com.rdiaz.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rdiaz.model.Ruta;

@Controller
@RequestMapping(value = "/rutas")
public class RutasController extends BaseController
{
    @RequestMapping(value = "agregar", method = RequestMethod.POST)
    @ResponseBody
    public Ruta agregarRuta(@RequestParam(value = "origen", required = true) String origen, @RequestParam(value = "destino", required = true) String destino)
    {
        Ruta ruta = new Ruta(origen, destino);
        template.convertAndSend("/topic/rutas", ruta);
        rutas.add(ruta);
        return ruta;
    }
    
    @RequestMapping(value = "editar", method = RequestMethod.POST)
    @ResponseBody
    public Ruta editarRuta(@RequestParam(value = "id", required = true) int id, @RequestParam(value = "origen", required = true) String origen, @RequestParam(value = "destino", required = true) String destino)
    {
        Ruta ruta = rutas.get(id);
        ruta.editar(origen, destino);
        template.convertAndSend("/topic/rutas", ruta);
        return ruta;
    }
    
    @RequestMapping
    public String rutasView(ModelMap model)
    {
        model.addAttribute("rutas", rutas.getRutas());
        return "rutas";
    }
    
    @RequestMapping(value = "{id}")
    public String rutaView(ModelMap model, @PathVariable("id") int id)
    {
        Ruta ruta = rutas.get(id);
        model.addAttribute("ruta", ruta);
        model.addAttribute("asignaciones", rutasAsignadas.getRutasAsignadas());
        model.addAttribute("ubicaciones", ubicaciones.getUbicacionesEnLaRuta(ruta).getUbicaciones());
        return "ruta";
    }
}
package com.rdiaz.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rdiaz.model.Ruta;
import com.rdiaz.model.Ubicacion;

@Controller
public class UbicacionController extends BaseController
{
    @RequestMapping(value = "/ubicacion/guardar/{placa}", method = RequestMethod.POST)
    @ResponseBody public String agregarUbicacion(@PathVariable String placa, @RequestParam(value = "latitud", required = true) String latitud, @RequestParam(value = "longitud", required = true) String longitud)
    {
        Ubicacion.nueva(1, placa, latitud, longitud);
        System.out.println(String.format("Nueva ubicacion: %s, %s %s", placa, latitud, longitud));
        return "sucess";
    }

    @RequestMapping(value = "/ubicacion/editar/{placa}", method = RequestMethod.POST)
    @ResponseBody public String editarUbicacion(@PathVariable String placa, @RequestParam(value = "id", required = true) int id, @RequestParam(value = "ruta", required = true) int ruta, @RequestParam(value = "latitud", required = true) String latitud, @RequestParam(value = "longitud", required = true) String longitud)
    {
        Ubicacion ubicacion = new Ubicacion(id);
        ubicacion.editar(ruta, placa, latitud, longitud);
        return "success";
    }
    
    @RequestMapping(value = "/ubicacion/{id}", method = RequestMethod.GET)
    public String ubicacion(ModelMap model, @PathVariable("id") int id)
    {
        Ubicacion ubicacion = new Ubicacion(id);
        model.addAttribute("ubicacion", ubicacion);
        model.addAttribute("vehiculos", vehiculos.lista());
        model.addAttribute("rutas", Ruta.todas());
        return "ubicacion";
    }
    
    @RequestMapping(value = "/ubicacion", method = RequestMethod.GET)
    public String ubicaciones(ModelMap model)
    {
        model.addAttribute("ubicaciones", Ubicacion.todas());
        model.addAttribute("vehiculos", vehiculos.lista());
        model.addAttribute("rutas", Ruta.todas());
        return "ubicaciones";
    }
}

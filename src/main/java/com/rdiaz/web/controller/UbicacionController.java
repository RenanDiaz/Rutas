package com.rdiaz.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rdiaz.model.Ubicacion;
import com.rdiaz.model.Vehiculo;

@Controller
public class UbicacionController extends BaseController
{
    @RequestMapping(value = "/ubicacion/guardar/{placa}", method = RequestMethod.POST)
    @ResponseBody public String agregarBus(@PathVariable String placa, @RequestParam(value = "latitud", required = true) String latitud, @RequestParam(value = "longitud", required = true) String longitud)
    {
        Vehiculo vehiculo = vehiculos.get(placa);
        Ubicacion.nueva(1, vehiculo, latitud, longitud);
        return "";
    }
    
    @RequestMapping(value = "/ubicacion/{placa}", method = RequestMethod.GET)
    public String ubicacion(ModelMap model, @PathVariable("placa") String placa)
    {
        model.addAttribute("ubicacion", Ubicacion.de(vehiculos.get(placa)));
        return "ubicacion";
    }
    
    @RequestMapping(value = "/ubicacion", method = RequestMethod.GET)
    public String ubicaciones(ModelMap model)
    {
        model.addAttribute("ubicaciones", Ubicacion.todas());
        model.addAttribute("vehiculos", vehiculos.lista());
        return "ubicaciones";
    }
}

package com.rdiaz.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rdiaz.model.Bus;

@Controller
public class VehiculosController extends BaseController
{
    @RequestMapping(value = "/bus/{placa}", method = RequestMethod.GET)
    public String vehiculo(ModelMap model, @PathVariable("placa") String placa)
    {
        model.addAttribute("vehiculo", vehiculos.get(placa));
        return "vehiculo";
    }
    
    @RequestMapping(value = "/bus/agregar/", method = RequestMethod.POST)
    @ResponseBody public String agregarVehiculo(@RequestParam(value = "placa", required = true) String placa, @RequestParam(value = "marca", required = true) String marca, @RequestParam(value = "modelo", required = true) String modelo, @RequestParam(value = "anno", required = true) int anno)
    {
        Bus bus = new Bus(placa, marca, modelo, anno);
        vehiculos.add(bus);
        return "";
    }
}

package com.rdiaz.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rdiaz.model.Bus;
import com.rdiaz.model.Marca;

@Controller
public class VehiculosController extends BaseController
{
    @RequestMapping(value = "/bus/{placa}", method = RequestMethod.GET)
    public String bus(ModelMap model, @PathVariable("placa") String placa)
    {
        model.addAttribute("vehiculo", vehiculos.get(placa));
        model.addAttribute("marcas", marcas.lista());
        return "vehiculo";
    }
    
    @RequestMapping(value = "/bus/agregar", method = RequestMethod.POST)
    @ResponseBody public String agregarBus(@RequestParam(value = "placa", required = true) String placa, @RequestParam(value = "marca", required = true) int marca, @RequestParam(value = "nombreMarca", required = false) String nombreMarca, @RequestParam(value = "modelo", required = true) String modelo, @RequestParam(value = "anno", required = true) int anno)
    {
        if(nombreMarca != null)
        {
            Marca nuevaMarca = new Marca(nombreMarca);
            marca = nuevaMarca.id();
        }
        Bus bus = new Bus(placa, marca, modelo, anno);
        vehiculos.add(bus);
        return "";
    }
    
    @RequestMapping(value = "/bus/nuevo", method = RequestMethod.GET)
    public String nuevoBus(ModelMap model)
    {
        model.addAttribute("marcas", marcas.lista());
        return "vehiculo";
    }
    
    @RequestMapping(value = "/buses", method = RequestMethod.GET)
    public String buses(ModelMap model)
    {
        model.addAttribute("vehiculos", vehiculos);
        return "vehiculos";
    }
}

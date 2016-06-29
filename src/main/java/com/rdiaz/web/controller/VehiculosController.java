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
import com.rdiaz.model.Particular;
import com.rdiaz.model.Taxi;

@Controller
public class VehiculosController extends BaseController
{
    @RequestMapping(value = "/{tipo}/{placa}", method = RequestMethod.GET)
    public String particular(ModelMap model, @PathVariable("tipo") String tipo, @PathVariable("placa") String placa)
    {
        model.addAttribute("vehiculo", vehiculos.get(placa));
        model.addAttribute("tipo", tipo);
        model.addAttribute("marcas", marcas.lista());
        return "vehiculo";
    }
    
    @RequestMapping(value = "/{tipo}/agregar", method = RequestMethod.POST)
    @ResponseBody public String agregarBus(@PathVariable String tipo, @RequestParam(value = "placa", required = true) String placa, @RequestParam(value = "marca", required = true) int marca, @RequestParam(value = "nombreMarca", required = false) String nombreMarca, @RequestParam(value = "modelo", required = true) String modelo, @RequestParam(value = "anno", required = true) int anno)
    {
        if(nombreMarca != null && marca == 0)
        {
            Marca nuevaMarca = new Marca(nombreMarca);
            marca = nuevaMarca.id();
        }
        
        switch(tipo)
        {
        case "buses":
            Bus bus = new Bus(placa, marca, modelo, anno);
            vehiculos.add(bus);
            break;
        case "taxis":
            Taxi taxi = new Taxi(placa, marca, modelo, anno);
            vehiculos.add(taxi);
            break;
        case "particulares":
            Particular particular = new Particular(placa, marca, modelo, anno);
            vehiculos.add(particular);
            break;
        }
        
        return "success";
    }
    
    @RequestMapping(value = "/{tipo}/editar", method = RequestMethod.POST)
    @ResponseBody public String editarBus(@PathVariable String tipo, @RequestParam(value = "placa", required = true) String placa, @RequestParam(value = "marca", required = true) int marca, @RequestParam(value = "nombreMarca", required = false) String nombreMarca, @RequestParam(value = "modelo", required = true) String modelo, @RequestParam(value = "anno", required = true) int anno)
    {
        if(nombreMarca != null && marca == 0)
        {
            Marca nuevaMarca = new Marca(nombreMarca);
            marca = nuevaMarca.id();
        }
        
        vehiculos.editar(placa, marca, modelo, anno);
        return "success";
    }
}

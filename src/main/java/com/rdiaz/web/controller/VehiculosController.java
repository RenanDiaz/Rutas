package com.rdiaz.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rdiaz.model.Marca;
import com.rdiaz.model.TipoDeVehiculo;
import com.rdiaz.model.Vehiculo;

@Controller
public class VehiculosController extends BaseController
{
    @RequestMapping(value = "/{tipo}/{placa}", method = RequestMethod.GET)
    public String particular(ModelMap model, @PathVariable("tipo") String tipo, @PathVariable("placa") String placa)
    {
        Vehiculo vehiculo = vehiculos.get(placa);
        model.addAttribute("vehiculo", vehiculo);
        model.addAttribute("tipo", tipo);
        model.addAttribute("marcas", marcas.getMarcas());
        model.addAttribute("rutasAsignadas", rutasAsignadas.getRutasAsignadasA(vehiculo).getRutasAsignadas());
        model.addAttribute("ubicaciones", ubicaciones.getUbicacionesDelVehiculo(vehiculo).getUbicaciones());
        return "vehiculo";
    }
    
    @RequestMapping(value = "/{tipo}/agregar", method = RequestMethod.POST)
    @ResponseBody public String agregarVehiculo(@PathVariable String tipo, @RequestParam(value = "placa", required = true) String placa, @RequestParam(value = "marca", required = true) int marca, @RequestParam(value = "nombreMarca", required = false) String nombreMarca, @RequestParam(value = "modelo", required = true) String modelo, @RequestParam(value = "anno", required = true) int anno)
    {
        if(nombreMarca != null && marca == 0)
        {
            Marca nuevaMarca = new Marca(nombreMarca);
            marca = nuevaMarca.getId();
            marcas.add(nuevaMarca);
        }
        vehiculos.add(Vehiculo.nuevo(placa, marcas.get(marca), modelo, anno, TipoDeVehiculo.getEnumByString(tipo)));
        
        return "success";
    }
    
    @RequestMapping(value = "/{tipo}/editar", method = RequestMethod.POST)
    @ResponseBody public String editarVehiculo(@PathVariable String tipo, @RequestParam(value = "placa", required = true) String placa, @RequestParam(value = "marca", required = true) int marca, @RequestParam(value = "nombreMarca", required = false) String nombreMarca, @RequestParam(value = "modelo", required = true) String modelo, @RequestParam(value = "anno", required = true) int anno)
    {
        if(nombreMarca != null && marca == 0)
        {
            Marca nuevaMarca = new Marca(nombreMarca);
            marca = nuevaMarca.getId();
            marcas.add(nuevaMarca);
        }
        
        vehiculos.get(placa).editar(marcas.get(marca), modelo, anno, TipoDeVehiculo.getEnumByString(tipo));
        return "success";
    }
}

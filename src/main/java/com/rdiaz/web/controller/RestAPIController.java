package com.rdiaz.web.controller;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.rdiaz.model.Bus;
import com.rdiaz.model.Particular;
import com.rdiaz.model.Rutas;
import com.rdiaz.model.RutasAsignadas;
import com.rdiaz.model.Taxi;
import com.rdiaz.model.Ubicaciones;
import com.rdiaz.model.Vehiculo;

@RestController
@RequestMapping(value = "/rest/")
public class RestAPIController extends BaseController
{
    @RequestMapping(value = "rutas", method = RequestMethod.GET)
    @ResponseBody
    public Rutas rutas(@RequestParam(value = "buscar", defaultValue = "") String buscar)
    {
        if (buscar.isEmpty())
        {
            return rutas;
        }
        return new Rutas(buscar);
    }
    
    @RequestMapping(value = "vehiculos", method = RequestMethod.GET)
    @ResponseBody
    public ArrayList<Vehiculo> vehiculos(@RequestParam(value = "buscar", defaultValue = "") String buscar)
    {
        return vehiculos.getVehiculos();
    }
    
    @RequestMapping(value = "buses", method = RequestMethod.GET)
    @ResponseBody
    public ArrayList<Bus> buses(@RequestParam(value = "buscar", defaultValue = "") String buscar)
    {
        return vehiculos.getBuses();
    }
    
    @RequestMapping(value = "taxis", method = RequestMethod.GET)
    @ResponseBody
    public ArrayList<Taxi> taxis(@RequestParam(value = "buscar", defaultValue = "") String buscar)
    {
        return vehiculos.getTaxis();
    }
    
    @RequestMapping(value = "particulares", method = RequestMethod.GET)
    @ResponseBody
    public ArrayList<Particular> particulares(@RequestParam(value = "buscar", defaultValue = "") String buscar)
    {
        return vehiculos.getParticulares();
    }
    
    @RequestMapping(value = "ubicaciones")
    @ResponseBody
    public Ubicaciones ubicaciones(@RequestParam(value = "placa", required = true) String placa, @RequestParam(value = "inicio", defaultValue = "1") int inicio, @RequestParam(value = "fin", defaultValue = "0") int fin)
    {
        if(fin == 0)
        {
            fin = ubicaciones.size();
        }
        return ubicaciones.ubicacionesDe(vehiculos.get(placa), inicio, fin);
    }
    
    @RequestMapping(value = "asignaciones")
    @ResponseBody
    public RutasAsignadas asignaciones(@RequestParam(value = "ruta", required = true) int ruta)
    {
        return rutasAsignadas.getAsignacionesDeHoyDe(rutas.get(ruta));
    }
}

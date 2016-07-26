package com.rdiaz.web.controller;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.rdiaz.model.Particular;
import com.rdiaz.model.Rutas;
import com.rdiaz.model.Asignaciones;
import com.rdiaz.model.Taxi;
import com.rdiaz.model.Ubicacion;
import com.rdiaz.model.Ubicaciones;
import com.rdiaz.model.Vehiculos;

@RestController
@RequestMapping(value = "/rest")
public class RestAPIController extends BaseController
{
    @RequestMapping(value = "rutas")
    @ResponseBody
    public Rutas rutas(@RequestParam(value = "buscar", defaultValue = "") String buscar)
    {
        if (buscar.isEmpty())
        {
            return rutas;
        }
        return new Rutas(buscar);
    }
    
    @RequestMapping(value = "vehiculos")
    @ResponseBody
    public Vehiculos vehiculos(@RequestParam(value = "buscar", defaultValue = "") String buscar)
    {
        return vehiculos;
    }
    
    @RequestMapping(value = "buses")
    @ResponseBody
    public Vehiculos buses(@RequestParam(value = "buscar", defaultValue = "") String buscar)
    {
        return vehiculos.buses();
    }
    
    @RequestMapping(value = "taxis")
    @ResponseBody
    public ArrayList<Taxi> taxis(@RequestParam(value = "buscar", defaultValue = "") String buscar)
    {
        return vehiculos.listaDeTaxis();
    }
    
    @RequestMapping(value = "particulares")
    @ResponseBody
    public ArrayList<Particular> particulares(@RequestParam(value = "buscar", defaultValue = "") String buscar)
    {
        return vehiculos.listaDeParticulares();
    }
    
    @RequestMapping(value = "ubicaciones")
    @ResponseBody
    public Ubicaciones ubicacionesDe(@RequestParam(value = "placa", required = true) String placa, @RequestParam(value = "inicio", defaultValue = "1") int inicio, @RequestParam(value = "fin", defaultValue = "0") int fin)
    {
        if (fin == 0)
        {
            fin = ubicaciones.size();
        }
        return ubicaciones.ubicacionesDe(vehiculos.get(placa), inicio, fin);
    }
    
    @RequestMapping(value = "{placa}/ubicaciones")
    @ResponseBody
    public Ubicaciones ubicacionesDe(@PathVariable String placa)
    {
        return ubicaciones.getUbicacionesDelVehiculo(vehiculos.get(placa));
    }
    
    @RequestMapping(value = "{placa}/ubicacion")
    @ResponseBody
    public Ubicacion ultimaUbicacionDe(@PathVariable String placa)
    {
        return ubicaciones.getUbicacionesDelVehiculo(vehiculos.get(placa)).getUltimaUbicacion();
    }
    
    @RequestMapping(value = "{placa}/ubicaciones/{ruta}")
    @ResponseBody
    public Ubicaciones ubicacionesDe(@PathVariable String placa, @PathVariable int ruta)
    {
        return ubicaciones.getUbicacionesDelVehiculo(vehiculos.get(placa)).getUbicacionesEnLaRuta(rutas.get(ruta));
    }
    
    @RequestMapping(value = "{placa}/ubicacion/{ruta}")
    @ResponseBody
    public Ubicacion ultimaUbicacionDe(@PathVariable String placa, @PathVariable int ruta)
    {
        return ubicaciones.getUbicacionesDelVehiculo(vehiculos.get(placa)).getUbicacionesEnLaRuta(rutas.get(ruta)).getUltimaUbicacion();
    }
    
    @RequestMapping(value = "asignaciones")
    @ResponseBody
    public Asignaciones asignaciones()
    {
        return asignaciones;
    }
    
    @RequestMapping(value = "asignaciones/hoy")
    @ResponseBody
    public Asignaciones asignacionesDeHoy(@RequestParam(value = "ruta", defaultValue = "0") int ruta)
    {
        if(ruta > 0)
            return asignaciones.getAsignacionesDeLaRuta(rutas.get(ruta)).asignacionesDeHoy();
        else
            return asignaciones.asignacionesDeHoy();
    }
    
    @RequestMapping(value = "ultimo-recorrido")
    @ResponseBody
    public Ubicaciones ultimoRecorrido(@RequestParam(value = "asignacion", required = true) int idAsignacion)
    {
        return ubicaciones.getUltimoRecorrido(asignaciones.get(idAsignacion));
    }
}

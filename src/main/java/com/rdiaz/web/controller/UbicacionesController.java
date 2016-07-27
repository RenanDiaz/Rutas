package com.rdiaz.web.controller;

import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rdiaz.model.Asignacion;
import com.rdiaz.model.Ruta;
import com.rdiaz.model.Ubicacion;
import com.rdiaz.model.Vehiculo;
import com.rdiaz.utils.WriteXMLFile;

@Controller
@RequestMapping("/ubicacion")
public class UbicacionesController extends BaseController
{
    ArrayList<HttpSession> subscribers = new ArrayList<>();
    
    @RequestMapping(value = "agregar", method = RequestMethod.POST)
    @ResponseBody
    public Ubicacion agregarUbicacion(@RequestParam(value = "fecha", required = true) long fecha, @RequestParam(value = "asignacion", required = true) int idAsignacion, @RequestParam(value = "latitud", required = true) String latitud, @RequestParam(value = "longitud", required = true) String longitud, @RequestParam(value = "altitud", required = true) String altitud)
    {
        Asignacion asignacion = asignaciones.get(idAsignacion);
        Vehiculo vehiculo = asignacion.getVehiculo();
        Ruta ruta = asignacion.getRuta();
        Ubicacion ubicacion = new Ubicacion(fecha, asignacion, latitud, longitud, altitud);
        ubicaciones.add(ubicacion);
        template.convertAndSend("/topic/ubicaciones", ubicacion);
        System.out.println(String.format("Nueva ubicacion: %s\t%s\t%s\t%s\t%s\t%s", new Date(fecha), ruta.getDescripcion(), vehiculo.getNombreCorto(), latitud, longitud, altitud));
        return ubicacion;
    }
    
    @RequestMapping(value = "editar", method = RequestMethod.POST)
    @ResponseBody
    public Ubicacion editarUbicacion(@RequestParam(value = "id", required = true) int id, @RequestParam(value = "asignacion", required = true) int idAsignacion, @RequestParam(value = "latitud", required = true) String latitud, @RequestParam(value = "longitud", required = true) String longitud, @RequestParam(value = "altitud", required = true) String altitud)
    {
        Ubicacion ubicacion = ubicaciones.get(id);
        Asignacion asignacion = asignaciones.get(idAsignacion);
        ubicacion.editar(asignacion, latitud, longitud, altitud);
        return ubicacion;
    }
    
    @RequestMapping(value = "exportar", method = RequestMethod.POST)
    @ResponseBody
    public String exportarUbicaciones(HttpServletRequest request, @RequestParam(value = "inicio", required = true) int inicio, @RequestParam(value = "fin", required = true) int fin)
    {
        return WriteXMLFile.createRouteFile(ubicaciones.rango(inicio, fin), request.getServletContext().getRealPath("/"));
    }
    
    @RequestMapping(value = "calcular", method = RequestMethod.POST)
    @ResponseBody
    public String calcularDistancias(@RequestParam(value = "inicio", required = true) int inicio, @RequestParam(value = "fin", required = true) int fin)
    {
        return String.valueOf(ubicaciones.calcularDistanciaTotalEntre(inicio, fin));
    }
    
    @RequestMapping
    public String ubicacionesView(ModelMap model)
    {
        model.addAttribute("ubicaciones", ubicaciones.getUbicaciones());
        model.addAttribute("asignaciones", asignaciones.getAsignaciones());
        model.addAttribute("vehiculos", vehiculos.getVehiculos());
        model.addAttribute("total", ubicaciones.size());
        return "ubicaciones";
    }
    
    @RequestMapping(value = "{id}")
    public String ubicacionView(ModelMap model, @PathVariable("id") int id)
    {
        model.addAttribute("ubicacion", ubicaciones.get(id));
        model.addAttribute("asignaciones", asignaciones.getAsignaciones());
        model.addAttribute("total", ubicaciones.size());
        return "ubicacion";
    }
}

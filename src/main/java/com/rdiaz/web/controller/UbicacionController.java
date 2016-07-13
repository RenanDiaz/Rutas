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

import com.rdiaz.model.Ruta;
import com.rdiaz.model.Ubicacion;
import com.rdiaz.model.Vehiculo;
import com.rdiaz.utils.WriteXMLFile;

@Controller
@RequestMapping("/ubicacion")
public class UbicacionController extends BaseController
{
    ArrayList<HttpSession> subscribers = new ArrayList<>();
    
    @RequestMapping(value = "agregar/{placa}", method = RequestMethod.POST)
    @ResponseBody
    public Ubicacion agregarUbicacion(@PathVariable String placa, @RequestParam(value = "fecha", required = true) long fecha, @RequestParam(value = "ruta", required = true) int idRuta, @RequestParam(value = "latitud", required = true) String latitud, @RequestParam(value = "longitud", required = true) String longitud)
    {
        Vehiculo vehiculo = vehiculos.get(placa);
        Ruta ruta = rutas.get(idRuta);
        Ubicacion ubicacion = new Ubicacion(fecha, ruta, vehiculo, latitud, longitud);
        ubicaciones.add(ubicacion);
        template.convertAndSend("/topic/ubicaciones", ubicacion);
        System.out.println(String.format("Nueva ubicacion: %s\t%s\t%s\t%s\t%s", new Date(fecha), ruta.getDescripcion(), vehiculo.getNombreCorto(), latitud, longitud));
        return ubicacion;
    }
    
    @RequestMapping(value = "editar/{placa}", method = RequestMethod.POST)
    @ResponseBody
    public Ubicacion editarUbicacion(@PathVariable String placa, @RequestParam(value = "id", required = true) int id, @RequestParam(value = "ruta", required = true) int ruta, @RequestParam(value = "latitud", required = true) String latitud, @RequestParam(value = "longitud", required = true) String longitud)
    {
        Ubicacion ubicacion = ubicaciones.get(id);
        ubicacion.editar(rutas.get(ruta), vehiculos.get(placa), latitud, longitud);
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
        model.addAttribute("vehiculos", vehiculos.getVehiculos());
        model.addAttribute("rutas", rutas.getRutas());
        model.addAttribute("total", ubicaciones.size());
        return "ubicaciones";
    }
    
    @RequestMapping(value = "{id}")
    public String ubicacionView(ModelMap model, @PathVariable("id") int id)
    {
        model.addAttribute("ubicacion", ubicaciones.get(id));
        model.addAttribute("vehiculos", vehiculos.getVehiculos());
        model.addAttribute("rutas", rutas.getRutas());
        model.addAttribute("total", ubicaciones.size());
        return "ubicacion";
    }
}

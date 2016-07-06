package com.rdiaz.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.atmosphere.cpr.AtmosphereRequest;
import org.atmosphere.cpr.AtmosphereResource;
import org.json.JSONObject;
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
    public String agregarUbicacion(@PathVariable String placa, @RequestParam(value = "fecha", required = true) long fecha, @RequestParam(value = "ruta", required = true) int idRuta, @RequestParam(value = "latitud", required = true) String latitud, @RequestParam(value = "longitud", required = true) String longitud)
    {
        Vehiculo vehiculo = vehiculos.get(placa);
        Ruta ruta = rutas.get(idRuta);
        Ubicacion.nueva(fecha, ruta, vehiculo, latitud, longitud);
        System.out.println(String.format("Nueva ubicacion: %s\t%d\t%s\t%s\t%s", new Date(fecha), ruta.getDescripcion(), vehiculo.getNombreCorto(), latitud, longitud));
        return "sucess";
    }
    
    @RequestMapping(value = "editar/{placa}", method = RequestMethod.POST)
    @ResponseBody
    public String editarUbicacion(@PathVariable String placa, @RequestParam(value = "id", required = true) int id, @RequestParam(value = "ruta", required = true) int ruta, @RequestParam(value = "latitud", required = true) String latitud, @RequestParam(value = "longitud", required = true) String longitud)
    {
        ubicaciones.get(id).editar(rutas.get(ruta), vehiculos.get(placa), latitud, longitud);
        return "success";
    }
    
    @RequestMapping(value = "exportar", method = RequestMethod.POST)
    @ResponseBody public String exportarUbicaciones(HttpServletRequest request, @RequestParam(value = "inicio", required = true) int inicio, @RequestParam(value = "fin", required = true) int fin)
    {
        return WriteXMLFile.createRouteFile(ubicaciones.rango(inicio, fin), request.getServletContext().getRealPath("/"));
    }
    
    @RequestMapping(value = "calcular", method = RequestMethod.POST)
    @ResponseBody public String calcularDistancias(@RequestParam(value = "inicio", required = true) int inicio, @RequestParam(value = "fin", required = true) int fin)
    {
        return String.valueOf(ubicaciones.calcularDistanciaTotalEntre(inicio, fin));
    }
    
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String ubicaciones(ModelMap model)
    {
        model.addAttribute("ubicaciones", ubicaciones.getUbicaciones());
        model.addAttribute("vehiculos", vehiculos.getVehiculos());
        model.addAttribute("rutas", rutas.getRutas());
        model.addAttribute("total", ubicaciones.size());
        return "ubicaciones";
    }
    
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public String ubicacion(ModelMap model, @PathVariable("id") int id)
    {
        model.addAttribute("ubicacion", ubicaciones.get(id));
        model.addAttribute("vehiculos", vehiculos.getVehiculos());
        model.addAttribute("rutas", rutas.getRutas());
        model.addAttribute("total", ubicaciones.size());
        return "ubicacion";
    }
    
    //TODO: websocket para notificaciones push
    @RequestMapping(value = "/n/ubicaciones", method = RequestMethod.GET)
    @ResponseBody
    public void ubicacionesGet(AtmosphereResource atmosphereResource, HttpSession session) throws IOException
    {
        AtmosphereRequest atmosphereRequest = atmosphereResource.getRequest();
        
        System.out.println(atmosphereRequest.getHeader("negotiating"));
        if (atmosphereRequest.getHeader("negotiating") == null)
        {
            atmosphereResource.resumeOnBroadcast(atmosphereResource.transport() == AtmosphereResource.TRANSPORT.LONG_POLLING).suspend();
        } else
        {
            atmosphereResource.getResponse().getWriter().write("OK");
        }

        subscribers.add(session);

        System.out.println("Subscribers: " + subscribers.size());

        for(HttpSession httpSession : subscribers) {
            System.out.println(httpSession);
        }
    }

    @RequestMapping(value = "/n/ubicaciones", method = RequestMethod.POST)
    @ResponseBody public void ubicacionesPost(AtmosphereResource atmosphereResource) throws IOException{

//        AtmosphereRequest atmosphereRequest = atmosphereResource.getRequest();
//
//        String body = atmosphereRequest.getReader().readLine().trim();
//
//        String author = body.substring(body.indexOf(":") + 2, body.indexOf(",") - 1);
//        String message = body.substring(body.lastIndexOf(":") + 2, body.length() - 2);
//
        atmosphereResource.getBroadcaster().broadcast(new JSONObject().put("prueba", 1));
    }
}

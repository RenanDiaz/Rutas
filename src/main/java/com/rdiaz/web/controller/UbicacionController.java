package com.rdiaz.web.controller;

import java.io.IOException;
import java.util.ArrayList;

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
import com.rdiaz.utils.WriteXMLFile;

@Controller
public class UbicacionController extends BaseController
{
    ArrayList<HttpSession> subscribers = new ArrayList<>();
    
    @RequestMapping(value = "/ubicacion/guardar/{placa}", method = RequestMethod.POST)
    @ResponseBody
    public String agregarUbicacion(@PathVariable String placa, @RequestParam(value = "fecha", required = true) long fecha, @RequestParam(value = "ruta", required = true) int ruta, @RequestParam(value = "latitud", required = true) String latitud, @RequestParam(value = "longitud", required = true) String longitud)
    {
        Ubicacion.nueva(fecha, ruta, placa, latitud, longitud);
        System.out.println(String.format("Nueva ubicacion: %s, %s %s", placa, latitud, longitud));
        return "sucess";
    }
    
    @RequestMapping(value = "/ubicacion/editar/{placa}", method = RequestMethod.POST)
    @ResponseBody
    public String editarUbicacion(@PathVariable String placa, @RequestParam(value = "id", required = true) int id, @RequestParam(value = "ruta", required = true) int ruta, @RequestParam(value = "latitud", required = true) String latitud, @RequestParam(value = "longitud", required = true) String longitud)
    {
        Ubicacion ubicacion = new Ubicacion(id);
        ubicacion.editar(ruta, placa, latitud, longitud);
        return "success";
    }
    
    @RequestMapping(value = "/ubicacion/exportar", method = RequestMethod.POST)
    @ResponseBody public String exportarUbicaciones(HttpServletRequest request, @RequestParam(value = "inicio", required = true) int inicio, @RequestParam(value = "fin", required = true) int fin)
    {
        return WriteXMLFile.createRouteFile(Ubicacion.rango(inicio, fin), request.getServletContext().getRealPath("/"));
    }
    
    @RequestMapping(value = "/ubicacion/calcular", method = RequestMethod.POST)
    @ResponseBody public String calcularDistancias(@RequestParam(value = "inicio", required = true) int inicio, @RequestParam(value = "fin", required = true) int fin)
    {
        return String.valueOf(Ubicacion.calcularDistanciaTotalEntre(inicio, fin));
    }
    
    @RequestMapping(value = "/ubicacion", method = RequestMethod.GET)
    public String ubicaciones(ModelMap model)
    {
        model.addAttribute("ubicaciones", Ubicacion.todas());
        model.addAttribute("vehiculos", vehiculos.lista());
        model.addAttribute("rutas", Ruta.todas());
        model.addAttribute("total", Ubicacion.todas().size());
        return "ubicaciones";
    }
    
    @RequestMapping(value = "/ubicacion/{id}", method = RequestMethod.GET)
    public String ubicacion(ModelMap model, @PathVariable("id") int id)
    {
        Ubicacion ubicacion = new Ubicacion(id);
        model.addAttribute("ubicacion", ubicacion);
        model.addAttribute("vehiculos", vehiculos.lista());
        model.addAttribute("rutas", Ruta.todas());
        model.addAttribute("total", Ubicacion.todas().size());
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

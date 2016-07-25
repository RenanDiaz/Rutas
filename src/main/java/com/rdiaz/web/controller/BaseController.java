package com.rdiaz.web.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.rdiaz.model.Marcas;
import com.rdiaz.model.Rutas;
import com.rdiaz.model.Asignaciones;
import com.rdiaz.model.Ubicaciones;
import com.rdiaz.model.Vehiculos;
import com.rdiaz.websocket.Contadores;

@Controller
public class BaseController
{
    static Rutas rutas = new Rutas();
    static Marcas marcas = new Marcas();
    static Vehiculos vehiculos = new Vehiculos(marcas);
    static Asignaciones asignaciones = new Asignaciones(vehiculos, rutas);
    static Ubicaciones ubicaciones = new Ubicaciones(asignaciones, vehiculos, rutas);
    static Contadores contadores = new Contadores();
    
    @Autowired
    protected SimpMessagingTemplate template;
    
    public BaseController()
    {
        contadores.add("rutas", rutas.size());
        contadores.add("marcas", marcas.size());
        contadores.add("asignaciones", asignaciones.size());
        contadores.add("ubicaciones", ubicaciones.size());
        contadores.add(vehiculos.sizes());
    }
    
    protected String encode(String str)
    {
        try
        {
            return URLEncoder.encode(str, "UTF8");
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return str;
    }
    
    protected String decode(String str)
    {
        try
        {
            return URLDecoder.decode(str, "UTF8");
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return str;
    }
}

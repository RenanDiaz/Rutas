package com.rdiaz.web.controller;

import org.springframework.stereotype.Controller;

import com.rdiaz.model.Marcas;
import com.rdiaz.model.Rutas;
import com.rdiaz.model.RutasAsignadas;
import com.rdiaz.model.Ubicaciones;
import com.rdiaz.model.Vehiculos;

@Controller
public class BaseController
{
    static Rutas rutas = new Rutas();
    static Marcas marcas = new Marcas();
    static Vehiculos vehiculos = new Vehiculos(marcas);
    static RutasAsignadas rutasAsignadas = new RutasAsignadas(vehiculos, rutas);
    static Ubicaciones ubicaciones = new Ubicaciones(vehiculos, rutas);
}

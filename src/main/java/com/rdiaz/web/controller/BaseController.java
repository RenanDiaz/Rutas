package com.rdiaz.web.controller;

import org.springframework.stereotype.Controller;

import com.rdiaz.model.Marcas;
import com.rdiaz.model.Vehiculos;

@Controller
public class BaseController
{
    Vehiculos vehiculos = new Vehiculos();
    Marcas marcas = new Marcas();
}

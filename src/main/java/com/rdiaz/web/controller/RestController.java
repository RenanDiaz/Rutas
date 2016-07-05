package com.rdiaz.web.controller;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rdiaz.model.Ruta;

@org.springframework.web.bind.annotation.RestController
@RequestMapping(value = "/rest")
public class RestController extends BaseController
{
    @RequestMapping(value = "/rutas", method = RequestMethod.GET)
    @ResponseBody public ArrayList<Ruta> rutas(@RequestParam(value = "buscar", defaultValue = "") String buscar)
    {
       return Ruta.todas();
    }
}

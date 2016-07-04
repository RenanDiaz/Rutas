package com.rdiaz.web.controller;

import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rdiaz.model.Ruta;

@Controller
public class RutasController extends BaseController
{
    @RequestMapping(value = "/obtener/rutas", method = RequestMethod.GET)
    @ResponseBody public ResponseEntity<String> rutas()
    {
        String rutas = Ruta.json().toString();
        System.out.println(rutas);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/javascript");
        return new ResponseEntity<String>(rutas, responseHeaders, HttpStatus.OK);
    }
}
package com.rdiaz.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController extends BaseController
{
    static String KEY = "AIzaSyCCZK_NqEkdDLgJz0OyWL-FvrxkZn1EbOQ";
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String inicioView(ModelMap model)
    {
        model.addAttribute("buses", vehiculos.listaDeBuses().size());
        model.addAttribute("taxis", vehiculos.listaDeTaxis().size());
        model.addAttribute("particulares", vehiculos.listaDeParticulares().size());
        model.addAttribute("rutas", rutas.size());
        model.addAttribute("rutasAsignadas", rutasAsignadas.size());
        model.addAttribute("ubicaciones", ubicaciones.size());
        
        return "home";
    }
    
    @RequestMapping(value = "/distancematrix/{type}")
    @ResponseBody
    public String distance(@PathVariable String type, @RequestParam(value = "origins") String origins, @RequestParam(value = "destinations") String destinations)
    {
        String url = String.format("https://maps.googleapis.com/maps/api/distancematrix/%s?origins=%s&destinations=%s&key=%s", type, origins, encode(destinations), KEY);
        
        return url;
    }
    
    @RequestMapping(value = "/login")
    public String loginView()
    {
        return "login";
    }
    
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public String login(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password)
    {
        System.out.println("****************");
        System.out.println(username);
        System.out.println(password);
        System.out.println("****************");
        return "success";
    }
}
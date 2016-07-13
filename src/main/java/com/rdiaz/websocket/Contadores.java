package com.rdiaz.websocket;

import java.util.HashMap;

public class Contadores
{
    HashMap<String, Integer> contadores = new HashMap<>();
    
    public void add(String nombre, int cantidad)
    {
        contadores.put(nombre, cantidad);
    }
    
    public void updateContador(String nombre, int nuevaCantidad)
    {
        contadores.put(nombre, nuevaCantidad);
    }
    
    public HashMap<String, Integer> getContadores()
    {
        return contadores;
    }

    public void add(HashMap<String, Integer> sizes)
    {
        sizes.forEach((key, value) -> add(key, value));
    }
}

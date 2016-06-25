package com.rdiaz.configuracion;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

@org.springframework.stereotype.Controller
public abstract class Controller
{
    protected static Environment environment;
    
    @Autowired
    public void setEnviroment(Environment environment)
    {
        Controller.environment = environment;
    }
    
    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        // TODO inicializar
    }
    
    static String obtenerFechaYHora()
    {
        long timeInMillis = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        
        return dateFormat.format(calendar.getTime());
    }
    
    static String obtenerFecha()
    {
        Calendar calendar = Calendar.getInstance();
        String fecha = calendar.get(Calendar.DATE) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR);
        
        return fecha;
    }
    
    String codificarEnUTF8(String texto)
    {
        String textoCodificado = texto;
        try
        {
            textoCodificado = URLEncoder.encode(texto, "UTF-8");
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        
        return textoCodificado;
    }
    
    String decodificarUTF8(String texto)
    {
        String textoDecodificado = texto;
        try
        {
            textoDecodificado = URLDecoder.decode(texto, "UTF-8");
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        
        return textoDecodificado;
    }
    
}

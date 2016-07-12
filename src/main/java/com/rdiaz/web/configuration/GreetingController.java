package com.rdiaz.web.configuration;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.rdiaz.model.Ruta;

@Controller
public class GreetingController
{
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Ruta greeting(Ruta ruta) throws Exception
    {
        Thread.sleep(3000);
        return ruta;
    }
}

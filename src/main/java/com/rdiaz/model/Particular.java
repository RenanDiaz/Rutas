package com.rdiaz.model;

public class Particular extends Vehiculo
{
    public Particular(String placa, int marca, String modelo, int anno)
    {
        super(placa, marca, modelo, anno, TipoDeVehiculo.PARTICULAR);
    }
    
}

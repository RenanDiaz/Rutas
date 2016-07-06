package com.rdiaz.model;

public class Particular extends Vehiculo
{
    public Particular(String placa, Marca marca, String modelo, int anno)
    {
        super(placa, marca, modelo, anno, TipoDeVehiculo.PARTICULAR);
    }
    
}

package com.rdiaz.model;

public class Bus extends Vehiculo
{
    public Bus(String placa, String marca, String modelo, int anno)
    {
        super(placa, marca, modelo, anno, TipoDeVehiculo.BUS);
    }
}

package com.rdiaz.model;

public class Taxi extends Vehiculo
{
    public Taxi(String placa, Marca marca, String modelo, int anno)
    {
        super(placa, marca, modelo, anno, TipoDeVehiculo.TAXI);
    }
}

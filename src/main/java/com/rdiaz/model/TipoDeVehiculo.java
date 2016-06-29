package com.rdiaz.model;

public enum TipoDeVehiculo
{
    BUS, TAXI, PARTICULAR;
    
    @Override
    public String toString()
    {
        return name().substring(0, 1) + name().substring(1).toLowerCase();
    }
    
    public static TipoDeVehiculo getEnumByString(int code)
    {
        for (TipoDeVehiculo e : TipoDeVehiculo.values())
        {
            if (code == e.ordinal())
                return e;
        }
        return null;
    }
}

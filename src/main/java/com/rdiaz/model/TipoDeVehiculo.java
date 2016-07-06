package com.rdiaz.model;

public enum TipoDeVehiculo
{
    BUS, TAXI, PARTICULAR;
    
    @Override
    public String toString()
    {
        return name().substring(0, 1) + name().substring(1).toLowerCase();
    }
    
    public String getNombre()
    {
        return toString();
    }
    
    public static TipoDeVehiculo getEnumByOrdinal(int code)
    {
        for (TipoDeVehiculo e : TipoDeVehiculo.values())
        {
            if (code == e.ordinal())
                return e;
        }
        return null;
    }
    
    public static TipoDeVehiculo getEnumByString(String string)
    {
        for (TipoDeVehiculo e : TipoDeVehiculo.values())
        {
            if (string.equals(e.toString()) || string.contains(e.toString().toLowerCase()))
                return e;
        }
        return null;
    }
}

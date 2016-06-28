package com.rdiaz.model;

public enum TipoDeVehiculo
{
    BUS(1), TAXI(2), PARTICULAR(3);
    
    private int _ordinal;
    
    private TipoDeVehiculo(int ordinal)
    {
        _ordinal = ordinal;
    }
    
    public int intValue()
    {
        return _ordinal;
    }
    
    @Override
    public String toString()
    {
        return name().substring(0, 1) + name().substring(1).toLowerCase();
    }
    
    public static int getEnumByString(int code)
    {
        for (TipoDeVehiculo e : TipoDeVehiculo.values())
        {
            if (code == e.intValue())
                return e.intValue();
        }
        return 0;
    }
}

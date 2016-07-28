package com.rdiaz.model;

public class Movimiento
{
    Ubicacion ubicacionInicial;
    Ubicacion ubicacionFinal;
    
    public Movimiento(Ubicacion ubicacionInicial, Ubicacion ubicacionFinal)
    {
        super();
        this.ubicacionInicial = ubicacionInicial;
        this.ubicacionFinal = ubicacionFinal;
    }

    public Ubicacion getUbicacionInicial()
    {
        return ubicacionInicial;
    }

    public void setUbicacionInicial(Ubicacion ubicacionInicial)
    {
        this.ubicacionInicial = ubicacionInicial;
    }

    public Ubicacion getUbicacionFinal()
    {
        return ubicacionFinal;
    }

    public void setUbicacionFinal(Ubicacion ubicacionFinal)
    {
        this.ubicacionFinal = ubicacionFinal;
    }
    
    public double getDistanciaLinealEnMetros()
    {
        double lat1 = Double.valueOf(ubicacionInicial.getLatitud());
        double lat2 = Double.valueOf(ubicacionFinal.getLatitud());
        double lon1 = Double.valueOf(ubicacionInicial.getLongitud());
        double lon2 = Double.valueOf(ubicacionFinal.getLongitud());
        double el1 = Double.valueOf(ubicacionInicial.getAltitud());
        double el2 = Double.valueOf(ubicacionFinal.getAltitud());
        final int R = 6371; // Radius of the earth

        Double latDistance = Math.toRadians(lat2 - lat1);
        Double lonDistance = Math.toRadians(lon2 - lon1);
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters
        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }
    
    public double getDiferenciaDeTiempoEnMilisegundos()
    {
        return ubicacionFinal.getFechahora().getTime() - ubicacionInicial.getFechahora().getTime();
    }
    
    public double getVelocidad()
    {
        if(getDiferenciaDeTiempoEnMilisegundos() > 0)
            return (getDistanciaLinealEnMetros() / 1000) / (getDiferenciaDeTiempoEnMilisegundos() / 1000 / 60 / 60);
        else
            return 0;
    }
}

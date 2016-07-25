package com.rdiaz.utils;

import com.rdiaz.model.Marcas;
import com.rdiaz.model.Rutas;
import com.rdiaz.model.Ubicacion;
import com.rdiaz.model.Asignacion;
import com.rdiaz.model.Asignaciones;
import com.rdiaz.model.Ubicaciones;
import com.rdiaz.model.Vehiculos;

public class AsignacionUpdater
{
    static Rutas rutas = new Rutas();
    static Marcas marcas = new Marcas();
    static Vehiculos vehiculos = new Vehiculos(marcas);
    static Asignaciones asignaciones = new Asignaciones(vehiculos, rutas);
    static Ubicaciones ubicaciones = new Ubicaciones(asignaciones, vehiculos, rutas);
    
    public static void main(String[] args)
    {
        asignar();
    }
    
    public static void asignar()
    {
        int i = 0;
        for(Ubicacion ubicacion : ubicaciones.getUbicaciones())
        {
            Asignaciones asignacionesParaLaUbicacion = asignaciones.getAsignacionesDelVehiculo(ubicacion.getVehiculo()).getAsignacionesDeLaRuta(ubicacion.getRuta());
            if(asignacionesParaLaUbicacion.size() > 0)
            {
                Asignacion asignacion = asignacionesParaLaUbicacion.getAsignaciones().get(0);
                ubicacion.editar(asignacion, asignacion.getRuta(), asignacion.getVehiculo(), ubicacion.getLatitud(), ubicacion.getLongitud());
                System.out.println(++i);
            }
        }
    }
}

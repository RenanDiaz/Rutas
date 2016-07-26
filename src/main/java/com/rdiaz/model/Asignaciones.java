package com.rdiaz.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Asignaciones
{
    @JsonProperty("asignaciones")
    ArrayList<Asignacion> asignaciones = new ArrayList<>();
    
    public Asignaciones(Vehiculos vehiculos, Rutas rutas)
    {
        try
        {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/rutas", "root", "");
            PreparedStatement stmt = conexion.prepareStatement("SELECT * FROM asignacion;");
            ResultSet rs = stmt.executeQuery();
            while(rs.next())
            {
                int id = rs.getInt(1);
                Vehiculo vehiculo = vehiculos.get(rs.getString(2));
                Ruta ruta = rutas.get(rs.getInt(3));
                Timestamp fechahoraDePartida = rs.getTimestamp(4);
                Timestamp fechahoraDeLlegada = rs.getTimestamp(5);
                asignaciones.add(new Asignacion(id, vehiculo, ruta, fechahoraDePartida, fechahoraDeLlegada));
            }
            conexion.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public Asignaciones()
    {
        
    }
    
    public void add(Asignacion asignacion)
    {
        asignaciones.add(asignacion);
    }
    
    public Asignacion get(int id)
    {
        for(Asignacion asignacion : asignaciones)
        {
            if(asignacion.getId() == id)
            {
                return asignacion;
            }
        }
        return null;
    }
    
    public int size()
    {
        return asignaciones.size();
    }
    
    public void remove(Asignacion asignacion)
    {
        asignaciones.remove(asignacion);
    }
    
    public void ordenarAscendentementePorFecha()
    {
        asignaciones.sort(Comparator.comparing(Asignacion::getFechahoraDePartida));
    }
    
    public void ordenarDescendentementePorFecha()
    {
        ordenarAscendentementePorFecha();
        Collections.reverse(asignaciones);
    }
    
    public void ordenar()
    {
        asignaciones.sort(Comparator.comparing(Asignacion::getId));
    }
    
    public ArrayList<Asignacion> getAsignaciones()
    {
        ordenar();
        return asignaciones;
    }
    
    public Asignaciones getAsignacionesDelVehiculo(Vehiculo vehiculo)
    {
        Asignaciones asignacionesParaEsteVehiculo = new Asignaciones();
        for(final Asignacion asignacion : asignaciones)
        {
            if(asignacion.getVehiculo().getPlaca().equals(vehiculo.getPlaca()))
            {
                asignacionesParaEsteVehiculo.add(asignacion);
            }
        }
        return asignacionesParaEsteVehiculo;
    }
    
    public Asignaciones getAsignacionesDeLaRuta(Ruta ruta)
    {
        Asignaciones asignacionesDeLaRuta = new Asignaciones();
        for(final Asignacion asignacion : asignaciones)
        {
            if(asignacion.getRuta().equals(ruta))
            {
                 asignacionesDeLaRuta.add(asignacion);
            }
        }
        return asignacionesDeLaRuta;
    }

    public Asignaciones asignacionesDeHoy()
    {
        Asignaciones asignacionesDeHoy = new Asignaciones();
        for(final Asignacion asignacion : asignaciones)
        {
            int fechaDePartida = getIntFecha(asignacion.getFechahoraDePartida());
            int fechaDeHoy = getIntFecha(getTimestampHoy());
            if(fechaDePartida >= fechaDeHoy)
            {
                asignacionesDeHoy.add(asignacion);
            }
        }
        asignacionesDeHoy.ordenarDescendentementePorFecha();
        return asignacionesDeHoy;
    }
    
    private int getIntFecha(Timestamp fechahora)
    {
        long timestamp = fechahora.getTime();
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp);
        int anno = cal.get(Calendar.YEAR);
        int mes = cal.get(Calendar.MONTH);
        int dia = cal.get(Calendar.DATE);
        return Integer.valueOf(String.format("%4d%02d%02d", anno, mes, dia));
    }
    
    private Timestamp getTimestampHoy()
    {
        return new Timestamp(Calendar.getInstance().getTimeInMillis());
    }
}

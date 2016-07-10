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

public class RutasAsignadas
{
    @JsonProperty("rutasAsignadas")
    ArrayList<RutaAsignada> rutasAsignadas = new ArrayList<>();
    
    public RutasAsignadas(Vehiculos vehiculos, Rutas rutas)
    {
        try
        {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/rutas", "root", "");
            PreparedStatement stmt = conexion.prepareStatement("SELECT * FROM ruta_asignada;");
            ResultSet rs = stmt.executeQuery();
            while(rs.next())
            {
                int id = rs.getInt(1);
                Vehiculo vehiculo = vehiculos.get(rs.getString(2));
                Ruta ruta = rutas.get(rs.getInt(3));
                Timestamp fechahoraDePartida = rs.getTimestamp(4);
                Timestamp fechahoraDeLlegada = rs.getTimestamp(5);
                rutasAsignadas.add(new RutaAsignada(id, vehiculo, ruta, fechahoraDePartida, fechahoraDeLlegada));
            }
            conexion.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public RutasAsignadas()
    {
        
    }
    
    public void add(RutaAsignada rutaAsignada)
    {
        rutasAsignadas.add(rutaAsignada);
    }
    
    public RutaAsignada get(int id)
    {
        for(RutaAsignada rutaAsignada : rutasAsignadas)
        {
            if(rutaAsignada.getId() == id)
            {
                return rutaAsignada;
            }
        }
        return null;
    }
    
    public int size()
    {
        return rutasAsignadas.size();
    }
    
    public void remove(RutaAsignada rutaAsignada)
    {
        rutasAsignadas.remove(rutaAsignada);
    }
    
    public void ordenarAscendentementePorFecha()
    {
        rutasAsignadas.sort(Comparator.comparing(RutaAsignada::getFechahoraDePartida));
    }
    
    public void ordenarDescendentementePorFecha()
    {
        ordenarAscendentementePorFecha();
        Collections.reverse(rutasAsignadas);
    }
    
    public void ordenar()
    {
        rutasAsignadas.sort(Comparator.comparing(RutaAsignada::getId));
    }
    
    public ArrayList<RutaAsignada> getRutasAsignadas()
    {
        ordenar();
        return rutasAsignadas;
    }
    
    public RutasAsignadas getRutasAsignadasA(Vehiculo vehiculo)
    {
        RutasAsignadas rutasAsignadasParaEsteVehiculo = new RutasAsignadas();
        for(final RutaAsignada rutaAsignada : rutasAsignadas)
        {
            if(rutaAsignada.getVehiculo().equals(vehiculo))
            {
                rutasAsignadasParaEsteVehiculo.add(rutaAsignada);
            }
        }
        
        return rutasAsignadasParaEsteVehiculo;
    }

    public RutasAsignadas getAsignacionesDeHoyDe(Ruta ruta)
    {
        RutasAsignadas asignacionesDeHoy = new RutasAsignadas();
        for(final RutaAsignada rutaAsignada : rutasAsignadas)
        {
            if(rutaAsignada.getRuta().equals(ruta))
            {
                int fechaDePartida = getIntFecha(rutaAsignada.getFechahoraDePartida());
                int fechaDeHoy = getIntFecha(getTimestampHoy());
                if(fechaDePartida >= fechaDeHoy)
                {
                    asignacionesDeHoy.add(rutaAsignada);
                }
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

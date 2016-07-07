package com.rdiaz.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
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
                Timestamp horaInicial = rs.getTimestamp(4);
                Timestamp horaFinal = rs.getTimestamp(5);
                rutasAsignadas.add(new RutaAsignada(id, vehiculo, ruta, horaInicial, horaFinal));
            }
            conexion.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
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
    
    public ArrayList<RutaAsignada> getRutasAsignadas()
    {
        rutasAsignadas.sort(Comparator.comparing(RutaAsignada::getId));
        return rutasAsignadas;
    }
    
    public RutasAsignadas getRutasAsignadasA(Vehiculo vehiculo)
    {
        RutasAsignadas rutasAsignadasParaEsteVehiculo = this;
        for(RutaAsignada rutaAsignada : rutasAsignadas)
        {
            if(!rutaAsignada.getVehiculo().equals(vehiculo))
            {
                rutasAsignadasParaEsteVehiculo.remove(rutaAsignada);
            }
        }
        
        return rutasAsignadasParaEsteVehiculo;
    }
}

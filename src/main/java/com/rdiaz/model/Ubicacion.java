package com.rdiaz.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

public class Ubicacion
{
    private int id;
    private Timestamp fechahora;
    private Asignacion asignacion;
    private String latitud;
    private String longitud;
    
    public Ubicacion(long fecha, Asignacion asignacion, String latitud, String longitud)
    {
        Timestamp fechahora = new Timestamp(fecha);
        setFechahora(fechahora);
        setAsignacion(asignacion);
        setLatitud(latitud);
        setLongitud(longitud);
        try
        {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/rutas", "root", "");
            PreparedStatement stmt = conexion.prepareStatement("INSERT INTO ubicacion (fechahora, asignacion, latitud, longitud) VALUES (?, ?, ?, ?);");
            stmt.setTimestamp(1, fechahora);
            stmt.setInt(2, asignacion.getId());
            stmt.setString(3, latitud);
            stmt.setString(4, longitud);
            stmt.executeUpdate();
            
            stmt = conexion.prepareStatement("SELECT @@IDENTITY FROM ubicacion;");
            ResultSet rs = stmt.executeQuery();
            if(rs.last())
            {
                setId(rs.getInt(1));
            }
            
            conexion.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public Ubicacion(int id, Timestamp fechahora, Asignacion asignacion, String latitud, String longitud)
    {
        setId(id);
        setFechahora(fechahora);
        setAsignacion(asignacion);
        setLatitud(latitud);
        setLongitud(longitud);
    }
    
    public int getId()
    {
        return this.id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    public Timestamp getFechahora()
    {
        return this.fechahora;
    }
    
    public void setFechahora(Timestamp fechahora)
    {
        this.fechahora = fechahora;
    }
    
    public Asignacion getAsignacion()
    {
        return this.asignacion;
    }
    
    public void setAsignacion(Asignacion asignacion)
    {
        this.asignacion = asignacion;
    }
    
    public String getLatitud()
    {
        return this.latitud;
    }
    
    public void setLatitud(String latitud)
    {
        this.latitud = latitud;
    }
    
    public String getLongitud()
    {
        return this.longitud;
    }
    
    public void setLongitud(String longitud)
    {
        this.longitud = longitud;
    }
    
    public void editar(Asignacion asignacion, String latitud, String longitud)
    {
        setAsignacion(asignacion);
        setLatitud(latitud);
        setLongitud(longitud);
        try
        {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/rutas", "root", "");
            PreparedStatement stmt = conexion.prepareStatement("UPDATE ubicacion SET asignacion = ?, latitud = ?, longitud = ? WHERE id = ?;");
            stmt.setInt(1, asignacion.getId());
            stmt.setString(2, latitud);
            stmt.setString(3, longitud);
            stmt.setInt(4, getId());
            stmt.executeUpdate();
            
            conexion.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
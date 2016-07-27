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
    private String altitud;
    
    public Ubicacion(long fecha, Asignacion asignacion, String latitud, String longitud, String altitud)
    {
        super();
        Timestamp fechahora = new Timestamp(fecha);
        setFechahora(fechahora);
        setAsignacion(asignacion);
        setLatitud(latitud);
        setLongitud(longitud);
        setAltitud(altitud);
        try
        {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/rutas", "root", "");
            PreparedStatement stmt = conexion.prepareStatement("INSERT INTO ubicacion (fechahora, asignacion, latitud, longitud, altitud) VALUES (?, ?, ?, ?, ?);");
            stmt.setTimestamp(1, fechahora);
            stmt.setInt(2, asignacion.getId());
            stmt.setString(3, latitud);
            stmt.setString(4, longitud);
            stmt.setString(5, altitud);
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
    
    public Ubicacion(int id, Timestamp fechahora, Asignacion asignacion, String latitud, String longitud, String altitud)
    {
        super();
        setId(id);
        setFechahora(fechahora);
        setAsignacion(asignacion);
        setLatitud(latitud);
        setLongitud(longitud);
        setAltitud(altitud);
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

    public String getAltitud()
    {
        return this.altitud;
    }
    
    public void setAltitud(String altitud)
    {
        this.altitud = altitud;
    }
    
    public void editar(Asignacion asignacion, String latitud, String longitud, String altitud)
    {
        setAsignacion(asignacion);
        setLatitud(latitud);
        setLongitud(longitud);
        setAltitud(altitud);
        try
        {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/rutas", "root", "");
            PreparedStatement stmt = conexion.prepareStatement("UPDATE ubicacion SET asignacion = ?, latitud = ?, longitud = ?, altitud = ? WHERE id = ?;");
            stmt.setInt(1, asignacion.getId());
            stmt.setString(2, latitud);
            stmt.setString(3, longitud);
            stmt.setString(4, altitud);
            stmt.setInt(5, getId());
            stmt.executeUpdate();
            
            conexion.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
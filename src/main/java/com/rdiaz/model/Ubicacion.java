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
    private Ruta ruta;
    private Vehiculo vehiculo;
    private String latitud;
    private String longitud;
    
    public Ubicacion(long fecha, Ruta ruta, Vehiculo vehiculo, String latitud, String longitud)
    {
        Timestamp fechahora = new Timestamp(fecha);
        try
        {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/rutas?allowMultiQueries=true", "root", "");
            PreparedStatement stmt = conexion.prepareStatement("INSERT INTO ubicacion (fechahora, ruta, vehiculo, latitud, longitud) VALUES (?, ?, ?, ?, ?); SELECT @@IDENTITY FROM ubicacion;");
            stmt.setTimestamp(1, fechahora);
            stmt.setInt(2, ruta.getId());
            stmt.setString(3, vehiculo.getPlaca());
            stmt.setString(4, latitud);
            stmt.setString(5, longitud);
            System.out.println(stmt.toString());
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
    
    public Ubicacion(int id, Timestamp fechahora, Ruta ruta, Vehiculo vehiculo, String latitud, String longitud)
    {
        setId(id);
        setFechahora(fechahora);
        setRuta(ruta);
        setVehiculo(vehiculo);
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
    
    public Ruta getRuta()
    {
        return this.ruta;
    }
    
    public void setRuta(Ruta ruta)
    {
        this.ruta = ruta;
    }
    
    public Vehiculo getVehiculo()
    {
        return this.vehiculo;
    }
    
    public void setVehiculo(Vehiculo vehiculo)
    {
        this.vehiculo = vehiculo;
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
    
    public void editar(Ruta ruta, Vehiculo vehiculo, String latitud, String longitud)
    {
        setRuta(ruta);
        setVehiculo(vehiculo);
        setLatitud(latitud);
        setLongitud(longitud);
        try
        {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/rutas", "root", "");
            PreparedStatement stmt = conexion.prepareStatement("UPDATE ubicacion SET ruta = ?, vehiculo = ?, latitud = ?, longitud = ? WHERE id = ?;");
            stmt.setInt(1, ruta.getId());
            stmt.setString(2, vehiculo.getPlaca());
            stmt.setString(3, latitud);
            stmt.setString(4, longitud);
            stmt.setInt(5, getId());
            stmt.executeUpdate();
            
            conexion.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
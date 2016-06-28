package com.rdiaz.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class Ubicacion
{
    private int _id;
    private Timestamp _fechahora;
    private int _ruta;
    private Vehiculo _vehiculo;
    private String _latitud;
    private String _longitud;
    
    Ubicacion(int id)
    {
        id(id);
        try
        {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/rutas", "root", "");
            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery(String.format("SELECT * FROM ubicacion WHERE id = %d;", id));
            if(rs.last())
            {
                fechahora(rs.getTimestamp(2));
                ruta(rs.getInt(3));
                vehiculo(rs.getString(4));
                latitud(rs.getString(5));
                longitud(rs.getString(6));
            }
            conexion.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public int id()
    {
        return _id;
    }
    
    public void id(int id)
    {
        _id = id;
    }
    public Timestamp fechahora()
    {
        return _fechahora;
    }
    
    public void fechahora(Timestamp fechahora)
    {
        _fechahora = fechahora;
    }
    
    public int ruta()
    {
        return _ruta;
    }
    
    public void ruta(int ruta)
    {
        _ruta = ruta;
    }
    
    public Vehiculo vehiculo()
    {
        return _vehiculo;
    }
    
    public void vehiculo(String placa)
    {
        _vehiculo = Bus.conPlaca(placa);
    }
    
    public String latitud()
    {
        return _latitud;
    }
    
    public void latitud(String latitud)
    {
        _latitud = latitud;
    }
    
    public String longitud()
    {
        return _longitud;
    }
    
    public void longitud(String longitud)
    {
        _longitud = longitud;
    }
    
    public static void nueva(int ruta, Vehiculo vehiculo, String latitud, String longitud)
    {
        Date date = new Date();
        Timestamp fechahora = new Timestamp(date.getTime());
        try
        {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/rutas", "root", "");
            PreparedStatement stmt = conexion.prepareStatement("INSERT INTO ubicacion (fechahora, ruta, vehiculo, latitud, longitud) VALUES (?, ?, ?, ?, ?);");
            stmt.setTimestamp(1, fechahora);
            stmt.setInt(2, ruta);
            stmt.setString(3, vehiculo.placa());
            stmt.setString(4, latitud);
            stmt.setString(5, longitud);
            stmt.executeUpdate();
            conexion.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public static Ubicacion de(Vehiculo vehiculo)
    {
        int id = 0;
        try
        {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/rutas", "root", "");
            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery(String.format("SELECT * FROM ubicacion WHERE vehiculo = '%s';", vehiculo.placa()));
            if(rs.last())
            {
                id = rs.getInt(1);
            }
            conexion.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return new Ubicacion(id);
    }
    
    public static ArrayList<Ubicacion> todas()
    {
        ArrayList<Ubicacion> ubicaciones = new ArrayList<Ubicacion>();
        try
        {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/rutas", "root", "");
            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id FROM ubicacion;");
            while(rs.next())
            {
                ubicaciones.add(new Ubicacion(rs.getInt(1)));
            }
            conexion.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return ubicaciones;
    }
}
package com.rdiaz.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Ruta
{
    private int id;
    private String origen;
    private String destino;
    
    public Ruta(String origen, String destino)
    {
        setOrigen(origen);
        setDestino(destino);
        try
        {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/rutas", "root", "");
            PreparedStatement stmt = conexion.prepareStatement("INSERT INTO rutas (origen, destino) VALUES (?, ?);");
            stmt.setString(1, origen);
            stmt.setString(2, destino);
            stmt.executeUpdate();
            
            stmt = conexion.prepareStatement("SELECT @@IDENTITY FROM rutas;");
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
    
    public Ruta(int id, String origen, String destino)
    {
        setId(id);
        setOrigen(origen);
        setDestino(destino);
    }
    
    public int getId()
    {
        return id;
    }
    
    void setId(int id)
    {
        this.id = id;
    }
    
    public String getOrigen()
    {
        return origen;
    }
    
    void setOrigen(String origen)
    {
        this.origen = origen;
    }
    
    public String getDestino()
    {
        return destino;
    }
    
    void setDestino(String destino)
    {
        this.destino = destino;
    }
    
    public String getDescripcion()
    {
        return String.format("%s - %s", getOrigen(), getDestino());
    }
    
    public void editar(String origen, String destino)
    {
        setOrigen(origen);
        setDestino(destino);
        try
        {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/rutas", "root", "");
            PreparedStatement stmt = conexion.prepareStatement("UPDATE rutas SET origen = ?, destino = ? WHERE id = ?;");
            stmt.setString(1, origen);
            stmt.setString(2, destino);
            stmt.setInt(3, getId());
            stmt.executeUpdate();
            
            conexion.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

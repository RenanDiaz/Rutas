package com.rdiaz.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Ruta
{
    private int id;
    private String partida;
    private String destino;
    
    public Ruta(String partida, String destino)
    {
        setPartida(partida);
        setDestino(destino);
        try
        {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/rutas", "root", "");
            PreparedStatement stmt = conexion.prepareStatement("INSERT INTO rutas (partida, destino) VALUES (?, ?);");
            stmt.setString(1, partida);
            stmt.setString(2, destino);
            stmt.executeUpdate();
            
            stmt = conexion.prepareStatement("SELECT id FROM rutas WHERE destino = ? AND partida = ?;");
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
    
    public Ruta(int id, String partida, String destino)
    {
        setId(id);
        setPartida(partida);
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
    
    public String getPartida()
    {
        return partida;
    }
    
    void setPartida(String partida)
    {
        this.partida = partida;
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
        return String.format("%s - %s", getPartida(), getDestino());
    }
    
    public static Ruta nueva(String partida, String destino)
    {
        int id = 0;
        try
        {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/rutas", "root", "");
            PreparedStatement stmt = conexion.prepareStatement("INSERT INTO rutas (partida, destino) VALUES (?, ?);");
            stmt.setString(1, partida);
            stmt.setString(2, destino);
            stmt.executeUpdate();
            
            stmt = conexion.prepareStatement("SELECT id FROM rutas WHERE partida = ? AND destino = ?;");
            stmt.setString(1, partida);
            stmt.setString(2, destino);
            ResultSet rs = stmt.executeQuery();
            if(rs.last())
            {
                id = rs.getInt(1);
            }
            
            conexion.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return new Ruta(id, partida, destino);
    }
    
    public void editar(String partida, String destino)
    {
        setPartida(partida);
        setDestino(destino);
        try
        {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/rutas", "root", "");
            PreparedStatement stmt = conexion.prepareStatement("UPDATE rutas SET partida = ?, destino = ? WHERE id = ?;");
            stmt.setString(1, partida);
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

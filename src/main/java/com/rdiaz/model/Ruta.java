package com.rdiaz.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Ruta
{
    private int _id;
    private String _partida;
    private String _destino;
    
    public Ruta(int id)
    {
        id(id);
        try
        {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/rutas", "root", "");
            PreparedStatement stmt = conexion.prepareStatement("SELECT * FROM rutas WHERE id = ?;");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.last())
            {
                partida(rs.getString(2));
                destino(rs.getString(3));
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
    
    void id(int id)
    {
        _id = id;
    }
    
    public String partida()
    {
        return _partida;
    }
    
    void partida(String partida)
    {
        _partida = partida;
    }
    
    public String destino()
    {
        return _destino;
    }
    
    void destino(String destino)
    {
        _destino = destino;
    }
    
    public String descripcion()
    {
        return String.format("%s - %s", partida(), destino());
    }
    
    public static ArrayList<Ruta> todas()
    {
        ArrayList<Ruta> rutas = new ArrayList<>();
        try
        {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/rutas", "root", "");
            PreparedStatement stmt = conexion.prepareStatement("SELECT id FROM rutas;");
            ResultSet rs = stmt.executeQuery();
            while(rs.next())
            {
                rutas.add(new Ruta(rs.getInt(1)));
            }
            conexion.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return rutas;
    }
}
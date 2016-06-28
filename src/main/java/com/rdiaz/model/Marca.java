package com.rdiaz.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Marca
{
    private int _id;
    private String _nombre;
    
    public Marca(String nombre)
    {
        _nombre = nombre;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/rutas", "root", "");
            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery(String.format("SELECT * FROM marcas WHERE nombre = '%s';", nombre));
            if(!rs.next())
            {
                stmt.execute(String.format("INSERT INTO marcas (nombre) VALUES ('%s');", nombre));
                rs = stmt.executeQuery(String.format("SELECT * FROM marcas WHERE nombre = '%s';", nombre));
                rs.next();
            }
            _id = rs.getInt(1);
            conexion.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    Marca(int id, String nombre)
    {
        id(id);
        nombre(nombre);
    }
    
    public int id()
    {
        return _id;
    }
    
    public void id(int id)
    {
        _id = id;
    }
    
    public String nombre()
    {
        return _nombre;
    }
    
    public void nombre(String nombre)
    {
        _nombre = nombre;
    }
    
    public static String getNombre(int id)
    {
        String nombre = null;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/rutas", "root", "");
            Statement stmt = conexion.createStatement();
            
            ResultSet rs = stmt.executeQuery(String.format("SELECT nombre FROM marcas WHERE id = %d;", id));
            rs.first();
            nombre = rs.getString(1);
            conexion.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return nombre;
    }
    
    public static int getId(String nombre)
    {
        int id = 0;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/rutas", "root", "");
            Statement stmt = conexion.createStatement();
            
            ResultSet rs = stmt.executeQuery(String.format("SELECT id FROM marcas WHERE nombre = '%s';", nombre));
            rs.first();
            id = rs.getInt(1);
            conexion.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return id;
    }
}

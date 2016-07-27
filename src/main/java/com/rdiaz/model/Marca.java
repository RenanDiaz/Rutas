package com.rdiaz.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Marca
{
    private int id;
    private String nombre;
    
    public Marca(String nombre)
    {
        super();
        setNombre(nombre);
        try
        {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/rutas", "root", "");
            PreparedStatement stmt = conexion.prepareStatement("INSERT INTO marcas (nombre) VALUES (?);");
            stmt.setString(1, nombre);
            stmt.executeUpdate();
            
            stmt = conexion.prepareStatement("SELECT id FROM marcas WHERE nombre = ?;");
            stmt.setString(1, nombre);
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
    
    Marca(int id, String nombre)
    {
        super();
        setId(id);
        setNombre(nombre);
    }
    
    public int getId()
    {
        return this.id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public String getNombre()
    {
        return this.nombre;
    }
    
    public void setNombre(String nombre)
    {
        this.nombre = nombre;
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

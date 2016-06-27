package com.rdiaz.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public abstract class Vehiculo
{
    private String _placa;
    private String _marca;
    private String _modelo;
    private int _anno;
    private TipoDeVehiculo _tipo;
    
    public Vehiculo(String placa, String marca, String modelo, int anno, TipoDeVehiculo tipo)
    {
        placa(placa);
        marca(marca);
        modelo(modelo);
        anno(anno);
        tipo(tipo);
    }
    
    public String placa()
    {
        return _placa;
    }
    
    public void placa(String placa)
    {
        _placa = placa;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/rutas", "root", "");
            Statement stmt = conexion.createStatement();
            
            stmt.execute(String.format("INSERT INTO vehiculos (placa) VALUES (%s);", placa));
            conexion.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public String marca()
    {
        return _marca;
    }
    
    public void marca(String marca)
    {
        _marca = marca;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/rutas", "root", "");
            Statement stmt = conexion.createStatement();
            
            ResultSet rs = stmt.executeQuery(String.format("SELECT * FROM marcas WHERE nombre = '%s';", marca));
            if(rs.next())
            {
                stmt.execute(String.format("INSERT INTO marcas (nombre) VALUES (%s);", marca));
                rs = stmt.executeQuery(String.format("SELECT * FROM marcas WHERE nombre = '%s';", marca));
            }
            else rs.first();
            while(rs.next())
            {
                int id = rs.getInt(1);
                stmt.execute(String.format("UPDATE vehiculos SET marca = %d WHERE placa = '%s';", id, placa()));
            }
            conexion.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public String modelo()
    {
        return _modelo;
    }
    
    public void modelo(String modelo)
    {
        _modelo = modelo;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/rutas", "root", "");
            Statement stmt = conexion.createStatement();
            
            stmt.execute(String.format("UPDATE vehiculos SET modelo = '%s' WHERE placa = '%s';", modelo, placa()));
            conexion.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public int anno()
    {
        return _anno;
    }
    
    public void anno(int anno)
    {
        _anno = anno;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/rutas", "root", "");
            Statement stmt = conexion.createStatement();
            
            stmt.execute(String.format("UPDATE vehiculos SET anno = %d WHERE placa = '%s';", anno, placa()));
            conexion.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public TipoDeVehiculo tipo()
    {
        return _tipo;
    }
    
    public void tipo(TipoDeVehiculo tipo)
    {
        _tipo = tipo;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/rutas", "root", "");
            Statement stmt = conexion.createStatement();
            
            stmt.execute(String.format("UPDATE vehiculos SET tipo = %d WHERE placa = '%s';", tipo.ordinal(), placa()));
            conexion.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

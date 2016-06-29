package com.rdiaz.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public abstract class Vehiculo
{
    private String _placa;
    private int _marca;
    private String _modelo;
    private int _anno;
    private TipoDeVehiculo _tipo;
    
    public Vehiculo(String placa, int marca, String modelo, int anno, TipoDeVehiculo tipo)
    {
        _placa = placa;
        _marca = marca;
        _modelo = modelo;
        _anno = anno;
        _tipo = tipo;

        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/rutas", "root", "");
            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery(String.format("SELECT * FROM vehiculos WHERE placa = '%s';", placa));
            if(!rs.next())
            {
                stmt.execute(String.format("INSERT INTO vehiculos (placa, marca, modelo, anno, tipo) VALUES ('%s', %d, '%s', %d, %d);", placa, marca, modelo, anno, tipo.ordinal()));
            }
            conexion.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
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
            
            stmt.execute(String.format("INSERT INTO vehiculos (placa) VALUES ('%s');", placa));
            conexion.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public String marca()
    {
        return Marca.getNombre(_marca);
    }
    
    public void marca(int marca)
    {
        _marca = marca;
        try
        {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/rutas", "root", "");
            Statement stmt = conexion.createStatement();
            
            stmt.execute(String.format("UPDATE vehiculos SET marca = %d WHERE placa = '%s';", marca, placa()));
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
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/rutas", "root", "");
            Statement stmt = conexion.createStatement();
            
            stmt.execute(String.format("UPDATE vehiculos SET anno = %d WHERE placa = '%s';", anno, placa()));
            conexion.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public String tipo()
    {
        return _tipo.toString();
    }
    
    public void tipo(TipoDeVehiculo tipo)
    {
        _tipo = tipo;
        try
        {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/rutas", "root", "");
            Statement stmt = conexion.createStatement();
            
            stmt.execute(String.format("UPDATE vehiculos SET tipo = %d WHERE placa = '%s';", tipo.ordinal(), placa()));
            conexion.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public String nombre()
    {
        return String.format("%s %s %s de %d con placa %s", tipo(), marca(), modelo(), anno(), placa());
    }
    
    public static Vehiculo conPlaca(String placa)
    {
        int marca = 0;
        String modelo = "";
        int anno = 0;
        int tipo = 0;
        
        try
        {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/rutas", "root", "");
            PreparedStatement stmt = conexion.prepareStatement("SELECT * FROM vehiculos WHERE placa = ?;");
            stmt.setString(1, placa);
            ResultSet rs = stmt.executeQuery();
            if(rs.next())
            {
                marca = rs.getInt(2);
                modelo = rs.getString(3);
                anno = rs.getInt(4);
                tipo = rs.getInt(5);
            }
            conexion.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        
        switch (tipo)
        {
        case 0:
            return new Bus(placa, marca, modelo, anno);
        case 1:
            return new Taxi(placa, marca, modelo, anno);
        case 2:
            return new Particular(placa, marca, modelo, anno);
        }
        return null;
    }

    public void editar(int marca, String modelo, int anno)
    {
        marca(marca);
        modelo(modelo);
        anno(anno);
    }
}

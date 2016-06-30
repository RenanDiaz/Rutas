package com.rdiaz.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
            PreparedStatement stmt = conexion.prepareStatement("SELECT * FROM vehiculos WHERE placa = ?;");
            stmt.setString(1, placa);
            ResultSet rs = stmt.executeQuery();
            if(!rs.next())
            {
                stmt = conexion.prepareStatement("INSERT INTO vehiculos (placa, marca, modelo, anno, tipo) VALUES (?, ?, ?, ?, ?);");
                stmt.setString(1, placa);
                stmt.setInt(2, marca);
                stmt.setString(3, modelo);
                stmt.setInt(4, anno);
                stmt.setInt(5, tipo.ordinal());
                stmt.execute();
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
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/rutas", "root", "");
            PreparedStatement stmt = conexion.prepareStatement("INSERT INTO vehiculos (placa) VALUES (?);");
            stmt.setString(1, placa());
            
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
            PreparedStatement stmt = conexion.prepareStatement("UPDATE vehiculos SET marca = ? WHERE placa = ?;");
            stmt.setInt(1, marca);
            stmt.setString(2, placa());
            
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
            PreparedStatement stmt = conexion.prepareStatement("UPDATE vehiculos SET modelo = ? WHERE placa = ?;");
            stmt.setString(1, modelo);
            stmt.setString(2, placa());
            
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
            PreparedStatement stmt = conexion.prepareStatement("UPDATE vehiculos SET anno = ? WHERE placa = ?;");
            stmt.setInt(1, anno);
            stmt.setString(2, placa());
            
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
            PreparedStatement stmt = conexion.prepareStatement("UPDATE vehiculos SET tipo = ? WHERE placa = ?;");
            stmt.setInt(1, tipo.ordinal());
            stmt.setString(2, placa());
            
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
    
    public String nombreCorto()
    {
        return String.format("%s %s placa %s", marca(), modelo(), placa());
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

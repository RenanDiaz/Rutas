package com.rdiaz.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public abstract class Vehiculo
{
    private String placa;
    private Marca marca;
    private String modelo;
    private int anno;
    private TipoDeVehiculo tipo;
    
    public Vehiculo(String placa, Marca marca, String modelo, int anno, TipoDeVehiculo tipo)
    {
        setPlaca(placa);
        setMarca(marca);
        setModelo(modelo);
        setAnno(anno);
        setTipo(tipo);
    }
    
    public String getPlaca()
    {
        return this.placa;
    }
    
    public void setPlaca(String placa)
    {
        this.placa = placa;
    }
    
    public Marca getMarca()
    {
        return this.marca;
    }
    
    public void setMarca(Marca marca)
    {
        this.marca = marca;
    }
    
    public String getModelo()
    {
        return this.modelo;
    }
    
    public void setModelo(String modelo)
    {
        this.modelo = modelo;
    }
    
    public int getAnno()
    {
        return this.anno;
    }
    
    public void setAnno(int anno)
    {
        this.anno = anno;
    }
    
    public String getTipo()
    {
        return this.tipo.toString();
    }
    
    public void setTipo(TipoDeVehiculo tipo)
    {
        this.tipo = tipo;
    }
    
    public String getNombre()
    {
        return String.format("%s %s %s de %d con placa %s", getTipo(), getMarca().getNombre(), getModelo(), getAnno(), getPlaca());
    }
    
    public String getNombreCorto()
    {
        return String.format("%s %s placa %s", getMarca().getNombre(), getModelo(), getPlaca());
    }
    
    public static Vehiculo nuevo(String placa, Marca marca, String modelo, int anno, TipoDeVehiculo tipo)
    {
        try
        {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/rutas", "root", "");
            PreparedStatement stmt = conexion.prepareStatement("INSERT INTO vehiculos (placa, marca, modelo, anno, tipo) VALUES (?, ?, ?, ?, ?);");
            stmt.setString(1, placa);
            stmt.setInt(2, marca.getId());
            stmt.setString(3, modelo);
            stmt.setInt(4, anno);
            stmt.setInt(5, tipo.ordinal());
            stmt.executeUpdate();
            
            conexion.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        switch(tipo.ordinal())
        {
        case 0:
            return new Bus(placa, marca, modelo, anno);
        case 1:
            return new Taxi(placa, marca, modelo, anno);
        case 2:
            return new Particular(placa, marca, modelo, anno);
        default:
            return null;
        }
    }

    public void editar(Marca marca, String modelo, int anno, TipoDeVehiculo tipo)
    {
        setMarca(marca);
        setModelo(modelo);
        setAnno(anno);
        setTipo(tipo);
        try
        {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/rutas", "root", "");
            PreparedStatement stmt = conexion.prepareStatement("UPDATE vehiculos SET marca = ?, modelo = ?, anno = ?, tipo = ? WHERE placa = ?;");
            stmt.setInt(1, marca.getId());
            stmt.setString(2, modelo);
            stmt.setInt(3, anno);
            stmt.setInt(4, tipo.ordinal());
            stmt.setString(5, getPlaca());
            stmt.executeUpdate();
            
            conexion.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

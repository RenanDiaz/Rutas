package com.rdiaz.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

public class RutaAsignada
{
    private int id;
    private Vehiculo vehiculo;
    private Ruta ruta;
    private Timestamp horaInicial;
    private Timestamp horaFinal;
    
    public RutaAsignada(Vehiculo vehiculo, Ruta ruta, long inicio, long fin)
    {
        Timestamp horaInicial = new Timestamp(inicio);
        Timestamp horaFinal = new Timestamp(fin);
        setVehiculo(vehiculo);
        setRuta(ruta);
        setHoraInicial(horaInicial);
        setHoraFinal(horaFinal);
        try
        {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/rutas", "root", "");
            PreparedStatement stmt = conexion.prepareStatement("INSERT INTO ruta_asignada (vehiculo, ruta, hora_inicial, hora_final) VALUES (?, ?, ?, ?);");
            stmt.setString(1, vehiculo.getPlaca());
            stmt.setInt(2, ruta.getId());
            stmt.setTimestamp(3, horaInicial);
            stmt.setTimestamp(4, horaFinal);
            stmt.executeUpdate();
            
            stmt = conexion.prepareStatement("SELECT @@IDENTITY FROM ruta_asignada;");
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
    
    public RutaAsignada(int id, Vehiculo vehiculo, Ruta ruta, Timestamp horaInicial, Timestamp horaFinal)
    {
        setId(id);
        setVehiculo(vehiculo);
        setRuta(ruta);
        setHoraInicial(horaInicial);
        setHoraFinal(horaFinal);
    }
    
    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public Vehiculo getVehiculo()
    {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo)
    {
        this.vehiculo = vehiculo;
    }

    public Ruta getRuta()
    {
        return ruta;
    }

    public void setRuta(Ruta ruta)
    {
        this.ruta = ruta;
    }

    public Timestamp getHoraInicial()
    {
        return horaInicial;
    }

    public void setHoraInicial(Timestamp horaInicial)
    {
        this.horaInicial = horaInicial;
    }

    public Timestamp getHoraFinal()
    {
        return horaFinal;
    }

    public void setHoraFinal(Timestamp horaFinal)
    {
        this.horaFinal = horaFinal;
    }

    public void editar(Vehiculo vehiculo, Ruta ruta, Timestamp horaInicial, Timestamp horaFinal)
    {
        setVehiculo(vehiculo);
        setRuta(ruta);
        setHoraInicial(horaInicial);
        setHoraFinal(horaFinal);
        try
        {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/rutas", "root", "");
            PreparedStatement stmt = conexion.prepareStatement("UPDATE ruta_asignada SET vehiculo = ?, ruta = ?, hora_inicial = ?, hora_final = ? WHERE id = ?;");
            stmt.setString(1, vehiculo.getPlaca());
            stmt.setInt(2, ruta.getId());
            stmt.setTimestamp(3, horaInicial);
            stmt.setTimestamp(4, horaFinal);
            stmt.setInt(5, getId());
            stmt.executeUpdate();
            
            conexion.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

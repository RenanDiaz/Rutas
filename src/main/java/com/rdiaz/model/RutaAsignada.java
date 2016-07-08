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
    private Timestamp horaDePartida;
    private Timestamp horaDeLlegada;
    
    public RutaAsignada(Vehiculo vehiculo, Ruta ruta, long partida, long llegada)
    {
        Timestamp horaDePartida = new Timestamp(partida);
        Timestamp horaDeLlegada = new Timestamp(llegada);
        setVehiculo(vehiculo);
        setRuta(ruta);
        setHoraDePartida(horaDePartida);
        setHoraDeLlegada(horaDeLlegada);
        try
        {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/rutas", "root", "");
            PreparedStatement stmt = conexion.prepareStatement("INSERT INTO ruta_asignada (vehiculo, ruta, hora_de_partida, hora_de_llegada) VALUES (?, ?, ?, ?);");
            stmt.setString(1, vehiculo.getPlaca());
            stmt.setInt(2, ruta.getId());
            stmt.setTimestamp(3, horaDePartida);
            stmt.setTimestamp(4, horaDeLlegada);
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
    
    public RutaAsignada(int id, Vehiculo vehiculo, Ruta ruta, Timestamp horaDePartida, Timestamp horaDeLlegada)
    {
        setId(id);
        setVehiculo(vehiculo);
        setRuta(ruta);
        setHoraDePartida(horaDePartida);
        setHoraDeLlegada(horaDeLlegada);
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

    public Timestamp getHoraDePartida()
    {
        return horaDePartida;
    }

    public void setHoraDePartida(Timestamp horaDePartida)
    {
        this.horaDePartida = horaDePartida;
    }

    public Timestamp getHoraDeLlegada()
    {
        return horaDeLlegada;
    }

    public void setHoraDeLlegada(Timestamp horaDeLlegada)
    {
        this.horaDeLlegada = horaDeLlegada;
    }
    
    public String getDescripcion()
    {
        return String.format("%s %s", vehiculo.getNombreCorto(), ruta.getDescripcion());
    }

    public void editar(Vehiculo vehiculo, Ruta ruta, Timestamp horaDePartida, Timestamp horaDeLlegada)
    {
        setVehiculo(vehiculo);
        setRuta(ruta);
        setHoraDePartida(horaDePartida);
        setHoraDeLlegada(horaDeLlegada);
        try
        {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/rutas", "root", "");
            PreparedStatement stmt = conexion.prepareStatement("UPDATE ruta_asignada SET vehiculo = ?, ruta = ?, hora_de_partida = ?, hora_de_llegada = ? WHERE id = ?;");
            stmt.setString(1, vehiculo.getPlaca());
            stmt.setInt(2, ruta.getId());
            stmt.setTimestamp(3, horaDePartida);
            stmt.setTimestamp(4, horaDeLlegada);
            stmt.setInt(5, getId());
            stmt.executeUpdate();
            
            conexion.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

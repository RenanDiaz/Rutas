package com.rdiaz.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Calendar;

public class RutaAsignada
{
    private int id;
    private Vehiculo vehiculo;
    private Ruta ruta;
    private Timestamp fechahoraDePartida;
    private Timestamp fechahoraDeLlegada;
    
    public RutaAsignada(Vehiculo vehiculo, Ruta ruta, long partida, long llegada)
    {
        Timestamp fechahoraDePartida = new Timestamp(partida);
        Timestamp fechahoraDeLlegada = new Timestamp(llegada);
        setVehiculo(vehiculo);
        setRuta(ruta);
        setFechahoraDePartida(fechahoraDePartida);
        setFechahoraDeLlegada(fechahoraDeLlegada);
        try
        {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/rutas", "root", "");
            PreparedStatement stmt = conexion.prepareStatement("INSERT INTO ruta_asignada (vehiculo, ruta, fechahora_de_partida, fechahora_de_llegada) VALUES (?, ?, ?, ?);");
            stmt.setString(1, vehiculo.getPlaca());
            stmt.setInt(2, ruta.getId());
            stmt.setTimestamp(3, fechahoraDePartida);
            stmt.setTimestamp(4, fechahoraDeLlegada);
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
    
    public RutaAsignada(int id, Vehiculo vehiculo, Ruta ruta, Timestamp fechahoraDePartida, Timestamp fechahoraDeLlegada)
    {
        setId(id);
        setVehiculo(vehiculo);
        setRuta(ruta);
        setFechahoraDePartida(fechahoraDePartida);
        setFechahoraDeLlegada(fechahoraDeLlegada);
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

    public Timestamp getFechahoraDePartida()
    {
        return fechahoraDePartida;
    }

    public void setFechahoraDePartida(Timestamp fechahoraDePartida)
    {
        this.fechahoraDePartida = fechahoraDePartida;
    }

    public Timestamp getFechahoraDeLlegada()
    {
        return fechahoraDeLlegada;
    }

    public void setFechahoraDeLlegada(Timestamp fechahoraDeLlegada)
    {
        this.fechahoraDeLlegada = fechahoraDeLlegada;
    }
    
    public String getDescripcion()
    {
        return String.format("%s %s", vehiculo.getNombreCorto(), ruta.getDescripcion());
    }
    
    public String getFechaDePartida()
    {
        return fechaFormateada(fechahoraDePartida);
    }
    
    public String getHoraDePartida()
    {
        return horaFormateada(fechahoraDePartida);
    }
    
    public String getFechaDeLlegada()
    {
        return fechaFormateada(fechahoraDeLlegada);
    }
    
    public String getHoraDeLlegada()
    {
        return horaFormateada(fechahoraDeLlegada);
    }
    
    public String getRangoDeHoras()
    {
        return String.format("%s - %s", getHoraDePartida(), getHoraDeLlegada());
    }

    public void editar(Vehiculo vehiculo, Ruta ruta, long partida, long llegada)
    {
        Timestamp fechahoraDePartida = new Timestamp(partida);
        Timestamp fechahoraDeLlegada = new Timestamp(llegada);
        setVehiculo(vehiculo);
        setRuta(ruta);
        setFechahoraDePartida(fechahoraDePartida);
        setFechahoraDeLlegada(fechahoraDeLlegada);
        try
        {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/rutas", "root", "");
            PreparedStatement stmt = conexion.prepareStatement("UPDATE ruta_asignada SET vehiculo = ?, ruta = ?, fechahora_de_partida = ?, fechahora_de_llegada = ? WHERE id = ?;");
            stmt.setString(1, vehiculo.getPlaca());
            stmt.setInt(2, ruta.getId());
            stmt.setTimestamp(3, fechahoraDePartida);
            stmt.setTimestamp(4, fechahoraDeLlegada);
            stmt.setInt(5, getId());
            stmt.executeUpdate();
            
            conexion.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    private String fechaFormateada(Timestamp fechahora)
    {
        long timestamp = fechahora.getTime();
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp);
        int anno = cal.get(Calendar.YEAR);
        int mes = cal.get(Calendar.MONTH) + 1;
        int dia = cal.get(Calendar.DATE);
        
        return String.format("%d-%02d-%02d", anno, mes, dia);
    }

    
    private String horaFormateada(Timestamp fechahora)
    {
        long timestamp = fechahora.getTime();
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp);
        int hora = cal.get(Calendar.HOUR_OF_DAY);
        int minuto = cal.get(Calendar.MINUTE);
        
        return String.format("%02d:%02d", hora, minuto);
    }
}

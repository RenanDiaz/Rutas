package com.rdiaz.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class Ubicacion
{
    private int _id;
    private Timestamp _fechahora;
    private Ruta _ruta;
    private Vehiculo _vehiculo;
    private String _latitud;
    private String _longitud;
    
    public Ubicacion(int id)
    {
        id(id);
        try
        {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/rutas", "root", "");
            PreparedStatement stmt = conexion.prepareStatement("SELECT * FROM ubicacion WHERE id = ?;");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.last())
            {
                fechahora(rs.getTimestamp(2));
                ruta(rs.getInt(3));
                vehiculo(rs.getString(4));
                latitud(rs.getString(5));
                longitud(rs.getString(6));
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
    
    public void id(int id)
    {
        _id = id;
    }
    public Timestamp fechahora()
    {
        return _fechahora;
    }
    
    public void fechahora(Timestamp fechahora)
    {
        _fechahora = fechahora;
    }
    
    public Ruta ruta()
    {
        return _ruta;
    }
    
    public void ruta(int ruta)
    {
        _ruta = new Ruta(ruta);
        try
        {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/rutas", "root", "");
            PreparedStatement stmt = conexion.prepareStatement("UPDATE ubicacion SET ruta = ? WHERE id = ?;");
            stmt.setInt(1, ruta);
            stmt.setInt(2, id());
            stmt.executeUpdate();
            
            conexion.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public Vehiculo vehiculo()
    {
        return _vehiculo;
    }
    
    public void vehiculo(String placa)
    {
        _vehiculo = Vehiculo.conPlaca(placa);
        try
        {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/rutas", "root", "");
            PreparedStatement stmt = conexion.prepareStatement("UPDATE ubicacion SET vehiculo = ? WHERE id = ?;");
            stmt.setString(1, placa);
            stmt.setInt(2, id());
            stmt.executeUpdate();
            
            conexion.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public String latitud()
    {
        return _latitud;
    }
    
    public void latitud(String latitud)
    {
        _latitud = latitud;
        try
        {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/rutas", "root", "");
            PreparedStatement stmt = conexion.prepareStatement("UPDATE ubicacion SET latitud = ? WHERE id = ?;");
            stmt.setString(1, latitud);
            stmt.setInt(2, id());
            stmt.executeUpdate();
            
            conexion.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public String longitud()
    {
        return _longitud;
    }
    
    public void longitud(String longitud)
    {
        _longitud = longitud;
        try
        {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/rutas", "root", "");
            PreparedStatement stmt = conexion.prepareStatement("UPDATE ubicacion SET longitud = ? WHERE id = ?;");
            stmt.setString(1, longitud);
            stmt.setInt(2, id());
            stmt.executeUpdate();
            
            conexion.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public static void nueva(int ruta, String placa, String latitud, String longitud)
    {
        Date date = new Date();
        Timestamp fechahora = new Timestamp(date.getTime());
        try
        {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/rutas", "root", "");
            PreparedStatement stmt = conexion.prepareStatement("INSERT INTO ubicacion (fechahora, ruta, vehiculo, latitud, longitud) VALUES (?, ?, ?, ?, ?);");
            stmt.setTimestamp(1, fechahora);
            stmt.setInt(2, ruta);
            stmt.setString(3, placa);
            stmt.setString(4, latitud);
            stmt.setString(5, longitud);
            stmt.executeUpdate();
            conexion.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void editar(int ruta, String placa, String latitud, String longitud)
    {
        ruta(ruta);
        vehiculo(placa);
        latitud(latitud);
        longitud(longitud);
    }
    
    public static Ubicacion de(Vehiculo vehiculo)
    {
        int id = 0;
        try
        {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/rutas", "root", "");
            PreparedStatement stmt = conexion.prepareStatement("SELECT * FROM ubicacion WHERE vehiculo = ?;");
            stmt.setString(1, vehiculo.placa());
            ResultSet rs = stmt.executeQuery();
            if(rs.last())
            {
                id = rs.getInt(1);
            }
            conexion.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return new Ubicacion(id);
    }
    
    public static ArrayList<Ubicacion> todas()
    {
        ArrayList<Ubicacion> ubicaciones = new ArrayList<Ubicacion>();
        try
        {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/rutas", "root", "");
            PreparedStatement stmt = conexion.prepareStatement("SELECT id FROM ubicacion;");
            ResultSet rs = stmt.executeQuery();
            while(rs.next())
            {
                ubicaciones.add(new Ubicacion(rs.getInt(1)));
            }
            conexion.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return ubicaciones;
    }

    public static ArrayList<Ubicacion> rango(int inicio, int fin)
    {
        ArrayList<Ubicacion> ubicaciones = new ArrayList<>();
        ArrayList<Ubicacion> todas = todas();
        
        for(int i = inicio - 1; i < fin; i++)
            ubicaciones.add(todas.get(i));
        
        return ubicaciones;
    }
    
    public static double calcularDistanciaTotalEntre(int inicio, int fin)
    {
        ArrayList<Ubicacion> rango = rango(inicio, fin);
        double distanciaTotal = 0;
        double tiempoAcumulado = 0;
        for(int i = 0; i < rango.size() - 1; i++)
        {
            Ubicacion partida = rango.get(i);
            Ubicacion destino = rango.get(i + 1);
            double distancia = distanciaEntre(partida, destino);
            distanciaTotal += distancia;
            double tiempo = destino.fechahora().getTime() - partida.fechahora().getTime();
            tiempoAcumulado += tiempo;
            double velocidad = (distancia / 1000) / (tiempo / 1000 / 60 / 60);
            System.out.printf("%d. %6.2fm\t%.2fms\t%6.2fkm/h\t%.2fm\t%.2fmin%n", destino.id(), distancia, tiempo, velocidad, distanciaTotal, tiempoAcumulado / 1000 / 60);
        }
        
        System.out.printf("Promedios: %6.2fm\t%.2fs\t%6.2fkm/h%n", distanciaTotal / rango.size(), tiempoAcumulado / 1000 / rango.size(), (distanciaTotal / 1000) / (tiempoAcumulado / 1000 / 60 / 60));
        
        return distanciaTotal;
    }
    
    public static double distanciaEntre(Ubicacion partida, Ubicacion destino)
    {
        double lat1 = Double.valueOf(partida.latitud());
        double lat2 = Double.valueOf(destino.latitud());
        double lon1 = Double.valueOf(partida.longitud());
        double lon2 = Double.valueOf(destino.longitud());
        double el1 = 0;
        double el2 = 0;
        final int R = 6371; // Radius of the earth

        Double latDistance = Math.toRadians(lat2 - lat1);
        Double lonDistance = Math.toRadians(lon2 - lon1);
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }
}
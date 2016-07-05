package com.rdiaz.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class Ruta
{
    private int id;
    private String partida;
    private String destino;
    
    public Ruta(int id)
    {
        setId(id);
        try
        {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/rutas", "root", "");
            PreparedStatement stmt = conexion.prepareStatement("SELECT * FROM rutas WHERE id = ?;");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.last())
            {
                setPartida(rs.getString(2));
                setDestino(rs.getString(3));
            }
            conexion.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public int getId()
    {
        return id;
    }
    
    void setId(int id)
    {
        this.id = id;
    }
    
    public String getPartida()
    {
        return partida;
    }
    
    void setPartida(String partida)
    {
        this.partida = partida;
    }
    
    public String getDestino()
    {
        return destino;
    }
    
    void setDestino(String destino)
    {
        this.destino = destino;
    }
    
    public String getDescripcion()
    {
        return String.format("%s - %s", getPartida(), getDestino());
    }
    
    public static ArrayList<Ruta> todas()
    {
        ArrayList<Ruta> rutas = new ArrayList<>();
        try
        {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/rutas", "root", "");
            PreparedStatement stmt = conexion.prepareStatement("SELECT id FROM rutas;");
            ResultSet rs = stmt.executeQuery();
            while(rs.next())
            {
                rutas.add(new Ruta(rs.getInt(1)));
            }
            conexion.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return rutas;
    }
    
    public static JSONObject json()
    {
        JSONArray arreglo = new JSONArray();
        for(Ruta ruta : todas())
        {
            JSONObject entrada = new JSONObject();
            entrada.put("id", ruta.getId());
            entrada.put("partida", ruta.getPartida());
            entrada.put("destino", ruta.getDestino());
            arreglo.put(entrada);
        }
        return new JSONObject().put("rutas", arreglo);
    }
    
    public static JSONObject json(String busqueda)
    {
        JSONArray arreglo = new JSONArray();
        for(Ruta ruta : todas())
        {
            if(busqueda.isEmpty() || ruta.getPartida().contains(busqueda) || ruta.getDestino().contains(busqueda))
            {
                JSONObject entrada = new JSONObject();
                entrada.put("id", ruta.getId());
                entrada.put("partida", ruta.getPartida());
                entrada.put("destino", ruta.getDestino());
                arreglo.put(entrada);
            }
        }
        return new JSONObject().put("rutas", arreglo);
    }
    
    public static Ruta unaRuta()
    {
        return todas().get(0);
    }
}

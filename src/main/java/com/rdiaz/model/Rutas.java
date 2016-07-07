package com.rdiaz.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Comparator;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Rutas
{
    @JsonProperty("rutas")
    ArrayList<Ruta> rutas = new ArrayList<>();
    
    public Rutas()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/rutas", "root", "");
            PreparedStatement stmt = conexion.prepareStatement("SELECT * FROM rutas;");
            ResultSet rs = stmt.executeQuery();
            while(rs.next())
            {
                int id = rs.getInt(1);
                String partida = rs.getString(2);
                String destino = rs.getString(3);
                rutas.add(new Ruta(id, partida, destino));
            }
            conexion.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public Rutas(String busqueda)
    {
        busqueda = "%" + busqueda + "%";
        try
        {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/rutas", "root", "");
            PreparedStatement stmt = conexion.prepareStatement("SELECT * FROM rutas WHERE partida LIKE ? OR destino LIKE ?;");
            stmt.setString(1, busqueda);
            stmt.setString(2, busqueda);
            ResultSet rs = stmt.executeQuery();
            while(rs.next())
            {
                int id = rs.getInt(1);
                String partida = rs.getString(2);
                String destino = rs.getString(3);
                rutas.add(new Ruta(id, partida, destino));
            }
            conexion.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void add(Ruta ruta)
    {
        rutas.add(ruta);
    }
    
    public Ruta get(int id)
    {
        for(Ruta ruta : rutas)
        {
            if(ruta.getId() == id)
            {
                return ruta;
            }
        }
        return null;
    }
    
    public JSONObject buscar(String busqueda)
    {
        JSONArray arreglo = new JSONArray();
        for(final Ruta ruta : rutas)
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
    
    public int size()
    {
        return rutas.size();
    }
    
    public void remove(String nombre)
    {
        rutas.remove(nombre);
    }
    
    public ArrayList<Ruta> getRutas()
    {
        rutas.sort(Comparator.comparing(Ruta::getDescripcion));
        return rutas;
    }
}

package com.rdiaz.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Marcas
{
    @JsonProperty("marcas")
    private ArrayList<Marca> marcas = new ArrayList<>();
    
    public Marcas()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/rutas", "root", "");
            Statement stmt = con.createStatement();
            
            ResultSet rs = stmt.executeQuery("SELECT * FROM marcas");
            clear();
            
            while (rs.next())
            {
                int id = rs.getInt(1);
                String nombre = rs.getString(2);
                add(new Marca(id, nombre));
            }
            con.close();
            
        } catch (Exception e)
        {
            System.out.println(e);
        }
    }
    
    public void add(Marca marca)
    {
        marcas.add(marca);
    }
    
    public Marca get(String nombre)
    {
        for(final Marca marca : marcas)
        {
            if(marca.getNombre().equals(nombre))
            {
                return marca;
            }
        }
        return null;
    }
    
    public Marca get(int id)
    {
        for(final Marca marca : marcas)
        {
            if(marca.getId() == id)
            {
                return marca;
            }
        }
        return null;
    }
    
    public int size()
    {
        return marcas.size();
    }
    
    public void remove(String nombre)
    {
        marcas.remove(nombre);
    }
    
    private void clear()
    {
        marcas.clear();
    }
    
    public ArrayList<Marca> getMarcas()
    {
        marcas.sort(Comparator.comparing(Marca::getNombre));
        return marcas;
    }
}

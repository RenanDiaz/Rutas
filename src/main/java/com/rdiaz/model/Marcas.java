package com.rdiaz.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Marcas
{
    private Map<String, Marca> _marcas = new HashMap<String, Marca>();
    
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
                Marca marca = new Marca(id, nombre);
                add(marca);
            }
            con.close();
            
        } catch (Exception e)
        {
            System.out.println(e);
        }
    }
    
    public void add(Marca marca)
    {
        _marcas.put(marca.nombre(), marca);
    }
    
    public Marca get(String nombre)
    {
        return _marcas.get(nombre);
    }
    
    public int size()
    {
        return _marcas.size();
    }
    
    public void remove(String nombre)
    {
        _marcas.remove(nombre);
    }
    
    private void clear()
    {
        _marcas.clear();
    }
    
    public ArrayList<Marca> lista()
    {
        ArrayList<Marca> marcas = new ArrayList<Marca>();
        for(Map.Entry<String, Marca> marca : _marcas.entrySet())
        {
            marcas.add(marca.getValue());
        }
        return marcas;
    }
}

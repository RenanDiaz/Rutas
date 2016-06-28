package com.rdiaz.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Vehiculos
{
    private Map<String, Vehiculo> _vehiculos = new HashMap<String, Vehiculo>();
    
    public Vehiculos()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/rutas", "root", "");
            Statement stmt = con.createStatement();
            
            ResultSet rs = stmt.executeQuery("SELECT * FROM vehiculos");
            clear();
            
            while (rs.next())
            {
                String placa = rs.getString(1);
                int marca = rs.getInt(2);
                String modelo = rs.getString(3);
                int anno = rs.getInt(4);
                Bus bus = new Bus(placa, marca, modelo, anno);
                add(bus);
            }
            con.close();
            
        } catch (Exception e)
        {
            System.out.println(e);
        }
    }
    
    public void add(Vehiculo vehiculo)
    {
        _vehiculos.put(vehiculo.placa(), vehiculo);
    }
    
    public Vehiculo get(String placa)
    {
        return _vehiculos.get(placa);
    }
    
    public int size()
    {
        return _vehiculos.size();
    }
    
    public void remove(String placa)
    {
        _vehiculos.remove(placa);
    }
    
    private void clear()
    {
        _vehiculos.clear();
    }
    
    public ArrayList<Vehiculo> lista()
    {
        ArrayList<Vehiculo> vehiculos = new ArrayList<Vehiculo>();
        for(Map.Entry<String, Vehiculo> vehiculo : _vehiculos.entrySet())
        {
            vehiculos.add(vehiculo.getValue());
        }
        return vehiculos;
    }
}

package com.rdiaz.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Vehiculos
{
    @JsonProperty("vehiculos")
    private ArrayList<Vehiculo> vehiculos = new ArrayList<>();
    
    public Vehiculos(Marcas marcas)
    {
        try
        {
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
                
                switch(rs.getInt(5))
                {
                case 0:
                    Bus bus = new Bus(placa, marcas.get(marca), modelo, anno);
                    add(bus);
                    break;
                case 1:
                    Taxi taxi = new Taxi(placa, marcas.get(marca), modelo, anno);
                    add(taxi);
                    break;
                case 2:
                    Particular particular = new Particular(placa, marcas.get(marca), modelo, anno);
                    add(particular);
                    break;
                }
            }
            con.close();
            
        } catch (Exception e)
        {
            System.out.println(e);
        }
    }
    
    public void add(Vehiculo vehiculo)
    {
        vehiculos.add(vehiculo);
    }
    
    public Vehiculo get(String placa)
    {
        for(final Vehiculo vehiculo : vehiculos)
        {
            if(vehiculo.getPlaca().equals(placa))
            {
                return vehiculo;
            }
        }
        return null;
    }
    
    public int size()
    {
        return vehiculos.size();
    }
    
    public void remove(String placa)
    {
        vehiculos.remove(placa);
    }
    
    private void clear()
    {
        vehiculos.clear();
    }
    
    public ArrayList<Vehiculo> getVehiculos()
    {
        vehiculos.sort(Comparator.comparing(Vehiculo::getPlaca));
        return vehiculos;
    }

    public ArrayList<Bus> getBuses()
    {
        ArrayList<Bus> buses = new ArrayList<>();
        for(Vehiculo vehiculo : vehiculos)
        {
            if(vehiculo instanceof Bus)
            {
                Bus bus = (Bus) vehiculo;
                buses.add(bus);
            }
        }
        buses.sort(Comparator.comparing(Bus::getPlaca));
        return buses;
    }

    public ArrayList<Taxi> getTaxis()
    {
        ArrayList<Taxi> taxis = new ArrayList<>();
        for(Vehiculo vehiculo : vehiculos)
        {
            if(vehiculo instanceof Taxi)
            {
                Taxi taxi = (Taxi) vehiculo;
                taxis.add(taxi);
            }
        }
        taxis.sort(Comparator.comparing(Taxi::getPlaca));
        return taxis;
    }

    public ArrayList<Particular> getParticulares()
    {
        ArrayList<Particular> particulares = new ArrayList<>();
        for(Vehiculo vehiculo : vehiculos)
        {
            if(vehiculo instanceof Particular)
            {
                Particular particular = (Particular) vehiculo;
                particulares.add(particular);
            }
        }
        particulares.sort(Comparator.comparing(Particular::getPlaca));
        return particulares;
    }
}

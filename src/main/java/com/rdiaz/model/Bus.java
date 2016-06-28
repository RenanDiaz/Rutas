package com.rdiaz.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Bus extends Vehiculo
{
    public Bus(String placa, int marca, String modelo, int anno)
    {
        super(placa, marca, modelo, anno, TipoDeVehiculo.BUS);
    }
    
    public static Vehiculo conPlaca(String placa)
    {
        int marca = 0;
        String modelo = "";
        int anno = 0;
        
        try
        {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/rutas", "root", "");
            PreparedStatement stmt = conexion.prepareStatement("SELECT * FROM vehiculos WHERE placa = ?;");
            stmt.setString(1, placa);
            ResultSet rs = stmt.executeQuery();
            if(rs.next())
            {
                marca = rs.getInt(2);
                modelo = rs.getString(3);
                anno = rs.getInt(4);
            }
            conexion.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return new Bus(placa, marca, modelo, anno);
    }
}

package com.rdiaz.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Ubicaciones
{
    @JsonProperty("ubicaciones")
    ArrayList<Ubicacion> ubicaciones = new ArrayList<>();
    
    public Ubicaciones(Asignaciones asignaciones)
    {
        try
        {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/rutas", "root", "");
            PreparedStatement stmt = conexion.prepareStatement("SELECT * FROM ubicacion;");
            ResultSet rs = stmt.executeQuery();
            while(rs.next())
            {
                int id = rs.getInt(1);
                Timestamp fechahora = rs.getTimestamp(2);
                Asignacion asignacion = asignaciones.get(rs.getInt(3));
                String latitud = rs.getString(4);
                String longitud = rs.getString(5);
                ubicaciones.add(new Ubicacion(id, fechahora, asignacion, latitud, longitud));
            }
            conexion.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    private Ubicaciones()
    {
        
    }
    
    public void add(Ubicacion ubicacion)
    {
        ubicaciones.add(ubicacion);
    }
    
    public Ubicacion get(int id)
    {
        for(final Ubicacion ubicacion : ubicaciones)
        {
            if(ubicacion.getId() == id)
            {
                return ubicacion;
            }
        }
        return null;
    }
    
    public int size()
    {
        return ubicaciones.size();
    }
    
    public void remove(Ubicacion ubicacion)
    {
        ubicaciones.remove(ubicacion);
    }
    
    public ArrayList<Ubicacion> getUbicaciones()
    {
        ubicaciones.sort(Comparator.comparing(Ubicacion::getId));
        return ubicaciones;
    }

    public Ubicaciones getUbicacionesEnLaRuta(Ruta ruta)
    {
        Ubicaciones ubicacionesDeLaRuta = new Ubicaciones();
        for(final Ubicacion ubicacion : ubicaciones)
        {
            if(ubicacion.getAsignacion().getRuta().getId() == ruta.getId())
            {
                ubicacionesDeLaRuta.add(ubicacion);
            }
        }
        return ubicacionesDeLaRuta;
    }

    public Ubicaciones getUbicacionesDelVehiculo(Vehiculo vehiculo)
    {
        Ubicaciones ubicacionesDelVehiculo = new Ubicaciones();
        for(final Ubicacion ubicacion : ubicaciones)
        {
            if(ubicacion.getAsignacion().getVehiculo().getPlaca().equals(vehiculo.getPlaca()))
            {
                ubicacionesDelVehiculo.add(ubicacion);
            }
        }
        return ubicacionesDelVehiculo;
    }
    
    public Ubicaciones getUbicacionesDeLaAsignacion(Asignacion asignacion)
    {
        Ubicaciones ubicacionesDeLaAsignacion = new Ubicaciones();
        for(final Ubicacion ubicacion : ubicaciones)
        {
            if(ubicacion.getAsignacion().getId() == asignacion.getId())
            {
                ubicacionesDeLaAsignacion.add(ubicacion);
            }
        }
        return ubicacionesDeLaAsignacion;
    }
    
    public Ubicaciones getUbicacionesDeLaFecha(Timestamp fechahora)
    {
        long timestamp = fechahora.getTime();
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp);
        int anno = cal.get(Calendar.YEAR);
        int mes = cal.get(Calendar.MONTH) + 1;
        int dia = cal.get(Calendar.DATE);
        Timestamp min = Timestamp.valueOf(LocalDate.of(anno, mes, dia).atStartOfDay());
        Timestamp max = Timestamp.valueOf(LocalDate.of(dia < 31 ? anno : mes < 12 ? anno : anno + 1, dia < 31 ? mes : mes < 12 ? mes + 1 : 1, dia < 31 ? dia + 1 : 1).atStartOfDay());
        
        Ubicaciones recorridoDeLaFecha = new Ubicaciones();
        
        for(final Ubicacion ubicacion : ubicaciones)
        {
            Timestamp fechahoraUbicacion = ubicacion.getFechahora();
            if((min.before(fechahoraUbicacion) || min.equals(fechahoraUbicacion)) && max.after(fechahoraUbicacion))
            {
                recorridoDeLaFecha.add(ubicacion);
            }
        }
        return recorridoDeLaFecha;
    }
    
    public Ubicacion getUltimaUbicacion()
    {
        if(size() > 0)
        {
            return getUbicaciones().get((size() - 1));
        }
        return null;
    }
    
    public Ubicaciones getUltimoRecorrido(Asignacion asignacion)
    {
        Ubicaciones recorridosDeLaAsignacion = getUbicacionesDeLaAsignacion(asignacion);
        Timestamp ultimaFecha = recorridosDeLaAsignacion.getUltimaFecha();
        return recorridosDeLaAsignacion.getUbicacionesDeLaFecha(ultimaFecha);
    }
    
    private Timestamp getUltimaFecha()
    {
        Timestamp ultimaFecha = new Timestamp(0);
        for(final Ubicacion ubicacion : ubicaciones)
        {
            if(ubicacion.getFechahora().after(ultimaFecha))
            {
                ultimaFecha = ubicacion.getFechahora();
            }
        }
        return ultimaFecha;
    }
    
    public Ubicaciones ubicacionesDe(Vehiculo vehiculo, int inicio, int fin)
    {
        Ubicaciones ubicacionesDelVehiculo = new Ubicaciones();
        for(final Ubicacion ubicacion : rango(inicio, fin))
        {
            if(ubicacion.getAsignacion().getVehiculo().equals(vehiculo))
            {
                ubicacionesDelVehiculo.add(ubicacion);
            }
        }
        return ubicacionesDelVehiculo;
    }

    public ArrayList<Ubicacion> rango(int inicio, int fin)
    {
        ArrayList<Ubicacion> ubicacionesEnElRango = new ArrayList<>();
        
        for(int i = inicio - 1; i < fin; i++)
            ubicacionesEnElRango.add(ubicaciones.get(i));
        return ubicacionesEnElRango;
    }
    
    public double calcularDistanciaTotalEntre(int inicio, int fin)
    {
        ArrayList<Ubicacion> rango = rango(inicio, fin);
        double distanciaTotal = 0;
        double tiempoAcumulado = 0;
        for(int i = 0; i < rango.size() - 1; i++)
        {
            Ubicacion origen = rango.get(i);
            Ubicacion destino = rango.get(i + 1);
            double distancia = distanciaEntre(origen, destino);
            distanciaTotal += distancia;
            double tiempo = destino.getFechahora().getTime() - origen.getFechahora().getTime();
            tiempoAcumulado += tiempo;
            double velocidad = (distancia / 1000) / (tiempo / 1000 / 60 / 60);
            System.out.printf("%d. %6.2fm\t%.2fms\t%6.2fkm/h\t%.2fm\t%.2fmin%n", destino.getId(), distancia, tiempo, velocidad, distanciaTotal, tiempoAcumulado / 1000 / 60);
        }
        
        System.out.printf("Promedios: %6.2fm\t%.2fs\t%6.2fkm/h%n", distanciaTotal / rango.size(), tiempoAcumulado / 1000 / rango.size(), (distanciaTotal / 1000) / (tiempoAcumulado / 1000 / 60 / 60));
        return distanciaTotal;
    }
    
    public double calcularDistanciaTotal()
    {
        double distanciaTotal = 0;
        double tiempoAcumulado = 0;
        for(int i = 0; i < ubicaciones.size() - 1; i++)
        {
            Ubicacion origen = ubicaciones.get(i);
            Ubicacion destino = ubicaciones.get(i + 1);
            double distancia = distanciaEntre(origen, destino);
            distanciaTotal += distancia;
            double tiempo = destino.getFechahora().getTime() - origen.getFechahora().getTime();
            tiempoAcumulado += tiempo;
            double velocidad = (distancia / 1000) / (tiempo / 1000 / 60 / 60);
            System.out.printf("%d. %6.2fm\t%.2fms\t%6.2fkm/h\t%.2fm\t%.2fmin%n", destino.getId(), distancia, tiempo, velocidad, distanciaTotal, tiempoAcumulado / 1000 / 60);
        }
        
        System.out.printf("Promedios: %6.2fm\t%.2fs\t%6.2fkm/h%n", distanciaTotal / ubicaciones.size(), tiempoAcumulado / 1000 / ubicaciones.size(), (distanciaTotal / 1000) / (tiempoAcumulado / 1000 / 60 / 60));
        return distanciaTotal;
    }
    
    public double distanciaEntre(Ubicacion origen, Ubicacion destino)
    {
        double lat1 = Double.valueOf(origen.getLatitud());
        double lat2 = Double.valueOf(destino.getLatitud());
        double lon1 = Double.valueOf(origen.getLongitud());
        double lon2 = Double.valueOf(destino.getLongitud());
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

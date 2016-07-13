package hello;

import java.util.HashMap;

public class Contadores
{
    HashMap<Class<?>, Integer> contadores = new HashMap<>();
    
    public void add(Class<?> clase, int cantidad)
    {
        contadores.put(clase, cantidad);
    }
    
    public void updateContador(Class<?> clase, int nuevaCantidad)
    {
        contadores.put(clase, nuevaCantidad);
    }
    
    public HashMap<Class<?>, Integer> getContadores()
    {
        return contadores;
    }
}

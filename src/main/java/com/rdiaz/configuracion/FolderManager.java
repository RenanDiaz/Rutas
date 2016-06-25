package com.rdiaz.configuracion;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.io.FileUtils;

public class FolderManager
{
    private final String DRIVE = getDrive();
    private final String TEMPORAL_DIRECTORY = DRIVE + "CARPETA_TEMPORAL_DE_SUBIDAS";
    
    public FolderManager()
    {
        crearCarpetas();
    }
    
    private static String getDrive()
    {
        File file = new File(".").getAbsoluteFile();
        File root = file.getParentFile();
        while (root.getParentFile() != null)
        {
            root = root.getParentFile();
        }
        return root.getPath();
    }
    
    private void crearCarpetas()
    {
        new File(TEMPORAL_DIRECTORY).mkdirs();
    }
    
    public File guardarArchivo(String fileName, byte[] bytes)
    {
        File nuevoFile = new File(TEMPORAL_DIRECTORY + File.separator + fileName);
        try
        {
            FileUtils.writeByteArrayToFile(nuevoFile, bytes);
            
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        
        return nuevoFile;
    }
    
    public void ejecutarScriptCrearTienda(String nombreEnLaURL, String nombre, String direccion, String horario, String telefono, String correo, String imagen, String nombreDeContacto) throws IOException, InterruptedException
    {
        boolean estoyEnUnix = System.getProperty("os.name").equalsIgnoreCase("Linux");
        
        if (estoyEnUnix)
        {
            String cmd = " sh $b2bScriptsHome/crearTienda.sh \"" + nombreEnLaURL + "\" \"" + nombre + "\" \"" + direccion + "\" \"" + telefono + "\" \"" + correo + "\" \"" + horario + "\" \"" + imagen + "\" \"" + nombreDeContacto + "\"";
            ejecutarBash(cmd);
        } else
        {
            Runtime.getRuntime().exec("cmd /c start  %b2bScriptsHome%\\crearTienda \"" + nombreEnLaURL + "\" \"" + nombre + "\" \"" + direccion + "\" \"" + telefono + "\" \"" + correo + "\" \"" + horario + "\" \"" + imagen + "\" \"" + nombreDeContacto + "\"");
        }
        
    }
    
    public static String ejecutarBash(String command)
    {
        StringBuilder sb = new StringBuilder();
        String[] commands = new String[] { "/bin/sh", "-c", command };
        try
        {
            Process proc = new ProcessBuilder(commands).start();
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            
            BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
            
            String s = null;
            while ((s = stdInput.readLine()) != null)
            {
                sb.append(s);
                sb.append("\n");
            }
            
            while ((s = stdError.readLine()) != null)
            {
                sb.append(s);
                sb.append("\n");
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return sb.toString();
    }
    
    public void ejecutarScriptCrearDistribuidor(String nombreEnLaURL, String nombre, int anno, String correoElectronico, String telefono, String contrasenna, String listaDeArchivosAjuntados) throws IOException, InterruptedException
    {
        boolean estoyEnUnix = System.getProperty("os.name").equalsIgnoreCase("Linux");
        
        if (estoyEnUnix)
        {
            String cmd = " sh $b2bScriptsHome/crearDistribuidor.sh \"" + nombreEnLaURL + "\" \"" + nombre + "\" \"" + anno + "\" \"" + correoElectronico + "\" \"" + telefono + "\" \"" + contrasenna + "\" \"" + listaDeArchivosAjuntados + "\"";
            ejecutarBash(cmd);
        } else
        {
            Runtime.getRuntime().exec("cmd /c start  %b2bScriptsHome%\\crearDistribuidor \"" + nombreEnLaURL + "\" \"" + nombre + "\" \"" + anno + "\" \"" + correoElectronico + "\" \"" + telefono + "\" \"" + contrasenna + "\" \"" + listaDeArchivosAjuntados + "\"");
        }
        
    }
    
}

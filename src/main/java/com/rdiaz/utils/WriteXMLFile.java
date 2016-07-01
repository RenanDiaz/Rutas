package com.rdiaz.utils;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.rdiaz.model.Ubicacion;

public class WriteXMLFile
{
    public static String createRouteFile(ArrayList<Ubicacion> ubicaciones, String path)
    {
        String response = "error";
        try
        {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            
            // root elements
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("gpx");
            doc.appendChild(rootElement);
            
            // set attributes to root element
            rootElement.setAttribute("version", "1.1");
            rootElement.setAttribute("creator", "http://190.141.120.200:8080/Rutas/");
            rootElement.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
            rootElement.setAttribute("xmlns", "http://www.topografix.com/GPX/1/1");
            rootElement.setAttribute("xsi:schemaLocation", "http://www.topografix.com/GPX/1/1 http://www.topografix.com/GPX/1/1/gpx.xsd");
            
            for(Ubicacion ubicacion : ubicaciones)
            {
                Element wpt = doc.createElement("wpt");
                rootElement.appendChild(wpt);
                wpt.setAttribute("lat", ubicacion.latitud());
                wpt.setAttribute("lon", ubicacion.longitud());
                
                Element name = doc.createElement("name");
                name.appendChild(doc.createTextNode(String.valueOf(ubicacion.id())));
                wpt.appendChild(name);
            }
            
            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            String separador = System.getProperty("file.separator");
            String filePath = String.format("%sresources%sfiles%sroute.xml", path, separador, separador);
            StreamResult result = new StreamResult(new File(filePath));
            
            // Output to console for testing
            // StreamResult result = new StreamResult(System.out);
            
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(source, result);
            
            response = "/resources/files/route.xml";
            
        } catch (ParserConfigurationException pce)
        {
            pce.printStackTrace();
        } catch (TransformerException tfe)
        {
            tfe.printStackTrace();
        }
        
        return response;
    }
}
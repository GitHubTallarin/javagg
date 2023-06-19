/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.mavenproject1.Aire;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import com.mycompany.mavenproject1.Sismos;

public class JsonUrlReader2 {

    public JsonUrlReader2() {

    }

    public static void main(String[] args) throws DatabindException, MalformedURLException, IOException {
        JsonUrlReader2 j = new JsonUrlReader2();
        ArrayList<Sismos> aSismos = j.cargarURL();

        for (Sismos s : aSismos) {
            System.out.println(s);
        }

    }

    public ArrayList<Sismos> cargarURL() throws StreamReadException, DatabindException, MalformedURLException, IOException {
        String url = "https://api.gael.cloud/general/public/sismos";
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Sismos> aSismos = new ArrayList();
        JsonNode elementos = mapper.readTree(new URL(url));
        for (JsonNode element : elementos) {
            Sismos s = new Sismos();
            JsonNode fecha = element.get("Fecha");
            JsonNode profund = element.get("Profundidad");
            JsonNode magnitud = element.get("Magnitud");
            JsonNode localidad = element.get("RefGeografica");
            JsonNode fecha_act = element.get("FechaUpdate");
            s.setFecha(fecha.asText());
            s.setProfundidad(profund.asText());
            s.setMagnitud(magnitud.asText());
            s.setrefgeografica(localidad.asText());
            s.setfechaupdate(fecha_act.asText());
            aSismos.add(s);
        }

        return aSismos;
    }

}//JsonUrlReader

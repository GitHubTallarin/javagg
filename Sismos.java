/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.apache.beam.examples;;  

/**
 *
 * @author leo
 */
public class Sismos {
    
    private String fecha;
    private String profundidad;
    private String magnitud;
    private String refgeografica;
    private String fechaupdate;
   
    
 
    public Sismos() {
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
    public String getProfundidad() {
        return profundidad;
    }

    public void setProfundidad(String profundidad) {
        this.profundidad = profundidad;
    }

    public String getMagnitud() {
        return magnitud;
    }

    public void setMagnitud(String magnitud) {
        this.magnitud = magnitud;
    }

    public String getrefgeografica() {
        return refgeografica;
    }

    public void setrefgeografica(String refgeografica) {
        this.refgeografica = refgeografica;
    }

    public String getfechaupdate() {
        return fechaupdate;
    }

    public void setfechaupdate(String fechaupdate) {
        this.fechaupdate = fechaupdate;
    }

   

   

    @Override
    public String toString() {
        return "Sismos{" + "fecha=" + fecha + ", profundidad=" + profundidad + ", magnitud=" + magnitud + ", refgeografica=" + refgeografica + ", fechaupdate=" + fechaupdate + '}';
    }
    
    public String toCSV()
    {
        return "" + fecha + "," + profundidad + "," + magnitud + "," + refgeografica + "," + fechaupdate + "," + "";
    }

}//Aire

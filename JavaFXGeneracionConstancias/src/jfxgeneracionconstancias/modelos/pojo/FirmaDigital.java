/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jfxgeneracionconstancias.modelos.pojo;

/**
 *
 * @author tristan
 */
public class FirmaDigital {
    
    private int idFirmaDigital;
    private String contenidoFirmaDigital;
    private String fechaOrigen;

    public FirmaDigital() {
    }

    
    public FirmaDigital(int idFirmaDigital, String contenidoFirmaDigital, String fechaExpiración) {
        this.idFirmaDigital = idFirmaDigital;
        this.contenidoFirmaDigital = contenidoFirmaDigital;
        this.fechaOrigen = fechaExpiración;
    }

    public int getIdFirmaDigital() {
        return idFirmaDigital;
    }

    public void setIdFirmaDigital(int idFirmaDigital) {
        this.idFirmaDigital = idFirmaDigital;
    }

    public String getContenidoFirmaDigital() {
        return contenidoFirmaDigital;
    }

    public void setContenidoFirmaDigital(String contenidoFirmaDigital) {
        this.contenidoFirmaDigital = contenidoFirmaDigital;
    }

    public String getFechaOrigen() {
        return fechaOrigen;
    }

    public void setFechaOrigen(String fechaExpiración) {
        this.fechaOrigen = fechaExpiración;
    }
    
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jfxgeneracionconstancias.modelos.pojo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author tristan
 */
public class FirmaDigital {
    
    private int idFirmaDigital;
    private String contenidoFirmaDigital;
    private String fechaOrigen;
    private String fechaExpiracion;

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

    public void setFechaOrigen(String fechaOrigen) {
        this.fechaOrigen = fechaOrigen;
    }
    
    public void setFechaExpiracion(String fechaOrigen) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        LocalDateTime dateTime = LocalDateTime.parse(fechaOrigen, formatter);
        LocalDateTime fechaResultado = dateTime.plusMonths(3);        
        LocalDateTime fechaExp = fechaResultado;
        fechaExp.format(formatter);
        this.fechaExpiracion  = fechaExp.toString();
    }

    public String getFechaExpiracion() {
        return fechaExpiracion;
    }    
}

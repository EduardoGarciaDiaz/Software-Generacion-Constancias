/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jfxgeneracionconstancias.modelos.pojo;

/**
 *
 * @author Daniel García Arcos
 */
public class PeriodoEscolar {
    int idPeriodo;
    private String nombre;
    private String fechaInicio;
    private String fechaFin;

    public PeriodoEscolar() {
        
    }
    
    public PeriodoEscolar(String nombre, String fechaInicio, String fechaFin, int idPeriodo) {
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.idPeriodo = idPeriodo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }
    
    public void setIdPeriodo(int idPeriodo) {
        this.idPeriodo = idPeriodo;
    }
    
    public int getIdPeriodo() {
        return this.idPeriodo;
    }
    
    
    @Override
    public String toString() {
        return nombre;
    }
}

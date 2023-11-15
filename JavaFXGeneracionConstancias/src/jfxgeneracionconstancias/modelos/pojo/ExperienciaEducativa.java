/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jfxgeneracionconstancias.modelos.pojo;

/**
 *
 * @author egd17
 */
public class ExperienciaEducativa {
    private int idExperienciaEducativa;
    private String nombre;
    private int bloque;
    private int seccion;
    private int creditos;
    private int idProgramaEducativo;
    private int idPeriodoEscolar;
    private String nombreProgramaEducativo;

    public ExperienciaEducativa() {
    }

    public ExperienciaEducativa(int idExperienciaEducativa, String nombre, int bloque, int seccion, int creditos, int idProgramaEducativo, int idPeriodoEscolar) {
        this.idExperienciaEducativa = idExperienciaEducativa;
        this.nombre = nombre;
        this.bloque = bloque;
        this.seccion = seccion;
        this.creditos = creditos;
        this.idProgramaEducativo = idProgramaEducativo;
        this.idPeriodoEscolar = idPeriodoEscolar;
    }

    public ExperienciaEducativa(String nombre, int bloque, int seccion, int creditos, int idProgramaEducativo, int idPeriodoEscolar) {
        this.nombre = nombre;
        this.bloque = bloque;
        this.seccion = seccion;
        this.creditos = creditos;
        this.idProgramaEducativo = idProgramaEducativo;
        this.idPeriodoEscolar = idPeriodoEscolar;
    }
    
    

    public ExperienciaEducativa(String nombre, int bloque, int seccion, int creditos) {
        this.nombre = nombre;
        this.bloque = bloque;
        this.seccion = seccion;
        this.creditos = creditos;
    }

    public int getIdExperienciaEducativa() {
        return idExperienciaEducativa;
    }

    public void setIdExperienciaEducativa(int idExperienciaEducativa) {
        this.idExperienciaEducativa = idExperienciaEducativa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getBloque() {
        return bloque;
    }

    public void setBloque(int bloque) {
        this.bloque = bloque;
    }

    public int getSeccion() {
        return seccion;
    }

    public void setSeccion(int seccion) {
        this.seccion = seccion;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    public int getIdProgramaEducativo() {
        return idProgramaEducativo;
    }

    public void setIdProgramaEducativo(int idProgramaEducativo) {
        this.idProgramaEducativo = idProgramaEducativo;
    }

    public int getIdPeriodoEscolar() {
        return idPeriodoEscolar;
    }

    public void setIdPeriodoEscolar(int idPeriodoEscolar) {
        this.idPeriodoEscolar = idPeriodoEscolar;
    }

    public String getNombreProgramaEducativo() {
        return nombreProgramaEducativo;
    }

    public void setNombreProgramaEducativo(String nombreProgramaEducativo) {
        this.nombreProgramaEducativo = nombreProgramaEducativo;
    }
    
    
    
    @Override
    public String toString() {
        return nombre + " - " + creditos + " creditos";
    }

}

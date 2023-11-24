/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jfxgeneracionconstancias.modelos.pojo;

/**
 *
 * @author egd17
 */
public class ProyectoCampo {
    
    private int idProyecto;
    private String nombreProyecto;
    private String duracion;
    private String impactoObtenido;
    private String lugar;
    private String alumnos;
    private int idProfesor;

    public ProyectoCampo() {
    }

    public ProyectoCampo(int idProyecto, String nombreProyecto, String duracion, String impactoObtenido, String lugar, String alumnos, int idProfesor) {
        this.idProyecto = idProyecto;
        this.nombreProyecto = nombreProyecto;
        this.duracion = duracion;
        this.impactoObtenido = impactoObtenido;
        this.lugar = lugar;
        this.alumnos = alumnos;
        this.idProfesor = idProfesor;
    }

    public ProyectoCampo(String nombreProyecto, String duracion, String impactoObtenido, String lugar, String alumnos, int idProfesor) {
        this.nombreProyecto = nombreProyecto;
        this.duracion = duracion;
        this.impactoObtenido = impactoObtenido;
        this.lugar = lugar;
        this.alumnos = alumnos;
        this.idProfesor = idProfesor;
    }

    public int getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
    }

    public String getNombreProyecto() {
        return nombreProyecto;
    }

    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getImpactoObtenido() {
        return impactoObtenido;
    }

    public void setImpactoObtenido(String impactoObtenido) {
        this.impactoObtenido = impactoObtenido;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(String alumnos) {
        this.alumnos = alumnos;
    }

    public int getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(int idProfesor) {
        this.idProfesor = idProfesor;
    }

    @Override
    public String toString() {
        return nombreProyecto;
    }
    
    
}

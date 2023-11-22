/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jfxgeneracionconstancias.modelos.pojo;

/**
 *
 * @author egd17
 */
public class TrabajoRecepcional {
    private int idTrabajoRecepcional;
    private String tituloTrabajoRecepcional;
    private String resultadoObtenido;
    private String fechaPresentacion;
    private String alumnos;
    private int idModalidad;
    private int idProgramaEducativo;
    private String nombreModalidad;
    private String nombreProgramaEducativo;
    private int idRolJurado;
    private String nombreRolJurado;

    public TrabajoRecepcional() {
    }

    public TrabajoRecepcional(int idTrabajoRecepcional, String tituloTrabajoRecepcional, String resultadoObtenido, String fechaPresentacion, String alumnos, int idModalidad, int idProgramaEducativo, String nombreModalidad, String nombreProgramaEducativo, int idRolJurado, String nombreRolJurado) {
        this.idTrabajoRecepcional = idTrabajoRecepcional;
        this.tituloTrabajoRecepcional = tituloTrabajoRecepcional;
        this.resultadoObtenido = resultadoObtenido;
        this.fechaPresentacion = fechaPresentacion;
        this.alumnos = alumnos;
        this.idModalidad = idModalidad;
        this.idProgramaEducativo = idProgramaEducativo;
        this.nombreModalidad = nombreModalidad;
        this.nombreProgramaEducativo = nombreProgramaEducativo;
        this.idRolJurado = idRolJurado;
        this.nombreRolJurado = nombreRolJurado;
    }

    public int getIdTrabajoRecepcional() {
        return idTrabajoRecepcional;
    }

    public void setIdTrabajoRecepcional(int idTrabajoRecepcional) {
        this.idTrabajoRecepcional = idTrabajoRecepcional;
    }

    public String getTituloTrabajoRecepcional() {
        return tituloTrabajoRecepcional;
    }

    public void setTituloTrabajoRecepcional(String tituloTrabajoRecepcional) {
        this.tituloTrabajoRecepcional = tituloTrabajoRecepcional;
    }

    public String getResultadoObtenido() {
        return resultadoObtenido;
    }

    public void setResultadoObtenido(String resultadoObtenido) {
        this.resultadoObtenido = resultadoObtenido;
    }

    public String getFechaPresentacion() {
        return fechaPresentacion;
    }

    public void setFechaPresentacion(String fechaPresentacion) {
        this.fechaPresentacion = fechaPresentacion;
    }

    public String getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(String alumnos) {
        this.alumnos = alumnos;
    }

    public int getIdModalidad() {
        return idModalidad;
    }

    public void setIdModalidad(int idModalidad) {
        this.idModalidad = idModalidad;
    }

    public int getIdProgramaEducativo() {
        return idProgramaEducativo;
    }

    public void setIdProgramaEducativo(int idProgramaEducativo) {
        this.idProgramaEducativo = idProgramaEducativo;
    }

    public String getNombreModalidad() {
        return nombreModalidad;
    }

    public void setNombreModalidad(String nombreModalidad) {
        this.nombreModalidad = nombreModalidad;
    }

    public String getNombreProgramaEducativo() {
        return nombreProgramaEducativo;
    }

    public void setNombreProgramaEducativo(String nombreProgramaEducativo) {
        this.nombreProgramaEducativo = nombreProgramaEducativo;
    }

    public int getIdRolJurado() {
        return idRolJurado;
    }

    public void setIdRolJurado(int idRolJurado) {
        this.idRolJurado = idRolJurado;
    }

    public String getNombreRolJurado() {
        return nombreRolJurado;
    }

    public void setNombreRolJurado(String nombreRolJurado) {
        this.nombreRolJurado = nombreRolJurado;
    }

    @Override
    public String toString() {
        return tituloTrabajoRecepcional + " presentado: " + fechaPresentacion;
    }
    
    
}

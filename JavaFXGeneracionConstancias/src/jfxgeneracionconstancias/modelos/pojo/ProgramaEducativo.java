/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jfxgeneracionconstancias.modelos.pojo;

/**
 *
 * @author egd17
 */
public class ProgramaEducativo {
    
    private int idProgramaEducativo;
    private String nombreProgramaEducativo;

    public ProgramaEducativo() {
    }
    
    

    public ProgramaEducativo(int idProgramaEducativo, String nombreProgramaEducativo) {
        this.idProgramaEducativo = idProgramaEducativo;
        this.nombreProgramaEducativo = nombreProgramaEducativo;
    }

    public ProgramaEducativo(String nombreProgramaEducativo) {
        this.nombreProgramaEducativo = nombreProgramaEducativo;
    }

    public int getIdProgramaEducativo() {
        return idProgramaEducativo;
    }

    public void setIdProgramaEducativo(int idProgramaEducativo) {
        this.idProgramaEducativo = idProgramaEducativo;
    }

    public String getNombreProgramaEducativo() {
        return nombreProgramaEducativo;
    }

    public void setNombreProgramaEducativo(String nombreProgramaEducativo) {
        this.nombreProgramaEducativo = nombreProgramaEducativo;
    }
    
    @Override
    public String toString() {
        return nombreProgramaEducativo;
    }

}

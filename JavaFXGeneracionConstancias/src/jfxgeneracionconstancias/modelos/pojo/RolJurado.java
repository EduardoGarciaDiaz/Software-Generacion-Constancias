/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jfxgeneracionconstancias.modelos.pojo;

/**
 *
 * @author egd17
 */
public class RolJurado {
    private int idRolJurado;
    private String nombreRolJurado;

    public RolJurado() {
    }

    public RolJurado(int idRolJurado, String nombreRolJurado) {
        this.idRolJurado = idRolJurado;
        this.nombreRolJurado = nombreRolJurado;
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
        return nombreRolJurado;
    }
    
}

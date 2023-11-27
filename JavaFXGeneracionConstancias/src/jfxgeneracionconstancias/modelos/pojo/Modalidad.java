/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jfxgeneracionconstancias.modelos.pojo;

import java.util.Objects;

/**
 *
 * @author egd17
 */
public class Modalidad {
    
    private int idModalidad;
    private String nombreModalidad;

    public Modalidad() {
    }

    public Modalidad(int idModalidad, String nombreModalidad) {
        this.idModalidad = idModalidad;
        this.nombreModalidad = nombreModalidad;
    }

    public int getIdModalidad() {
        return idModalidad;
    }

    public void setIdModalidad(int idModalidad) {
        this.idModalidad = idModalidad;
    }

    public String getNombreModalidad() {
        return nombreModalidad;
    }

    public void setNombreModalidad(String nombreModalidad) {
        this.nombreModalidad = nombreModalidad;
    }

    @Override
    public String toString() {
        return nombreModalidad;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Modalidad other = (Modalidad) obj;
        if (this.idModalidad != other.idModalidad) {
            return false;
        }
        return Objects.equals(this.nombreModalidad, other.nombreModalidad);
    }
    
    
}

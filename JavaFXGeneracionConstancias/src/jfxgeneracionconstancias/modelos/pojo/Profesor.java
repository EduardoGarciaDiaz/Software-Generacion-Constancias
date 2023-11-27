package jfxgeneracionconstancias.modelos.pojo;

import java.util.Objects;

public class Profesor extends Usuario {
    
    private int idUsuario;
    private String fechaNacimiento;
    private String correoAlterno;
    private String gradoEstudios;

    public Profesor(){
        
    }
    
    public Profesor(int idUsuario, String fechaNacimiento, String correoAlterno, String gradoEstudios) {
        this.idUsuario = idUsuario;
        this.fechaNacimiento = fechaNacimiento;
        this.correoAlterno = correoAlterno;
        this.gradoEstudios = gradoEstudios;
    }
    
    public Profesor(String fechaNacimiento, String correoAlterno, String gradoEstudios, long numeroPersonal, String nombre, String primerApellido, String segundoApellido, String correoInstitucional) {
        super(numeroPersonal, nombre, primerApellido, segundoApellido, correoInstitucional);
        this.fechaNacimiento = fechaNacimiento;
        this.correoAlterno = correoAlterno;
        this.gradoEstudios = gradoEstudios;
    }


    public Profesor(int idUsuario, String fechaNacimiento, String correoAlterno, String gradoEstudios, int numeroPersonal, String nombre, String primerApellido, String segundoApellido, boolean esAdministrador, String correoInstitucional, int tipoUsuario, String nombreTipoUsuario) {
        super(numeroPersonal, nombre, primerApellido, segundoApellido, esAdministrador, correoInstitucional, tipoUsuario, nombreTipoUsuario);
        this.idUsuario = idUsuario;
        this.fechaNacimiento = fechaNacimiento;
        this.correoAlterno = correoAlterno;
        this.gradoEstudios = gradoEstudios;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getCorreoAlterno() {
        return correoAlterno;
    }

    public void setCorreoAlterno(String correoAlterno) {
        this.correoAlterno = correoAlterno;
    }

    public String getGradoEstudios() {
        return gradoEstudios;
    }

    public void setGradoEstudios(String gradoEstudios) {
        this.gradoEstudios = gradoEstudios;
    }

    @Override
    public int hashCode() {
        int hash = 3;
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
        final Profesor other = (Profesor) obj;
        if (this.idUsuario != other.idUsuario) {
            return false;
        }
        if (!Objects.equals(this.fechaNacimiento, other.fechaNacimiento)) {
            return false;
        }
        if (!Objects.equals(this.correoAlterno, other.correoAlterno)) {
            return false;
        }
        return Objects.equals(this.gradoEstudios, other.gradoEstudios);
    }
    
    
    
}

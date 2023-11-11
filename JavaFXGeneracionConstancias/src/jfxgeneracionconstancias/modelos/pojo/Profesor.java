package jfxgeneracionconstancias.modelos.pojo;

public class Profesor extends Usuario {
    
    private int idUsuario;
    private String fechaNacimiento;
    private String correoAlterno;
    private String gradoEstudios;

    public Profesor(){}
    
    public Profesor(int idUsuario, String fechaNacimiento, String correoAlterno, String gradoEstudios) {
        this.idUsuario = idUsuario;
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
    
    
}

package jfxgeneracionconstancias.modelos.pojo;

/**
 *
 * @author Daniel García Arcos
 */
public class Usuario {
    
    private long numeroPersonal;
    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    private boolean esAdministrador;
    private String correoInstitucional;
    private int tipoUsuario;
    private String nombreTipoUsuario;
    private String contraseña;

    public Usuario() {
        
    }
    
    public Usuario(int numeroPersonal, String nombre, String primerApellido, String segundoApellido, boolean esAdministrador, String correoInstitucional, int tipoUsuario, String nombreTipoUsuario) {
        this.numeroPersonal = numeroPersonal;
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.esAdministrador = esAdministrador;
        this.correoInstitucional = correoInstitucional;
        this.tipoUsuario = tipoUsuario;
        this.nombreTipoUsuario = nombreTipoUsuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public long getNumeroPersonal() {
        return numeroPersonal;
    }

    public void setNumeroPersonal(long numeroPersonal) {
        this.numeroPersonal = numeroPersonal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public boolean isAdministrador() {
        return esAdministrador;
    }

    public void setEsAdministrador(boolean esAdministrador) {
        this.esAdministrador = esAdministrador;
    }

    public String getCorreoInstitucional() {
        return correoInstitucional;
    }

    public void setCorreoInstitucional(String correoInstitucional) {
        this.correoInstitucional = correoInstitucional;
    }

    public int getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(int tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getNombreTipoUsuario() {
        return nombreTipoUsuario;
    }

    public void setNombreTipoUsuario(String nombreTipoUsuario) {
        this.nombreTipoUsuario = nombreTipoUsuario;
    }

}

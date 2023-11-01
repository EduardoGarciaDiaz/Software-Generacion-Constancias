package jfxgeneracionconstancias.utils;

public enum Codigos {
    
    EXITO("200"),
    ERROR_CONSULTA("400"),
    ERROR_CONEXION_BD("500");

    private final String codigo;

    private Codigos(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }
    
}
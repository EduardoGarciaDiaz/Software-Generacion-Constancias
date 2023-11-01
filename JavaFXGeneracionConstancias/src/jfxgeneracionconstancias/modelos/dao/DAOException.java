package jfxgeneracionconstancias.modelos.dao;

import jfxgeneracionconstancias.utils.Codigos;

public class DAOException extends Exception {
    
    private final Codigos codigo;
    
    public DAOException(String mensaje, Codigos codigo) {
        super(mensaje);
        this.codigo = codigo;
    }
    
    public Codigos getCodigo() {
        return codigo;
    }
    
}

package jfxgeneracionconstancias.modelos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import jfxgeneracionconstancias.modelos.dao.DAOException;
import jfxgeneracionconstancias.utils.Codigos;

public class ConexionBD {
    
    private static Connection conexion;
    
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String NOMBRE_BASE = "sastr";
    private static final String HOSTNAME = "localhost";
    private static final String PUERTO = "3306";
    private static final String USUARIO = "userGeneracionConstancias";
    private static final String PASSWORD = "Gen_Constancias23";
    
    private static final String URL_CONEXION = "jdbc:mysql://" + HOSTNAME + ":" + PUERTO + "/" + NOMBRE_BASE 
            + "?allowPublicKeyRetrieval=true&useSSL=false";
    
    private static Connection abrirConexionBD() throws SQLException {
        Connection newConnection = DriverManager.getConnection(URL_CONEXION, USUARIO, PASSWORD);
        return newConnection;
    }
      
    public static Connection obtenerConexionBD() throws DAOException {
        try {
            if (conexion == null || conexion.isClosed()) {
                conexion = abrirConexionBD();
            }
        } catch (SQLException ex) {
            throw new DAOException("No se pudo conectar con la base de datos. Intente de nuevo o hágalo más tarde", 
                    Codigos.ERROR_CONEXION_BD);
        }
        return conexion;
    }
    
    public static boolean cerrarConexionBD() throws DAOException {
        boolean conexionCerrada = false;
        try {
            if (conexion != null) {
                conexion.close();
            }
            conexionCerrada = true;
        } catch (SQLException ex) {
            throw new DAOException("Ha ocurrido un error.", Codigos.ERROR_CONEXION_BD);
        }
        return conexionCerrada;
    }
    
}
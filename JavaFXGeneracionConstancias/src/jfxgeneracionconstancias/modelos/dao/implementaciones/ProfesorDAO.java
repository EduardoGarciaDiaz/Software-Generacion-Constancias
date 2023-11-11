package jfxgeneracionconstancias.modelos.dao.implementaciones;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import jfxgeneracionconstancias.modelos.ConexionBD;
import jfxgeneracionconstancias.modelos.dao.DAOException;
import jfxgeneracionconstancias.modelos.dao.contratos.IProfesorDAO;
import jfxgeneracionconstancias.modelos.pojo.Profesor;
import jfxgeneracionconstancias.utils.Codigos;

/**
 *
 * @author Daniel García Arcos
 */
public class ProfesorDAO implements IProfesorDAO {
    
    private static String REGISTRAR_PROFESOR = "insert into profesores (fechaNacimiento, "
            + "correoAlterno, gradoEstudios, numeroPersonal) value (?, ?, ?, ?);";

    @Override
    public int registrarProfesor(Profesor profesor) throws DAOException {
        int respuesta = -1;
        try {
            PreparedStatement sentencia = ConexionBD.obtenerConexionBD().prepareStatement(REGISTRAR_PROFESOR, 
                    Statement.RETURN_GENERATED_KEYS);
            sentencia.setString(1, profesor.getFechaNacimiento());
            sentencia.setString(2, profesor.getCorreoAlterno());
            sentencia.setString(3, profesor.getGradoEstudios());
            sentencia.setLong(4, profesor.getNumeroPersonal());
            sentencia.executeUpdate();
            ResultSet rs = sentencia.getGeneratedKeys();
            while (rs.next()) {
                respuesta = rs.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DAOException("", Codigos.ERROR_CONSULTA);
        } finally {
            ConexionBD.cerrarConexionBD();
        }
        return respuesta;
    }

    @Override
    public int editarProfesor(Profesor profesor, int idProfesor) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Profesor obtenerProfesor(int idProfesor) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<Profesor> obtenerProfesores() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}

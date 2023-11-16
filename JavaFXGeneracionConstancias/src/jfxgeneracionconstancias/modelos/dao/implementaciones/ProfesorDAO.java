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
    private static String OBTENER_PROFESOR_POR_NUMERO_PERSONAL = "SELECT p.idProfesor, p.fechaNacimiento, p.correoAlterno, "
            + " p.gradoEstudios, p.numeroPersonal, "
            + "u.nombre, u.primerApellido, u.segundoApellido, u.correoInstitucional "
            + "FROM Profesores p "
            + "INNER JOIN usuarios u "
            + "ON p.numeroPersonal = u.numeroPersonal "
            + "WHERE p.numeroPersonal = ?";
    private static String EDITAR_PROFESOR = "UPDATE `constancias`.`profesores` "
            + "SET `fechaNacimiento` = ?, `correoAlterno` = ?, `gradoEstudios` = ? "
            + "WHERE `numeroPersonal` = ?";
    private static String RECUPERAR_TODOS_LOS_PROFESORES = "SELECT * FROM constancias.usuarios "         
            + "Join constancias.profesores ON usuarios.numeroPersonal = profesores.numeroPersonal;";

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
    public long editarProfesor(Profesor profesor) throws DAOException {
        long respuesta = -1;
        try {
            PreparedStatement sentencia = ConexionBD.obtenerConexionBD().prepareStatement(EDITAR_PROFESOR);
            sentencia.setString(1, profesor.getFechaNacimiento());
            sentencia.setString(2, profesor.getCorreoAlterno());
            sentencia.setString(3, profesor.getGradoEstudios());
            sentencia.setLong(4, profesor.getNumeroPersonal());
            int filasAfectadas = sentencia.executeUpdate();
            respuesta = (filasAfectadas == 1) ? profesor.getNumeroPersonal(): -1;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DAOException("", Codigos.ERROR_CONSULTA);
        } finally {
            ConexionBD.cerrarConexionBD();
        }
        return respuesta;    
    }

    @Override
    public Profesor obtenerProfesor(long numeroPersonal) throws DAOException {
        Profesor profesor = null;
        try {
            PreparedStatement sentencia = ConexionBD.obtenerConexionBD().prepareStatement(OBTENER_PROFESOR_POR_NUMERO_PERSONAL);
            sentencia.setLong(1, numeroPersonal);
            ResultSet resultado = sentencia.executeQuery();
            if (resultado.next()) {
                profesor = new Profesor();
                profesor.setIdUsuario(resultado.getInt("idProfesor"));
                profesor.setFechaNacimiento(resultado.getString("fechaNacimiento"));
                profesor.setCorreoAlterno(resultado.getString("correoAlterno"));
                profesor.setGradoEstudios(resultado.getString("gradoEstudios"));
                profesor.setNumeroPersonal(resultado.getLong("numeroPersonal"));
                profesor.setNombre(resultado.getString("nombre"));
                profesor.setPrimerApellido(resultado.getString("primerApellido"));
                profesor.setSegundoApellido(resultado.getString("segundoApellido"));
                profesor.setCorreoInstitucional(resultado.getString("correoInstitucional"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DAOException("Error al obtener el profesor por numero de Personal", Codigos.ERROR_CONSULTA);
        } finally {
            ConexionBD.cerrarConexionBD();
        }
        return profesor;
    }

    @Override
    public ArrayList<Profesor> obtenerProfesores() throws DAOException {
        ArrayList<Profesor> profesores = new ArrayList<>();
        try {
            PreparedStatement sentencia = ConexionBD.obtenerConexionBD().prepareStatement(RECUPERAR_TODOS_LOS_PROFESORES);
            ResultSet resultado = sentencia.executeQuery();
            if (resultado.next()) {
                Profesor profesor = new Profesor();
                profesor.setIdUsuario(resultado.getInt("idProfesor"));
                profesor.setFechaNacimiento(resultado.getString("fechaNacimiento"));
                profesor.setCorreoAlterno(resultado.getString("correoAlterno"));
                profesor.setGradoEstudios(resultado.getString("gradoEstudios"));
                profesor.setNumeroPersonal(resultado.getLong("numeroPersonal"));
                profesor.setNombre(resultado.getString("nombre"));
                profesor.setPrimerApellido(resultado.getString("primerApellido"));
                profesor.setSegundoApellido(resultado.getString("segundoApellido"));
                profesor.setCorreoInstitucional(resultado.getString("correoInstitucional"));
                profesores.add(profesor);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DAOException("Error al obtener el profesor por numero de Personal", Codigos.ERROR_CONSULTA);
        } finally {
            ConexionBD.cerrarConexionBD();
        }
        return profesores;
    }
    
}

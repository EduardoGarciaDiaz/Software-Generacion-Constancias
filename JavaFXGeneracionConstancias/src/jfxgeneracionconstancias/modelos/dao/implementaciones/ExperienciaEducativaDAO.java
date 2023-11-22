/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jfxgeneracionconstancias.modelos.dao.implementaciones;

import com.sun.javafx.collections.ElementObservableListDecorator;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jfxgeneracionconstancias.modelos.ConexionBD;
import jfxgeneracionconstancias.modelos.dao.DAOException;
import jfxgeneracionconstancias.modelos.pojo.ExperienciaEducativa;
import jfxgeneracionconstancias.modelos.pojo.Profesor;
import jfxgeneracionconstancias.modelos.pojo.Usuario;
import jfxgeneracionconstancias.utils.Codigos;

/**
 *
 * @author egd17
 */
public class ExperienciaEducativaDAO {
    
    private static String REGISTRAR_EXPERIENCIA_EDUCATIVA = " INSERT INTO `constancias`.`experienciaseducativas` "
            + "(`nombre`, `bloque`, `seccion`, `creditos`, `idProgramaEducativo`, `idPeriodoEscolar`) VALUES (?, ?, ?, ?, ?, ?);";
    private static String REGISTRAR_EXPERIENCIA_EDUCATIVA_A_PROFESOR = "INSERT INTO `constancias`.`profesoresexperienciaseducativas` "
            + "(`idProfesor`, `idExperienciaEducativa`) VALUES (?, ?);";
    private static String OBTENER_EXPERIENCIAS_EDUCATIVAS_DEL_PROFESOR = "SELECT ee.idExperienciaEducativa, ee.nombre, ee.bloque, "
            + " ee.seccion, ee.creditos, ee.idProgramaEducativo, ee.idPeriodoEscolar, pgE.nombreProgramaEducativo "
            + "FROM ExperienciasEducativas ee "
            + "INNER JOIN profesoresExperienciasEducativas pee ON ee.idExperienciaEducativa = pee.idExperienciaEducativa "
            + "INNER JOIN programasEducativos pgE ON ee.idProgramaEducativo = pgE.idProgramaEducativo "
            + "WHERE pee.idProfesor = ?;";
    private static String OBTENER_EXPERIENCIAS_EDUCATIVAS_NO_ASIGNADAS_AL_PROFESOR = "SELECT ee.idExperienciaEducativa, "
            + " ee.nombre, ee.bloque, ee.seccion, ee.creditos, ee.idProgramaEducativo, ee.idPeriodoEscolar, "
            + "       pgE.nombreProgramaEducativo FROM constancias.experienciaseducativas ee "
            + "LEFT JOIN constancias.profesoresexperienciaseducativas pee "
            + "ON ee.idExperienciaEducativa = pee.idExperienciaEducativa AND pee.idProfesor = ? "
            + "INNER JOIN constancias.programaseducativos pgE ON ee.idProgramaEducativo = pgE.idProgramaEducativo "
            + "WHERE pee.idExperienciaEducativa IS NULL OR pee.idExperienciaEducativa IS NOT NULL AND pee.idProfesor IS NULL;";
    private static String OBTENER_EXPERIENCIA_EDUCATIVA_EXISTENTE = "SELECT * FROM constancias.experienciaseducativas "
            + "WHERE nombre = ? AND bloque = ? "
            + " AND seccion =  ? AND creditos = ? AND idProgramaEducativo = ? AND idPeriodoEscolar = ?;";
    private static String DESASIGNAR_EXPERIENCIA_EDUCATIVA_AL_PROFESOR = "DELETE FROM profesoresexperienciaseducativas "
            + "WHERE idProfesor = ? AND idExperienciaEducativa = ?";
    private final String OBTENER_EXPERIENCIAS_EDUCATIVAS_PERIODO_PROGRAMA_EDUCATIVO = "SELECT "
            + "experienciaseducativas.idExperienciaEducativa, " 
            + "experienciaseducativas.nombre, " 
            + "experienciaseducativas.bloque, "
            + "experienciaseducativas.seccion, "
            + "experienciaseducativas.creditos, "
            + "experienciaseducativas.idProgramaEducativo, "
            + "experienciaseducativas.idPeriodoEscolar, " 
            + "programaseducativos.nombreProgramaEducativo "
            + "FROM experienciaseducativas "
            + "left join periodosescolares  "
            + "on experienciaseducativas.idPeriodoEscolar = periodosescolares.idPeriodoEscolar "
            + "left join programaseducativos "
            + "on experienciaseducativas.idProgramaEducativo = programaseducativos.idProgramaEducativo "
            + "left join profesoresexperienciaseducativas "
            + "on experienciaseducativas.idExperienciaEducativa = profesoresexperienciaseducativas.idExperienciaEducativa "
            + "left join profesores "
            + "on profesoresexperienciaseducativas.idProfesor = profesores.idProfesor "
            + "where periodosescolares.idPeriodoEscolar = ? and programaseducativos.idProgramaEducativo = ? "
            + "and profesores.numeroPersonal = ?;";
    
    public int registrarExperienciaEducativa(ExperienciaEducativa experienciaEducativa)throws DAOException{
        int respuesta = -1;
        try {
            PreparedStatement sentencia = ConexionBD.obtenerConexionBD().prepareStatement(REGISTRAR_EXPERIENCIA_EDUCATIVA,
                    Statement.RETURN_GENERATED_KEYS);
            sentencia.setString(1, experienciaEducativa.getNombre());
            sentencia.setInt(2, experienciaEducativa.getBloque());
            sentencia.setInt(3, experienciaEducativa.getSeccion());
            sentencia.setInt(4, experienciaEducativa.getCreditos());
            sentencia.setInt(5, experienciaEducativa.getIdProgramaEducativo());
            sentencia.setInt(6, experienciaEducativa.getIdPeriodoEscolar());
            sentencia.executeUpdate();
            ResultSet resultado = sentencia.getGeneratedKeys();
            while (resultado.next()) {
                respuesta = resultado.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DAOException("", Codigos.ERROR_CONSULTA);
        } finally {
            ConexionBD.cerrarConexionBD();
        }
        return respuesta;
    }

    public int asignarExperienciaEducativaAProfesor(int idExperienciaEducativa, int idProfesor)throws DAOException{
        int respuesta = -1;
        try {
            PreparedStatement sentencia = ConexionBD.obtenerConexionBD().prepareStatement(REGISTRAR_EXPERIENCIA_EDUCATIVA_A_PROFESOR,
                    Statement.RETURN_GENERATED_KEYS);
            sentencia.setInt(1, idProfesor);
            sentencia.setInt(2, idExperienciaEducativa);
            sentencia.executeUpdate();
            ResultSet resultado = sentencia.getGeneratedKeys();
            while (resultado.next()) {
                respuesta = resultado.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DAOException("", Codigos.ERROR_CONSULTA);
        } finally {
            ConexionBD.cerrarConexionBD();
        }
        return respuesta;
    }
    
    public ObservableList<ExperienciaEducativa> obtenerExperienciasEducativasPorIdProfesor(int idProfesor) throws DAOException{
        ObservableList<ExperienciaEducativa> experienciasEducativas = FXCollections.observableArrayList();
        try{
            PreparedStatement sentencia = ConexionBD.obtenerConexionBD().prepareStatement(OBTENER_EXPERIENCIAS_EDUCATIVAS_DEL_PROFESOR);
            sentencia.setInt(1, idProfesor);
            ResultSet resultado = sentencia.executeQuery();
            while (resultado.next()) {
                ExperienciaEducativa experienciaEducativa = new ExperienciaEducativa();
                experienciaEducativa.setIdExperienciaEducativa(resultado.getInt("idExperienciaEducativa"));
                experienciaEducativa.setNombre(resultado.getString("nombre"));
                experienciaEducativa.setBloque(resultado.getInt("bloque"));
                experienciaEducativa.setSeccion(resultado.getInt("seccion"));
                experienciaEducativa.setCreditos(resultado.getInt("creditos"));
                experienciaEducativa.setIdProgramaEducativo(resultado.getInt("idProgramaEducativo"));
                experienciaEducativa.setIdPeriodoEscolar(resultado.getInt("idPeriodoEscolar"));
                experienciaEducativa.setNombreProgramaEducativo(resultado.getString("nombreProgramaEducativo"));
                experienciasEducativas.add(experienciaEducativa);
            }
        } catch (SQLException ex) {
            throw new DAOException("", Codigos.ERROR_CONSULTA);
        } finally {
            ConexionBD.cerrarConexionBD();
        }
        return experienciasEducativas;
    }
    
    public ObservableList<ExperienciaEducativa> obtenerExperienciasEducativasPorPeriodoYPrograma(int idPeriodo, int idProgramaEducativo, long numeroPersonal) throws DAOException{
        ObservableList<ExperienciaEducativa> experienciasEducativas = FXCollections.observableArrayList();
        try{
            PreparedStatement sentencia = ConexionBD.obtenerConexionBD().prepareStatement(OBTENER_EXPERIENCIAS_EDUCATIVAS_PERIODO_PROGRAMA_EDUCATIVO);
            sentencia.setInt(1, idPeriodo);
            sentencia.setInt(2, idProgramaEducativo);
            sentencia.setLong(3, numeroPersonal);
            ResultSet resultado = sentencia.executeQuery();
            while (resultado.next()) {
                ExperienciaEducativa experienciaEducativa = new ExperienciaEducativa();
                experienciaEducativa.setIdExperienciaEducativa(resultado.getInt("idExperienciaEducativa"));
                experienciaEducativa.setNombre(resultado.getString("nombre"));
                experienciaEducativa.setBloque(resultado.getInt("bloque"));
                experienciaEducativa.setSeccion(resultado.getInt("seccion"));
                experienciaEducativa.setCreditos(resultado.getInt("creditos"));
                experienciaEducativa.setIdProgramaEducativo(resultado.getInt("idProgramaEducativo"));
                experienciaEducativa.setIdPeriodoEscolar(resultado.getInt("idPeriodoEscolar"));
                experienciaEducativa.setNombreProgramaEducativo(resultado.getString("nombreProgramaEducativo"));
                experienciasEducativas.add(experienciaEducativa);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DAOException("", Codigos.ERROR_CONSULTA);
        } finally {
            ConexionBD.cerrarConexionBD();
        }
        return experienciasEducativas;
    }
    
    public boolean validarExperienciaEducativaExistente(ExperienciaEducativa experienciaEducativa) throws DAOException {
        boolean existExperienciaEducativa = false;
        try {
            PreparedStatement sentencia = ConexionBD.obtenerConexionBD().prepareStatement(OBTENER_EXPERIENCIA_EDUCATIVA_EXISTENTE);
            sentencia.setString(1, experienciaEducativa.getNombre());
            sentencia.setInt(2, experienciaEducativa.getBloque());
            sentencia.setInt(3, experienciaEducativa.getSeccion());
            sentencia.setInt(4, experienciaEducativa.getCreditos());
            sentencia.setInt(5, experienciaEducativa.getIdProgramaEducativo());
            sentencia.setInt(6, experienciaEducativa.getIdPeriodoEscolar());
            ResultSet resultado = sentencia.executeQuery();
            if (resultado.next()) {
                existExperienciaEducativa = true;
            }
        } catch (SQLException ex) {
            throw new DAOException("", Codigos.ERROR_CONSULTA);
        } finally {
            ConexionBD.cerrarConexionBD();
        }
        return existExperienciaEducativa;
    }
    
    public ObservableList<ExperienciaEducativa> obtenerExperiencasEducativasNoAsignadas(int idProfesor) throws DAOException {
        ObservableList<ExperienciaEducativa> experienciasEducativas = FXCollections.observableArrayList();
        try{
            PreparedStatement sentencia = ConexionBD.obtenerConexionBD().prepareStatement(OBTENER_EXPERIENCIAS_EDUCATIVAS_NO_ASIGNADAS_AL_PROFESOR);
            sentencia.setInt(1, idProfesor);
            ResultSet resultado = sentencia.executeQuery();
            while (resultado.next()) {
                ExperienciaEducativa experienciaEducativa = new ExperienciaEducativa();
                experienciaEducativa.setIdExperienciaEducativa(resultado.getInt("idExperienciaEducativa"));
                experienciaEducativa.setNombre(resultado.getString("nombre"));
                experienciaEducativa.setBloque(resultado.getInt("bloque"));
                experienciaEducativa.setSeccion(resultado.getInt("seccion"));
                experienciaEducativa.setCreditos(resultado.getInt("creditos"));
                experienciaEducativa.setIdProgramaEducativo(resultado.getInt("idProgramaEducativo"));
                experienciaEducativa.setIdPeriodoEscolar(resultado.getInt("idPeriodoEscolar"));
                experienciaEducativa.setNombreProgramaEducativo(resultado.getString("nombreProgramaEducativo"));
                experienciasEducativas.add(experienciaEducativa);
            }
        } catch (SQLException ex) {
            throw new DAOException("", Codigos.ERROR_CONSULTA);
        } finally {
            ConexionBD.cerrarConexionBD();
        }
        return experienciasEducativas;
    }
    
    public int desasignarExperienciaEducativaProfesor(int idProfesor, int idExperienciaEducativa) throws DAOException{
        int filasAfectadas = -1;
        try {
            PreparedStatement sentencia = ConexionBD.obtenerConexionBD().prepareStatement(DESASIGNAR_EXPERIENCIA_EDUCATIVA_AL_PROFESOR);
            sentencia.setInt(1, idProfesor);
            sentencia.setInt(2, idExperienciaEducativa);
            filasAfectadas = sentencia.executeUpdate();
        } catch (SQLException ex) {
            throw new DAOException("", Codigos.ERROR_CONSULTA);
        } finally {
            ConexionBD.cerrarConexionBD();
        }
        return filasAfectadas;
    }

}

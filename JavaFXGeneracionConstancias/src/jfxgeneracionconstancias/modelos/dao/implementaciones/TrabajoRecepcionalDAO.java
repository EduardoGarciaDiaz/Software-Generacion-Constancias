/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jfxgeneracionconstancias.modelos.dao.implementaciones;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jfxgeneracionconstancias.modelos.ConexionBD;
import jfxgeneracionconstancias.modelos.dao.DAOException;
import jfxgeneracionconstancias.modelos.pojo.ExperienciaEducativa;
import jfxgeneracionconstancias.modelos.pojo.TrabajoRecepcional;
import jfxgeneracionconstancias.utils.Codigos;

/**
 *
 * @author egd17
 */
public class TrabajoRecepcionalDAO {
    
    private static String REGISTRAR_TRABAJO_RECEPCIONAL = "INSERT INTO `constancias`.`trabajosrecepcionales` "
            + "(`tituloTrabajo`, `resultadoObtenido`, `fechaPresentacion`, `alumnos`, `idModalidad`, `idProgramaEducativo`) "
            + "VALUES (?, ?, ?, ?, ?, ?);";
    private static String REGISTRAR_TRABAJO_RECEPCIONAL_A_PROFESOR = "INSERT INTO `constancias`.`profesorestrabajosrecepcionales` "
            + "(`idTrabajoRecepcional`, `idRolJurado`, `idProfesor`) "
            + "VALUES (?, ?, ?);";
    private static String OBTENER_TRABAJOS_RECEPCIONALES_DEL_PROFESOR = "SELECT tr.idTrabajosRecepcionales, tr.tituloTrabajo, tr.resultadoObtenido, " 
            + "    tr.fechaPresentacion, tr.alumnos, tr.idModalidad, tr.idProgramaEducativo, " 
            + "    pgE.nombreProgramaEducativo, m.nombreModalidad, rj.nombreRolJurado " 
            + "FROM TrabajosRecepcionales tr " 
            + "INNER JOIN profesoresTrabajosRecepcionales ptr ON tr.idTrabajosRecepcionales = ptr.idTrabajoRecepcional " 
            + "INNER JOIN programasEducativos pgE ON tr.idProgramaEducativo = pgE.idProgramaEducativo " 
            + "INNER JOIN modalidades m ON tr.idModalidad = m.idModalidad " 
            + "INNER JOIN rolesjurado rj ON ptr.idRolJurado = rj.idRolJurado " 
            + "WHERE ptr.idProfesor = ?;";
    private static String OBTENER_TRABAJOS_RECEPCIONALES_NO_ASIGNADOS_AL_PROFESOR = "SELECT tr.idTrabajosRecepcionales, tr.tituloTrabajo, tr.resultadoObtenido, " +
        "    tr.fechaPresentacion, tr.alumnos, tr.idModalidad, tr.idProgramaEducativo, \n" +
        "    pgE.nombreProgramaEducativo, m.nombreModalidad \n" +
        "FROM TrabajosRecepcionales tr \n" +
        "LEFT JOIN profesoresTrabajosRecepcionales ptr \n" +
        "ON tr.idTrabajosRecepcionales = ptr.idTrabajoRecepcional AND ptr.idProfesor = ?\n" +
        "INNER JOIN programasEducativos pgE ON tr.idProgramaEducativo = pgE.idProgramaEducativo \n" +
        "INNER JOIN modalidades m ON tr.idModalidad = m.idModalidad \n" +
        "WHERE ptr.idTrabajoRecepcional is NULL OR ptr.idTrabajoRecepcional IS NOT NULL AND ptr.idProfesor IS NULL;";
    private static String OBTENER_TRABAJO_RECEPCIONAL_EXISTENTE = "SELECT * FROM constancias.trabajosrecepcionales "
        + "WHERE tituloTrabajo = ? AND resultadoObtenido = ? "
        + "AND fechaPresentacion =  ? AND idModalidad = ? AND idProgramaEducativo = ?;"; //AND alumnos = ? 
    private static String DESASIGNAR_TRABAJO_RECEPCIONAL_AL_PROFESOR = "DELETE FROM profesorestrabajosrecepcionales "
            + "WHERE idProfesor = ? AND idTrabajoRecepcional = ? AND idRolJurado = ?";
    
    
    public int registrarTrabajoRecepcional(TrabajoRecepcional trabajoRecepcional)throws DAOException{
        int respuesta = -1;
        try {
            PreparedStatement sentencia = ConexionBD.obtenerConexionBD().prepareStatement(REGISTRAR_TRABAJO_RECEPCIONAL,
                    Statement.RETURN_GENERATED_KEYS);
            sentencia.setString(1, trabajoRecepcional.getTituloTrabajoRecepcional());
            sentencia.setString(2, trabajoRecepcional.getResultadoObtenido());
            sentencia.setString(3, trabajoRecepcional.getFechaPresentacion());
            sentencia.setString(4, trabajoRecepcional.getAlumnos());
            sentencia.setInt(5, trabajoRecepcional.getIdModalidad());
            sentencia.setInt(6, trabajoRecepcional.getIdProgramaEducativo());            
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
    
    public int asignarTrabajoRecepcionalAProfesor(int idTrabajoRecepcional, int idRolJurado, int idProfesor)throws DAOException{
        int respuesta = -1;
        try {
            PreparedStatement sentencia = ConexionBD.obtenerConexionBD().prepareStatement(REGISTRAR_TRABAJO_RECEPCIONAL_A_PROFESOR,
                    Statement.RETURN_GENERATED_KEYS);
            sentencia.setInt(1, idTrabajoRecepcional);
            sentencia.setInt(2, idRolJurado);
            sentencia.setInt(3, idProfesor);
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
    
     public ObservableList<TrabajoRecepcional> obtenerTrabajosRecepcionalesPorIdProfesor(int idProfesor) throws DAOException{
        ObservableList<TrabajoRecepcional> trabajosRecepcionales = FXCollections.observableArrayList();
        try{
            PreparedStatement sentencia = ConexionBD.obtenerConexionBD().prepareStatement(OBTENER_TRABAJOS_RECEPCIONALES_DEL_PROFESOR);
            sentencia.setInt(1, idProfesor);
            ResultSet resultado = sentencia.executeQuery();
            while (resultado.next()) {
                TrabajoRecepcional trabajoRecepcional = new TrabajoRecepcional();
                trabajoRecepcional.setIdTrabajoRecepcional(resultado.getInt("idTrabajosRecepcionales"));
                trabajoRecepcional.setTituloTrabajoRecepcional(resultado.getString("tituloTrabajo"));
                trabajoRecepcional.setResultadoObtenido(resultado.getString("resultadoObtenido"));
                trabajoRecepcional.setFechaPresentacion(resultado.getString("fechaPresentacion"));
                trabajoRecepcional.setAlumnos(resultado.getString("alumnos"));
                trabajoRecepcional.setIdModalidad(resultado.getInt("idModalidad"));
                trabajoRecepcional.setIdProgramaEducativo(resultado.getInt("idProgramaEducativo"));
                trabajoRecepcional.setNombreProgramaEducativo(resultado.getString("nombreProgramaEducativo"));
                trabajoRecepcional.setNombreModalidad(resultado.getString("nombreModalidad"));
                trabajoRecepcional.setNombreRolJurado(resultado.getString("nombreRolJurado"));
                trabajosRecepcionales.add(trabajoRecepcional);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DAOException("", Codigos.ERROR_CONSULTA);
        } finally {
            ConexionBD.cerrarConexionBD();
        }
        return trabajosRecepcionales;
    }
     
    public boolean validarTrabajoRecepcionalExistente(TrabajoRecepcional trabajoRecepcional) throws DAOException {
        boolean existeTrabajoRecepcional = false;
        try {
            PreparedStatement sentencia = ConexionBD.obtenerConexionBD().prepareStatement(OBTENER_TRABAJO_RECEPCIONAL_EXISTENTE);
            sentencia.setString(1, trabajoRecepcional.getTituloTrabajoRecepcional());
            sentencia.setString(2, trabajoRecepcional.getResultadoObtenido());
            sentencia.setString(3, trabajoRecepcional.getFechaPresentacion());
            sentencia.setInt(4, trabajoRecepcional.getIdModalidad());
            sentencia.setInt(5, trabajoRecepcional.getIdProgramaEducativo());
            //sentencia.setString(6, trabajoRecepcional.getAlumnos());
            ResultSet resultado = sentencia.executeQuery();
            if (resultado.next()) {
                existeTrabajoRecepcional = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DAOException("", Codigos.ERROR_CONSULTA);
        } finally {
            ConexionBD.cerrarConexionBD();
        }
        return existeTrabajoRecepcional;
    }
    
    public ObservableList<TrabajoRecepcional> obtenerTrabajosRecepcionalesNoAsignados(int idProfesor) throws DAOException {
        ObservableList<TrabajoRecepcional> trabajosRecepcionales = FXCollections.observableArrayList();
        try{
            PreparedStatement sentencia = ConexionBD.obtenerConexionBD().prepareStatement(OBTENER_TRABAJOS_RECEPCIONALES_NO_ASIGNADOS_AL_PROFESOR);
            sentencia.setInt(1, idProfesor);
            ResultSet resultado = sentencia.executeQuery();
            while (resultado.next()) {
                TrabajoRecepcional trabajoRecepcional = new TrabajoRecepcional();
                trabajoRecepcional.setIdTrabajoRecepcional(resultado.getInt("idTrabajosRecepcionales"));
                trabajoRecepcional.setTituloTrabajoRecepcional(resultado.getString("tituloTrabajo"));
                trabajoRecepcional.setResultadoObtenido(resultado.getString("resultadoObtenido"));
                trabajoRecepcional.setFechaPresentacion(resultado.getString("fechaPresentacion"));
                trabajoRecepcional.setAlumnos(resultado.getString("alumnos"));
                trabajoRecepcional.setIdModalidad(resultado.getInt("idModalidad"));
                trabajoRecepcional.setIdProgramaEducativo(resultado.getInt("idProgramaEducativo"));
                trabajoRecepcional.setNombreProgramaEducativo(resultado.getString("nombreProgramaEducativo"));
                trabajoRecepcional.setNombreModalidad(resultado.getString("nombreModalidad"));

                trabajosRecepcionales.add(trabajoRecepcional);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DAOException("", Codigos.ERROR_CONSULTA);
        } finally {
            ConexionBD.cerrarConexionBD();
        }
        return trabajosRecepcionales;
    }
    
    public int desasignarTrabajoRecepcionalProfesor(int idProfesor, int idExperienciaEducativa, int idRolJurado) throws DAOException{
        int filasAfectadas = -1;
        try {
            PreparedStatement sentencia = ConexionBD.obtenerConexionBD().prepareStatement(DESASIGNAR_TRABAJO_RECEPCIONAL_AL_PROFESOR);
            sentencia.setInt(1, idProfesor);
            sentencia.setInt(2, idExperienciaEducativa);
            sentencia.setInt(3, idRolJurado);
            filasAfectadas = sentencia.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DAOException("", Codigos.ERROR_CONSULTA);
        } finally {
            ConexionBD.cerrarConexionBD();
        }
        return filasAfectadas;
    }
}

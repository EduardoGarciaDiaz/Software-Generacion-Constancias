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
import jfxgeneracionconstancias.modelos.pojo.ProyectoCampo;
import jfxgeneracionconstancias.utils.Codigos;

/**
 *
 * @author egd17
 */
public class ProyectoCampoDAO {
    
    private static String REGISTRAR_PROYECTO_CAMPO = "INSERT INTO `constancias`.`proyectos` "
        + "(`nombreProyecto`, `duracion`, `impactoObtenido`, `lugar`, `alumnos`, `idProfesor`) "
        + "VALUES (?, ?, ?, ?, ?, ?);";
    private static String OBTENER_PROYECTOS_CAMPO_DEL_PROFESOR = "SELECT * FROM constancias.proyectos "
            + "WHERE idProfesor = ?";
    private static String ELIMINAR_PROYECTO_CAMPO = "DELETE FROM constancias.proyectos "
            + "WHERE idProyecto = ? AND idProfesor = ?";
    
    public int eliminarProyectoCampo(int idProyecto, int idProfesor) throws DAOException{
        int filasAfectadas = -1;
        try {
            PreparedStatement sentencia = ConexionBD.obtenerConexionBD().prepareStatement(ELIMINAR_PROYECTO_CAMPO);
            sentencia.setInt(1, idProyecto);
            sentencia.setInt(2, idProfesor);
            filasAfectadas = sentencia.executeUpdate();
        } catch (SQLException ex) {
            throw new DAOException("", Codigos.ERROR_CONSULTA);
        } finally {
            ConexionBD.cerrarConexionBD();
        }
        return filasAfectadas;
    }
    
    public int registrarProyectoCampo(ProyectoCampo proyectoCampo)throws DAOException{
        int respuesta = -1;
        try {
            PreparedStatement sentencia = ConexionBD.obtenerConexionBD().prepareStatement(REGISTRAR_PROYECTO_CAMPO,
                    Statement.RETURN_GENERATED_KEYS);
            sentencia.setString(1, proyectoCampo.getNombreProyecto());
            sentencia.setString(2, proyectoCampo.getDuracion());
            sentencia.setString(3, proyectoCampo.getImpactoObtenido());
            sentencia.setString(4, proyectoCampo.getLugar());
            sentencia.setString(5, proyectoCampo.getAlumnos());
            sentencia.setInt(6, proyectoCampo.getIdProfesor());
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
    
    public ObservableList<ProyectoCampo> obtenerProyectosCampoPorIdProfesor(int idProfesor) throws DAOException {
        ObservableList<ProyectoCampo> proyectosCampo = FXCollections.observableArrayList();
        try{
            PreparedStatement sentencia = ConexionBD.obtenerConexionBD().prepareStatement(OBTENER_PROYECTOS_CAMPO_DEL_PROFESOR);
            sentencia.setInt(1, idProfesor);
            ResultSet resultado = sentencia.executeQuery();
            while (resultado.next()) {
                ProyectoCampo proyectoCampo = new ProyectoCampo();
                proyectoCampo.setIdProyecto(resultado.getInt("idProyecto"));
                proyectoCampo.setNombreProyecto(resultado.getString("nombreProyecto"));
                proyectoCampo.setDuracion(resultado.getString("duracion"));
                proyectoCampo.setImpactoObtenido(resultado.getString("impactoObtenido"));
                proyectoCampo.setLugar(resultado.getString("lugar"));
                proyectoCampo.setAlumnos(resultado.getString("alumnos"));
                proyectoCampo.setIdProfesor(resultado.getInt("idProfesor"));

                proyectosCampo.add(proyectoCampo);
            }
        } catch (SQLException ex) {
            throw new DAOException("", Codigos.ERROR_CONSULTA);
        } finally {
            ConexionBD.cerrarConexionBD();
        }
        return proyectosCampo;
    }
    
}

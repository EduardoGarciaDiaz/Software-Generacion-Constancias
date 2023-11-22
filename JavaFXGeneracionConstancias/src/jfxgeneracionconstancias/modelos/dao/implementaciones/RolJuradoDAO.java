/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jfxgeneracionconstancias.modelos.dao.implementaciones;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jfxgeneracionconstancias.modelos.ConexionBD;
import jfxgeneracionconstancias.modelos.dao.DAOException;
import jfxgeneracionconstancias.modelos.pojo.Modalidad;
import jfxgeneracionconstancias.modelos.pojo.RolJurado;
import jfxgeneracionconstancias.utils.Codigos;

/**
 *
 * @author egd17
 */
public class RolJuradoDAO {
    
    private static String OBTENER_ROLES_JURADO = "SELECT * FROM rolesJurado";
    private static String OBTENER_ID_ROL_JURADO_POR_NOMBRE = "SELECT idRolJurado FROM constancias.rolesjurado WHERE nombreRolJurado = ?";
    
    public ObservableList<RolJurado> obtenerRolesJurado() throws DAOException{
        ObservableList<RolJurado> rolesJurado = FXCollections.observableArrayList();
        try {
            PreparedStatement sentencia = ConexionBD.obtenerConexionBD().prepareStatement(OBTENER_ROLES_JURADO);
            ResultSet resultado = sentencia.executeQuery();
            while (resultado.next()) {
                RolJurado rolJurado = new RolJurado();
                rolJurado.setIdRolJurado(resultado.getInt("idRolJurado"));
                rolJurado.setNombreRolJurado(resultado.getString("nombreRolJurado"));
                rolesJurado.add(rolJurado);
            }
        } catch (SQLException ex) {
            throw new DAOException("", Codigos.ERROR_CONSULTA);
        } finally {
            ConexionBD.cerrarConexionBD();
        }
        return rolesJurado;
    }
     public int obtenerIdRolJuradoPorNombre(String nombreRolJurado) throws DAOException{
         int idRolJurado = -1;
        try {
            PreparedStatement sentencia = ConexionBD.obtenerConexionBD().prepareStatement(OBTENER_ID_ROL_JURADO_POR_NOMBRE);
            sentencia.setString(1, nombreRolJurado);
            ResultSet resultado = sentencia.executeQuery();
            while (resultado.next()) {
                idRolJurado = resultado.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DAOException("", Codigos.ERROR_CONSULTA);
        } finally {
            ConexionBD.cerrarConexionBD();
        }
        return idRolJurado;
    }
    
}

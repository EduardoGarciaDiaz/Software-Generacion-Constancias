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
import jfxgeneracionconstancias.modelos.pojo.ProgramaEducativo;
import jfxgeneracionconstancias.utils.Codigos;

/**
 *
 * @author egd17
 */
public class ModalidadDAO {
    
    private static String OBTENER_MODALIDADES = "SELECT * FROM modalidades";
    
    public ObservableList<Modalidad> obtenerModalidades() throws DAOException{
        ObservableList<Modalidad> modalidades = FXCollections.observableArrayList();
        try {
            PreparedStatement sentencia = ConexionBD.obtenerConexionBD().prepareStatement(OBTENER_MODALIDADES);
            ResultSet resultado = sentencia.executeQuery();
            while (resultado.next()) {
                Modalidad modalidad = new Modalidad();
                modalidad.setIdModalidad(resultado.getInt("idModalidad"));
                modalidad.setNombreModalidad(resultado.getString("nombreModalidad"));
                modalidades.add(modalidad);
            }
        } catch (SQLException ex) {
            throw new DAOException("", Codigos.ERROR_CONSULTA);
        } finally {
            ConexionBD.cerrarConexionBD();
        }
        return modalidades;
    }
    
}

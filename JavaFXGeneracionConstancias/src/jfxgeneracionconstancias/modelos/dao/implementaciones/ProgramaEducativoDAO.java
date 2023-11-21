/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jfxgeneracionconstancias.modelos.dao.implementaciones;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jfxgeneracionconstancias.modelos.ConexionBD;
import jfxgeneracionconstancias.modelos.dao.DAOException;
import jfxgeneracionconstancias.modelos.pojo.ProgramaEducativo;
import jfxgeneracionconstancias.modelos.pojo.Usuario;
import jfxgeneracionconstancias.utils.Codigos;

/**
 *
 * @author egd17
 */
public class ProgramaEducativoDAO {
    
    private static String OBTENER_PROGRAMAS_EDUCATIVOS = "SELECT * FROM ProgramasEducativos";
    
    public ObservableList<ProgramaEducativo> obtenerProgramasEducativos() throws DAOException{
        ObservableList<ProgramaEducativo> programasEducativos = FXCollections.observableArrayList();
        try {
            PreparedStatement sentencia = ConexionBD.obtenerConexionBD().prepareStatement(OBTENER_PROGRAMAS_EDUCATIVOS);
            ResultSet resultado = sentencia.executeQuery();
            while (resultado.next()) {
                ProgramaEducativo programaEducativo = new ProgramaEducativo();
                programaEducativo.setIdProgramaEducativo(resultado.getInt("idProgramaEducativo"));
                programaEducativo.setNombreProgramaEducativo(resultado.getString("nombreProgramaEducativo"));
                programasEducativos.add(programaEducativo);
            }
        } catch (SQLException ex) {
            throw new DAOException("", Codigos.ERROR_CONSULTA);
        } finally {
            ConexionBD.cerrarConexionBD();
        }
        return programasEducativos;
    }
                
}

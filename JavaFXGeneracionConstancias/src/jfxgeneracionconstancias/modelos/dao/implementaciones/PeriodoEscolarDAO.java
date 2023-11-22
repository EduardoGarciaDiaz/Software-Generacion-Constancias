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
import jfxgeneracionconstancias.modelos.dao.contratos.IPeriodoEscolarDAO;
import jfxgeneracionconstancias.modelos.pojo.PeriodoEscolar;
import jfxgeneracionconstancias.utils.Codigos;

/**
 *
 * @author Daniel García Arcos
 */
public class PeriodoEscolarDAO implements IPeriodoEscolarDAO{
    
    private final String OBTENER_PERIODOS = "SELECT * FROM constancias.periodosescolares";

    @Override
    public ObservableList<PeriodoEscolar> obtenerPeriodosEscolares() throws DAOException {
        ObservableList<PeriodoEscolar> periodosEscolares = FXCollections.observableArrayList();
        try{
            PreparedStatement sentencia = ConexionBD.obtenerConexionBD().prepareStatement(OBTENER_PERIODOS);
            ResultSet resultado = sentencia.executeQuery();
            while (resultado.next()) {
                PeriodoEscolar periodoEscolar = new PeriodoEscolar();
                periodoEscolar.setIdPeriodo(resultado.getInt("idPeriodoEscolar"));
                periodoEscolar.setNombre(resultado.getString("nombre"));
                periodoEscolar.setFechaFin(resultado.getString("fechaFin"));
                periodoEscolar.setFechaInicio(resultado.getString("fechaInicio"));
                periodosEscolares.add(periodoEscolar);
            }
        } catch (SQLException ex) {
            throw new DAOException("", Codigos.ERROR_CONSULTA);
        } finally {
            ConexionBD.cerrarConexionBD();
        }
        return periodosEscolares;
    }
    
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jfxgeneracionconstancias.modelos.dao.implementaciones;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import jfxgeneracionconstancias.modelos.ConexionBD;
import jfxgeneracionconstancias.modelos.dao.DAOException;
import jfxgeneracionconstancias.modelos.dao.contratos.IFirmaDAO;
import jfxgeneracionconstancias.modelos.pojo.FirmaDigital;
import jfxgeneracionconstancias.utils.Codigos;

/**
 *
 * @author tristan
 */
public class FirmaDAO implements IFirmaDAO{

    private static final String COMPORBAR_FIRMA_EXISTENTE = "SELECT COUNT(*) FROM firmaselectrónicas;";
    private static final String RECUPERAR_FIRMA_DIGITAL = "SELECT * FROM firmaselectrónicas LIMIT 1;";
    private static final String ACTUALIZAR_FIRMA_DIGITAL = "UPDATE `constancias`.`firmaselectrónicas` SET `firma` = ?, `fechaExpiracion` = ? WHERE (`idFirmaElectronica` = '1');";
    private static final String REGISTRAR_FIRMA = "INSERT INTO `constancias`.`firmaselectrónicas` (`idFirmaElectronica`,`firma`, `fechaExpiracion`) VALUES (?,?, ?)";
    @Override
    public FirmaDigital obtenerFirmaDigitalActual() throws DAOException {
        FirmaDigital firmaActual = null;
        try{
            PreparedStatement sentencia = ConexionBD.obtenerConexionBD().prepareStatement(RECUPERAR_FIRMA_DIGITAL);
            ResultSet resultado = sentencia.executeQuery();
            if(resultado.next()){
                firmaActual = new FirmaDigital();
                firmaActual.setIdFirmaDigital(resultado.getInt("idFirmaElectronica"));
                firmaActual.setContenidoFirmaDigital(resultado.getString("firma"));
                firmaActual.setFechaOrigen(resultado.getTimestamp("fechaExpiracion").toString());
            }
        }catch (SQLException ex) {
            ex.printStackTrace();
            throw new DAOException("", Codigos.ERROR_CONSULTA);
        } finally {
            ConexionBD.cerrarConexionBD();
        }
        return firmaActual;
    }  

    @Override
    public int actualizarFirmaDigital(String firmaNueva, String fechaExpiración) throws DAOException {
        int exitoOperacion = 0;
                try{
            PreparedStatement sentencia = ConexionBD.obtenerConexionBD().prepareStatement(ACTUALIZAR_FIRMA_DIGITAL, Statement.RETURN_GENERATED_KEYS);
            sentencia.setString(1, firmaNueva);
            sentencia.setString(2, fechaExpiración);            
            exitoOperacion = sentencia.executeUpdate();          
        }catch (SQLException ex) {
            ex.printStackTrace();
            throw new DAOException("", Codigos.ERROR_CONSULTA);
        } finally {
            ConexionBD.cerrarConexionBD();
        }
       return exitoOperacion;
    }

    @Override
    public int comporbarFirmaDigital() throws DAOException {
        int exitoOperacion = -1;
                try{
            PreparedStatement sentencia = ConexionBD.obtenerConexionBD().prepareStatement(COMPORBAR_FIRMA_EXISTENTE);            
            ResultSet resultado = sentencia.executeQuery();
            if(resultado.next()){
                exitoOperacion = resultado.getInt(1);
            }
        }catch (SQLException ex) {
            ex.printStackTrace();
            throw new DAOException("", Codigos.ERROR_CONSULTA);
        } finally {
            ConexionBD.cerrarConexionBD();
        }
       return exitoOperacion;
    }

    @Override
    public int registrarFirma(String firmaNueva, String fechaExpiración) throws DAOException {
       int exitoOperacion = -1;
                try{
            PreparedStatement sentencia = ConexionBD.obtenerConexionBD().prepareStatement(REGISTRAR_FIRMA, Statement.RETURN_GENERATED_KEYS);
            sentencia.setInt(1,1);
            sentencia.setString(2, firmaNueva);
            sentencia.setString(3, fechaExpiración); 
            sentencia.executeUpdate(); 
            ResultSet resultado = sentencia.getGeneratedKeys();
            if(resultado.next()){
                exitoOperacion = resultado.getInt(1);
            }
        }catch (SQLException ex) {
            ex.printStackTrace();
            throw new DAOException("", Codigos.ERROR_CONSULTA);
        } finally {
            ConexionBD.cerrarConexionBD();
        }
       return exitoOperacion;
    }
    
}

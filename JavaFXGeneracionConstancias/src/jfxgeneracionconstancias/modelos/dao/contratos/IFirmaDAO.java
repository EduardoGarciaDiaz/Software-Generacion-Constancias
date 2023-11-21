/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package jfxgeneracionconstancias.modelos.dao.contratos;

import jfxgeneracionconstancias.modelos.dao.DAOException;
import jfxgeneracionconstancias.modelos.pojo.FirmaDigital;

/**
 *
 * @author tristan
 */
public interface IFirmaDAO {
    public FirmaDigital obtenerFirmaDigitalActual() throws DAOException;
    public int actualizarFirmaDigital(String firmaNueva, String fechaExpiración) throws DAOException;
    public int comporbarFirmaDigital() throws DAOException;
    public int registrarFirma(String firmaNueva, String fechaExpiración) throws DAOException;
}

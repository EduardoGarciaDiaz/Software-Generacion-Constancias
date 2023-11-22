/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package jfxgeneracionconstancias.modelos.dao.contratos;

import javafx.collections.ObservableList;
import jfxgeneracionconstancias.modelos.dao.DAOException;
import jfxgeneracionconstancias.modelos.pojo.PeriodoEscolar;

/**
 *
 * @author Daniel García Arcos
 */
public interface IPeriodoEscolarDAO {
    ObservableList<PeriodoEscolar> obtenerPeriodosEscolares() throws DAOException;
}

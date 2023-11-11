package jfxgeneracionconstancias.modelos.dao.contratos;

import java.util.ArrayList;
import jfxgeneracionconstancias.modelos.dao.DAOException;
import jfxgeneracionconstancias.modelos.pojo.Profesor;

/**
 *
 * @author Daniel García Arcos
 */
public interface IProfesorDAO {
    public int registrarProfesor(Profesor profesor) throws DAOException;
    public int editarProfesor(Profesor profesor, int idProfesor) throws DAOException;
    public Profesor obtenerProfesor(int idProfesor) throws DAOException;
    public ArrayList<Profesor> obtenerProfesores() throws DAOException;
}

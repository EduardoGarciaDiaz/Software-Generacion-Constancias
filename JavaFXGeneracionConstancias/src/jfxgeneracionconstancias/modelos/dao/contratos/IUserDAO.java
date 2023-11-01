package jfxgeneracionconstancias.modelos.dao.contratos;

import java.util.ArrayList;
import jfxgeneracionconstancias.modelos.dao.DAOException;
import jfxgeneracionconstancias.modelos.pojo.Usuario;

/**
 *
 * @author Daniel García Arcos
 */
public interface IUserDAO {
    
    public int registrarUsuario(Usuario usuario) throws DAOException;
    public int editarUsuario(Usuario usuario, int numeroPersonal) throws DAOException;
    public Usuario obtenerUsuario(int numeroPersonal) throws DAOException;
    public Usuario autenticarUsuario(int numeroPersonal, String contraseña) throws DAOException;
    public ArrayList<Usuario> obtenerUsuarios() throws DAOException;
}

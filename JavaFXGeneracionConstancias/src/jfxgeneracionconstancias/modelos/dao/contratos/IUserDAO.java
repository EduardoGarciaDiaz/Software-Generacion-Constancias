package jfxgeneracionconstancias.modelos.dao.contratos;

import java.util.ArrayList;
import jfxgeneracionconstancias.modelos.dao.DAOException;
import jfxgeneracionconstancias.modelos.pojo.Usuario;

/**
 *
 * @author Daniel García Arcos
 */
public interface IUserDAO {

    public long registrarUsuario(Usuario usuario) throws DAOException;
    public long editarUsuario(Usuario usuario, long numeroPersonal) throws DAOException;
    public Usuario obtenerUsuario(long numeroPersonal) throws DAOException;
    public Usuario autenticarUsuario(long numeroPersonal, String contraseña) throws DAOException;
    public ArrayList<Usuario> obtenerUsuarios() throws DAOException;

}

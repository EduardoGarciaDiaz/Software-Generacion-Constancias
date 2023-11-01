package jfxgeneracionconstancias.modelos.dao.implementaciones;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import jfxgeneracionconstancias.modelos.ConexionBD;
import jfxgeneracionconstancias.modelos.dao.DAOException;
import jfxgeneracionconstancias.modelos.dao.contratos.IUserDAO;
import jfxgeneracionconstancias.modelos.pojo.Usuario;
import jfxgeneracionconstancias.utils.Codigos;

/**
 *
 * @author Daniel García Arcos
 */
public class UsuarioDAO implements IUserDAO {

    private final String REGISTRAR_USUARIO = "INSER INTO usuarios (numeroPersonal, nombre, "
            + "primerApellido, segundoApellido, esAdmin, correoInstitucional, idTipoUsuario, contraseña)"
            + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private final String EDITAR_USUARIO = "UPDATE usuarios SET `nombre` = ?, `primerApellido` = ?, `segundoApellido` = ?, `esAdmin` = ?, "
            + "correoInstitucional = ?, idTipoUsuario = ?, contraseña = ? WHERE (`numeroPersonal` = ?)";
    private final String OBTENER_USUARIO = "SELECT * FROM usuarios WHERE usuarios.numeroPersonal = ?";
    private final String AUTENTICAR_USUARIO = "SELECT * FROM usuarios WHERE usuarios.numeroPersonal = ? AND usuarios.contraseña = ?";
    private final String OBTENER_USUARIOS = "SELECT * FROM usuarios";
    
    @Override
    public int registrarUsuario(Usuario usuario) throws DAOException {
        int respuesta = -1;
        try {
            PreparedStatement sentencia = ConexionBD.obtenerConexionBD().prepareStatement(REGISTRAR_USUARIO, 
                    Statement.RETURN_GENERATED_KEYS);
            sentencia.setInt(1, usuario.getNumeroPersonal());
            sentencia.setString(2, usuario.getNombre());
            sentencia.setString(3, usuario.getPrimerApellido());
            sentencia.setString(4, usuario.getSegundoApellido());
            sentencia.setBoolean(5, usuario.isAdministrador());
            sentencia.setString(6, usuario.getCorreoInstitucional());
            sentencia.setInt(7, usuario.getTipoUsuario());
            sentencia.setString(8, usuario.getContraseña());
            sentencia.executeUpdate();
            ResultSet rs = sentencia.getGeneratedKeys();
            while (rs.next()) {
                respuesta = rs.getInt(1);
            }
        } catch (SQLException ex) {
            throw new DAOException("", Codigos.ERROR_CONSULTA);
        } finally {
            ConexionBD.cerrarConexionBD();
        }
        return respuesta;
    }

    @Override
    public int editarUsuario(Usuario usuario, int numeroPersonal) throws DAOException {
        int respuesta = -1;
        try {
            PreparedStatement sentencia = ConexionBD.obtenerConexionBD().prepareStatement(EDITAR_USUARIO);
            sentencia.setString(1, usuario.getNombre());
            sentencia.setString(2, usuario.getPrimerApellido());
            sentencia.setString(3, usuario.getSegundoApellido());
            sentencia.setBoolean(4, usuario.isAdministrador());
            sentencia.setString(5, usuario.getCorreoInstitucional());
            sentencia.setInt(6, usuario.getTipoUsuario());
            sentencia.setString(7, usuario.getContraseña());
            sentencia.setInt(8, usuario.getNumeroPersonal());
            int filasAfectadas = sentencia.executeUpdate();
            respuesta = (filasAfectadas == 1) ? usuario.getNumeroPersonal(): -1;
        } catch (SQLException ex) {
            throw new DAOException("", Codigos.ERROR_CONSULTA);
        } finally {
            ConexionBD.cerrarConexionBD();
        }
        return respuesta;
    }

    @Override
    public Usuario obtenerUsuario(int numeroPersonal) throws DAOException {
        Usuario usuario = new Usuario();
        usuario.setNumeroPersonal(-1);
        try {
            PreparedStatement sentencia = ConexionBD.obtenerConexionBD().prepareStatement(OBTENER_USUARIO);
            sentencia.setInt(1, numeroPersonal);
            ResultSet resultado = sentencia.executeQuery();
            if (resultado.next()) {
                usuario.setNumeroPersonal(resultado.getInt("numeroPersonal"));
                usuario.setNombre(resultado.getString("nombre"));
                usuario.setPrimerApellido(resultado.getString("primerApellido"));
                usuario.setSegundoApellido(resultado.getString("segundoApellido"));
                usuario.setEsAdministrador(resultado.getBoolean("esAdmin"));
                usuario.setCorreoInstitucional(resultado.getString("correoInstitucional"));
                usuario.setTipoUsuario(resultado.getInt("idTipoUsuario"));
                usuario.setContraseña(resultado.getString("contraseña"));
            }
        } catch (SQLException ex) {
            throw new DAOException("", Codigos.ERROR_CONSULTA);
        } finally {
            ConexionBD.cerrarConexionBD();
        }
        return usuario;
    }

    @Override
    public Usuario autenticarUsuario(int numeroPersonal, String contraseña) throws DAOException {
        Usuario usuario = new Usuario();
        usuario.setNumeroPersonal(-1);
        try {
            PreparedStatement sentencia = ConexionBD.obtenerConexionBD().prepareStatement(AUTENTICAR_USUARIO);
            sentencia.setInt(1, numeroPersonal);
            sentencia.setString(2, contraseña);
            ResultSet resultado = sentencia.executeQuery();
            if (resultado.next()) {
                usuario.setNumeroPersonal(resultado.getInt("numeroPersonal"));
                usuario.setNombre(resultado.getString("nombre"));
                usuario.setPrimerApellido(resultado.getString("primerApellido"));
                usuario.setSegundoApellido(resultado.getString("segundoApellido"));
                usuario.setEsAdministrador(resultado.getBoolean("esAdmin"));
                usuario.setCorreoInstitucional(resultado.getString("correoInstitucional"));
                usuario.setTipoUsuario(resultado.getInt("idTipoUsuario"));
                usuario.setContraseña(resultado.getString("contraseña"));
            }
        } catch (SQLException ex) {
            throw new DAOException("", Codigos.ERROR_CONSULTA);
        } finally {
            ConexionBD.cerrarConexionBD();
        }
        return usuario;
    }

    @Override
    public ArrayList<Usuario> obtenerUsuarios() throws DAOException {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        try {
            PreparedStatement sentencia = ConexionBD.obtenerConexionBD().prepareStatement(OBTENER_USUARIOS);
            ResultSet resultado = sentencia.executeQuery();
            while (resultado.next()) {
                Usuario usuario = new Usuario();
                usuario.setNumeroPersonal(resultado.getInt("numeroPersonal"));
                usuario.setNombre(resultado.getString("nombre"));
                usuario.setPrimerApellido(resultado.getString("primerApellido"));
                usuario.setSegundoApellido(resultado.getString("segundoApellido"));
                usuario.setEsAdministrador(resultado.getBoolean("esAdmin"));
                usuario.setCorreoInstitucional(resultado.getString("correoInstitucional"));
                usuario.setTipoUsuario(resultado.getInt("idTipoUsuario"));
                usuario.setContraseña(resultado.getString("contraseña"));
                usuarios.add(usuario);
            }
        } catch (SQLException ex) {
            throw new DAOException("", Codigos.ERROR_CONSULTA);
        } finally {
            ConexionBD.cerrarConexionBD();
        }
        return usuarios;
    }
    
}

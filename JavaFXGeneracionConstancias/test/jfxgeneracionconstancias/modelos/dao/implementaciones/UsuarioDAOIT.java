/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package jfxgeneracionconstancias.modelos.dao.implementaciones;

import java.util.ArrayList;
import jfxgeneracionconstancias.modelos.dao.DAOException;
import jfxgeneracionconstancias.modelos.pojo.Usuario;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Daniel García Arcos
 */
public class UsuarioDAOIT {
    private static Usuario usuarioPrueba;
    private static Usuario usuarioPruebaDos;
    
    public UsuarioDAOIT() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        usuarioPrueba = new Usuario();
        usuarioPruebaDos = new Usuario();
        usuarioPrueba.setNumeroPersonal(5678123456L);
        usuarioPrueba.setEsAdministrador(true);
        usuarioPrueba.setTipoUsuario(1);
        usuarioPrueba.setNombre("UsuarioPrueba");
        usuarioPrueba.setPrimerApellido("Primero");
        usuarioPrueba.setSegundoApellido("Segundo");
        usuarioPrueba.setCorreoInstitucional("correoprueba@uv.mx");
        usuarioPrueba.setContraseña("Contrasena");
        
        usuarioPruebaDos.setNumeroPersonal(5678123450L);
        usuarioPruebaDos.setEsAdministrador(true);
        usuarioPruebaDos.setTipoUsuario(1);
        usuarioPruebaDos.setNombre("UsuarioPrueba");
        usuarioPruebaDos.setPrimerApellido("Primero");
        usuarioPruebaDos.setSegundoApellido("Segundo");
        usuarioPruebaDos.setCorreoInstitucional("correoprueba@uv.mx");
        usuarioPruebaDos.setContraseña("Contrasena");
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        try {
            usuarioDAO.registrarUsuario(usuarioPrueba);
            usuarioDAO.registrarUsuario(usuarioPruebaDos);
        } catch (DAOException ex) {
            System.out.println("Error en preparacion de base de datos");
        }
    }
    
    @AfterClass
    public static void tearDownClass() {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        try {
            usuarioDAO.eliminarUsuario(usuarioPrueba.getNumeroPersonal());
            usuarioDAO.eliminarUsuario(6573828734L);
        } catch (DAOException ex) {
            System.out.println("Error en preparacion de base de datos");
        }
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of registrarUsuario method, of class UsuarioDAO.
     */
    @Test
    public void testRegistrarUsuario() throws Exception {
        System.out.println("registrarUsuario");
        Usuario usuario = new Usuario();
        usuario.setNumeroPersonal(6573828734L);
        usuario.setEsAdministrador(true);
        usuario.setTipoUsuario(1);
        usuario.setNombre("Juan");
        usuario.setPrimerApellido("De la Cruz");
        usuario.setSegundoApellido("Garcia");
        usuario.setCorreoInstitucional("correojuan@uv.mx");
        usuario.setContraseña("Contrasena123");
        UsuarioDAO instance = new UsuarioDAO();
        long expResult = 6573828734L;
        long result = instance.registrarUsuario(usuario);
        assertEquals(expResult, result);
    }

    /**
     * Test of editarUsuario method, of class UsuarioDAO.
     */
    @Test
    public void testEditarUsuario() throws Exception {
        System.out.println("editarUsuario");
        usuarioPrueba.setNombre("NuevoNombre");
        long numeroPersonal = usuarioPrueba.getNumeroPersonal();
        UsuarioDAO instance = new UsuarioDAO();
        long expResult = usuarioPrueba.getNumeroPersonal();
        long result = instance.editarUsuario(usuarioPrueba, numeroPersonal);
        assertEquals(expResult, result);
        assertEquals(usuarioPrueba.getNombre(), "NuevoNombre");
    }

    /**
     * Test of editarUsuario method, of class UsuarioDAO.
     */
    @Test
    public void testEditarUsuarioNumeroPersonal() throws Exception {
        System.out.println("editarUsuario");
        long numeroPersonal = usuarioPrueba.getNumeroPersonal();
        usuarioPrueba.setNumeroPersonal(5678123467L);
        UsuarioDAO instance = new UsuarioDAO();
        long expResult = usuarioPrueba.getNumeroPersonal();
        long result = instance.editarUsuario(usuarioPrueba, numeroPersonal);
        assertNotEquals(expResult, result);
        usuarioPrueba.setNumeroPersonal(numeroPersonal);
    }
    
    /**
     * Test of editarUsuarioPorPersonalAdministrativo method, of class UsuarioDAO.
     */
    @Test
    public void testEditarUsuarioPorPersonalAdministrativo() throws Exception {
        System.out.println("editarUsuarioPorPersonalAdministrativo");
        usuarioPrueba.setNombre("NombrePruebaPersonal");
        usuarioPrueba.setPrimerApellido("PrimerApellido");
        usuarioPrueba.setSegundoApellido("SegundoApellido");
        usuarioPrueba.setCorreoInstitucional("correoInstitucional");
        UsuarioDAO instance = new UsuarioDAO();
        long expResult = usuarioPrueba.getNumeroPersonal();
        long result = instance.editarUsuarioPorPersonalAdministrativo(usuarioPrueba);
        assertEquals(expResult, result);
        assertEquals(usuarioPrueba.getNombre(), "NombrePruebaPersonal");
    }

    /**
     * Test of obtenerUsuario method, of class UsuarioDAO.
     */
    @Test
    public void testObtenerUsuario() throws Exception {
        System.out.println("obtenerUsuario");
        long numeroPersonal = usuarioPrueba.getNumeroPersonal();
        UsuarioDAO instance = new UsuarioDAO();
        Usuario result = instance.obtenerUsuario(numeroPersonal);
        assertEquals(usuarioPrueba.getNumeroPersonal(), result.getNumeroPersonal());
    }
    
    /**
     * Test of obtenerUsuario method, of class UsuarioDAO.
     */
    @Test
    public void testObtenerUsuarioNoExistente() throws Exception {
        System.out.println("obtenerUsuario");
        long numeroPersonal = 7823671212L;
        UsuarioDAO instance = new UsuarioDAO();
        Usuario result = instance.obtenerUsuario(numeroPersonal);
        assertEquals(-1, result.getNumeroPersonal());
    }

    /**
     * Test of autenticarUsuario method, of class UsuarioDAO.
     */
    @Test
    public void testAutenticarUsuario() throws Exception {
        System.out.println("autenticarUsuario");
        long numeroPersonal = usuarioPrueba.getNumeroPersonal();
        String contraseña = usuarioPrueba.getContraseña();
        UsuarioDAO instance = new UsuarioDAO();
        Usuario result = instance.autenticarUsuario(numeroPersonal, contraseña);
        assertEquals(usuarioPrueba.getNumeroPersonal(), result.getNumeroPersonal());
    }

    /**
     * Test of obtenerUsuarios method, of class UsuarioDAO.
     */
    @Test
    public void testObtenerUsuarios() throws Exception {
        System.out.println("obtenerUsuarios");
        UsuarioDAO instance = new UsuarioDAO();
        ArrayList<Usuario> result = instance.obtenerUsuarios();
        assertTrue(!result.isEmpty());
    }

    /**
     * Test of eliminarUsuario method, of class UsuarioDAO.
     */
    @Test
    public void testEliminarUsuario() throws Exception {
        System.out.println("eliminar usuario");
        UsuarioDAO instance = new UsuarioDAO();
        long expectResult = usuarioPruebaDos.getNumeroPersonal();
        long result = instance.eliminarUsuario(usuarioPruebaDos.getNumeroPersonal());
        assertEquals(expectResult, result);
    }
    
}

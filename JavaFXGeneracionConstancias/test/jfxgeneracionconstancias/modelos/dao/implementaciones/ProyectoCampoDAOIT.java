/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package jfxgeneracionconstancias.modelos.dao.implementaciones;

import javafx.collections.ObservableList;
import jfxgeneracionconstancias.modelos.dao.DAOException;
import jfxgeneracionconstancias.modelos.pojo.Profesor;
import jfxgeneracionconstancias.modelos.pojo.ProyectoCampo;
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
public class ProyectoCampoDAOIT {
    private static Profesor profesorPrueba;
    private static int idProfesor;
    private static int idProyectoPruebaEliminar;
    private static int idProyectoPruebaObtencion;
    private static int idProyectoPruebaCreacion;
    
    public ProyectoCampoDAOIT() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        Usuario usuario = new Usuario();
        usuario.setNumeroPersonal(6573828734L);
        usuario.setEsAdministrador(true);
        usuario.setTipoUsuario(1);
        usuario.setNombre("Juan");
        usuario.setPrimerApellido("De la Cruz");
        usuario.setSegundoApellido("Garcia");
        usuario.setCorreoInstitucional("correojuan@uv.mx");
        usuario.setContraseña("Contrasena123");
        profesorPrueba = new Profesor();
        profesorPrueba.setNumeroPersonal(usuario.getNumeroPersonal());
        
        UsuarioDAO instance = new UsuarioDAO();
        ProfesorDAO profesorDAO = new ProfesorDAO();
        try {
            instance.registrarUsuario(usuario);
            idProfesor = profesorDAO.registrarProfesor(profesorPrueba);
            
            ProyectoCampo proyectoCampo = new ProyectoCampo("Proyecto de prueba", "5 meses",
                    "Alto", "Xalapa, Veracruz", "Alumno 1, Alumno 2", idProfesor);
            ProyectoCampoDAO proyectoCampoDAO = new ProyectoCampoDAO();
            idProyectoPruebaEliminar = proyectoCampoDAO.registrarProyectoCampo(proyectoCampo);
            idProyectoPruebaObtencion = proyectoCampoDAO.registrarProyectoCampo(proyectoCampo);
        } catch (DAOException ex) {
            System.err.println("Error en preparacion de clase de prueba");
        }
    }
    
    @AfterClass
    public static void tearDownClass() {
        try {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            ProfesorDAO profesorDAO = new ProfesorDAO();
            ProyectoCampoDAO proyectoCampoDAO = new ProyectoCampoDAO();
            proyectoCampoDAO.eliminarProyectoCampo(idProyectoPruebaObtencion, idProfesor);
            proyectoCampoDAO.eliminarProyectoCampo(idProyectoPruebaCreacion, idProfesor);
            profesorDAO.eliminarProfesor(idProfesor);
            usuarioDAO.eliminarUsuario(profesorPrueba.getNumeroPersonal());
            
        } catch (DAOException ex) {
            System.err.println("Error");
        }
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of eliminarProyectoCampo method, of class ProyectoCampoDAO.
     */
    @Test
    public void testEliminarProyectoCampo() throws Exception {
        System.out.println("eliminarProyectoCampo");
        ProyectoCampoDAO instance = new ProyectoCampoDAO();
        int result = instance.eliminarProyectoCampo(idProyectoPruebaEliminar, idProfesor);
        assertTrue(result>0);
    }

    /**
     * Test of registrarProyectoCampo method, of class ProyectoCampoDAO.
     */
    @Test
    public void testRegistrarProyectoCampo() throws Exception {
        System.out.println("registrarProyectoCampo");
        ProyectoCampo proyectoCampo = new ProyectoCampo("Proyecto de prueba", "5 meses",
                    "Alto", "Xalapa, Veracruz", "Alumno 1, Alumno 2", idProfesor);
        ProyectoCampoDAO instance = new ProyectoCampoDAO();
        int notExpResult = -1;
        idProyectoPruebaCreacion = instance.registrarProyectoCampo(proyectoCampo);
        assertNotEquals(notExpResult, idProyectoPruebaCreacion);
        assertTrue(idProyectoPruebaCreacion>0);
    }

    /**
     * Test of obtenerProyectosCampoPorIdProfesor method, of class ProyectoCampoDAO.
     */
    @Test
    public void testObtenerProyectosCampoPorIdProfesor() throws Exception {
        System.out.println("obtenerProyectosCampoPorIdProfesor");
        ProyectoCampoDAO instance = new ProyectoCampoDAO();
        ObservableList<ProyectoCampo> result = instance.obtenerProyectosCampoPorIdProfesor(idProfesor);
        assertTrue(!result.isEmpty());
    }

    /**
     * Test of obtenerProyectosCampoPorNumeroPersonal method, of class ProyectoCampoDAO.
     */
    @Test
    public void testObtenerProyectosCampoPorNumeroPersonal() throws Exception {
        System.out.println("obtenerProyectosCampoPorNumeroPersonal");
        long numeroPersonal = profesorPrueba.getNumeroPersonal();
        ProyectoCampoDAO instance = new ProyectoCampoDAO();
        ObservableList<ProyectoCampo> result = instance.obtenerProyectosCampoPorNumeroPersonal(numeroPersonal);
        assertTrue(!result.isEmpty());
    }
    
}

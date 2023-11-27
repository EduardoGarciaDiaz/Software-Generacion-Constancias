/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package jfxgeneracionconstancias.modelos.dao.implementaciones;

import java.util.ArrayList;
import jfxgeneracionconstancias.modelos.dao.DAOException;
import jfxgeneracionconstancias.modelos.pojo.Profesor;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author egd17
 */
public class ProfesorDAOTest {
    private static Profesor profesorPrueba;
    private static int idProfesor;
    
    public ProfesorDAOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        profesorPrueba = new Profesor(0, "2003-11-11", "profesor@uv.mx", "Licenciatura");
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
        try {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            ProfesorDAO profesorDAO = new ProfesorDAO();
            profesorDAO.eliminarProfesor(idProfesor);
            //usuarioDAO.eliminarUsuario(profesorPrueba.getNumeroPersonal());
            
        } catch (DAOException ex) {
            System.err.println("Error Error en preparacion de clase de prueba en metodo tearDown");
        }
    }

    /**
     * Test of registrarProfesor method, of class ProfesorDAO.
     */
    @Test
    public void testRegistrarProfesorExito() throws Exception {
        System.out.println("registrarProfesor exitoso");
        ProfesorDAO profesorDAO = new ProfesorDAO();
        int expResult = 0;
        int result = profesorDAO.registrarProfesor(profesorPrueba);
        idProfesor = result;
        assertTrue("La PK del profesor registrado debe ser mayor a 0", result > expResult);
    }

    /**
     * Test of editarProfesor method, of class ProfesorDAO.
     */
    @Test
    public void testEditarProfesorExito() throws Exception {
        System.out.println("editarProfesor");
        ProfesorDAO instance = new ProfesorDAO();
        long expResult = 0;
        long result = instance.editarProfesor(profesorPrueba);
        assertEquals("El numero de personal debe ser mayor a -1",expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
        @Test
    public void testEditarProfesorFalla() throws Exception {
        System.out.println("editarProfesor");
        Profesor profesor = new Profesor();
        profesor.setNumeroPersonal(-400);
        ProfesorDAO instance = new ProfesorDAO();
        long expResult = -1;
        long result = instance.editarProfesor(profesor);
        assertEquals("El numero de personal debe ser menor a 0",expResult, result);
    }

    /**
     * Test of obtenerProfesor method, of class ProfesorDAO.
     */
    @Test
    public void testObtenerProfesorExito() throws Exception {
        System.out.println("obtenerProfesor");
        long numeroPersonal = 123456789L;
        ProfesorDAO instance = new ProfesorDAO();

        Profesor result = instance.obtenerProfesor(numeroPersonal);
        
        assertNotNull(result);
    }

    @Test
    public void testObtenerProfesorFalla() throws Exception {
        System.out.println("obtenerProfesor");
        long numeroPersonal = -500L;
        ProfesorDAO instance = new ProfesorDAO();

        Profesor result = instance.obtenerProfesor(numeroPersonal);
        
        assertNull(result);
    }
    /**
     * Test of obtenerProfesores method, of class ProfesorDAO.
     */
    @Test
    public void testObtenerProfesoresExito() throws Exception {
        System.out.println("obtenerProfesores");
        ProfesorDAO instance = new ProfesorDAO();
        ArrayList<Profesor> result = instance.obtenerProfesores();
        assertNotNull(result);
    }
    
    @Test
    public void testObtenerProfesoresFalla() throws Exception {
        System.out.println("obtenerProfesores");
        ArrayList<Profesor> profesoreEsperados = new ArrayList<>();
        ProfesorDAO instance = new ProfesorDAO();
        ArrayList<Profesor> result = instance.obtenerProfesores();
        assertNotEquals(profesoreEsperados, result);
    }
    
}

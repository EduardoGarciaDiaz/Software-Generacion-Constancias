/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package jfxgeneracionconstancias.modelos.dao.implementaciones;

import javafx.collections.ObservableList;
import jfxgeneracionconstancias.modelos.dao.DAOException;
import jfxgeneracionconstancias.modelos.pojo.ExperienciaEducativa;
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
public class ExperienciaEducativaDAOTest {
    
    private static ExperienciaEducativa experienciaEducativaPruebaRegistro;
    private static ExperienciaEducativa experienciaEducativaPruebaEdicion;
    private static int idExperienciaEducativaRegistro;
    private static int idExperienciaEducativaEdicion;
    private static Profesor profesorPrueba;
    private static int idProfesor;

    
    public ExperienciaEducativaDAOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        experienciaEducativaPruebaRegistro = new ExperienciaEducativa("Experiencia prueba", 1,
                1, 5, 1, 1);
        experienciaEducativaPruebaEdicion = new ExperienciaEducativa("Experiencia prueba", 1,
                1, 5, 1, 1);
        profesorPrueba = new Profesor(0, "2003-11-11", "profesor@uv.mx", "Licenciatura");

        try {
                    profesorPrueba = new Profesor(0, "2003-11-11", "profesor@uv.mx", "Licenciatura");

            ExperienciaEducativaDAO experienciaEducativaDAO = new ExperienciaEducativaDAO();
            ProfesorDAO profesorDAO = new ProfesorDAO();
            idExperienciaEducativaEdicion = experienciaEducativaDAO.registrarExperienciaEducativa(experienciaEducativaPruebaEdicion);
            experienciaEducativaDAO.desasignarExperienciaEducativaProfesor(0, idExperienciaEducativaEdicion);
            idProfesor = profesorDAO.registrarProfesor(profesorPrueba);

        } catch (DAOException ex) {
            System.err.println("Error");
        }
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
            ExperienciaEducativaDAO experienciaEducativaDAO = new ExperienciaEducativaDAO();

            experienciaEducativaDAO.eliminarExperienciaEducativa(idExperienciaEducativaRegistro);
        } catch (DAOException ex) {
            System.err.println("Error");
        }
        try {
            ExperienciaEducativaDAO experienciaEducativaDAO = new ExperienciaEducativaDAO();
            ProfesorDAO profesorDAO = new ProfesorDAO();
            experienciaEducativaDAO.eliminarExperienciaEducativa(idExperienciaEducativaEdicion);
            profesorDAO.eliminarProfesor(idProfesor);
        } catch (DAOException ex) {
            System.err.println("Error");
        }
    }

    /**
     * Test of registrarExperienciaEducativa method, of class ExperienciaEducativaDAO.
     */
    @Test
    public void testRegistrarExperienciaEducativa() throws Exception {
        System.out.println("registrarExperienciaEducativa");
        ExperienciaEducativa experienciaEducativa = experienciaEducativaPruebaRegistro;
        ExperienciaEducativaDAO instance = new ExperienciaEducativaDAO();
        int result = instance.registrarExperienciaEducativa(experienciaEducativa);
        idExperienciaEducativaRegistro = result;
        assertTrue(result > 0);
    }

    /**
     * Test of asignarExperienciaEducativaAProfesor method, of class ExperienciaEducativaDAO.
     */
    @Test
    public void testAsignarExperienciaEducativaAProfesor() throws Exception {
        System.out.println("asignarExperienciaEducativaAProfesor");
        ExperienciaEducativaDAO instance = new ExperienciaEducativaDAO();
        int expResult = 0;
        int result = instance.asignarExperienciaEducativaAProfesor(idExperienciaEducativaEdicion, idProfesor);
        assertTrue(result > 0);
    }

    /**
     * Test of obtenerExperienciasEducativasPorIdProfesor method, of class ExperienciaEducativaDAO.
     */
    @Test
    public void testObtenerExperienciasEducativasPorIdProfesor() throws Exception {
        System.out.println("obtenerExperienciasEducativasPorIdProfesor");

        ExperienciaEducativaDAO instance = new ExperienciaEducativaDAO();
        ObservableList<ExperienciaEducativa> result = instance.obtenerExperienciasEducativasPorIdProfesor(idProfesor);
        assertTrue(result != null || result.size() > 0);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of obtenerExperienciasEducativasPorPeriodoYPrograma method, of class ExperienciaEducativaDAO.
     */
    @Test
    public void testObtenerExperienciasEducativasPorPeriodoYPrograma() throws Exception {
        System.out.println("obtenerExperienciasEducativasPorPeriodoYPrograma");
        int idPeriodo = 1;
        int idProgramaEducativo = 1;
        long numeroPersonal = 0L;
        ExperienciaEducativaDAO instance = new ExperienciaEducativaDAO();
        ObservableList<ExperienciaEducativa> expResult = null;
        ObservableList<ExperienciaEducativa> result = instance.obtenerExperienciasEducativasPorPeriodoYPrograma(idPeriodo, idProgramaEducativo, numeroPersonal);
        
        assertTrue(result != null || result.size() > 0);
    }

    /**
     * Test of validarExperienciaEducativaExistente method, of class ExperienciaEducativaDAO.
     */
    @Test
    public void testValidarExperienciaEducativaExistente() throws Exception {
        System.out.println("validarExperienciaEducativaExistente");
        ExperienciaEducativaDAO instance = new ExperienciaEducativaDAO();
        boolean result = instance.validarExperienciaEducativaExistente(experienciaEducativaPruebaEdicion);
        assertTrue(result);
    }

    /**
     * Test of obtenerExperiencasEducativasNoAsignadas method, of class ExperienciaEducativaDAO.
     */
    @Test
    public void testObtenerExperiencasEducativasNoAsignadas() throws Exception {
        System.out.println("obtenerExperiencasEducativasNoAsignadas");
        ExperienciaEducativaDAO instance = new ExperienciaEducativaDAO();
        ObservableList<ExperienciaEducativa> result = instance.obtenerExperiencasEducativasNoAsignadas(idProfesor);
        assertTrue(result != null || result.size() > 0);
    }

    /**
     * Test of desasignarExperienciaEducativaProfesor method, of class ExperienciaEducativaDAO.
     */
    /*@Test
    public void testDesasignarExperienciaEducativaProfesor() throws Exception {
        System.out.println("desasignarExperienciaEducativaProfesor");
        ExperienciaEducativaDAO instance = new ExperienciaEducativaDAO();
        int expResult = 0;
        int result = instance.desasignarExperienciaEducativaProfesor(idProfesor, idExperienciaEducativaEdicion);
        assertTrue(result > 0);
    }*/
    
}

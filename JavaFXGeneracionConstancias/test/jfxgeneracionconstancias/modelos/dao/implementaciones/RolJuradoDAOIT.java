/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package jfxgeneracionconstancias.modelos.dao.implementaciones;

import javafx.collections.ObservableList;
import jfxgeneracionconstancias.modelos.pojo.RolJurado;
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
public class RolJuradoDAOIT {
    
    public RolJuradoDAOIT() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of obtenerRolesJurado method, of class RolJuradoDAO.
     */
    @Test
    public void testObtenerRolesJurado() throws Exception {
        System.out.println("obtenerRolesJurado");
        RolJuradoDAO instance = new RolJuradoDAO();
        ObservableList<RolJurado> result = instance.obtenerRolesJurado();
        assertTrue(!result.isEmpty());
    }

    /**
     * Test of obtenerIdRolJuradoPorNombre method, of class RolJuradoDAO.
     */
    @Test
    public void testObtenerIdRolJuradoPorNombre() throws Exception {
        System.out.println("obtenerIdRolJuradoPorNombre");
        String nombreRolJurado = "Director";
        RolJuradoDAO instance = new RolJuradoDAO();
        int result = instance.obtenerIdRolJuradoPorNombre(nombreRolJurado);
        assertTrue(result>0);
    }
    
}

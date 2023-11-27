/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package jfxgeneracionconstancias.modelos.dao.implementaciones;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jfxgeneracionconstancias.modelos.pojo.Modalidad;
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
public class ModalidadDAOTest {
    
    public ModalidadDAOTest() {
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
     * Test of obtenerModalidades method, of class ModalidadDAO.
     */
    @Test
    public void testObtenerModalidadesExitoso() throws Exception {
        System.out.println("obtenerModalidades exitoso");
        
        ObservableList<Modalidad> expResult = FXCollections.observableArrayList();
        expResult.addAll(new Modalidad(1, "Tesis"),
                new Modalidad(2, "Tesina"),
                new Modalidad(3, "Memoria"),
                new Modalidad(4, "Reporte"),
                new Modalidad(5, "Monografía"));
        
        ModalidadDAO modalidadDAO = new ModalidadDAO();
        ObservableList<Modalidad> result = modalidadDAO.obtenerModalidades();
        assertEquals("Prueba no exitosa, no son iguales",result, expResult);
    }
    
    @Test
    public void testObtenerModalidadesFalla() throws Exception {
        System.out.println("obtenerModalidades");
        
        ObservableList<Modalidad> expResult = FXCollections.observableArrayList();
        expResult.addAll(new Modalidad(1, "Tesis"),
                new Modalidad(2, "T"),
                new Modalidad(3, "M"),
                new Modalidad(4, "Monografía"));
        
        ModalidadDAO modalidadDAO = new ModalidadDAO();
        ObservableList<Modalidad> result = modalidadDAO.obtenerModalidades();
        assertNotEquals("Prueba no exitosa, son iguales",result, expResult);
    }
    
}

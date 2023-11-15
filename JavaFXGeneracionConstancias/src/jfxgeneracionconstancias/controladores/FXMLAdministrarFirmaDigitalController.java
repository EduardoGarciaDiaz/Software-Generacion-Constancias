/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package jfxgeneracionconstancias.controladores;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import jfxgeneracionconstancias.JavaFXGeneracionConstancias;
import jfxgeneracionconstancias.modelos.dao.DAOException;
import jfxgeneracionconstancias.modelos.dao.implementaciones.FirmaDAO;
import jfxgeneracionconstancias.modelos.pojo.FirmaDigital;
import jfxgeneracionconstancias.utils.Utilidades;
import jfxgeneracionconstancias.utils.VentanasEmergentes;

/**
 * FXML Controller class
 *
 * @author tristan
 */
public class FXMLAdministrarFirmaDigitalController implements Initializable {

    @FXML
    private TextArea txaFrimaDigitalContenido;
    @FXML
    private Label lblFechaOrigen;
    @FXML
    private Label lblFechaExpiracion;
    @FXML
    private Button btnGenerarFirma;
    @FXML
    private Button btnCerrarVentanaFD;

    private FirmaDigital firmaDigital;
    private LocalDateTime fechaExpiracionFirmaActual;
    private FirmaDAO firmaDao = new FirmaDAO();
    @FXML
    private Label lblAdvetenciaFechaExpirada;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        prepararPantalla();
    } 
    
    private void prepararPantalla(){
        if(recuperarFirmaDigitalActual()){cargarFirmaDigital();}
    }
    
    private void cargarFirmaDigital(){
        txaFrimaDigitalContenido.setText(firmaDigital.getContenidoFirmaDigital());
        lblFechaOrigen.setText(firmaDigital.getFechaOrigen());
        lblFechaExpiracion.setText(calcularFechaExpiracion()); 
        if(firmaEstaExpirada()){
            lblAdvetenciaFechaExpirada.setText("Advetencia: La firma digital actual ya no es valida, porfavor renuevela.");
        }
        else{
            lblAdvetenciaFechaExpirada.setText("");
        }        
    }
    
    private boolean recuperarFirmaDigitalActual(){
        if(comprobarSiExisteFirma()){
            try {            
                firmaDigital = firmaDao.obtenerFirmaDigitalActual();
                return true;                
            } catch (DAOException ex) {
               Utilidades.manejarExcepcion(ex.getCodigo()); 
            }
        } 
        else{
        firmaDigital = new FirmaDigital();        
        }
        return false;
    }
    
    private boolean comprobarSiExisteFirma(){
        try {            
            if(firmaDao.comporbarFirmaDigital()== 1){
                return true;            
            }
            else{
                return false;
            }
        } catch (DAOException ex) {              
           Utilidades.manejarExcepcion(ex.getCodigo()); 
           return false;
        }
    }
    
    private String calcularFechaExpiracion(){
        String fechaSql = firmaDigital.getFechaOrigen();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        LocalDateTime dateTime = LocalDateTime.parse(fechaSql, formatter);
        LocalDateTime fechaResultado = dateTime.plusMonths(3);        
        fechaExpiracionFirmaActual = fechaResultado;
        fechaExpiracionFirmaActual.format(formatter);
        return fechaExpiracionFirmaActual.toString();
    }
    
    private boolean firmaEstaExpirada(){       
        return fechaExpiracionFirmaActual.isBefore(LocalDateTime.now());
    }
    
    private boolean renovarFirma() throws DAOException{
        String firmaNueva = generarFirmaDigital();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        String fechaExpiración = LocalDateTime.now().format(formatter);        
        return firmaDao.actualizarFirmaDigital(firmaNueva, fechaExpiración) != 0;
    }
    
    private boolean registrarFirma() throws DAOException{
        String firmaNueva = generarFirmaDigital();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        String fechaExpiración = LocalDateTime.now().format(formatter); 
        return firmaDao.registrarFirma(firmaNueva, fechaExpiración) != -1;
    }
    
    private String generarFirmaDigital(){                
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!#$%&/()=?¡";
        int longitud = 350;
        Random random = new Random();
        StringBuilder cadenaGenerada = new StringBuilder(longitud);
        for (int i = 0; i < longitud; i++) {           
            int indiceAleatorio = random.nextInt(caracteres.length());
            cadenaGenerada.append(caracteres.charAt(indiceAleatorio));
        }
        return cadenaGenerada.toString();
    }

    @FXML
    private void clicBtnGenerarFirma(ActionEvent event) {
        if(fechaExpiracionFirmaActual != null && !firmaEstaExpirada()){
            if(VentanasEmergentes.mostrarDialogoConfirmacion("Advertencia", "¿Estas seguro que quieres renovar la firma digital?, la firma actual aún es vigente.")){
                 try {
                    if(renovarFirma()){
                        VentanasEmergentes.mostrarDialogoSimple("Infromación","Se ha actualizado la firma digital correctamente.", Alert.AlertType.INFORMATION);                    
                        prepararPantalla();
                    }
                } catch (DAOException ex) {
                    VentanasEmergentes.mostrarDialogoSimple("ERROR","Ocurrio un problema al renovar la firma digital", Alert.AlertType.ERROR);
                    }               
            }       
        }else{
            try {
                if(registrarFirma()){
                    VentanasEmergentes.mostrarDialogoSimple("Infromación","Se ha registrado la firma digital correctamente.", Alert.AlertType.INFORMATION);
                    prepararPantalla();
                }
            } catch (DAOException ex) {
               VentanasEmergentes.mostrarDialogoSimple("ERROR","Ocurrio un problema al crear la firma digital", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void clicBtnCerrarVentanaFD(ActionEvent event) {
             try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXGeneracionConstancias.class.getResource("vistas/FXMLMenuPrincipal.fxml"));
            Parent vista = accesoControlador.load();            
            Stage escenario = (Stage) lblAdvetenciaFechaExpirada.getScene().getWindow();
            escenario.setScene(new Scene(vista));
            escenario.centerOnScreen();
            escenario.setTitle("Inicio Sesion");
            escenario.show();            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
}

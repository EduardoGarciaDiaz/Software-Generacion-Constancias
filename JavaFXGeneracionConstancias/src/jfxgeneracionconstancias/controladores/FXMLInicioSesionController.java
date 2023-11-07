package jfxgeneracionconstancias.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import jfxgeneracionconstancias.JavaFXGeneracionConstancias;
import jfxgeneracionconstancias.UsuarioSingleton;
import jfxgeneracionconstancias.modelos.dao.DAOException;
import jfxgeneracionconstancias.modelos.dao.implementaciones.UsuarioDAO;
import jfxgeneracionconstancias.modelos.pojo.Usuario;
import jfxgeneracionconstancias.utils.Codigos;
import jfxgeneracionconstancias.utils.VentanasEmergentes;

public class FXMLInicioSesionController implements Initializable {

    @FXML
    private TextField tfUsuario;
    @FXML
    private PasswordField pfContraseña;
    @FXML
    private Label lbErrorUsuario;
    @FXML
    private Label lbErrorContraseña;
    
    private final String VALOR_FALTANTE = "Campo obligatorio"; 
    private final String USUARIO_INVALIDO = "El campo usuairo solo permite numeros.";
    private Usuario usuarioValido;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    private boolean comprobarCamposVacios(String usuario, String contraseña){
        boolean tieneContenido = false;
        if(usuario.isEmpty() || contraseña.isEmpty()){
           if(usuario.isEmpty())lbErrorUsuario.setText(VALOR_FALTANTE);
           if(contraseña.isEmpty())lbErrorContraseña.setText(VALOR_FALTANTE);       
        }else {
           tieneContenido = true;
           limpiarLbaelsError();
        }
        
        return tieneContenido;
    }
    private int ValidarCampos(String usuario, String Contraseña) {
        int usuarioValido = -1;
        if(usuario.matches("\\d+")){            
            usuarioValido = Integer.parseInt(usuario);
        }
        return usuarioValido;
    }
    
    private boolean ValidarUsuario(int numerpPersonal, String Contraseña) {
        boolean credencialesCorrectas = false;
        Usuario usuarioIngresado;
        UsuarioDAO usuarioDao = new UsuarioDAO();
        try {
            usuarioIngresado = usuarioDao.autenticarUsuario(numerpPersonal, Contraseña);
            if(usuarioIngresado != null) {
                if(usuarioIngresado.getNumeroPersonal() == numerpPersonal && usuarioIngresado.getContraseña().equals(Contraseña)){
                    credencialesCorrectas = true;
                    usuarioValido = usuarioIngresado;
                }
            }            
        } catch (DAOException ex) {
            manejarExcepcion(ex.getCodigo());           
        }
        return credencialesCorrectas;
    }
    
    private void limpiarLbaelsError(){
        lbErrorUsuario.setText(" ");
        lbErrorContraseña.setText(" ");
    }
    
    private void setSingleton(){
        if(usuarioValido != null){
            UsuarioSingleton usuarioSing = UsuarioSingleton.obtenerInstancia( usuarioValido.getNumeroPersonal(), usuarioValido.getNombre(),
                                       usuarioValido.getPrimerApellido(),usuarioValido.getSegundoApellido(),
                                      usuarioValido.isAdministrador(),usuarioValido.getCorreoInstitucional(),
                                         usuarioValido.getTipoUsuario(),usuarioValido.getNombreTipoUsuario(),
                                          usuarioValido.getContraseña());
        }        
    }
    
    private void irMenuPrincipal(){       
          try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXGeneracionConstancias.class.getResource("vistas/FXMLMenuPrincipal.fxml"));
            Parent vista = accesoControlador.load();            
            Stage escenario = (Stage) lbErrorUsuario.getScene().getWindow();
            escenario.setScene(new Scene(vista));
            escenario.setTitle("Inicio Sesion");
            escenario.show();            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private void manejarExcepcion(Codigos codigoError){
        String titulo = "", mensaje = "";
        if(codigoError == Codigos.ERROR_CONSULTA) {
            titulo = "ERROR";
            mensaje = "Hubo un error al realizar la consulta en la Base de Datos";             
        }
        if(codigoError == Codigos.ERROR_CONEXION_BD) {
            titulo = "ERROR";
            mensaje = "No se pudo conectar con la Base de datos";
        }
        VentanasEmergentes.mostrarDialogoSimple(titulo,mensaje, Alert.AlertType.ERROR);
    }

    @FXML
    private void clicBtnIniciarSesion(ActionEvent event) {
        limpiarLbaelsError();
        String usuario = tfUsuario.getText();
        String contraseña = pfContraseña.getText();        
        if(comprobarCamposVacios(usuario, contraseña)){
            int usuarioValidado = ValidarCampos(usuario, contraseña);
            if (usuarioValidado != -1) {
                 if(ValidarUsuario(usuarioValidado, contraseña)){
                     setSingleton();
                     irMenuPrincipal();
                 }
                 else{
                     VentanasEmergentes.mostrarDialogoSimple("Acceso denegado", "Credenciales incorrectas", Alert.AlertType.INFORMATION);
                 }
            }else{
                limpiarLbaelsError();
                lbErrorUsuario.setText(USUARIO_INVALIDO);
            }
        }
    }
    
}

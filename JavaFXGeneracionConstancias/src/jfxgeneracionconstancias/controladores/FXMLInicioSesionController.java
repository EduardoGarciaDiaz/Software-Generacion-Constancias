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
import jfxgeneracionconstancias.utils.Utilidades;
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
    
    private long ValidarCampos(String usuario, String Contraseña) {
        long usuarioValido = -1;
        if(usuario.matches("\\d+")){            
            usuarioValido = Long.valueOf(usuario);
        }
        return usuarioValido;
    }
    
    private boolean ValidarUsuario(long numeroPersonal, String Contraseña) {
        boolean credencialesCorrectas = false;
        Usuario usuarioIngresado;
        UsuarioDAO usuarioDao = new UsuarioDAO();
        try {
            usuarioIngresado = usuarioDao.autenticarUsuario(numeroPersonal, Contraseña);
            if(usuarioIngresado != null) {
                if(usuarioIngresado.getNumeroPersonal() == numeroPersonal && usuarioIngresado.getContraseña().equals(Contraseña)){
                    credencialesCorrectas = true;
                    usuarioValido = usuarioIngresado;
                }
            }            
        } catch (DAOException ex) {
            Utilidades.manejarExcepcion(ex.getCodigo());           
        }
        return credencialesCorrectas;
    }
    
    private void limpiarLbaelsError(){
        lbErrorUsuario.setText(" ");
        lbErrorContraseña.setText(" ");
    }
    
    private void setSingleton(){
        if(usuarioValido != null){
            UsuarioSingleton usuarioSing = UsuarioSingleton.obtenerInstancia();
            usuarioSing.setNumeroPersonal(usuarioValido.getNumeroPersonal());
            usuarioSing.setNombre(usuarioValido.getNombre());
            usuarioSing.setPrimerApellido(usuarioValido.getPrimerApellido());
            usuarioSing.setSegundoApellido(usuarioValido.getSegundoApellido());
            usuarioSing.setEsAdministrador(usuarioValido.isAdministrador());
            usuarioSing.setCorreoInstitucional(usuarioValido.getCorreoInstitucional());
            usuarioSing.setContraseña(usuarioValido.getContraseña());
            usuarioSing.setTipoUsuario(usuarioValido.getTipoUsuario());
            usuarioSing.setNombreTipoUsuario(usuarioValido.getNombreTipoUsuario());
        }        
    }
    
    private void irMenuPrincipal(){       
        try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXGeneracionConstancias.class.getResource("vistas/FXMLMenuPrincipal.fxml"));
            Parent vista = accesoControlador.load();            
            Stage escenario = (Stage) lbErrorUsuario.getScene().getWindow();
            escenario.setScene(new Scene(vista));
            escenario.centerOnScreen();
            escenario.setTitle("Inicio Sesion");
            escenario.show();            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private void irSolicitarConstancia(){       
        try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXGeneracionConstancias.class.getResource("vistas/FXMLSolicitarConstancia.fxml"));
            Parent vista = accesoControlador.load();            
            Stage escenario = (Stage) lbErrorUsuario.getScene().getWindow();
            escenario.setScene(new Scene(vista));
            escenario.centerOnScreen();
            escenario.setTitle("Solicitar constancia");
            escenario.show();            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    
    @FXML
    private void clicBtnIniciarSesion(ActionEvent event) {
        limpiarLbaelsError();
        String usuario = tfUsuario.getText();
        String contraseña = pfContraseña.getText();        
        if(comprobarCamposVacios(usuario, contraseña)){
            long usuarioValidado = ValidarCampos(usuario, contraseña);
            if (usuarioValidado != -1) {
                 if(ValidarUsuario(usuarioValidado, contraseña)){
                    setSingleton();
                    if (UsuarioSingleton.obtenerInstancia().getTipoUsuario() == 1) {
                        irSolicitarConstancia();
                    } else {
                        irMenuPrincipal();
                    }
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

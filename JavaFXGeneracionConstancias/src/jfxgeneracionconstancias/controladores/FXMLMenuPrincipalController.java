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
import javafx.scene.control.Button;
import javafx.stage.Stage;
import jfxgeneracionconstancias.JavaFXGeneracionConstancias;
import jfxgeneracionconstancias.UsuarioSingleton;

/**
 * FXML Controller class
 *
 * @author tristan
 */
public class FXMLMenuPrincipalController implements Initializable {

    @FXML
    private Button btnAdministrarUsuarios;
    @FXML
    private Button btnAdministrarConstancias;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (UsuarioSingleton.obtenerInstancia().isEsAdministrador()) {
            btnAdministrarUsuarios.setVisible(true);
        }
        if (UsuarioSingleton.obtenerInstancia().getTipoUsuario() == 2){
            btnAdministrarConstancias.setVisible(true);
        }
    }    

    @FXML
    private void clicBtnAdministrarUsuarios(ActionEvent event) {
        irAdministrarUsuarios();
    }
    
    private void irAdministrarUsuarios(){       
          try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXGeneracionConstancias.class.getResource("vistas/FXMLAdministrarUsuarios.fxml"));
            Parent vista = accesoControlador.load();            
            Stage escenario = (Stage) btnAdministrarUsuarios.getScene().getWindow();
            escenario.setScene(new Scene(vista));
            escenario.setTitle("Administrar usuarios");
            escenario.centerOnScreen();
            escenario.show();            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void clicBtnAdministrarConstancias(ActionEvent event) {
        irAdministrarConstancias();
    }
    
        private void irAdministrarConstancias(){       
          try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXGeneracionConstancias.class
                    .getResource("vistas/FXMLRegistroInformacionConstancias.fxml"));
            Parent vista = accesoControlador.load();            
            Stage escenario = (Stage) btnAdministrarConstancias.getScene().getWindow();
            escenario.setScene(new Scene(vista));
            escenario.setTitle("Administrar constancias");
            escenario.centerOnScreen();
            escenario.show();            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
          
    }
}

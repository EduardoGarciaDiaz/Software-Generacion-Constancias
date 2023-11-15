package jfxgeneracionconstancias.controladores;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import jfxgeneracionconstancias.JavaFXGeneracionConstancias;
import jfxgeneracionconstancias.utils.TarjetaArchivo;
import jfxgeneracionconstancias.utils.VentanasEmergentes;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

/**
 * FXML Controller class
 *
 * @author tristan
 */
public class FXMLImportarCVSProfesoresController implements Initializable {

    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnCerrarVentana;
    @FXML
    private Button btnCargarArchivo1;
    @FXML
    private Label lblAdvertenciaError;
    @FXML
    private Pane paneMostrarArchivo;
    
    private File archivoSeleccionado;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    
    private boolean comporbarSiHayArchivos(){
        ObservableList<Node> archivosCargados = paneMostrarArchivo.getChildren();
        return !archivosCargados.isEmpty();
    }
    
    private void seleccionarArchivo() throws IOException{
        FileChooser dialogoSeleccionImagen = new FileChooser();
        dialogoSeleccionImagen.setTitle("Selecciona una imagen");
        FileChooser.ExtensionFilter filtroDialogo = 
                new FileChooser.ExtensionFilter("Archivos CSV", "*.CSV");
        dialogoSeleccionImagen.getExtensionFilters().add(filtroDialogo);        
        Stage escenarioBase = (Stage) lblAdvertenciaError.getScene().getWindow();
        archivoSeleccionado = dialogoSeleccionImagen.showOpenDialog(escenarioBase);
        if(comprobarPesoArchivo()){
            mostrarArchivo();
        }        
    }
    
     private boolean comprobarPesoArchivo() throws IOException{
        long pesoMaximo = 1024*5;
        Path pathArchivo = Paths.get(archivoSeleccionado.getAbsolutePath());
        long pesoArchivo = Files.size(pathArchivo);
        if(pesoMaximo >= pesoArchivo){
            return true;
        }
        else{
            return false;
        }
    }
    
    private void mostrarArchivo() throws IOException{
        TarjetaArchivo tarjetaArchivo = new TarjetaArchivo(archivoSeleccionado);
        tarjetaArchivo.getBotonEliminar().setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {              
                      quitarArchivo();                      
                }
            });
        paneMostrarArchivo.getChildren().add(tarjetaArchivo);
    }
    
    private void quitarArchivo(){
        archivoSeleccionado = null;
        paneMostrarArchivo.getChildren().clear();        
    }  
    
    
    
    private boolean comprobarIntegridadArchivo() throws FileNotFoundException, IOException{
        String[] columnasEsperadas = {"Numero Personal, Nombre, 1er Apellido, 2do Apellido, Correo Institucional, Correo Alterno, Grado Estudios, Fecha Nacimiento"};
        String rutaArchivo = archivoSeleccionado.getAbsolutePath();
        CSVParser csvParsedArchivo = CSVParser.parse(new FileReader(rutaArchivo), CSVFormat.DEFAULT.withHeader());
        for(CSVRecord record : csvParsedArchivo){
            for (int i = 0; i < columnasEsperadas.length; i++) {
                    if (!record.isSet(columnasEsperadas[i])) {
                        return false;
                    }
            }
            return true;                    
        }
        return false;
    }
    
    
    @FXML
    private void clicBtnGuardarArchivo(ActionEvent event) {
        if(comporbarSiHayArchivos()){
            try {
                if(comprobarIntegridadArchivo()){
                    
                }
            } catch (IOException ex) {
                VentanasEmergentes.mostrarDialogoSimple("Error", "Ocurrio un error al guardar el archivo, no cumple con el formato de columnas necesarias.", Alert.AlertType.ERROR);
            }
        }
        else{
            lblAdvertenciaError.setText("Debe de haber un archivo .CSV cargado para continuar");
        }
    }

    @FXML
    private void clicBtnCerrarVentana(ActionEvent event) {
        if(VentanasEmergentes.mostrarDialogoConfirmacion("Advertencia", "¿Estas seguro que deseas cancelar la captura de los datos de profesores?, se perderan todos los datos no guardados")){
            try {
                FXMLLoader accesoControlador = new FXMLLoader(JavaFXGeneracionConstancias.class.getResource("vistas/FXMLMenuPrincipal.fxml"));
                Parent vista = accesoControlador.load();            
                Stage escenario = (Stage) lblAdvertenciaError.getScene().getWindow();
                escenario.setScene(new Scene(vista));
                escenario.centerOnScreen();
                escenario.setTitle("Inicio Sesion");
                escenario.show();            
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @FXML
    private void clicBtnCargarArchivo(ActionEvent event) {
        try {
            seleccionarArchivo();
        } catch (IOException ex) {
            VentanasEmergentes.mostrarDialogoSimple("Error", "Ocurrio un error al cargar elarchivo", Alert.AlertType.ERROR);
        }
    }
    
}

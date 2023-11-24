package jfxgeneracionconstancias.controladores;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
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
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import jfxgeneracionconstancias.JavaFXGeneracionConstancias;
import jfxgeneracionconstancias.modelos.dao.DAOException;
import jfxgeneracionconstancias.modelos.dao.implementaciones.ProfesorDAO;
import jfxgeneracionconstancias.modelos.pojo.Profesor;
import jfxgeneracionconstancias.utils.TarjetaArchivo;
import jfxgeneracionconstancias.utils.Utilidades;
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
    @FXML
    private ListView<String> lstviewProfesoresNoGuardados;
    
    private File archivoSeleccionado;
    private ObservableList<Profesor> profesores = FXCollections.observableArrayList();
    private ObservableList<String> profesoresNoEncontrados = FXCollections.observableArrayList();
   

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
    
    private void seleccionarArchivo() throws IOException{
        FileChooser dialogoSeleccionImagen = new FileChooser();
        dialogoSeleccionImagen.setTitle("Selecciona una imagen");
        FileChooser.ExtensionFilter filtroDialogo =  new FileChooser.ExtensionFilter("Archivos CSV", "*.CSV");
        dialogoSeleccionImagen.getExtensionFilters().add(filtroDialogo);        
        Stage escenarioBase = (Stage) lblAdvertenciaError.getScene().getWindow();
        archivoSeleccionado = dialogoSeleccionImagen.showOpenDialog(escenarioBase);
        if(archivoSeleccionado != null){
            if(comprobarPesoArchivo()){
                lblAdvertenciaError.setText(" ");
                mostrarArchivo();
            }  
            else{
                archivoSeleccionado = null;
                lblAdvertenciaError.setText("El archivo no puede pésar mas de "+ 1024*5 + " KB");
            }
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
        String[] columnasEsperadas = {"Numero Personal", "Nombre", "1er Apellido", "2do Apellido", "Correo Institucional", "Correo Alterno", "Grado Estudios", "Fecha Nacimiento"};
        String rutaArchivo = archivoSeleccionado.getAbsolutePath();
        CSVParser csvParsedArchivo = CSVParser.parse(new FileReader(rutaArchivo), CSVFormat.DEFAULT.withHeader());
        List<String> headersName = csvParsedArchivo.getHeaderNames();
        for (int i = 0; i < columnasEsperadas.length; i++) {
                String x =headersName.get(i);
                String y = columnasEsperadas[i];
                if (!x.equals(y)) {
                    return false;
                }
        }
        return true;
    }
    
    private boolean cargarPorfesores() throws DAOException{
        profesoresNoEncontrados.clear();
        profesores.clear(); 
        ProfesorDAO profesorDao = new ProfesorDAO();
        profesores = FXCollections.observableList(profesorDao.obtenerProfesores());
        try  { 
            Reader reader = new FileReader(archivoSeleccionado.getAbsolutePath());
            CSVParser archivoCSVParseado = new CSVParser(reader, CSVFormat.DEFAULT.withHeader());
            String[] encabezados = archivoCSVParseado.getHeaderMap().keySet().toArray(new String[0]);
            for (CSVRecord csvRecord : archivoCSVParseado) { 
                boolean existeCuenta = false;
                for (Profesor profesor : profesores) {                    
                    if(String.valueOf(profesor.getNumeroPersonal()).equals(csvRecord.get("Numero Personal"))){
                        profesor.setNombre(csvRecord.get("Nombre"));
                        profesor.setPrimerApellido(csvRecord.get("1er Apellido"));
                        profesor.setSegundoApellido("2do Apellido");
                        profesor.setCorreoInstitucional("Correo Institucional");
                        profesor.setCorreoAlterno("Correo Alterno");
                        profesor.setGradoEstudios("Grado Estudios");
                        profesor.setFechaNacimiento("Fecha Nacimiento");
                        existeCuenta = true;
                        break;
                    }                    
               }
               if(!existeCuenta){
                   profesoresNoEncontrados.add(csvRecord.get("Numero Personal") + " " + csvRecord.get("Nombre"));
               }
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            VentanasEmergentes.mostrarDialogoSimple("Error", "Ocurrio un error al recuperar la infromacion del archivo", Alert.AlertType.ERROR);
            return false;            
        }
    }
    
    private void guardarProfesores() throws DAOException{
        ProfesorDAO profesorDao = new ProfesorDAO();
        for (Profesor profesore : profesores) {
           long resultado = profesorDao.editarProfesor(profesore);
           if(resultado == -1){
               profesoresNoEncontrados.add(String.valueOf(profesore.getNumeroPersonal()) + " " + profesore.getNombre());
           }
        }       
    }   
    
    private void mostrarPorfesoresSinRegistrar(){   
        lblAdvertenciaError.setText("Estos profesores no se guardaron, revise los numeros de Personal o verifique que haya una cuenta existente");
        if (!profesoresNoEncontrados.isEmpty()) {
            lstviewProfesoresNoGuardados.setDisable(false);
            lstviewProfesoresNoGuardados.setItems(profesoresNoEncontrados);
        }else{
           lstviewProfesoresNoGuardados.setDisable(true);   
        }               
    }
    
     private boolean comporbarSiHayArchivos(){
        ObservableList<Node> archivosCargados = paneMostrarArchivo.getChildren();
        return !archivosCargados.isEmpty();
    }
     
    @FXML
    private void clicBtnGuardarArchivo(ActionEvent event) {
        if(comporbarSiHayArchivos()){
            try {
                lblAdvertenciaError.setText(" ");
                if(comprobarIntegridadArchivo()){
                    if(cargarPorfesores()){
                        lblAdvertenciaError.setText(" ");
                        guardarProfesores();
                        VentanasEmergentes.mostrarDialogoSimple("Exito", "Se guardadron los profesores correctamente", Alert.AlertType.INFORMATION);                        
                        mostrarPorfesoresSinRegistrar();
                    }
                    else{
                        lblAdvertenciaError.setText("Ocurrio un error al recuperar la infomación del archivo");
                    }
                }
                else{
                   lblAdvertenciaError.setText("El archivo no cumple con el formato de columnas necesarias."); 
                }
            } catch (DAOException ex) {
                Utilidades.manejarExcepcion(ex.getCodigo());
            } catch (IOException ex) {
                ex.printStackTrace();
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
        if(paneMostrarArchivo.getChildren().isEmpty()){
            try {
                lblAdvertenciaError.setText(" ");
                seleccionarArchivo();
            } catch (IOException ex) {
                ex.printStackTrace();
                VentanasEmergentes.mostrarDialogoSimple("Error", "Ocurrio un error al cargar elarchivo", Alert.AlertType.ERROR);
            }
        }
        else{
            lblAdvertenciaError.setText("Ya existe un archivo CSV cargado");
        }
        
    }
    
}

package jfxgeneracionconstancias.controladores;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import jfxgeneracionconstancias.JavaFXGeneracionConstancias;
import jfxgeneracionconstancias.UsuarioSingleton;
import jfxgeneracionconstancias.modelos.dao.DAOException;
import jfxgeneracionconstancias.modelos.dao.implementaciones.ExperienciaEducativaDAO;
import jfxgeneracionconstancias.modelos.dao.implementaciones.ProfesorDAO;
import jfxgeneracionconstancias.modelos.dao.implementaciones.ProgramaEducativoDAO;
import jfxgeneracionconstancias.modelos.dao.implementaciones.UsuarioDAO;
import jfxgeneracionconstancias.modelos.pojo.ExperienciaEducativa;
import jfxgeneracionconstancias.modelos.pojo.Profesor;
import jfxgeneracionconstancias.modelos.pojo.ProgramaEducativo;
import jfxgeneracionconstancias.utils.Utilidades;
import jfxgeneracionconstancias.modelos.pojo.Usuario;
import jfxgeneracionconstancias.utils.VentanasEmergentes;

/**
 * FXML Controller class
 *
 * @author egd1703
 */
public class FXMLRegistroInformacionConstanciasController implements Initializable {

    private int idProfesor = -1;
    private ObservableList<String> gradosEstudio;
    private static String GRADO_ESTUDIO_UNO = "Licenciatura";
    private static String GRADO_ESTUDIO_DOS = "Maestría";
    private static String GRADO_ESTUDIO_TRES = "Doctorado";
    
    private ObservableList<ExperienciaEducativa> experienciasEducativas;
    private ObservableList<ProgramaEducativo> programasEducativos;

    
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfPrimerApellido;
    @FXML
    private TextField tfSegundoApellido;
    @FXML
    private TextField tfCorreoInstitucional;
    @FXML
    private TextField tfCorreoAlterno;
    @FXML
    private ComboBox<String> cbGradoEstudios;
    @FXML
    private DatePicker dpFechaNacimiento;
    @FXML
    private Label lbAdvertenciaNombre;
    @FXML
    private TextField tfBuscarProfesor;
    @FXML
    private Label lbNumeroPersonal;
    @FXML
    private Label lbAdvertenciaCorreoInstitucional;
    @FXML
    private Label lbAdvertenciaCorreoAlterno;
    @FXML
    private Label lbAdvertenciaNumeroPersonal;
    @FXML
    private Label lbAdvertenciaPrimerApellido;
    @FXML
    private Label lbAdvertenciaSegundoApellido;
    @FXML
    private Button btnGuardar;
    @FXML
    private Label lbAdvertenciaNumeroPersonalNoValido;
    @FXML
    private Button btnConstanciaImparticionEE;
    @FXML
    private Button btnConstanciaJurado;
    @FXML
    private Button btnConstanciaProyecto;
    @FXML
    private Pane paneFormularioConstancias;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        llenarComboBoxGradosEstudio();
    }    
    
    private void llenarComboBoxGradosEstudio(){
        gradosEstudio = FXCollections.observableArrayList();
        gradosEstudio.addAll(GRADO_ESTUDIO_UNO, GRADO_ESTUDIO_DOS, GRADO_ESTUDIO_TRES);
        cbGradoEstudios.setItems(gradosEstudio);
    }
    
     @FXML
    private void clicBuscar(MouseEvent event) {
        establecerEstiloNormal();
        buscarProfesor();
        paneFormularioConstancias.getChildren().clear();
        paneFormularioConstancias.setVisible(true);
    }
    
    private void establecerEstiloNormal() {
        lbAdvertenciaCorreoAlterno.setVisible(false);
        lbAdvertenciaCorreoInstitucional.setVisible(false);
        lbAdvertenciaNombre.setVisible(false);
        lbAdvertenciaNumeroPersonal.setVisible(false);
        lbAdvertenciaPrimerApellido.setVisible(false);
        lbAdvertenciaSegundoApellido.setVisible(false);
        lbAdvertenciaNumeroPersonalNoValido.setVisible(false);
    }
    
    private void buscarProfesor() {
        String textoABuscar = tfBuscarProfesor.getText().trim();
        if (Utilidades.numeroPersonalValido(textoABuscar)){
                Long numeroPersonal = Long.valueOf(textoABuscar);
                Profesor profesorEncontrado = obtenerProfesor(numeroPersonal);
            if (profesorEncontrado != null) {
                habilitarBotonesPrincipales();
                cargarDatosUsuario(profesorEncontrado);
            } else {
                lbAdvertenciaNumeroPersonal.setVisible(true);
                ocultarPaneConstancias();
            }
        } else {
            lbAdvertenciaNumeroPersonalNoValido.setVisible(true);
        } 
    }
    
    private void habilitarBotonesPrincipales() {
        btnGuardar.setDisable(false);
        btnConstanciaImparticionEE.setDisable(false);
        btnConstanciaJurado.setDisable(false);
        btnConstanciaProyecto.setDisable(false);
    }
    
    private void ocultarPaneConstancias() {
        paneFormularioConstancias.setVisible(false);
    }
    
    private Profesor obtenerProfesor(long numeroPersonal) {
        ProfesorDAO profesorDAO = new ProfesorDAO();
        Profesor profesor = null;
        try {
            profesor = profesorDAO.obtenerProfesor(numeroPersonal);
        } catch (DAOException ex) {
            Utilidades.manejarExcepcion(ex.getCodigo());
        }
        return profesor;
    }
    
    private void cargarDatosUsuario(Profesor profesorEncontrado) {
        idProfesor = profesorEncontrado.getIdUsuario();
        Long numeroPersonal = profesorEncontrado.getNumeroPersonal();
        String nombre = profesorEncontrado.getNombre();
        String primerApellido = profesorEncontrado.getPrimerApellido();
        String segundoApellido = profesorEncontrado.getSegundoApellido();
        String correoInstitucional = profesorEncontrado.getCorreoInstitucional();
        String fechaNacimiento = profesorEncontrado.getFechaNacimiento();
        String correoAlterno = profesorEncontrado.getCorreoAlterno();
        String gradoEstudios = profesorEncontrado.getGradoEstudios();

        validarDatosCargados(tfNombre, nombre);
        validarDatosCargados(tfPrimerApellido, primerApellido);
        validarDatosCargados(tfSegundoApellido, segundoApellido);
        validarDatosCargados(tfCorreoInstitucional, correoInstitucional);
        validarDatosCargados(tfCorreoAlterno, correoAlterno);
        if (numeroPersonal != null && numeroPersonal > 0) {
            lbNumeroPersonal.setText(numeroPersonal.toString());
        }
        if (gradoEstudios != null && !gradoEstudios.isEmpty()) {
             cbGradoEstudios.getSelectionModel().select(gradoEstudios);       
        }
        if (fechaNacimiento != null && !fechaNacimiento.isEmpty()) {
            dpFechaNacimiento.setValue(LocalDate.parse(fechaNacimiento));
        }
    }
    
    private void validarDatosCargados(TextField tfDatoProfesor, String datoAMostrar){
        if (datoAMostrar != null && !datoAMostrar.isEmpty()){
            tfDatoProfesor.setText(datoAMostrar);
        }
    } 
   
    @FXML
    private void clicGuardar(ActionEvent event) {
        validarCampos();
    }
    
    private void validarCampos() {
        establecerEstiloNormal();
        boolean datosValidos = true;
        
        Long numeroPersonal = Long.valueOf(lbNumeroPersonal.getText().trim());
        String nombre = tfNombre.getText().trim();
        String primerApellido = tfPrimerApellido.getText().trim();
        String segundoApellido = tfSegundoApellido.getText().trim();
        String correoInstitucional = tfCorreoInstitucional.getText().trim();
        String correoAlterno = tfCorreoAlterno.getText().trim();
        LocalDate fechaNacimientoLocalDate = dpFechaNacimiento.getValue();
        String fechaNacimiento = "";
        String gradoEstudio = "";
        int posicionGradoEstudios = cbGradoEstudios.getSelectionModel().getSelectedIndex();

        if (fechaNacimientoLocalDate != null) {
            fechaNacimiento = fechaNacimientoLocalDate.toString();
            // TODO: Validar fecha de nacimiento: Menor a hoy y mayor a 18
            if (fechaNacimiento.isEmpty()){
                datosValidos = false;
            }
        }
        if (!Utilidades.datoPersonalValido(nombre)){
            datosValidos = false;
            lbAdvertenciaNombre.setVisible(true);
        }
        if (!Utilidades.datoPersonalValido(primerApellido)){
            datosValidos = false;
            lbAdvertenciaPrimerApellido.setVisible(true);
        }
        if (!segundoApellido.isEmpty() && !Utilidades.datoPersonalValido(segundoApellido)){
            datosValidos = false;
            lbAdvertenciaSegundoApellido.setVisible(true);
        }
        if (!correoInstitucional.isEmpty() && !Utilidades.correoValido(correoInstitucional)){
            datosValidos = false;
            lbAdvertenciaCorreoInstitucional.setVisible(true);
        }
        if (!correoAlterno.isEmpty() && !Utilidades.correoValido(correoAlterno)){
            datosValidos = false;
            lbAdvertenciaCorreoAlterno.setVisible(true);
        }
        
        if (datosValidos) {
           if (posicionGradoEstudios != -1) {
                gradoEstudio = gradosEstudio.get(posicionGradoEstudios);     
           }
            Usuario profesorValidado = new Profesor(fechaNacimiento, correoAlterno, 
                    gradoEstudio, numeroPersonal,nombre, primerApellido, segundoApellido, correoInstitucional);
            guardarCambios(profesorValidado);
        }
    }
    
    private void guardarCambios(Usuario profesorValidado){
         UsuarioDAO usuarioDAO = new UsuarioDAO();
         ProfesorDAO profesorDAO = new ProfesorDAO();
         try {
            long respuestaUsuario = usuarioDAO.editarUsuarioPorPersonalAdministrativo(profesorValidado);
            long respuestaProfesor = profesorDAO.editarProfesor((Profesor) profesorValidado);
            if (respuestaUsuario > 0 && respuestaProfesor > 0) {
                VentanasEmergentes.mostrarDialogoSimple("Profesor actualizado","Se ha actualizado la información del profesor",
                        Alert.AlertType.INFORMATION);
            }
        } catch (DAOException ex) {
            Utilidades.manejarExcepcion(ex.getCodigo());
        }
    }

    @FXML
    private void clicConstanciaImparticionEE(ActionEvent event) {
        mostrarConstanciaExperienciaEducativaPane();
        // TODO: Ocultar los otros pane
    }

    private void mostrarConstanciaExperienciaEducativaPane() {
        paneFormularioConstancias.getChildren().clear();
        try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXGeneracionConstancias.class.getResource("utils/tarjetas/FXMLConstanciaImparticionEE.fxml"));
            AnchorPane anchorPaneConstanciaImparticionEE = accesoControlador.load();            
            FXMLConstanciaImparticionEEController controladorConstanciaImparticionEE = accesoControlador.getController();
            controladorConstanciaImparticionEE.llenarVentana(idProfesor);
            paneFormularioConstancias.getChildren().add(anchorPaneConstanciaImparticionEE);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void clicConstanciaJurado(ActionEvent event) {
        mostrarConstanciaJuradoPane();
    }
    
    private void mostrarConstanciaJuradoPane(){
        paneFormularioConstancias.getChildren().clear();
         try {
            FXMLLoader accesoControlador = new FXMLLoader(JavaFXGeneracionConstancias.class.getResource("utils/tarjetas/FXMLConstanciaJurado.fxml"));
            AnchorPane anchorPaneConstanciaImparticionEE = accesoControlador.load();            
            FXMLConstanciaJuradoController controladorConstanciaJurado = accesoControlador.getController();
            controladorConstanciaJurado.llenarVentana(idProfesor);
            paneFormularioConstancias.getChildren().add(anchorPaneConstanciaImparticionEE);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void clicConstanciaProyecto(ActionEvent event) {
        mostrarConstanciaProyectoPane();
    }
    
    private void mostrarConstanciaProyectoPane(){
        //TODO
        //paneConstanciaExperienciaEducativa.setVisible(false);
    }



}

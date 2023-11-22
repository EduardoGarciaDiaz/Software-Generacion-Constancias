/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package jfxgeneracionconstancias.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import jfxgeneracionconstancias.modelos.dao.DAOException;
import jfxgeneracionconstancias.modelos.dao.implementaciones.ExperienciaEducativaDAO;
import jfxgeneracionconstancias.modelos.dao.implementaciones.ProgramaEducativoDAO;
import jfxgeneracionconstancias.modelos.pojo.ExperienciaEducativa;
import jfxgeneracionconstancias.modelos.pojo.ProgramaEducativo;
import jfxgeneracionconstancias.utils.Utilidades;
import jfxgeneracionconstancias.utils.VentanasEmergentes;

/**
 * FXML Controller class
 *
 * @author egd17
 */
public class FXMLConstanciaImparticionEEController implements Initializable {

    private ObservableList<ExperienciaEducativa> experienciasEducativas;
    private ObservableList<ProgramaEducativo> programasEducativos;
    
    @FXML
    private TableColumn colNombreEE;
    @FXML
    private TableColumn colBloque;
    @FXML
    private TableColumn colSeccion;
    @FXML
    private TableColumn colCreditos;
    @FXML
    private Pane paneConstanciaExperienciaEducativa;
    @FXML
    private ComboBox<Integer> cbBloque;
    @FXML
    private TextField tfNombreExperienciaEducativa;
    @FXML
    private ComboBox<Integer> cbSeccion;
    @FXML
    private Button btnEliminarExperienciaEducativa;
    @FXML
    private Button btnGuardarExperienciaEducativa;
    @FXML
    private TextField tfCreditos;
    @FXML
    private Label lbAdvertenciaNombreEE;
    @FXML
    private Label lbAdvertenciaBloque;
    @FXML
    private Label lbAdvertenciaSeccion;
    @FXML
    private Label lbAdvertenciaCreditos;
    @FXML
    private TableView<ExperienciaEducativa> tvExperienciasEducativas;
    @FXML
    private TableColumn colProgramaEducativo;
    @FXML
    private ComboBox<ProgramaEducativo> cbProgramaEducativo;
    @FXML
    private Label lbAdvertenciaProgramaEducativo;
    @FXML
    private ComboBox<ExperienciaEducativa> cbExperienciaEducativa;
    
    private int idProfesor;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    public void llenarVentana(int idProfesor) {
        this.idProfesor = idProfesor;
        mostrarConstanciaExperienciaEducativaPane();
    }
    
    private void mostrarConstanciaExperienciaEducativaPane(){
        //TODO: setVisible(false) los otros pane's
        paneConstanciaExperienciaEducativa.setVisible(true);
        if (idProfesor > 0) {
            configurarTablaExperienciaEducativa();
            cargarInformacionTablaExperienciaEducativa();
            llenarComboBoxesConstanciaExperienciaEducativa();
        }
    }
    
    private void configurarTablaExperienciaEducativa(){
        colNombreEE.setCellValueFactory(new PropertyValueFactory("nombre"));
        colBloque.setCellValueFactory(new PropertyValueFactory("bloque"));
        colSeccion.setCellValueFactory(new PropertyValueFactory("seccion"));
        colCreditos.setCellValueFactory(new PropertyValueFactory("creditos")); 
        colProgramaEducativo.setCellValueFactory(new PropertyValueFactory("nombreProgramaEducativo"));
    }
    
    private void cargarInformacionTablaExperienciaEducativa(){
        if (obtenerExperienciasEducativasProfesor()){
            tvExperienciasEducativas.setItems(experienciasEducativas);
        } else {
            System.out.println("No se puedieron recuperar las experiencias educativas");
        }
    }
    
    private boolean obtenerExperienciasEducativasProfesor() {
        boolean experienciasObtenidas = false;
        ExperienciaEducativaDAO experienciaEducativaDAO = new ExperienciaEducativaDAO();
         try {
            experienciasEducativas = experienciaEducativaDAO.obtenerExperienciasEducativasPorIdProfesor(idProfesor);
            if (experienciasEducativas != null || !experienciasEducativas.isEmpty()){
                experienciasObtenidas = true;
            }
        } catch (DAOException ex) {
            Utilidades.manejarExcepcion(ex.getCodigo());
        }
         return experienciasObtenidas;
    }
    
    private void llenarComboBoxesConstanciaExperienciaEducativa(){
        ObservableList<Integer> numeros = FXCollections.observableArrayList();
        numeros.addAll(1, 2);
        cbBloque.setItems(numeros);
        cbSeccion.setItems(numeros);
        llenarComboBoxProgramaEducativo();
        llenarComboBoxExperienciaEducativa();
    }
    
    private void llenarComboBoxProgramaEducativo() {
        ProgramaEducativoDAO programaEducativoDAO = new ProgramaEducativoDAO();
        try {
            programasEducativos = programaEducativoDAO.obtenerProgramasEducativos();
            if (programasEducativos != null) {
                cbProgramaEducativo.setItems(programasEducativos);
            } else {
                System.out.println("Los programas educativos vienen nulos");
            }
        } catch (DAOException ex) {
            Utilidades.manejarExcepcion(ex.getCodigo());
        }
    }
    
    private void llenarComboBoxExperienciaEducativa() {
        ExperienciaEducativaDAO experienciaEducativaDAO = new ExperienciaEducativaDAO();
         try {
            experienciasEducativas = experienciaEducativaDAO.obtenerExperiencasEducativasNoAsignadas(idProfesor);
            if (experienciasEducativas != null || !experienciasEducativas.isEmpty()){
                cbExperienciaEducativa.setItems(experienciasEducativas);
            }
        } catch (DAOException ex) {
            Utilidades.manejarExcepcion(ex.getCodigo());
        }
    }
    
    
    private void establecerEstiloNormalErroresExperienciaEducativa() {
        lbAdvertenciaNombreEE.setVisible(false);
        lbAdvertenciaBloque.setVisible(false);
        lbAdvertenciaSeccion.setVisible(false);
        lbAdvertenciaCreditos.setVisible(false);
        lbAdvertenciaProgramaEducativo.setVisible(false);
    }
    
    private void validarCamposExperienciaEducativa() {
        boolean datosValidos = true;
        String nombreEE = tfNombreExperienciaEducativa.getText().trim();
        int posicionBloque = cbBloque.getSelectionModel().getSelectedIndex();
        int posicionSeccion = cbSeccion.getSelectionModel().getSelectedIndex();
        int posicionProgramaEducativo = cbProgramaEducativo.getSelectionModel().getSelectedIndex();
        String creditosDigitados = tfCreditos.getText().trim();
        int creditos = 0;
        
        
        if (!Utilidades.nombreEEValido(nombreEE)) {
            datosValidos = false;
            lbAdvertenciaNombreEE.setVisible(true);
        }
        if (posicionBloque == -1) {
            datosValidos = false;
            lbAdvertenciaBloque.setVisible(true);
        }
        if (posicionSeccion == -1) {
            datosValidos = false;
            lbAdvertenciaSeccion.setVisible(true);
        }
        if (posicionProgramaEducativo == -1) {
            datosValidos = false;
            lbAdvertenciaProgramaEducativo.setVisible(true);
        }
        if (creditosDigitados == null && creditosDigitados.isEmpty()) {
            datosValidos = false;
            lbAdvertenciaCreditos.setVisible(true);  
        }
        if (!Utilidades.numeroCreditosValido(creditosDigitados)) {
            datosValidos = false;
            lbAdvertenciaCreditos.setVisible(true);  
        }

        if (datosValidos) {
            
            int idPeriodoEscolar = 1;
            
            creditos = Integer.parseInt(creditosDigitados);
            int bloque = cbBloque.getSelectionModel().getSelectedItem();
            int seccion = cbSeccion.getSelectionModel().getSelectedItem();
            ProgramaEducativo programaEducativo = programasEducativos.get(posicionProgramaEducativo);
            int idProgramaEducativo = programaEducativo.getIdProgramaEducativo();
            
            ExperienciaEducativa experienciaEducativa = new ExperienciaEducativa(nombreEE, bloque, seccion, creditos,
                    idProgramaEducativo, idPeriodoEscolar);
            agregarExperienciaEducativa(experienciaEducativa);
        }
    }
    
    private void agregarExperienciaEducativa(ExperienciaEducativa experienciaEducativa) {
        if (!validarExperienciaEducativaExistente(experienciaEducativa)) {
            ExperienciaEducativaDAO experienciaEducativaDAO = new ExperienciaEducativaDAO();
            try {
                int idExperienciaEducativaRegistrada = experienciaEducativaDAO.registrarExperienciaEducativa(experienciaEducativa);
                if (idExperienciaEducativaRegistrada > 0) {
                    asignarExperienciaEducativaAProfesor(idExperienciaEducativaRegistrada);
                }
            } catch (DAOException ex) {
                Utilidades.manejarExcepcion(ex.getCodigo());
            }
        } else {
            VentanasEmergentes.mostrarDialogoSimple("Experiencia Educativa existente",
                    "La experiencia educativa ya está registrada en el sistema, "
                    + "por favor verifica en la lista de Experiencias Educativas y agregala a el profesor", 
                    Alert.AlertType.WARNING);
        }
    }
    
    private void asignarExperienciaEducativaAProfesor(int idExperienciaEducativa) {
        ExperienciaEducativaDAO experienciaEducativaDAO = new ExperienciaEducativaDAO();
        try {
            experienciaEducativaDAO.asignarExperienciaEducativaAProfesor(idExperienciaEducativa, idProfesor);
            limpiarCamposRegistroEE();
            cargarInformacionTablaExperienciaEducativa();
        } catch (DAOException ex) {
            Utilidades.manejarExcepcion(ex.getCodigo());
        }
       
    }
    
    private void limpiarCamposRegistroEE() {
        tfNombreExperienciaEducativa.setText("");
        cbBloque.getSelectionModel().select(-1);
        cbSeccion.getSelectionModel().select(-1);
        tfCreditos.setText("");
        cbProgramaEducativo.getSelectionModel().select(-1);
    }
    
    private boolean validarExperienciaEducativaExistente(ExperienciaEducativa experienciaEducativa) {
        boolean existExperienciaEducativa = false;
        ExperienciaEducativaDAO experienciaEducativaDAO = new ExperienciaEducativaDAO();
        try {
            existExperienciaEducativa = experienciaEducativaDAO.validarExperienciaEducativaExistente(experienciaEducativa);
            return existExperienciaEducativa;
        } catch (DAOException ex) {
            Utilidades.manejarExcepcion(ex.getCodigo());
        }
        return existExperienciaEducativa;
    }
    
    private void desasignarExperienciaEducativaDeProfesor(ExperienciaEducativa experienciaEducativa) {
        ExperienciaEducativaDAO experienciaEducativaDAO = new ExperienciaEducativaDAO();
        if (experienciaEducativa != null){
            boolean borrarRegistro = VentanasEmergentes.mostrarDialogoConfirmacion("Desasignar Experiencia Educativa", 
                    "¿Estás seguro que deseas desasignar la experiencia educativa " 
                            + experienciaEducativa.getNombre() + " del profesor?");
            if (borrarRegistro){
                try {
                    int filasAfectadas = experienciaEducativaDAO.desasignarExperienciaEducativaProfesor(idProfesor,
                        experienciaEducativa.getIdExperienciaEducativa());
                    if (filasAfectadas > 0) {
                        cargarInformacionTablaExperienciaEducativa();
                        llenarComboBoxExperienciaEducativa();
                    }
                } catch (DAOException ex) {
                    Utilidades.manejarExcepcion(ex.getCodigo());
                }
            }
        } else {
            VentanasEmergentes.mostrarDialogoSimple("Selecciona una Experiencia Educativa", "Selecciona el registro en la tabla que deseas desasignar",
                    Alert.AlertType.WARNING);
        }
    }
    
    public Button obtenerBotonEliminarExperienciaEducativa() {
        return btnEliminarExperienciaEducativa;
    }
    
    public Button obtenerBotonGuardarExperienciaEducativa() {
        return btnGuardarExperienciaEducativa;
    }
    
    public ComboBox obtenerComboBoxExperienciaEducativa() {
        return cbExperienciaEducativa;
    }

    @FXML
    private void clicAgregarExperienciaEducativa(ActionEvent event) {
        establecerEstiloNormalErroresExperienciaEducativa();
        validarCamposExperienciaEducativa();
    }

    @FXML
    private void clic_seleccionAsignarExperienciaEducativa(ActionEvent event) {
        ExperienciaEducativa experienciaEducativaSeleccionada = cbExperienciaEducativa.getSelectionModel().getSelectedItem();
        if (experienciaEducativaSeleccionada != null) {
            asignarExperienciaEducativaAProfesor(experienciaEducativaSeleccionada.getIdExperienciaEducativa());
        }
        actualizarComboBoxExperienciasEducativas();
    }
    
    private void actualizarComboBoxExperienciasEducativas() {
        Platform.runLater(() -> {
            llenarComboBoxExperienciaEducativa();
        });
    }
    
    @FXML
    private void clicEliminarExperienciaEducativa(ActionEvent event) {
        ExperienciaEducativa experienciaEducativaSeleccionada = tvExperienciasEducativas.getSelectionModel().getSelectedItem();
        desasignarExperienciaEducativaDeProfesor(experienciaEducativaSeleccionada);    
    }


    
}

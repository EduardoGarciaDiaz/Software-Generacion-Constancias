/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package jfxgeneracionconstancias.controladores;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
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
import javafx.scene.layout.AnchorPane;
import jfxgeneracionconstancias.modelos.dao.DAOException;
import jfxgeneracionconstancias.modelos.dao.implementaciones.ProyectoCampoDAO;
import jfxgeneracionconstancias.modelos.dao.implementaciones.TrabajoRecepcionalDAO;
import jfxgeneracionconstancias.modelos.pojo.Modalidad;
import jfxgeneracionconstancias.modelos.pojo.ProgramaEducativo;
import jfxgeneracionconstancias.modelos.pojo.ProyectoCampo;
import jfxgeneracionconstancias.modelos.pojo.RolJurado;
import jfxgeneracionconstancias.modelos.pojo.TrabajoRecepcional;
import jfxgeneracionconstancias.utils.Utilidades;
import jfxgeneracionconstancias.utils.VentanasEmergentes;

/**
 * FXML Controller class
 *
 * @author egd17
 */
public class FXMLConstanciaProyectoController implements Initializable {

    private int idProfesor;
    private ObservableList<ProyectoCampo> proyectosCampo;

    @FXML
    private TableColumn colAlumnos;
    @FXML
    private Button btnEliminarProyectoCampo;
    @FXML
    private TextField tfNombreProyecto;
    @FXML
    private Button btnGuardarProyectoCampo;
    private Label lbAdvertenciaNombreTR;
    private Label lbAdvertenciaResultadoObtenido;
    private Label lbAdvertenciaFechaPresentación;
    private Label lbAdvertenciaModalidad;
    private Label lbAdvertenciaProgramaEducativo;
    @FXML
    private Label lbAdvertenciaAlumnos;
    private Label lbAdvertenciaRolJurado;
    @FXML
    private TextField tfDuracion;
    @FXML
    private TextField tfLugar;
    @FXML
    private TextField tfAlumnos;
    @FXML
    private TextField tfImpactoObtenido;
    @FXML
    private ComboBox<ProyectoCampo> cbProyectoCampo;
    @FXML
    private TableView<ProyectoCampo> tvProyectosCampo;
    @FXML
    private TableColumn colNombreProyecto;
    @FXML
    private TableColumn colDuracion;
    @FXML
    private TableColumn colImpactoObtenido;
    @FXML
    private TableColumn colLugar;
    @FXML
    private AnchorPane paneConstanciaProyectoCampo;
    @FXML
    private Label lbAdvertenciaNombreProyecto;
    @FXML
    private Label lbAdvertenciaDuracion;
    @FXML
    private Label lbAdvertenciaImpactoObtenido;
    @FXML
    private Label lbAdvertenciaLugar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void llenarVentana(int idProfesor) {
        this.idProfesor = idProfesor;
        mostrarConstanciaProyectoCampoPane();
    }
    
    private void mostrarConstanciaProyectoCampoPane() {
        paneConstanciaProyectoCampo.setVisible(true);
        if (idProfesor > 0) {
            configurarTablaProyectoCampo();
            cargarInformacionTablaProyectoCampo();
            llenarComboBoxesConstanciaProyecto();
        }
    }
    
    private void configurarTablaProyectoCampo(){
        colNombreProyecto.setCellValueFactory(new PropertyValueFactory("nombreProyecto"));
        colDuracion.setCellValueFactory(new PropertyValueFactory("duracion"));
        colImpactoObtenido.setCellValueFactory(new PropertyValueFactory("impactoObtenido"));
        colLugar.setCellValueFactory(new PropertyValueFactory("lugar"));
        colAlumnos.setCellValueFactory(new PropertyValueFactory("alumnos"));
    }
    
    private void cargarInformacionTablaProyectoCampo(){
        if (obtenerProyectosCampoProfesor()){
            tvProyectosCampo.setItems(proyectosCampo);
        } else {
            System.out.println("No se puedieron recuperar los trabajos recepcionales");
        }
    }
    
    private boolean obtenerProyectosCampoProfesor() {
        boolean proyectosObtenidos = false;
        ProyectoCampoDAO proyectoCampoDAO = new ProyectoCampoDAO();
         try {
            proyectosCampo = proyectoCampoDAO.obtenerProyectosCampoPorIdProfesor(idProfesor);
            if (proyectosCampo != null || !proyectosCampo.isEmpty()){
                proyectosObtenidos = true;
            }
        } catch (DAOException ex) {
            Utilidades.manejarExcepcion(ex.getCodigo());
        }
         return proyectosObtenidos;
    }
    
    private void llenarComboBoxesConstanciaProyecto(){        
        llenarComboBoxProyectoCampo();
    }
    
    private void llenarComboBoxProyectoCampo() {
        // TODO
        /*ProyectoCampoDAO proyectoCampoDAO = new ProyectoCampoDAO();
         try {
            //proyectosCampo = proyectoCampoDAO.obtenerProyectosCampoNoAsignados(idProfesor);
            if (proyectosCampo != null || !proyectosCampo.isEmpty()){
                cbProyectoCampo.setItems(proyectosCampo);
            }
        } catch (DAOException ex) {
            Utilidades.manejarExcepcion(ex.getCodigo());
        }*/
    }
    
    private void establecerEstiloNormalErroresProyectoCampo() {
        lbAdvertenciaNombreProyecto.setVisible(false);
        lbAdvertenciaDuracion.setVisible(false);
        lbAdvertenciaImpactoObtenido.setVisible(false);
        lbAdvertenciaLugar.setVisible(false);
        lbAdvertenciaAlumnos.setVisible(false);
    }
    
    private void validarCamposProyectoCampo(){
        boolean datosValidos = true;
        String nombreProyecto = tfNombreProyecto.getText().trim();
        String duracion = tfDuracion.getText().trim();
        String impactoObtenido = tfImpactoObtenido.getText().trim();
        String lugar = tfLugar.getText().trim();
        String alumnos = tfAlumnos.getText().trim();
        
        if (!Utilidades.nombreProyectoValido(nombreProyecto)) {
            datosValidos = false;
            lbAdvertenciaNombreProyecto.setVisible(true);
        }
        if (!Utilidades.duracionProyectoValido(duracion)) {
            datosValidos = false;
            lbAdvertenciaDuracion.setVisible(true);
        }
        if (!Utilidades.impactoObtenidoProyectoValido(impactoObtenido)) {
            datosValidos = false;
            lbAdvertenciaImpactoObtenido.setVisible(true);
        }
        if (!Utilidades.lugarProyectoValido(lugar)) {
            datosValidos = false;
            lbAdvertenciaLugar.setVisible(true);
        }
        if (!Utilidades.alumnosValido(alumnos)) {
            datosValidos = false;
            lbAdvertenciaAlumnos.setVisible(true);
        }
        
        if (datosValidos) {
            ProyectoCampo proyectoCampo = new ProyectoCampo(nombreProyecto, duracion,
                    impactoObtenido, lugar, alumnos, idProfesor);
            agregarProyectoCampo(proyectoCampo);
        }
    }
    
    private void agregarProyectoCampo(ProyectoCampo proyectoCampo) {
        //if (!validarProyectoCampoExistente(proyectoCampo)) {
        ProyectoCampoDAO proyectoCampoDAO = new ProyectoCampoDAO();
            try {
                int idProyectoCampoRegistrado = proyectoCampoDAO.registrarProyectoCampo(proyectoCampo);
                if (idProyectoCampoRegistrado > 0) {
                    limpiarCamposRegistroProyectoCampo();
                    cargarInformacionTablaProyectoCampo();
                }
            } catch (DAOException ex) {
                Utilidades.manejarExcepcion(ex.getCodigo());
            }
        //} else {
            /*
                VentanasEmergentes.mostrarDialogoSimple("Trabajo Recepcional existente",
                    "El Trabajo Recepcional ya está registrado en el sistema, "
                    + "por favor verifica en la lista de Trabajos Recepcionales y agregalo a el profesor", 
                    Alert.AlertType.WARNING);
            */
        //}
    }
    
    private void limpiarCamposRegistroProyectoCampo() {
        tfNombreProyecto.setText("");
        tfDuracion.setText("");
        tfImpactoObtenido.setText("");
        tfLugar.setText("");
        tfAlumnos.setText("");
    }
    
    
    @FXML
    private void clicAgregarProyectoCampo(ActionEvent event) {
        establecerEstiloNormalErroresProyectoCampo();
        validarCamposProyectoCampo();
    }

    @FXML
    private void clicEliminarProyectoCampo(ActionEvent event) {
        ProyectoCampo proyectoCampoSeleccionado = tvProyectosCampo.getSelectionModel().getSelectedItem();
        eliminarProyectoCampo(proyectoCampoSeleccionado);
    }
    
    private void eliminarProyectoCampo(ProyectoCampo proyectoCampoSeleccionado) {
        ProyectoCampoDAO proyectoCampoDAO = new ProyectoCampoDAO();
        if (proyectoCampoSeleccionado != null){
            boolean borrarRegistro = VentanasEmergentes.mostrarDialogoConfirmacion("Eliminar Trabajo Recepcional", 
                    "¿Estás seguro que deseas eliminar el trabajo recepcional " 
                            + proyectoCampoSeleccionado.getNombreProyecto() + "?");
            if (borrarRegistro){
                try {
                    int filasAfectadas = proyectoCampoDAO.eliminarProyectoCampo(
                            proyectoCampoSeleccionado.getIdProyecto(), idProfesor);
                    if (filasAfectadas > 0) {
                        cargarInformacionTablaProyectoCampo();
                        llenarComboBoxProyectoCampo();
                    }
                } catch (DAOException ex) {
                    Utilidades.manejarExcepcion(ex.getCodigo());
                }
            }
        } else {
            VentanasEmergentes.mostrarDialogoSimple("Selecciona un Proyecto de campo", "Selecciona el registro en la tabla que deseas desasignar",
                    Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void clic_seleccionProyectoCampo(ActionEvent event) {
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package jfxgeneracionconstancias.controladores;

import java.net.URL;
import java.time.LocalDate;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import jfxgeneracionconstancias.modelos.dao.DAOException;
import jfxgeneracionconstancias.modelos.dao.implementaciones.ExperienciaEducativaDAO;
import jfxgeneracionconstancias.modelos.dao.implementaciones.ModalidadDAO;
import jfxgeneracionconstancias.modelos.dao.implementaciones.ProgramaEducativoDAO;
import jfxgeneracionconstancias.modelos.dao.implementaciones.RolJuradoDAO;
import jfxgeneracionconstancias.modelos.dao.implementaciones.TrabajoRecepcionalDAO;
import jfxgeneracionconstancias.modelos.pojo.ExperienciaEducativa;
import jfxgeneracionconstancias.modelos.pojo.Modalidad;
import jfxgeneracionconstancias.modelos.pojo.ProgramaEducativo;
import jfxgeneracionconstancias.modelos.pojo.RolJurado;
import jfxgeneracionconstancias.modelos.pojo.TrabajoRecepcional;
import jfxgeneracionconstancias.utils.Utilidades;
import jfxgeneracionconstancias.utils.VentanasEmergentes;

/**
 * FXML Controller class
 *
 * @author egd17
 */
public class FXMLConstanciaJuradoController implements Initializable {
    private int idProfesor;
    
    private ObservableList<TrabajoRecepcional> trabajosRecepcionales;
    private ObservableList<ProgramaEducativo> programasEducativos;
    private ObservableList<Modalidad> modalidades;
    private ObservableList<RolJurado> rolesJurado;
    
    @FXML
    private ComboBox<TrabajoRecepcional> cbTrabajoRecepcional;
    @FXML
    private ComboBox<Modalidad> cbModalidad;
    @FXML
    private TextField tfNombreTrabajoRecepcional;
    @FXML
    private Button btnGuardarTrabajoRecepcional;
    @FXML
    private ComboBox<ProgramaEducativo> cbProgramaEducativo;
    @FXML
    private Label lbAdvertenciaProgramaEducativo;
    @FXML
    private ComboBox<String> cbResultadoObtenido;
    @FXML
    private DatePicker dpFechaPresentacion;
    @FXML
    private TextField tfAlumnos;
    @FXML
    private Button btnEliminarTrabajoRecepcional;
    @FXML
    private TableColumn colNombreTR;
    @FXML
    private TableColumn colResultadoObtenido;
    @FXML
    private TableColumn colFechaPresentacion;
    @FXML
    private TableColumn colModalidad;
    @FXML
    private TableColumn colProgramaEducativo;
    @FXML
    private TableColumn colAlumnos;
    @FXML
    private AnchorPane paneConstanciaJurado;
    @FXML
    private TableView<TrabajoRecepcional> tvTrabajosRecepcionales;
    @FXML
    private Label lbAdvertenciaNombreTR;
    @FXML
    private Label lbAdvertenciaResultadoObtenido;
    @FXML
    private Label lbAdvertenciaFechaPresentación;
    @FXML
    private Label lbAdvertenciaModalidad;
    @FXML
    private Label lbAdvertenciaAlumnos;
    @FXML
    private ComboBox<RolJurado> cbRolJurado;
    @FXML
    private TableColumn colNombreRolJurado;
    @FXML
    private Label lbAdvertenciaRolJurado;
    @FXML
    private ComboBox<RolJurado> cbRolJuradoTR;
   
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void llenarVentana(int idProfesor) {
        this.idProfesor = idProfesor;
        mostrarConstanciaExperienciaEducativaPane();
    }
    
     private void mostrarConstanciaExperienciaEducativaPane(){
        //TODO: setVisible(false) los otros pane's
        paneConstanciaJurado.setVisible(true);
        if (idProfesor > 0) {
            configurarTablaTrabajoRecepcional();
            cargarInformacionTablaTrabajoRecepcional();
            llenarComboBoxesConstanciaJurado();
        }
    }
     
    private void configurarTablaTrabajoRecepcional(){
        colNombreTR.setCellValueFactory(new PropertyValueFactory("tituloTrabajoRecepcional"));
        colResultadoObtenido.setCellValueFactory(new PropertyValueFactory("resultadoObtenido"));
        colFechaPresentacion.setCellValueFactory(new PropertyValueFactory("fechaPresentacion"));
        colModalidad.setCellValueFactory(new PropertyValueFactory("nombreModalidad")); 
        colProgramaEducativo.setCellValueFactory(new PropertyValueFactory("nombreProgramaEducativo"));
        colAlumnos.setCellValueFactory(new PropertyValueFactory("alumnos"));
        colNombreRolJurado.setCellValueFactory(new PropertyValueFactory("nombreRolJurado"));
    }
     
    private void cargarInformacionTablaTrabajoRecepcional(){
        if (obtenerTrabajosRecepcionalesProfesor()){
            tvTrabajosRecepcionales.setItems(trabajosRecepcionales);
        } else {
            System.out.println("No se puedieron recuperar los trabajos recepcionales");
        }
    }
    
    private boolean obtenerTrabajosRecepcionalesProfesor() {
        boolean trabajosObtenidos = false;
        TrabajoRecepcionalDAO trabajoRecepcionalDAO = new TrabajoRecepcionalDAO();
         try {
            trabajosRecepcionales = trabajoRecepcionalDAO.obtenerTrabajosRecepcionalesPorIdProfesor(idProfesor);
            if (trabajosRecepcionales != null || !trabajosRecepcionales.isEmpty()){
                trabajosObtenidos = true;
            }
        } catch (DAOException ex) {
            Utilidades.manejarExcepcion(ex.getCodigo());
        }
         return trabajosObtenidos;
    }
    
    private void llenarComboBoxesConstanciaJurado(){        
        llenarComboBoxResultadoObtenido();
        llenarComboBoxModalidad();
        llenarComboBoxProgramaEducativo();
        llenarComboBoxTrabajoRecepcional();
        llenarComboBoxesRolJurado();
    }
    
    private void llenarComboBoxResultadoObtenido() {
        ObservableList<String> posiblesResultados = FXCollections.observableArrayList();
        //String[] resultados = new String[]{"Aprobado con Mención Honorífica", "Aprobado por Unanimidad","Aprobado por Mayoría", "Suspendido"};
        String[] resultados = new String[]{"Aprobado", "Rechazado", "Pendiente"};
        posiblesResultados.addAll(resultados);
        cbResultadoObtenido.setItems(posiblesResultados);
    }

    private void llenarComboBoxModalidad() {
        ModalidadDAO modalidadDAO = new ModalidadDAO();
        try {
            modalidades = modalidadDAO.obtenerModalidades();
            if (modalidades != null) {
                cbModalidad.setItems(modalidades);
            } else {
                System.out.println("Las modalidades vienen nulas");
            }
        } catch (DAOException ex) {
            Utilidades.manejarExcepcion(ex.getCodigo());
        }
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
    
    private void llenarComboBoxesRolJurado() {
        RolJuradoDAO rolJuradoDAO = new RolJuradoDAO();
        try {
            rolesJurado = rolJuradoDAO.obtenerRolesJurado();
            if (rolesJurado != null) {
                cbRolJurado.setItems(rolesJurado);
                cbRolJuradoTR.setItems(rolesJurado);
            } else {
                System.out.println("Los roles jurados vienen nulos");
            }
        } catch (DAOException ex) {
            Utilidades.manejarExcepcion(ex.getCodigo());
        } 
    }

    private void llenarComboBoxTrabajoRecepcional() {
        TrabajoRecepcionalDAO trabajoRecepcionalDAO = new TrabajoRecepcionalDAO();
         try {
            trabajosRecepcionales = trabajoRecepcionalDAO.obtenerTrabajosRecepcionalesNoAsignados(idProfesor);
            if (trabajosRecepcionales != null || !trabajosRecepcionales.isEmpty()){
                cbTrabajoRecepcional.setItems(trabajosRecepcionales);
            }
        } catch (DAOException ex) {
            Utilidades.manejarExcepcion(ex.getCodigo());
        }
    }
    
    private void establecerEstiloNormalErroresTrabajoRecepcional() {
        lbAdvertenciaNombreTR.setVisible(false);
        lbAdvertenciaResultadoObtenido.setVisible(false);
        lbAdvertenciaFechaPresentación.setVisible(false);
        lbAdvertenciaModalidad.setVisible(false);
        lbAdvertenciaProgramaEducativo.setVisible(false);
        lbAdvertenciaAlumnos.setVisible(false);
        lbAdvertenciaRolJurado.setVisible(false);

    }
    
    private void validarCamposTrabajoRecepcional() {
        boolean datosValidos = true;
        String nombreTR = tfNombreTrabajoRecepcional.getText().trim();
        String alumnos = tfAlumnos.getText().trim();
        String fechaPresentacion = "";
        LocalDate fechaPresentacionLocalDate = dpFechaPresentacion.getValue();
        int posicionResultadoObtenido = cbResultadoObtenido.getSelectionModel().getSelectedIndex();
        int posicionModalidad = cbModalidad.getSelectionModel().getSelectedIndex();
        int posicionProgramaEducativo = cbProgramaEducativo.getSelectionModel().getSelectedIndex();
        int posicionRolJurado = cbRolJurado.getSelectionModel().getSelectedIndex();
        
        if (fechaPresentacionLocalDate != null) {
            fechaPresentacion = fechaPresentacionLocalDate.toString();
            // TODO: Validar fecha de nacimiento: Menor o igual a hoy
            if (fechaPresentacion.isEmpty()){
                datosValidos = false;
                lbAdvertenciaFechaPresentación.setVisible(true);
            }
        } else {
            lbAdvertenciaFechaPresentación.setVisible(true);
        }
        if (!Utilidades.nombreTRValido(nombreTR)) {
            datosValidos = false;
            lbAdvertenciaNombreTR.setVisible(true);
        }
        if (!Utilidades.alumnosValido(alumnos)) {
            datosValidos = false;
            lbAdvertenciaAlumnos.setVisible(true);
        }
        if (posicionResultadoObtenido == -1) {
            datosValidos = false;
            lbAdvertenciaResultadoObtenido.setVisible(true);
        }
        if (posicionModalidad == -1) {
            datosValidos = false;
            lbAdvertenciaModalidad.setVisible(true);
        }
        if (posicionProgramaEducativo == -1) {
            datosValidos = false;
            lbAdvertenciaProgramaEducativo.setVisible(true);
        }
        if (posicionRolJurado == -1) {
             datosValidos = false;
            lbAdvertenciaRolJurado.setVisible(true);
        }
        
        if (datosValidos) {
            String resultadoObtenido = cbResultadoObtenido.getSelectionModel().getSelectedItem();
            Modalidad modalidad = modalidades.get(posicionModalidad);
            RolJurado rolJurado = rolesJurado.get(posicionRolJurado);
            ProgramaEducativo programaEducativo = programasEducativos.get(posicionProgramaEducativo);
            int idProgramaEducativo = programaEducativo.getIdProgramaEducativo();
            int idModalidad = modalidad.getIdModalidad();
            int idRolJurado = rolJurado.getIdRolJurado();
            
            TrabajoRecepcional trabajoRecepcional = new TrabajoRecepcional();
            trabajoRecepcional.setTituloTrabajoRecepcional(nombreTR);
            trabajoRecepcional.setAlumnos(alumnos);
            trabajoRecepcional.setFechaPresentacion(fechaPresentacion);
            trabajoRecepcional.setResultadoObtenido(resultadoObtenido);
            trabajoRecepcional.setIdModalidad(idModalidad);
            trabajoRecepcional.setNombreModalidad(modalidad.getNombreModalidad());
            trabajoRecepcional.setIdProgramaEducativo(idProgramaEducativo);
            trabajoRecepcional.setNombreProgramaEducativo(programaEducativo.getNombreProgramaEducativo());
            trabajoRecepcional.setIdRolJurado(idRolJurado);
            trabajoRecepcional.setNombreRolJurado(rolJurado.getNombreRolJurado());
            
            agregarTrabajoRecepcional(trabajoRecepcional);
        }
    }
    
    private void agregarTrabajoRecepcional(TrabajoRecepcional trabajoRecepcional) {
        if (!validarTrabajoRecepcionalExistente(trabajoRecepcional)) {
            TrabajoRecepcionalDAO trabajoRecepcionalDAO = new TrabajoRecepcionalDAO();
            try {
                int idTrabajoRecepcionalRegistrado = trabajoRecepcionalDAO.registrarTrabajoRecepcional(trabajoRecepcional);
                if (idTrabajoRecepcionalRegistrado > 0) {
                    asignarTrabajoRecepcionalAProfesor(idTrabajoRecepcionalRegistrado,
                            trabajoRecepcional.getIdRolJurado());
                }
            } catch (DAOException ex) {
                Utilidades.manejarExcepcion(ex.getCodigo());
            }
        } else {
            VentanasEmergentes.mostrarDialogoSimple("Trabajo Recepcional existente",
                    "El Trabajo Recepcional ya está registrado en el sistema, "
                    + "por favor verifica en la lista de Trabajos Recepcionales y agregalo a el profesor", 
                    Alert.AlertType.WARNING);
        }
    }
    
    private void asignarTrabajoRecepcionalAProfesor(int idTrabajoRecepcional, int idRolJurado) {
        TrabajoRecepcionalDAO trabajoRecepcionalDAO = new TrabajoRecepcionalDAO();
        try {
            trabajoRecepcionalDAO.asignarTrabajoRecepcionalAProfesor(idTrabajoRecepcional,idRolJurado ,idProfesor);
            limpiarCamposRegistroTR();
            cargarInformacionTablaTrabajoRecepcional();
        } catch (DAOException ex) {
            Utilidades.manejarExcepcion(ex.getCodigo());
        }
    }
    
    private void limpiarCamposRegistroTR() {
        tfNombreTrabajoRecepcional.setText("");
        tfAlumnos.setText("");
        cbResultadoObtenido.getSelectionModel().select(-1);
        cbModalidad.getSelectionModel().select(-1);
        cbProgramaEducativo.getSelectionModel().select(-1);
        cbRolJurado.getSelectionModel().select(-1);
        dpFechaPresentacion.setValue(null);
    }
    
    private boolean validarTrabajoRecepcionalExistente(TrabajoRecepcional trabajoRecepcional) {
        boolean existeTrabajoRecepcional = false;
        TrabajoRecepcionalDAO trabajoRecepcionalDAO = new TrabajoRecepcionalDAO();
        try {
            existeTrabajoRecepcional = trabajoRecepcionalDAO.validarTrabajoRecepcionalExistente(trabajoRecepcional);
            return existeTrabajoRecepcional;
        } catch (DAOException ex) {
            Utilidades.manejarExcepcion(ex.getCodigo());
        }
        return existeTrabajoRecepcional;
    }
    
    @FXML
    private void clicAgregarTrabajoRecepcional(ActionEvent event) {
        establecerEstiloNormalErroresTrabajoRecepcional();
        validarCamposTrabajoRecepcional();
    }


    @FXML
    private void clicEliminarExperienciaEducativa(ActionEvent event) {
        TrabajoRecepcional trabajoRecepcionalSeleccionado = tvTrabajosRecepcionales.getSelectionModel().getSelectedItem();
        desasignarTrabajoRecepcionalDeProfesor(trabajoRecepcionalSeleccionado);  
    }

    private void desasignarTrabajoRecepcionalDeProfesor(TrabajoRecepcional trabajoRecepcional) {
        TrabajoRecepcionalDAO trabajoRecepcionalDAO = new TrabajoRecepcionalDAO();
        if (trabajoRecepcional != null){
            boolean borrarRegistro = VentanasEmergentes.mostrarDialogoConfirmacion("Desasignar Trabajo Recepcional", 
                    "¿Estás seguro que deseas desasignar el trabajo recepcional " 
                            + trabajoRecepcional.getTituloTrabajoRecepcional()+ " del profesor?");
            if (borrarRegistro){
                try {
                    int idRolJurado = obtenerIdRolJuradoPorNombreJurado(trabajoRecepcional.getNombreRolJurado());
                    if (idRolJurado > 0) {
                        int filasAfectadas = trabajoRecepcionalDAO.desasignarTrabajoRecepcionalProfesor(idProfesor,
                        trabajoRecepcional.getIdTrabajoRecepcional(), idRolJurado);
                        if (filasAfectadas > 0) {
                            cargarInformacionTablaTrabajoRecepcional();
                            llenarComboBoxTrabajoRecepcional();
                        }
                    } else {
                        System.out.println("El nombre del rol jurado no está en la BD");
                    }
                } catch (DAOException ex) {
                    Utilidades.manejarExcepcion(ex.getCodigo());
                }
            }
        } else {
            VentanasEmergentes.mostrarDialogoSimple("Selecciona un Trabajo Recepcional", "Selecciona el registro en la tabla que deseas desasignar",
                    Alert.AlertType.WARNING);
        }
    }
    
    private int obtenerIdRolJuradoPorNombreJurado(String nombreRolJurado) {
        RolJuradoDAO rolJuradoDAO = new RolJuradoDAO();
        try {
            return rolJuradoDAO.obtenerIdRolJuradoPorNombre(nombreRolJurado);
        } catch (DAOException ex) {
            Utilidades.manejarExcepcion(ex.getCodigo());
        }
        return -1;
    }
    
    @FXML
    private void clic_seleccionAsignarExperienciaEducativa(ActionEvent event) {
        cbRolJuradoTR.setDisable(false);
    }

    @FXML
    private void clic_seleccionAsignarRolJuradoTR(ActionEvent event) {
        TrabajoRecepcional trabajoRecepcionalSeleccionado = cbTrabajoRecepcional.getSelectionModel().getSelectedItem();
        if (trabajoRecepcionalSeleccionado != null) {
            RolJurado rolJuradoTR = cbRolJuradoTR.getSelectionModel().getSelectedItem();            
            asignarTrabajoRecepcionalAProfesor(trabajoRecepcionalSeleccionado.getIdTrabajoRecepcional(),
                    rolJuradoTR.getIdRolJurado());
        }
        actualizarComboBoxTrabajosRecepcionales();
        reiniciarEstadoComboBoxRolJuradoTR();
    }
    
    private void reiniciarEstadoComboBoxRolJuradoTR() {
        Platform.runLater(() -> {
            cbRolJuradoTR.getSelectionModel().select(-1);
        });
        cbRolJuradoTR.setDisable(true);
    }
    
    private void actualizarComboBoxTrabajosRecepcionales() {
        Platform.runLater(() -> {
            llenarComboBoxTrabajoRecepcional();
        });
    }
    
}

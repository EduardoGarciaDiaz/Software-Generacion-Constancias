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
import javafx.scene.layout.Pane;
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
    private TableView<ExperienciaEducativa> tvExperienciasEducativas;
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
    private Button btnConstanciaImparticionEE;
    @FXML
    private Button btnConstanciaJurado;
    @FXML
    private Button btnConstanciaProyecto;
    @FXML
    private TableColumn colProgramaEducativo;
    @FXML
    private ComboBox<ExperienciaEducativa> cbExperienciaEducativa;
    @FXML
    private ComboBox<ProgramaEducativo> cbProgramaEducativo;
    @FXML
    private Label lbAdvertenciaProgramaEducativo;

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
        
    }
    
    private void mostrarConstanciaExperienciaEducativaPane(){
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
    
    @FXML
    private void clicAgregarExperienciaEducativa(ActionEvent event) {
        establecerEstiloNormalErroresExperienciaEducativa();
        validarCamposExperienciaEducativa();
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
    
     @FXML
    private void clicEliminarExperienciaEducativa(ActionEvent event) {
        ExperienciaEducativa experienciaEducativaSeleccionada = tvExperienciasEducativas.getSelectionModel().getSelectedItem();
        desasignarExperienciaEducativaDeProfesor(experienciaEducativaSeleccionada);
    }
    
    private void desasignarExperienciaEducativaDeProfesor(ExperienciaEducativa experienciaEducativa) {
        ExperienciaEducativaDAO experienciaEducativaDAO = new ExperienciaEducativaDAO();
        if (experienciaEducativa != null){
            boolean borrarRegistro = VentanasEmergentes.mostrarDialogoConfirmacion("Eliminar alumno", 
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
            VentanasEmergentes.mostrarDialogoSimple("Selecciona un alumno", "Selecciona el registro en la tabla que deseas desasignar",
                    Alert.AlertType.WARNING);
        }
    }



    @FXML
    private void clicConstanciaJurado(ActionEvent event) {

    }

    @FXML
    private void clicConstanciaProyecto(ActionEvent event) {
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

}

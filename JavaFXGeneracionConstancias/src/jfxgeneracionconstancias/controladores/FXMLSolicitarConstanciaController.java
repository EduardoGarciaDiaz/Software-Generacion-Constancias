/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package jfxgeneracionconstancias.controladores;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Observable;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import jfxgeneracionconstancias.JavaFXGeneracionConstancias;
import jfxgeneracionconstancias.UsuarioSingleton;
import jfxgeneracionconstancias.modelos.dao.DAOException;
import jfxgeneracionconstancias.modelos.dao.implementaciones.ExperienciaEducativaDAO;
import jfxgeneracionconstancias.modelos.dao.implementaciones.PeriodoEscolarDAO;
import jfxgeneracionconstancias.modelos.dao.implementaciones.ProgramaEducativoDAO;
import jfxgeneracionconstancias.modelos.pojo.ExperienciaEducativa;
import jfxgeneracionconstancias.modelos.pojo.PeriodoEscolar;
import jfxgeneracionconstancias.modelos.pojo.ProgramaEducativo;
import jfxgeneracionconstancias.utils.Utilidades;
import jfxgeneracionconstancias.utils.VentanasEmergentes;

/**
 * FXML Controller class
 *
 * @author Daniel García Arcos
 */
public class FXMLSolicitarConstanciaController implements Initializable {

    private ObservableList<ExperienciaEducativa> experienciasEducativas;
    private ObservableList<ProgramaEducativo> programasEducativos;
    private ObservableList<PeriodoEscolar> periodosEscolares;
    private String RUTA;
    
    @FXML
    private ComboBox<String> cmbxTiposConstancia;
    @FXML
    private ComboBox<ProgramaEducativo> cmbxProgramaEducativo;
    @FXML
    private ComboBox<PeriodoEscolar> cmbxPeriodoEscolar;
    @FXML
    private ComboBox<ExperienciaEducativa> cmbxExperienciaEducativa;
    @FXML
    private Pane contenedorConstanciaImparticion;
    @FXML
    private Label lblNombreProfesor;
    @FXML
    private Label lblNombrePeriodo;
    @FXML
    private TableView<ExperienciaEducativa> tblInformacionConstanciaImparticionEE;
    @FXML
    private Label lblFecha;
    @FXML
    private TableColumn<?, ?> colProgramaEducativoEE;
    @FXML
    private TableColumn<?, ?> colExperienciaEducaticaEE;
    @FXML
    private TableColumn<?, ?> colBloqueEE;
    @FXML
    private TableColumn<?, ?> colSeccionEE;
    @FXML
    private TableColumn<?, ?> colCreditosEE;
    @FXML
    private TableColumn<?, ?> colHorasEE;
    @FXML
    private Button btnExport;
    @FXML
    private Button btnGenerarConstancia;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTablaEE();
        cargarTiposConstancias();
        llenarComboBoxPeriodosEscolares();
        agregarListenerProgramaEducativo();
        agregarListenerPeriodoEscolar();
    }    
    
    private void cargarTiposConstancias() {
        ObservableList<String> elementos = FXCollections.observableArrayList("Constancia Imparticion EE");
        cmbxTiposConstancia.setItems(elementos);
        cmbxTiposConstancia.valueProperty().addListener(new ChangeListener<String> () {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue == cmbxTiposConstancia.getItems().get(0)) {
                    cmbxProgramaEducativo.setVisible(true);
                    llenarComboBoxProgramaEducativo();
                }
            }
        });
    }
    
    private void agregarListenerProgramaEducativo() {
        cmbxProgramaEducativo.valueProperty().addListener(new ChangeListener<ProgramaEducativo> () {
            @Override
            public void changed(ObservableValue<? extends ProgramaEducativo> observable, ProgramaEducativo oldValue, ProgramaEducativo newValue) {
                if (newValue != null) {
                    cmbxPeriodoEscolar.setVisible(true);
                }
                
                if (cmbxPeriodoEscolar.getSelectionModel().getSelectedItem()!= null) {
                    llenarComboBoxExperienciasEducativas();
                }
            }
        });
    }
    
    private void agregarListenerPeriodoEscolar() {
        cmbxPeriodoEscolar.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                cmbxExperienciaEducativa.setVisible(true);
                llenarComboBoxExperienciasEducativas();
                btnGenerarConstancia.setDisable(false);
            }
        });
    }
    
    private void llenarComboBoxProgramaEducativo() {
        ProgramaEducativoDAO programaEducativoDAO = new ProgramaEducativoDAO();
        try {
            programasEducativos = programaEducativoDAO.obtenerProgramasEducativos();
            if (programasEducativos != null) {
                cmbxProgramaEducativo.setItems(programasEducativos);
            } else {
                System.out.println("Los programas educativos vienen nulos");
            }
        } catch (DAOException ex) {
            Utilidades.manejarExcepcion(ex.getCodigo());
        }
    }
    
    private void llenarComboBoxPeriodosEscolares() {
        PeriodoEscolarDAO periodoEscolarDAO = new PeriodoEscolarDAO();
        try {
            periodosEscolares = periodoEscolarDAO.obtenerPeriodosEscolares();
            if (periodosEscolares != null) {
                cmbxPeriodoEscolar.setItems(periodosEscolares);
            } else {
                System.out.println("Los programas educativos vienen nulos");
            }
        } catch (DAOException ex) {
            Utilidades.manejarExcepcion(ex.getCodigo());
        }
    }
    
    private void llenarComboBoxExperienciasEducativas() {
        ExperienciaEducativaDAO experienciaEducativaDAO = new ExperienciaEducativaDAO();
        try {
            int idPeriodo = cmbxPeriodoEscolar.getSelectionModel().getSelectedItem().getIdPeriodo();
            int idProgramaEducativo = cmbxProgramaEducativo.getSelectionModel().getSelectedItem().getIdProgramaEducativo();
            long numeroPersonal = UsuarioSingleton.obtenerInstancia().getNumeroPersonal();
            experienciasEducativas = 
                    experienciaEducativaDAO.obtenerExperienciasEducativasPorPeriodoYPrograma(idPeriodo, idProgramaEducativo, numeroPersonal);
            if (experienciasEducativas != null) {
                cmbxExperienciaEducativa.setItems(experienciasEducativas);
            } else {
                System.out.println("Los programas educativos vienen nulos");
            }
        } catch (DAOException ex) {
            Utilidades.manejarExcepcion(ex.getCodigo());
        }
    }

    
    private void crearDocumentoConstanciaImparticion() {
        String nombreProgramaEducativo = cmbxProgramaEducativo.getSelectionModel().getSelectedItem().getNombreProgramaEducativo();
        ExperienciaEducativa experiencia = cmbxExperienciaEducativa.getSelectionModel().getSelectedItem();
        Document document = new Document();
        try {
            PdfWriter writer = PdfWriter.getInstance(document, 
                    new FileOutputStream(RUTA+"/Constancia"+experiencia.getNombre()+".pdf"));
            document.open();
            document.add(new Paragraph("A quien corresponda"));
            document.add(new Paragraph("\n\n"));
            document.add(new Paragraph("​​El que suscribe, Director de la Facultad de Estadística e Informática, de la Universidad Veracruzana "));
            document.add(new Paragraph("\n"));
            Paragraph parrafo = new Paragraph("HACE CONSTAR");
            parrafo.setAlignment(Element.ALIGN_CENTER);
            document.add(parrafo);
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("​​​que el Mtro. " 
                    + UsuarioSingleton.obtenerInstancia().getNombre() + " "
                    + UsuarioSingleton.obtenerInstancia().getPrimerApellido() + " "
                    + UsuarioSingleton.obtenerInstancia().getSegundoApellido()
                    + ", impartió la siguiente experiencia educativa en el periodo " 
                    + cmbxPeriodoEscolar.getSelectionModel().getSelectedItem().getNombre()));
            document.add(new Paragraph("\n"));
            PdfPTable tabla = new PdfPTable(6);
            PdfPCell programaEducativo = new PdfPCell(new Phrase("Programa Educativo"));
            programaEducativo.setHorizontalAlignment(1);
            tabla.addCell(programaEducativo);
            PdfPCell experienciaEducativa = new PdfPCell(new Phrase("Experiencia Educativa"));
            experienciaEducativa.setHorizontalAlignment(1);
            tabla.addCell(experienciaEducativa);
            PdfPCell bloque = new PdfPCell(new Phrase("Bloque"));
            bloque.setHorizontalAlignment(1);
            tabla.addCell(bloque);
            PdfPCell seccion = new PdfPCell(new Phrase("Seccion"));
            seccion.setHorizontalAlignment(1);
            tabla.addCell(seccion);
            PdfPCell creditos = new PdfPCell(new Phrase("Créditos"));
            creditos.setHorizontalAlignment(1);
            tabla.addCell(creditos);
            PdfPCell horas = new PdfPCell(new Phrase("Horas"));
            horas.setHorizontalAlignment(1);
            tabla.addCell(horas);
            PdfPCell nProgramaEducativo = new PdfPCell(new Phrase(nombreProgramaEducativo));
            nProgramaEducativo.setHorizontalAlignment(1);
            tabla.addCell(nProgramaEducativo);
            PdfPCell nombreExperienciaEducativa = new PdfPCell(new Phrase(experiencia.getNombre()));
            nombreExperienciaEducativa.setHorizontalAlignment(1);
            tabla.addCell(nombreExperienciaEducativa);
            PdfPCell nombreBloque = new PdfPCell(new Phrase(String.valueOf(experiencia.getBloque())));
            nombreBloque.setHorizontalAlignment(1);
            tabla.addCell(nombreBloque);
            PdfPCell nombreSeccion = new PdfPCell(new Phrase(String.valueOf(experiencia.getSeccion())));
            nombreSeccion.setHorizontalAlignment(1);
            tabla.addCell(nombreSeccion);
            PdfPCell numeroCreditos = new PdfPCell(new Phrase(String.valueOf(experiencia.getCreditos())));
            numeroCreditos.setHorizontalAlignment(1);
            tabla.addCell(numeroCreditos);
            PdfPCell numeroHoras = new PdfPCell(new Phrase());
            numeroHoras.setHorizontalAlignment(1);
            tabla.addCell(numeroHoras);
            
            document.add(tabla);
            LocalDate currentDate = LocalDate.now();

        // Definir el formato deseado (día/mes/año)
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Formatear la fecha actual en el formato deseado
            String formattedDate = currentDate.format(formatter);
            document.add(new Paragraph("\n\n"));
            document.add(new Paragraph("A petición del interesado y para los fines legales que al mismo convenga, se extiende la presente en la ciudad de Xalapa-Enríquez, Veracruz el dia "
                    + formattedDate));
            document.add(new Paragraph("\n\n"));
            Paragraph parrafoAtentamente = new Paragraph("A t e n t a m e n t e");
            parrafoAtentamente.setAlignment(Element.ALIGN_CENTER);
            document.add(parrafoAtentamente);
            Paragraph firmaUv = new Paragraph("<<Lis de Veracruz: Ciencia, Arte, Luz>>");
            firmaUv.setAlignment(Element.ALIGN_CENTER);
            document.add(firmaUv);
        } catch (DocumentException de) {
            
        } catch (FileNotFoundException ex) {
            
        }
        document.close();
    }
    
    private void configurarTablaEE() {
        colProgramaEducativoEE.setCellValueFactory(new PropertyValueFactory("nombreProgramaEducativo"));
        colExperienciaEducaticaEE.setCellValueFactory(new PropertyValueFactory("nombre"));
        colBloqueEE.setCellValueFactory(new PropertyValueFactory("bloque"));
        colSeccionEE.setCellValueFactory(new PropertyValueFactory("seccion"));
        colCreditosEE.setCellValueFactory(new PropertyValueFactory("creditos"));
    }

    @FXML
    private void clicGenerarConstancia(ActionEvent event) {
        if (cmbxTiposConstancia.getSelectionModel().getSelectedIndex() == 0) {
            if (cmbxExperienciaEducativa.getSelectionModel().getSelectedItem()!= null) {
                tblInformacionConstanciaImparticionEE.getItems().clear();
                lblNombreProfesor.setText(UsuarioSingleton.obtenerInstancia().getNombre() + " "
                        + UsuarioSingleton.obtenerInstancia().getPrimerApellido() + " "
                        + UsuarioSingleton.obtenerInstancia().getSegundoApellido());
                lblNombrePeriodo.setText(cmbxPeriodoEscolar.getSelectionModel().getSelectedItem().getNombre());
                LocalDate currentDate = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String formattedDate = currentDate.format(formatter);
                lblFecha.setText(formattedDate);
                tblInformacionConstanciaImparticionEE.getItems().add(cmbxExperienciaEducativa.getSelectionModel().getSelectedItem());
                contenedorConstanciaImparticion.setVisible(true);
                btnExport.setDisable(false);
            }
        }
    }

    @FXML
    private void clicExportarComoPDF(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Selecciona una carpeta");
        File selectedDirectory = directoryChooser.showDialog(lblFecha.getScene().getWindow());
        if (selectedDirectory!= null) {
            RUTA = selectedDirectory.getAbsolutePath();
            if (cmbxTiposConstancia.getSelectionModel().getSelectedIndex() == 0) {
                crearDocumentoConstanciaImparticion();
            }
            VentanasEmergentes.mostrarDialogoSimple("Constancia generada", "Constancia generada correctamente", Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    private void clicRegresarVentanaAnterior(MouseEvent event) {
        irMenuPrincipal();
    }
    
    private void irMenuPrincipal(){
         if(VentanasEmergentes.mostrarDialogoConfirmacion("Advertencia", "¿Estas seguro que deseas regresar al menu principal?, se perderan todos los datos no guardados")){
            try {
                FXMLLoader accesoControlador = new FXMLLoader(JavaFXGeneracionConstancias.class.getResource("vistas/FXMLMenuPrincipal.fxml"));
                Parent vista = accesoControlador.load();            
                Stage escenario = (Stage) lblFecha.getScene().getWindow();
                escenario.setScene(new Scene(vista));
                escenario.centerOnScreen();
                escenario.setTitle("Inicio Sesion");
                escenario.show();            
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
     
}

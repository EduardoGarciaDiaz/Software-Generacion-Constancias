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
import jfxgeneracionconstancias.modelos.dao.implementaciones.FirmaDAO;
import jfxgeneracionconstancias.modelos.dao.implementaciones.PeriodoEscolarDAO;
import jfxgeneracionconstancias.modelos.dao.implementaciones.ProgramaEducativoDAO;
import jfxgeneracionconstancias.modelos.dao.implementaciones.ProyectoCampoDAO;
import jfxgeneracionconstancias.modelos.dao.implementaciones.TrabajoRecepcionalDAO;
import jfxgeneracionconstancias.modelos.pojo.ExperienciaEducativa;
import jfxgeneracionconstancias.modelos.pojo.FirmaDigital;
import jfxgeneracionconstancias.modelos.pojo.PeriodoEscolar;
import jfxgeneracionconstancias.modelos.pojo.ProgramaEducativo;
import jfxgeneracionconstancias.modelos.pojo.ProyectoCampo;
import jfxgeneracionconstancias.modelos.pojo.TrabajoRecepcional;
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
    private ObservableList<TrabajoRecepcional> trabajosRecepcionales;
    private ObservableList<ProyectoCampo> proyectosCampo;
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
    private FirmaDigital firma;
    @FXML
    private ComboBox<TrabajoRecepcional> cmbxTrabajoRecepcional;
    private Pane contenedorConstanciaImparticion1;
    @FXML
    private Label lblNombreProgramaEducativo;
    @FXML
    private TableColumn<?, ?> colNombreAlumnos;
    @FXML
    private TableColumn<?, ?> colTituloTrabajo;
    @FXML
    private TableColumn<?, ?> colModalidadTrabajo;
    @FXML
    private TableColumn<?, ?> colFechaPresentacion;
    @FXML
    private TableColumn<?, ?> colResultadoObtenido;
    @FXML
    private Label lblFecha1;
    @FXML
    private Label lblNombreRolJurado;
    @FXML
    private TableView<TrabajoRecepcional> tblInformacionConstanciaTrabajoRecepcional;
    @FXML
    private Label lblNombreProfesorTrabajo;
    @FXML
    private Pane contenedorConstanciaTrabajo;
    @FXML
    private ComboBox<ProyectoCampo> cmbxProyectosDeCampo;
    @FXML
    private Pane contenedorConstanciaProyecto;
    @FXML
    private Label lblNombreProfesorProyecto;
    @FXML
    private Label lblNombreProgramaEducativo1;
    @FXML
    private TableView<ProyectoCampo> tblInformacionConstanciaProyecto;
    @FXML
    private TableColumn<?, ?> colNombreProyectoRealizado;
    @FXML
    private TableColumn<?, ?> colDuracion;
    @FXML
    private TableColumn<?, ?> colLugarProyecto;
    @FXML
    private TableColumn<?, ?> colNombreAlumnosProyecto;
    @FXML
    private Label lblFechaProyecto;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        obtenerFirmaElectronica();
        configurarTablaEE();
        configurarTablaProyecto();
        configurarTablaTrabajosRecepcionales();
        cargarTiposConstancias();
        llenarComboBoxPeriodosEscolares();
        agregarListenerProgramaEducativo();
        agregarListenerPeriodoEscolar();
        agregarListenerProyecto();
    }    
    
    private void obtenerFirmaElectronica() {
        FirmaDAO firmaDAO = new FirmaDAO();
        try {
            firma = firmaDAO.obtenerFirmaDigitalActual();
        } catch (DAOException ex) {
            Utilidades.manejarExcepcion(ex.getCodigo());
        }
    }
    
    private void cargarTiposConstancias() {
        ObservableList<String> elementos = FXCollections.observableArrayList("Constancia Imparticion EE", "Constancia Jurado", "Constancia de Proyecto de Campo");
        cmbxTiposConstancia.setItems(elementos);
        cmbxTiposConstancia.valueProperty().addListener(new ChangeListener<String> () {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue == cmbxTiposConstancia.getItems().get(0)) {
                    btnGenerarConstancia.setDisable(true);
                    btnExport.setDisable(true);
                    cmbxTrabajoRecepcional.setVisible(false);
                    cmbxProyectosDeCampo.setVisible(false);
                    cmbxProgramaEducativo.setVisible(true);
                    llenarComboBoxProgramaEducativo();
                }
                if (newValue.equals(cmbxTiposConstancia.getItems().get(1))) {
                    btnGenerarConstancia.setDisable(true);
                    btnExport.setDisable(true);
                    cmbxPeriodoEscolar.setVisible(false);
                    cmbxExperienciaEducativa.setVisible(false);
                    cmbxProyectosDeCampo.setVisible(false);
                    cmbxProgramaEducativo.setVisible(true);
                    llenarComboBoxProgramaEducativo();
                }
                if (newValue.equals(cmbxTiposConstancia.getItems().get(2))) {
                    btnGenerarConstancia.setDisable(true);
                    btnExport.setDisable(true);
                    cmbxPeriodoEscolar.setVisible(false);
                    cmbxProgramaEducativo.setVisible(false);
                    cmbxExperienciaEducativa.setVisible(false);
                    cmbxTrabajoRecepcional.setVisible(false);
                    cmbxProyectosDeCampo.setVisible(true);
                    llenarComboBoxProyectosDeCampo();
                }
            }
        });
    }
    
    private void agregarListenerProgramaEducativo() {
        cmbxProgramaEducativo.valueProperty().addListener(new ChangeListener<ProgramaEducativo> () {
            @Override
            public void changed(ObservableValue<? extends ProgramaEducativo> observable, ProgramaEducativo oldValue, ProgramaEducativo newValue) {
                if (newValue != null) {
                    if (cmbxTiposConstancia.getSelectionModel().getSelectedIndex() == 0) {
                        cmbxPeriodoEscolar.setVisible(true);                        
                    }
                    if (cmbxTiposConstancia.getSelectionModel().getSelectedIndex() == 1) {
                        cmbxTrabajoRecepcional.setVisible(true);
                        llenarComboBoxTrabajosRecepcionales();
                        btnGenerarConstancia.setDisable(false);
                    }
                    if (cmbxPeriodoEscolar.getSelectionModel().getSelectedItem()!= null 
                            && cmbxTiposConstancia.getSelectionModel().getSelectedIndex() == 0) {
                        llenarComboBoxExperienciasEducativas();
                    }
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
    
    private void agregarListenerProyecto() {
        cmbxProyectosDeCampo.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
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
                System.out.println("Las experiencias educativas vienen nulas");
            }
        } catch (DAOException ex) {
            Utilidades.manejarExcepcion(ex.getCodigo());
        }
    }

    private void llenarComboBoxTrabajosRecepcionales() {
        TrabajoRecepcionalDAO trabajoRecepcionalDAO = new TrabajoRecepcionalDAO();
        try {
            int idProgramaEducativo = cmbxProgramaEducativo.getSelectionModel().getSelectedItem().getIdProgramaEducativo();
            long numeroPersonal = UsuarioSingleton.obtenerInstancia().getNumeroPersonal();
            trabajosRecepcionales = 
                    trabajoRecepcionalDAO.obtenerTrabajosRecepcionalesPorProgramaEducativoYProfesor(numeroPersonal, idProgramaEducativo);
            if (trabajosRecepcionales != null) {
                cmbxTrabajoRecepcional.setItems(trabajosRecepcionales);
            } else {
                System.out.println("Los trabajos recepcionales vienen nulos");
            }
        } catch (DAOException ex) {
            Utilidades.manejarExcepcion(ex.getCodigo());
        }
    }
    
    private void llenarComboBoxProyectosDeCampo() {
        ProyectoCampoDAO proyectoCampoDAO = new ProyectoCampoDAO();
        try {
            long numeroPersonal = UsuarioSingleton.obtenerInstancia().getNumeroPersonal();
            proyectosCampo = 
                    proyectoCampoDAO.obtenerProyectosCampoPorNumeroPersonal(numeroPersonal);
            if (proyectosCampo != null) {
                cmbxProyectosDeCampo.setItems(proyectosCampo);
            } else {
                System.out.println("Los proyectos de campo vienen nulos");
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
            document.add(new Paragraph("​​​que el Mtro. (a) " 
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
            Paragraph director = new Paragraph("Dr. Luis Gerardo Montané Jiménez");
            director.setAlignment(Element.ALIGN_CENTER);
            document.add(director);
            String firma = this.firma.getContenidoFirmaDigital();
            Paragraph firmaDigital = new Paragraph(firma);
            firmaDigital.setAlignment(Element.ALIGN_BOTTOM);
            document.add(new Paragraph("\n\n\n"));
            document.add(firmaDigital);
        } catch (DocumentException de) {
            System.err.println(de.getMessage());
        } catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage());
        }
        document.close();
    }
    
    private void crearDocumentoConstanciaTrabajoRecepcional() {
        String nombreProgramaEducativo = cmbxProgramaEducativo.getSelectionModel().getSelectedItem().getNombreProgramaEducativo();
        TrabajoRecepcional trabajoRecepcional = cmbxTrabajoRecepcional.getSelectionModel().getSelectedItem();
        Document document = new Document();
        try {
            PdfWriter writer = PdfWriter.getInstance(document, 
                    new FileOutputStream(RUTA+"/Constancia"+trabajoRecepcional.getTituloTrabajoRecepcional()+".pdf"));
            document.open();
            document.add(new Paragraph("A quien corresponda"));
            document.add(new Paragraph("\n\n"));
            document.add(new Paragraph("​​El que suscribe, Director de la Facultad de Estadística e Informática, de la Universidad Veracruzana "));
            document.add(new Paragraph("\n"));
            Paragraph parrafo = new Paragraph("HACE CONSTAR");
            parrafo.setAlignment(Element.ALIGN_CENTER);
            document.add(parrafo);
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("​​​que el Mtro. (a)" 
                    + UsuarioSingleton.obtenerInstancia().getNombre() + " "
                    + UsuarioSingleton.obtenerInstancia().getPrimerApellido() + " "
                    + UsuarioSingleton.obtenerInstancia().getSegundoApellido()
                    + ", fungió como " 
                    + cmbxTrabajoRecepcional.getSelectionModel().getSelectedItem().getNombreRolJurado()
                    + " en el siguiente trabajo recepcional del programa "
                    + nombreProgramaEducativo
            ));
            document.add(new Paragraph("\n"));
            PdfPTable tabla = new PdfPTable(5);
            PdfPCell alumnos = new PdfPCell(new Phrase("Nombre(s) del (los) alumno(s)"));
            alumnos.setHorizontalAlignment(1);
            tabla.addCell(alumnos);
            PdfPCell tituloTrabajo = new PdfPCell(new Phrase("Titulo del trabajo"));
            tituloTrabajo.setHorizontalAlignment(1);
            tabla.addCell(tituloTrabajo);
            PdfPCell modalidad = new PdfPCell(new Phrase("Modalidad"));
            modalidad.setHorizontalAlignment(1);
            tabla.addCell(modalidad);
            PdfPCell fechaPresentacion = new PdfPCell(new Phrase("Fecha de presentacion"));
            fechaPresentacion.setHorizontalAlignment(1);
            tabla.addCell(fechaPresentacion);
            PdfPCell resultadoObtenido = new PdfPCell(new Phrase("Resultado obtenido en la defensa"));
            resultadoObtenido.setHorizontalAlignment(1);
            tabla.addCell(resultadoObtenido);
            PdfPCell nombresAlumnos = new PdfPCell(new Phrase(trabajoRecepcional.getAlumnos()));
            nombresAlumnos.setHorizontalAlignment(1);
            tabla.addCell(nombresAlumnos);
            PdfPCell titulo = new PdfPCell(new Phrase(trabajoRecepcional.getTituloTrabajoRecepcional()));
            titulo.setHorizontalAlignment(1);
            tabla.addCell(titulo);
            PdfPCell nombreModalidad = new PdfPCell(new Phrase(trabajoRecepcional.getNombreModalidad()));
            nombreModalidad.setHorizontalAlignment(1);
            tabla.addCell(nombreModalidad);
            PdfPCell fechaP = new PdfPCell(new Phrase(trabajoRecepcional.getFechaPresentacion()));
            fechaP.setHorizontalAlignment(1);
            tabla.addCell(fechaP);
            PdfPCell resultadoObtenidoDefensa = new PdfPCell(new Phrase(trabajoRecepcional.getResultadoObtenido()));
            resultadoObtenidoDefensa.setHorizontalAlignment(1);
            tabla.addCell(resultadoObtenidoDefensa);
            
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
            Paragraph director = new Paragraph("Dr. Luis Gerardo Montané Jiménez");
            director.setAlignment(Element.ALIGN_CENTER);
            document.add(director);
            String firma = this.firma.getContenidoFirmaDigital();
            Paragraph firmaDigital = new Paragraph(firma);
            firmaDigital.setAlignment(Element.ALIGN_BOTTOM);
            document.add(new Paragraph("\n\n\n"));
            document.add(firmaDigital);
        } catch (DocumentException de) {
            System.err.println(de.getMessage());
        } catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage());
        }
        document.close();
    }
    
    private void crearDocumentoConstanciaProyecto() {
        ProyectoCampo proyectoCampo = cmbxProyectosDeCampo.getSelectionModel().getSelectedItem();
        Document document = new Document();
        try {
            PdfWriter writer = PdfWriter.getInstance(document, 
                    new FileOutputStream(RUTA+"/Constancia_"+proyectoCampo.getNombreProyecto()+".pdf"));
            document.open();
            document.add(new Paragraph("A quien corresponda"));
            document.add(new Paragraph("\n\n"));
            document.add(new Paragraph("​​El que suscribe, Director de la Facultad de Estadística e Informática, de la Universidad Veracruzana "));
            document.add(new Paragraph("\n"));
            Paragraph parrafo = new Paragraph("HACE CONSTAR");
            parrafo.setAlignment(Element.ALIGN_CENTER);
            document.add(parrafo);
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("​​​que el Mtro. (a)" 
                    + UsuarioSingleton.obtenerInstancia().getNombre() + " "
                    + UsuarioSingleton.obtenerInstancia().getPrimerApellido() + " "
                    + UsuarioSingleton.obtenerInstancia().getSegundoApellido()
                    + " participó en un proyecto con las siguientes características:"
            ));
            document.add(new Paragraph("\n"));
            PdfPTable tabla = new PdfPTable(4);
            PdfPCell proyectoRealizado = new PdfPCell(new Phrase("Proyecto realizado"));
            proyectoRealizado.setHorizontalAlignment(1);
            tabla.addCell(proyectoRealizado);
            PdfPCell duracion = new PdfPCell(new Phrase("Duracion"));
            duracion.setHorizontalAlignment(1);
            tabla.addCell(duracion);
            PdfPCell lugar = new PdfPCell(new Phrase("Lugar donde se desarrollo"));
            lugar.setHorizontalAlignment(1);
            tabla.addCell(lugar);
            PdfPCell nombreAlumnos = new PdfPCell(new Phrase("Nombre de las y los alumnos involucrados"));
            nombreAlumnos.setHorizontalAlignment(1);
            tabla.addCell(nombreAlumnos);
            PdfPCell nombreProyecto = new PdfPCell(new Phrase(proyectoCampo.getNombreProyecto()));
            nombreProyecto.setHorizontalAlignment(1);
            tabla.addCell(nombreProyecto);
            PdfPCell duracionProyecto = new PdfPCell(new Phrase(proyectoCampo.getDuracion()));
            duracionProyecto.setHorizontalAlignment(1);
            tabla.addCell(duracionProyecto);
            PdfPCell lugarDesarrollo = new PdfPCell(new Phrase(proyectoCampo.getLugar()));
            lugarDesarrollo.setHorizontalAlignment(1);
            tabla.addCell(lugarDesarrollo);
            PdfPCell nombreAlumnosProyecto = new PdfPCell(new Phrase(proyectoCampo.getAlumnos()));
            nombreAlumnosProyecto.setHorizontalAlignment(1);
            tabla.addCell(nombreAlumnosProyecto);
            
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
            Paragraph director = new Paragraph("Dr. Luis Gerardo Montané Jiménez");
            director.setAlignment(Element.ALIGN_CENTER);
            document.add(director);
            String firma = this.firma.getContenidoFirmaDigital();
            Paragraph firmaDigital = new Paragraph(firma);
            firmaDigital.setAlignment(Element.ALIGN_BOTTOM);
            document.add(new Paragraph("\n\n\n"));
            document.add(firmaDigital);
        } catch (DocumentException de) {
            System.err.println(de.getMessage());
        } catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage());
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
    
    private void configurarTablaTrabajosRecepcionales() {
        colNombreAlumnos.setCellValueFactory(new PropertyValueFactory("alumnos"));
        colTituloTrabajo.setCellValueFactory(new PropertyValueFactory("tituloTrabajoRecepcional"));
        colModalidadTrabajo.setCellValueFactory(new PropertyValueFactory("nombreModalidad"));
        colFechaPresentacion.setCellValueFactory(new PropertyValueFactory("fechaPresentacion"));
        colResultadoObtenido.setCellValueFactory(new PropertyValueFactory("resultadoObtenido"));
    }
    
    private void configurarTablaProyecto() {
        colNombreProyectoRealizado.setCellValueFactory(new PropertyValueFactory("nombreProyecto"));
        colDuracion.setCellValueFactory(new PropertyValueFactory("duracion"));
        colLugarProyecto.setCellValueFactory(new PropertyValueFactory("lugar"));
        colNombreAlumnosProyecto.setCellValueFactory(new PropertyValueFactory("alumnos"));
    }

    @FXML
    private void clicGenerarConstancia(ActionEvent event) {
        contenedorConstanciaImparticion.setVisible(false);
        contenedorConstanciaTrabajo.setVisible(false);
        contenedorConstanciaProyecto.setVisible(false);
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
        if (cmbxTiposConstancia.getSelectionModel().getSelectedIndex()  == 1) {
            if (cmbxTrabajoRecepcional.getSelectionModel().getSelectedItem()!= null) {
                tblInformacionConstanciaTrabajoRecepcional.getItems().clear();
                lblNombreProfesorTrabajo.setText(UsuarioSingleton.obtenerInstancia().getNombre() + " "
                        + UsuarioSingleton.obtenerInstancia().getPrimerApellido() + " "
                        + UsuarioSingleton.obtenerInstancia().getSegundoApellido());
                lblNombreRolJurado.setText(cmbxTrabajoRecepcional.getSelectionModel().getSelectedItem().getNombreRolJurado());
                lblNombreProgramaEducativo.setText(cmbxProgramaEducativo.getSelectionModel().getSelectedItem().getNombreProgramaEducativo());
                LocalDate currentDate = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String formattedDate = currentDate.format(formatter);
                lblFecha1.setText(formattedDate);
                tblInformacionConstanciaTrabajoRecepcional.getItems().add(cmbxTrabajoRecepcional.getSelectionModel().getSelectedItem());
                contenedorConstanciaTrabajo.setVisible(true);
                btnExport.setDisable(false);
            }
        }
        if (cmbxTiposConstancia.getSelectionModel().getSelectedIndex() == 2) {
            if (cmbxProyectosDeCampo.getSelectionModel().getSelectedItem()!= null) {
                tblInformacionConstanciaProyecto.getItems().clear();
                lblNombreProfesorProyecto.setText(UsuarioSingleton.obtenerInstancia().getNombre() + " "
                        + UsuarioSingleton.obtenerInstancia().getPrimerApellido() + " "
                        + UsuarioSingleton.obtenerInstancia().getSegundoApellido());
                LocalDate currentDate = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String formattedDate = currentDate.format(formatter);
                lblFechaProyecto.setText(formattedDate);
                tblInformacionConstanciaProyecto.getItems().add(cmbxProyectosDeCampo.getSelectionModel().getSelectedItem());
                contenedorConstanciaProyecto.setVisible(true);
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
            if (cmbxTiposConstancia.getSelectionModel().getSelectedIndex() == 0
                    && cmbxExperienciaEducativa.getSelectionModel().getSelectedItem() != null) {
                crearDocumentoConstanciaImparticion();
            }
            if (cmbxTiposConstancia.getSelectionModel().getSelectedIndex()== 1
                    && cmbxTrabajoRecepcional.getSelectionModel().getSelectedItem() != null) {
                crearDocumentoConstanciaTrabajoRecepcional();
            }
            if (cmbxTiposConstancia.getSelectionModel().getSelectedIndex()== 2
                    && cmbxProyectosDeCampo.getSelectionModel().getSelectedItem() != null) {
                crearDocumentoConstanciaProyecto();
            }
            VentanasEmergentes.mostrarDialogoSimple("Constancia generada", "Constancia generada correctamente", Alert.AlertType.INFORMATION);
        }
    }

    private void irAVentanaLogin(){
         if(VentanasEmergentes.mostrarDialogoConfirmacion("Advertencia", "¿Estas seguro que deseas salir?")){
            try {
                FXMLLoader accesoControlador = new FXMLLoader(JavaFXGeneracionConstancias.class.getResource("vistas/FXMLInicioSesion.fxml"));
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

    @FXML
    private void clicRegresarAMenuPrincipal(MouseEvent event) {
        irAVentanaLogin();
    }
     
}

package jfxgeneracionconstancias.controladores;

import java.awt.Color;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import jfxgeneracionconstancias.modelos.dao.DAOException;
import jfxgeneracionconstancias.modelos.dao.implementaciones.ProfesorDAO;
import jfxgeneracionconstancias.modelos.dao.implementaciones.UsuarioDAO;
import jfxgeneracionconstancias.modelos.pojo.Profesor;
import jfxgeneracionconstancias.modelos.pojo.Usuario;
import jfxgeneracionconstancias.utils.TarjetaUsuario;
import jfxgeneracionconstancias.utils.Utilidades;
import jfxgeneracionconstancias.utils.VentanasEmergentes;

/**
 * FXML Controller class
 *
 * @author Daniel García Arcos
 */
public class FXMLAdministrarUsuariosController implements Initializable {

    @FXML
    private TextField txfNumeroPersonal;
    @FXML
    private TextField txfNombreUsuario;
    @FXML
    private TextField txfCorreoInstitucional;
    @FXML
    private PasswordField txfContrasena;
    @FXML
    private RadioButton tgPersonalAdministrativo;
    @FXML
    private ToggleGroup tgTipoUsuario;
    @FXML
    private RadioButton tgProfesor;
    @FXML
    private ToggleButton tfEsAdministrador;
    @FXML
    private Label lblAdvertenciaContraseña;
    @FXML
    private Label lblAdvertenciaNumeroPersonal;
    @FXML
    private VBox vbxContenedorUsuarios;
    @FXML
    private TextField txfPrimerApellido;
    @FXML
    private TextField txfSegundoApellido;
    private ObservableList<Usuario> usuarios;
    private boolean esEdicion = false;
    @FXML
    private Button btnLimpiarCancelar;
    private String estiloLimpiar = "-fx-background-color: yellow; -fx-border: 0";
    private String estiloCancelar = "-fx-background-color: red; -fx-border: 0";
    @FXML
    private Button btnGuardarAgregar;
    private Usuario usuarioEdicion = null;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        obtenerListaDeUsuarios();
        cargarListaUsuarios(usuarios);
    }  
    
    private void obtenerListaDeUsuarios() {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        try {
            usuarios = FXCollections.observableArrayList(usuarioDAO.obtenerUsuarios());
        } catch (DAOException ex) {
            Utilidades.manejarExcepcion(ex.getCodigo());
        }
    }
    
    private void cargarListaUsuarios(ObservableList<Usuario> usuarios) {
        vbxContenedorUsuarios.getChildren().clear();
        for (Usuario usuario : usuarios) {
            TarjetaUsuario tarjeta = new TarjetaUsuario(usuario);
            tarjeta.getBotonModificar().setOnAction((event) -> {
                esEdicion = true;
                usuarioEdicion = usuario;
                tgPersonalAdministrativo.setDisable(true);
                tgProfesor.setDisable(true);
                txfNumeroPersonal.setEditable(false);
                btnLimpiarCancelar.setStyle(estiloCancelar);
                btnLimpiarCancelar.setText("Cancelar");
                btnLimpiarCancelar.setTextFill(Paint.valueOf("white"));
                btnGuardarAgregar.setText("Guardar");
                cargarDatosUsuario(usuario);
            });
            vbxContenedorUsuarios.getChildren().add(tarjeta);
        }
    }

    private void cargarDatosUsuario(Usuario usuario) {
        txfNumeroPersonal.setText(String.valueOf(usuario.getNumeroPersonal()));
        txfNombreUsuario.setText(usuario.getNombre());
        txfPrimerApellido.setText(usuario.getPrimerApellido());
        txfSegundoApellido.setText(usuario.getSegundoApellido());
        txfCorreoInstitucional.setText(usuario.getCorreoInstitucional());
        txfContrasena.setText(usuario.getContraseña());
        if (usuario.getTipoUsuario() == 1) {
            tgProfesor.setSelected(true);
        } else {
            tgPersonalAdministrativo.setSelected(true);
        }
        if (usuario.isAdministrador()) {
            tfEsAdministrador.setSelected(true);
        } else {
            tfEsAdministrador.setSelected(false);
        }
        txfNombreUsuario.requestFocus();
    }
    
    @FXML
    private void clicBtnAgregarUsuario(ActionEvent event) {
        limpiarAdvertencias();
        boolean campoContraseñaLLeno = estaCampoContraseñaLleno();
        boolean contraseñaValida = false;
        if (campoContraseñaLLeno) {
            contraseñaValida = comprobarFormatoContrasena();
        } 
        if (esEdicion) {
            asignarDatosUsuario(usuarioEdicion);
            if (actualizarUsuario(usuarioEdicion)) {
                esEdicion = false;
                VentanasEmergentes.mostrarDialogoSimple("Usuario registrado", "Usuario actualizado con exito", Alert.AlertType.INFORMATION);
                limpiarCampos();
                restablecerEstiloAAgregarUsuario();
                obtenerListaDeUsuarios();
                cargarListaUsuarios(usuarios);
            }
        } else {
            boolean numeroPersonalValido = false;
            boolean campoNumeroPersonalLleno = estaCampoNumeroPersonalLLeno();
            if (campoNumeroPersonalLleno) {
                numeroPersonalValido = comprobarFormatoNumeroPersonal();
            }
            if (numeroPersonalValido && contraseñaValida) {
                Usuario usuario = new Usuario();
                long numeroDePersonal = Long.valueOf(txfNumeroPersonal.getText());
                usuario.setNumeroPersonal(numeroDePersonal);
                if (tgPersonalAdministrativo.isSelected()) {
                    usuario.setTipoUsuario(2);
                } else if (tgProfesor.isSelected()) {
                    usuario.setTipoUsuario(1);
                }
                asignarDatosUsuario(usuario);
                if (guardarUsuario(usuario)) {
                    VentanasEmergentes.mostrarDialogoSimple("Usuario registrado", "Usuario registrado con exito", Alert.AlertType.INFORMATION);
                    limpiarCampos();
                    obtenerListaDeUsuarios();
                    cargarListaUsuarios(usuarios);
                }
            }
        }       
    }
    
    private boolean guardarUsuario(Usuario usuario) {
        boolean usuarioGuardado = true;
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        try {
            long respuesta = usuarioDAO.registrarUsuario(usuario);
            if (respuesta != -1 && usuario.getTipoUsuario() == 1) {
                guardarProfesor(usuario);
            }
        } catch (DAOException ex) {
            usuarioGuardado = false;
            Utilidades.manejarExcepcion(ex.getCodigo());
        }
        return usuarioGuardado;
    }
    
    private boolean actualizarUsuario(Usuario usuario) {
        boolean usuarioActualizado = true;
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        try {
            long respuesta = usuarioDAO.editarUsuario(usuario, usuarioEdicion.getNumeroPersonal());
        } catch (DAOException ex) {
            usuarioActualizado = false;
            Utilidades.manejarExcepcion(ex.getCodigo());
        }
        return usuarioActualizado;
    }
    
    private void guardarProfesor(Usuario usuario) throws DAOException {
        Profesor profesor = new Profesor();
        profesor.setNumeroPersonal(usuario.getNumeroPersonal());
        ProfesorDAO profesorDAO = new ProfesorDAO();
        profesorDAO.registrarProfesor(profesor);
    }
    
    private void asignarDatosUsuario(Usuario usuario) {
        usuario.setNombre(txfNombreUsuario.getText());
        usuario.setPrimerApellido(txfPrimerApellido.getText());
        usuario.setSegundoApellido(txfSegundoApellido.getText());
        usuario.setCorreoInstitucional(txfCorreoInstitucional.getText());
        usuario.setContraseña(txfContrasena.getText());
        if (tfEsAdministrador.isSelected()) {
            usuario.setEsAdministrador(true);
        } else {
            usuario.setEsAdministrador(false);
        }
    }

    @FXML
    private void clicBtnLimpiarCampos(ActionEvent event) {
        limpiarCampos();
        if (esEdicion) {
            esEdicion = false;
            restablecerEstiloAAgregarUsuario();
        }
    }
    
    private void restablecerEstiloAAgregarUsuario() {
        txfNumeroPersonal.setEditable(true);
        tgPersonalAdministrativo.setDisable(false);
        tgProfesor.setDisable(false);
        btnGuardarAgregar.setText("Agregar");
        btnLimpiarCancelar.setText("Limpiar");
        btnLimpiarCancelar.setStyle(estiloLimpiar);
        btnLimpiarCancelar.setTextFill(Paint.valueOf("black"));
    }
    
    private void limpiarCampos() {
        txfNumeroPersonal.setText("");
        txfNombreUsuario.setText("");
        txfPrimerApellido.setText("");
        txfSegundoApellido.setText("");
        txfCorreoInstitucional.setText("");
        txfContrasena.setText("");
    }
    
    private void limpiarAdvertencias() {
        lblAdvertenciaContraseña.setText("");
        lblAdvertenciaNumeroPersonal.setText("");
    }
    
    private boolean comprobarFormatoContrasena() {
        boolean formatoValido = true;
        String contrasena = txfContrasena.getText().trim();
        if (!contrasena.matches("^(?=.*[A-Z])(?=.*[\\W_]).{7,16}$")) {
            formatoValido = false;
            lblAdvertenciaContraseña.setText("La contraseña debe estar compuesta por al menos una letra, un caracter especial y entre 7 y 16 caracteres.");
        }
        return formatoValido;
    }
    
    private boolean comprobarFormatoNumeroPersonal() {
        boolean formatoValido = true;
        String noPersonal = txfNumeroPersonal.getText().trim();
        if (!noPersonal.matches("^\\d{10}$")) {
            formatoValido = false;
            lblAdvertenciaNumeroPersonal.setText("El numero de personal debe estar compuesto por 10 numeros.");
        }
        return formatoValido;
    }
    
    private boolean estaCampoNumeroPersonalLLeno() {
        boolean campoLleno = true;
        String noPersonal = txfNumeroPersonal.getText().trim();
        if (noPersonal.isEmpty() || noPersonal == null) {
            campoLleno = false;
            lblAdvertenciaNumeroPersonal.setText("El campo numero de personal es requerido");
        }
        return campoLleno;
    }
    
    private boolean estaCampoContraseñaLleno() {
        boolean campoLLeno = true;
        String contraseña = txfContrasena.getText().trim();
        if (contraseña.isEmpty() || contraseña == null) {
            campoLLeno = false;
            lblAdvertenciaContraseña.setText("El campo contraseña es requerido");
        }
        return campoLLeno;
    }
    
}

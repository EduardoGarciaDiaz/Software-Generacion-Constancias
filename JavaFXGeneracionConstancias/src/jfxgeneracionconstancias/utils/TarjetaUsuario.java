package jfxgeneracionconstancias.utils;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import jfxgeneracionconstancias.modelos.pojo.Usuario;

/**
 *
 * @author Daniel García Arcos
 */
public class TarjetaUsuario extends Pane {
    private long numeroPersonal;
    private Label lblNumeroPersonal;
    private Label nombreCompleto;
    private Label correoInstitucional;
    private Label tipoUsuario;
    private Button btnModificar;
    private Button btnDesactivar;
    private Usuario usuario;
    Font fuenteLabels = new Font(20);
    
    public TarjetaUsuario(Usuario usuario) {
        if (usuario != null) {
            this.usuario = usuario;
            inicializarElementos();
            establecerEstilos();
            getChildren().addAll(lblNumeroPersonal, nombreCompleto, correoInstitucional, tipoUsuario, btnModificar);
        }
    }
    
    public Button getBotonModificar() {
        return btnModificar;
    }
    
    public Button getBotonDesactivar() {
        return btnDesactivar;
    }
    
    private void inicializarElementos() {
        Label titulo = new Label("Datos del usuario");
        titulo.setFont(new Font(24));
        titulo.setLayoutX(20);
        titulo.setLayoutY(5);
        getChildren().add(titulo);
        this.numeroPersonal = usuario.getNumeroPersonal();
        lblNumeroPersonal = new Label(String.valueOf(usuario.getNumeroPersonal()));
        nombreCompleto = new Label(usuario.getNombre() + " " + usuario.getPrimerApellido() + " " + usuario.getSegundoApellido());
        if (usuario.getCorreoInstitucional() != null) {
            correoInstitucional = new Label(usuario.getCorreoInstitucional());
        } else {
            correoInstitucional = new Label("");
        }
        tipoUsuario = new Label(usuario.getNombreTipoUsuario());
        btnModificar = new Button("Modificar");
        btnDesactivar = new Button("Desactivar");
    }
    
    private void establecerEstilos() {
        establecerEstiloTarjeta();
        establecerEstiloNumeroPersonal();
        establecerEstiloNombreCompleto();
        establecerEstiloCorreoInstitucional();
        establecerEstiloTipoUsuario();
        establecerEstiloBotonModificar();
    }
    
    private void establecerEstiloTarjeta() {
         setPrefSize(969, 248);
        setStyle("-fx-background-color: white; -fx-background-radius: 15;");
    }
    
    private void establecerEstiloNumeroPersonal() {
        Label labelNoPersonal = new Label("Numero de personal:");
        labelNoPersonal.setFont(fuenteLabels);
        labelNoPersonal.setLayoutX(25);
        labelNoPersonal.setLayoutY(45);
        getChildren().add(labelNoPersonal);
        lblNumeroPersonal.setFont(fuenteLabels);
        lblNumeroPersonal.setLayoutX(220);
        lblNumeroPersonal.setLayoutY(45);
    }
    
    private void establecerEstiloNombreCompleto() {
        Label labelNombreCompleto = new Label("Nombre completo:");
        labelNombreCompleto.setFont(fuenteLabels);
        labelNombreCompleto.setLayoutX(43);
        labelNombreCompleto.setLayoutY(77);
        getChildren().add(labelNombreCompleto);
        nombreCompleto.setFont(fuenteLabels);
        nombreCompleto.setLayoutX(220);
        nombreCompleto.setLayoutY(77);
    }
    
    private void establecerEstiloCorreoInstitucional() {
        Label labelCorreoInstitucional = new Label("Correo institucional:");
        labelCorreoInstitucional.setFont(fuenteLabels);
        labelCorreoInstitucional.setLayoutX(31);
        labelCorreoInstitucional.setLayoutY(108);
        getChildren().add(labelCorreoInstitucional);
        correoInstitucional.setFont(fuenteLabels);
        correoInstitucional.setLayoutX(220);
        correoInstitucional.setLayoutY(108);
    }
    
    private void establecerEstiloTipoUsuario() {
        Label labelTipoUsuario = new Label("Tipo usuario:");
        labelTipoUsuario.setFont(fuenteLabels);
        labelTipoUsuario.setLayoutX(92);
        labelTipoUsuario.setLayoutY(141);
        getChildren().add(labelTipoUsuario);
        tipoUsuario.setFont(fuenteLabels);
        tipoUsuario.setLayoutX(220);
        tipoUsuario.setLayoutY(141);
    }
    
    private void establecerEstiloBotonModificar() {
        btnModificar.setStyle("-fx-background-color: #C4DAEF; -fx-background-radius: 15;");
        btnModificar.setLayoutX(724);
        btnModificar.setLayoutY(187);
        btnModificar.setPrefWidth(120);
    }
    
    private void establecerEstiloBotonEliminar() {
        btnDesactivar.setStyle("-fx-background-color: #C4DAEF; -fx-background-radius: 15;");
        btnDesactivar.setLayoutX(851);
        btnDesactivar.setLayoutY(187);
        btnDesactivar.setPrefWidth(100);
    }
}

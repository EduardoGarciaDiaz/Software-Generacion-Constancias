/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jfxgeneracionconstancias.utils;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author tristan
 */
public class TarjetaArchivo extends Pane{
    private Label peso = new Label();
    private Label nombre = new Label();
    private ImageView imgEliminar;
    private ImageView imgArchivo;
    private File archivoAMostrar;
    
    public TarjetaArchivo(File archivo) throws IOException {
        if (archivo != null) {
            this.archivoAMostrar = archivo;            
            establecerEstilos();
            getChildren().addAll(imgArchivo, nombre, peso, imgEliminar);
        }
    }
    
    public ImageView getBotonEliminar() {
        return imgEliminar;
    }
    
    private void establecerEstilos() throws IOException {
        establecerEstiloTarjeta();
        establecerEstiloImagenCsv();
        establcerEstiloImagenELiminar();
        establecerEstiloNombre();
        establecerEstiloPeso();
    }
    
    private void establecerEstiloTarjeta() {
        setPrefSize(290, 70);
        setStyle("-fx-background-color: whitesmoke; -fx-background-radius: 15;");
    }
    
    private void establecerEstiloImagenCsv(){
        imgArchivo.prefHeight(70);
        imgArchivo.prefWidth(70);
        imgArchivo = new ImageView(new javafx.scene.image.Image("file:src/JavaFXGeneracionConstancias/recursos/csvImage.jpg"));
        imgArchivo.setX(0);
        imgArchivo.setY(0);
    }
    
    private void establcerEstiloImagenELiminar(){
        imgEliminar.prefHeight(20);
        imgEliminar.prefWidth(20);
        imgEliminar =  new ImageView(new javafx.scene.image.Image("file:src/JavaFXGeneracionConstancias/recursos/CrossImageWhite.jpg"));
        imgEliminar.setY(0);
        imgEliminar.setX(290-20);
    }
    
     private void establecerEstiloNombre(){
        nombre.setPrefSize(200, 30.0);
        nombre.setLayoutX(72);
        nombre.setLayoutY(20);
        nombre.setTextAlignment(TextAlignment.LEFT);
        nombre.setWrapText(true);
        nombre.setFont(new Font(15));
        nombre.setText(archivoAMostrar.getName());
        //peso.setAlignment(Pos.CENTER_LEFT);
    }
    
    private void establecerEstiloPeso() throws IOException {
        peso.setPrefSize(200, 30.0);
        peso.setLayoutX(72);
        peso.setLayoutY(50);
        peso.setTextAlignment(TextAlignment.LEFT);
        peso.setWrapText(true);
        peso.setFont(new Font(15));     
        double pesoEnBytes = Files.size(Paths.get(archivoAMostrar.getAbsolutePath())) / 1024.0;
        peso.setText(String.valueOf(pesoEnBytes) + " KB");
        //peso.setAlignment(Pos.CENTER_LEFT);
    }
    
}

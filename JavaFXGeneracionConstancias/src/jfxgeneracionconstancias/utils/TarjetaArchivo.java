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
    private ImageView imgEliminar ;
    private ImageView imgArchivo ;
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
        imgArchivo = new ImageView(new javafx.scene.image.Image("file:src/jfxgeneracionconstancias/recursos/csvImage.png"));
        imgArchivo.setFitHeight(70);
        imgArchivo.setFitWidth(70);          
        imgArchivo.setLayoutY(1);
        imgArchivo.setLayoutX(1);
    }
    
    private void establcerEstiloImagenELiminar(){
        imgEliminar =  new ImageView(new javafx.scene.image.Image("file:src/jfxgeneracionconstancias/recursos/CrossImageWhite.png"));
        imgEliminar.setFitHeight(20);
        imgEliminar.setFitWidth(20);        
        imgEliminar.setLayoutY(1);
        imgEliminar.setLayoutX(290-20);
    }
    
     private void establecerEstiloNombre(){
        nombre.setPrefSize(200, 30.0);
        nombre.setLayoutX(72);
        nombre.setLayoutY(10);
        nombre.setTextAlignment(TextAlignment.LEFT);
        nombre.setWrapText(true);
        nombre.setFont(new Font(15));
        nombre.setText(archivoAMostrar.getName());
        //peso.setAlignment(Pos.CENTER_LEFT);
    }
    
    private void establecerEstiloPeso() throws IOException {
        peso.setPrefSize(200, 30.0);
        peso.setLayoutX(72);
        peso.setLayoutY(40);
        peso.setTextAlignment(TextAlignment.LEFT);
        peso.setWrapText(true);
        peso.setFont(new Font(15));     
        double pesoEnBytes = Files.size(Paths.get(archivoAMostrar.getAbsolutePath())) / 1024.0;
        peso.setText(String.valueOf(pesoEnBytes) + " KB");
        //peso.setAlignment(Pos.CENTER_LEFT);
    }
    
}

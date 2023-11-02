/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jfxgeneracionconstancias.utils;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author tristan
 */
public class VentanasEmergentes {
    
    
     public static void mostrarDialogoSimple(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alertaSimple = new Alert(tipo);
        alertaSimple.setTitle(titulo);
        alertaSimple.setContentText(mensaje);
        alertaSimple.setHeaderText(null);
        alertaSimple.showAndWait();
    }
    
    public static boolean mostrarDialogoConfirmacion(String titulo, String mensaje){
        ButtonType buttonTypeYes = new ButtonType("Sí");
        ButtonType buttonTypeNo = new ButtonType("No");
        Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        alertaConfirmacion.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
        alertaConfirmacion.setTitle(titulo);
        alertaConfirmacion.setContentText(mensaje);
        alertaConfirmacion.setHeaderText(null);
        Optional<ButtonType> botonSeleccionado = alertaConfirmacion.showAndWait(); 
        return botonSeleccionado.isPresent() && botonSeleccionado.get() == buttonTypeYes;
    }
    
}

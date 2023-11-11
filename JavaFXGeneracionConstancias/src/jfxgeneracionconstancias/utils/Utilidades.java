/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jfxgeneracionconstancias.utils;

import javafx.scene.control.Alert;

/**
 *
 * @author Daniel García Arcos
 */
public class Utilidades {
    
    public static void manejarExcepcion(Codigos codigoError){
        String titulo = "", mensaje = "";
        if(codigoError == Codigos.ERROR_CONSULTA) {
            titulo = "ERROR";
            mensaje = "Hubo un error al realizar la consulta en la Base de Datos";             
        }
        if(codigoError == Codigos.ERROR_CONEXION_BD) {
            titulo = "ERROR";
            mensaje = "No se pudo conectar con la Base de datos";
        }
        VentanasEmergentes.mostrarDialogoSimple(titulo,mensaje, Alert.AlertType.ERROR);
    }
}

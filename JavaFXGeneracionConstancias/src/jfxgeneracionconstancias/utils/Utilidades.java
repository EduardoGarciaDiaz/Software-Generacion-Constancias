/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jfxgeneracionconstancias.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
    
    public static boolean correoValido(String correo) {
        if(correo != null && !correo.isEmpty()) {
            Pattern patronCorreo = Pattern.compile( "([a-z0-9]+(\\.?[a-z0-9])*)+@(([a-z]+)\\.([a-z]+))+");
            //Pasamos la evaluación a un match
            Matcher matchPatron = patronCorreo.matcher(correo); //Prepara la expresión de cual patrón evaluará la cadena
            return matchPatron.find();//Hace la comparación, Si encuentra el patrón, regresa un booleano
        }else{
            return false;
        }
    }
    
    public static boolean datoPersonalValido(String datoPersonal) {
        if (datoPersonal != null && !datoPersonal.isEmpty()){
            Pattern patronDatoPersonal = Pattern.compile("^[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ ]{1,50}$");
            Matcher matchPatron = patronDatoPersonal.matcher(datoPersonal);
            return matchPatron.find();
        } else {
            return false;
        }
    }
    
    public static boolean numeroPersonalValido(String numeroPersonal) {
        if (numeroPersonal != null && !numeroPersonal.isEmpty()){
            Pattern patronNumeroPersonal = Pattern.compile("^\\d{0,15}$");
            Matcher matchPatron = patronNumeroPersonal.matcher(numeroPersonal);
            return matchPatron.find();
        } else {
            return false;
        }
    }
    
    public static boolean numeroCreditosValido(String numero){
        if (numero != null && !numero.isEmpty()){
            Pattern patronNumero = Pattern.compile("^(?:[1-9]|1[0-5])$");
            Matcher matchPatron = patronNumero.matcher(numero);
            return matchPatron.find();
        } else {
            return false;
        }
    }
    
    public static boolean nombreEEValido(String nombreEE) {
        if (nombreEE != null && !nombreEE.isEmpty()){
            Pattern patronNombreEE = Pattern.compile("^[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ ]{1,45}$");
            Matcher matchPatron = patronNombreEE.matcher(nombreEE);
            return matchPatron.find();
        } else {
            return false;
        }
    }
    
}

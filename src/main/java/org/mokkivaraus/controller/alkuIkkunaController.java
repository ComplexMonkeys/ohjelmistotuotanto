package org.mokkivaraus.controller;

import org.mokkivaraus.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;
import java.io.*;

public class alkuIkkunaController {

    @FXML
    private TitledPane alkuIkkuna;

    @FXML
    private Button btAlue;

    @FXML
    private Button btMokit;

    @FXML
    private Button btAsiakas;

    @FXML
    private Button btPalvelu;

    @FXML
    private Button btVaraus;

    @FXML
    private Button btPosti;

    @FXML
    private Button btLasku;

    
    /** 
     * Painike aluehallinnan avaamiselle.
     * 
     * @param event Tapahtuma jolla metodi suoritetaan.
     */
    @FXML
    void btAlueAction(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(Mokinvaraus.class.getResource("alueIkkuna.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Aluehallinta");
            stage.show();
            ((Node) event.getSource()).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    /** 
     * Painike mokki-ikkunan avaamiselle.
     * 
     * @param event Tapahtuma jolla metodi suoritetaan.
     */
    @FXML
    void btMokitAction(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(Mokinvaraus.class.getResource("mokitIkkuna.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Mökkien hallinta");
            stage.show();
            ((Node) event.getSource()).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    /** 
     * Painike asiakas-ikkunan avaamiselle.
     * 
     * @param event Tapahtuma jolla metodi suoritetaan.
     */
    @FXML
    void btAsiakasAction(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(Mokinvaraus.class.getResource("asiakasIkkuna.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Asiakashallinta");
            stage.show();
            ((Node) event.getSource()).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    /** 
     * Painike palvelu-ikkunan avaamiselle.
     * 
     * @param event Tapahtuma jolla metodi suoritetaan.
     */
    @FXML
    void btPalveluAction(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(Mokinvaraus.class.getResource("palveluIkkuna.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Palveluhallinta");
            stage.show();
            ((Node) event.getSource()).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    /** 
     * Painike varaus-ikkunan.
     * 
     * @param event Tapahtuma jolla metodi suoritetaan.
     */
    @FXML
    void btVarausAction(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(Mokinvaraus.class.getResource("varausIkkuna.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Varaushallinta");
            stage.show();
            ((Node) event.getSource()).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    /** 
     * Painike posti-ikkunan avaamiselle
     * 
     * @param event Tapahtuma jolla metodi suoritetaan.
     */
    @FXML
    void btPostiAction(ActionEvent event){
        Parent root;
        try {
            root = FXMLLoader.load(Mokinvaraus.class.getResource("postiIkkuna.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Postitoimipaikat");
            stage.show();
            ((Node) event.getSource()).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    /** 
     * Painike lasku-ikkunan avaamiselle
     * 
     * @param event Tapahtuma jolla metodi suoritetaan.
     */
    @FXML
    void btLaskuAction(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(Mokinvaraus.class.getResource("laskuIkkuna.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Laskutus");
            stage.show();
            ((Node) event.getSource()).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
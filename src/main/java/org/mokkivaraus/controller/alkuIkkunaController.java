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

    // Painiketta painaessa ohjelma hakee seuraavan ikkunan fxml-tiedoston, avaa
    // uuden ikkunan sen pohjalta ja piilottaa nykyisen ikkunan
    // IOException: Jostakin syystä tiedostoa mokitIkkuna.fxml ei löydy. Tarkista
    // tiedostopolut.
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
}
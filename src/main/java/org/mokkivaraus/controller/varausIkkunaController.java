package org.mokkivaraus.controller;

import java.io.IOException;

import org.mokkivaraus.Mokinvaraus;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class varausIkkunaController {

    @FXML
    private Button btLisaa;

    @FXML
    private Button btMuokkaa;

    @FXML
    private Button btPaivita;

    @FXML
    private Button btPaluu;

    @FXML
    private Button btPoista;

    @FXML
    private TableColumn<?, ?> cAloitusPvm;

    @FXML
    private TableColumn<?, ?> cMokkiId;

    @FXML
    private TableColumn<?, ?> cPaatosPvm;

    @FXML
    private TableColumn<?, ?> cVarausId;

    @FXML
    private HBox hbNapit;

    @FXML
    private TableView<?> tvVaraus;

    @FXML
    void btLisaaAction(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(Mokinvaraus.class.getResource("lisaaVarausIkkuna.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Lisää varaus");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btMuokkaAction(ActionEvent event) {

    }

    @FXML
    void btPaivitaAction(ActionEvent event) {

    }

    @FXML
    void btPaluuAction(ActionEvent event) {

    }

    @FXML
    void btPoistaAction(ActionEvent event) {

    }

}
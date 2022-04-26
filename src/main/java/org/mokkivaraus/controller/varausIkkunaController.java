package org.mokkivaraus.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;

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
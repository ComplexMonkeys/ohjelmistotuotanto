package org.mokkivaraus.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class varausIkkunaController {

    @FXML
    private Button btPaluu;

    @FXML
    private Button btVaraus;

    @FXML
    private Button btPaivita;

    @FXML
    private TableColumn<?, ?> cAlue;

    @FXML
    private TableColumn<?, ?> cHenkilomaara;

    @FXML
    private TableColumn<?, ?> cMokkiId;

    @FXML
    private TableColumn<?, ?> cMokkiNimi;

    @FXML
    private DatePicker dpAloitus;

    @FXML
    private DatePicker dpLopetus;

    @FXML
    private TableView<?> tvMokit;

    @FXML
    void btPaluuAction(ActionEvent event) {

    }

    @FXML
    void btVarausAction(ActionEvent event) {

    }

    @FXML
    void btPaivitaAction(ActionEvent event) {
        
    }

    @FXML
    void dpAloitusAction(ActionEvent event) {

    }

    @FXML
    void dpLopetusAction(ActionEvent event) {

    }

}

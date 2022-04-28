package org.mokkivaraus.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class muokkaaVarausIkkunaController {

    @FXML
    private DatePicker Lopetus;

    @FXML
    private Button btPeruuta;

    @FXML
    private Button btTallenna;

    @FXML
    private DatePicker dpAloitus;

    @FXML
    private DatePicker dpVahvistus;

    @FXML
    private Label labelId;

    @FXML
    private Label labelPvm;

    @FXML
    private TextField tfAsiakas;

    @FXML
    private TextField tfMokki;

    @FXML
    void btPeruutaAction(ActionEvent event) {

    }

    @FXML
    void btTallennaAction(ActionEvent event) {

    }

}

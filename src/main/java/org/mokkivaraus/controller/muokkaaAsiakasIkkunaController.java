package org.mokkivaraus.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class muokkaaAsiakasIkkunaController {

    @FXML
    private Button btPeruuta;

    @FXML
    private Button btTallenna;

    @FXML
    private Label labelId;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfEtunimi;

    @FXML
    private TextField tfOsoite;

    @FXML
    private TextField tfPostiNro;

    @FXML
    private TextField tfPuhNro;

    @FXML
    private TextField tfSukunimi;

    @FXML
    void btPeruutaAction(ActionEvent event) {
    Stage stage = (Stage) btPeruuta.getScene().getWindow();
    stage.close();
    }

    @FXML
    void btTallennaAction(ActionEvent event) {

    }
    public void initdata(int asiakas_id) {
    labelId.setText(Integer.toString(asiakas_id));
    }
}

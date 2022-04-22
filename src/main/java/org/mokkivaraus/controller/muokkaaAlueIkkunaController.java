package org.mokkivaraus.controller;

import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.stage.Stage;

// TODO, lisää label, jossa on alue_id

public class muokkaaAlueIkkunaController {

    @FXML
    private Button btPeruuta;

    @FXML
    private Button btTallenna;

    @FXML
    private TextField tfAlueNimi;

    @FXML
    void btPeruutaAction(ActionEvent event) {
    Stage stage = (Stage) btPeruuta.getScene().getWindow();
    stage.close();
    }

    @FXML
    void btTallennaAction(ActionEvent event) {

    }

}

package org.mokkivaraus.controller;

import org.mokkivaraus.Mokki;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;

public class muokkaaMokkiIkkunaController {

    @FXML
    private Button btPeruuta;

    @FXML
    private Button btTallenna;

    @FXML
    private TextField tfAlue;

    @FXML
    private TextField tfHenkilomaara;

    @FXML
    private TextField tfHinta;

    @FXML
    private TextField tfKuvaus;

    @FXML
    private TextField tfNimi;

    @FXML
    private TextField tfOsoite;

    @FXML
    private TextField tfPostinumero;

    @FXML
    private TextField tfVarustelu;

    Mokki mokki;

    @FXML
    void btPeruutaAction(ActionEvent event) {

    }

    @FXML
    void btTallennaAction(ActionEvent event) {

    }

    public void initdata(int mokki_id) {
        tfNimi.setText(Integer.toString(mokki_id));
    }

}

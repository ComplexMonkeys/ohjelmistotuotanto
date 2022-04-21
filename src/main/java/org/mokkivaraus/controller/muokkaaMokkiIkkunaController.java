package org.mokkivaraus.controller;


import org.mokkivaraus.Mokki;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.stage.Stage;

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

    @FXML
    private Label labelId;

    Mokki mokki;

    @FXML
    void btPeruutaAction(ActionEvent event) {
    Stage stage = (Stage) btPeruuta.getScene().getWindow();
    stage.close();
    }

    @FXML
    void btTallennaAction(ActionEvent event) {
    }

    public void initdata(int mokki_id, int alueenid, String kuvaus, String varustelu, String nimi, String postinro, String osoite, int henkilomaara, double hinta) {
        tfNimi.setText(nimi);
        tfAlue.setText(Integer.toString(alueenid));
        tfVarustelu.setText(varustelu);
        tfOsoite.setText(osoite);
        tfKuvaus.setText(kuvaus);
        tfPostinumero.setText(postinro);
        tfHinta.setText(Double.toString(hinta));
        tfHenkilomaara.setText(Integer.toString(henkilomaara));
    }

}

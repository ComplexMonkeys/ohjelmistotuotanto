package org.mokkivaraus.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class muokkaaAlueIkkunaController {

    @FXML
    private Button btPeruuta;

    @FXML
    private Button btTallenna;

    @FXML
    private TextField tfAlueNimi;

    @FXML
    private Label labelId;

    @FXML
    void btPeruutaAction(ActionEvent event) {
    Stage stage = (Stage) btPeruuta.getScene().getWindow();
    stage.close();
    }

    @FXML
    void btTallennaAction(ActionEvent event) throws Exception {
        Connection con = DriverManager.getConnection(
            // Tässä asetetaan tietokannan tiedot, osoite, käyttäjätunnus, salasana.
            "jdbc:mysql://localhost:3306/vn", "employee", "password");
    try {
        Statement stmt = con.createStatement();
        // Haetaan tiedot tekstikentistä ja muutetaan oikeisiin muotoihin.
        String alueennimi = tfAlueNimi.getText();
        int alueenid = Integer.parseInt(labelId.getText());

        // Määrittää SQL komennon ja lähettää sen tietokannalle.
        stmt.executeUpdate("UPDATE alue set aluenimi = '"+ alueennimi + "' WHERE alue_id = '"+alueenid+"' ;");
    } catch (Exception e) {
        System.out.println(e);
    } finally {
        // Yhteys tietokantaan suljetaan.
        con.close();
    }
    Stage stage = (Stage) btTallenna.getScene().getWindow();
    stage.close();
}
public void initdata(int alue_id) {
    labelId.setText(Integer.toString(alue_id));
}
}

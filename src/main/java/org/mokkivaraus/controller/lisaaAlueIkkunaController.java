package org.mokkivaraus.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class lisaaAlueIkkunaController {

    @FXML
    private Button btPeruuta;

    @FXML
    private Button btTallenna;

    @FXML
    private TextField tfAlueNimi;

    @FXML
    void btPeruutaAction(ActionEvent event) {

    }

    @FXML
    void btTallennaAction(ActionEvent event) {
        if (tfAlueNimi.getText() != "") {

            try {
                Connection con = DriverManager.getConnection(
                        // Tässä asetetaan tietokannan tiedot, osoite, käyttäjätunnus, salasana.
                        "jdbc:mysql://localhost:3306/vn", "employee", "password");
                Statement stmt = con.createStatement();
                String alueNimi = tfAlueNimi.getText();
                // Määrittää SQL komennon ja lähettää sen tietokannalle.
                stmt.executeUpdate("INSERT INTO alue (nimi) VALUE ('" + alueNimi + "');");
                // Yhteys tietokantaan suljetaan komennon jälkeen.
                con.close();
                // Nappaa poikkeukset ja tulostaa ne.
            } catch (Exception e) {
                System.out.println(e);
            }
            Stage stage = (Stage) btTallenna.getScene().getWindow();
            stage.close();
        }

    }
}

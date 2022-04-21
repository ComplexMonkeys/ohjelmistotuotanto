package org.mokkivaraus.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.*;

public class lisaaAsiakasIkkunaController {

    @FXML
    private Button btPeruuta;

    @FXML
    private Button btTallenna;

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

    }

    @FXML
    void btTallennaAction(ActionEvent event) throws Exception {
        if (tfPostiNro.getText() != "" && tfEtunimi.getText() != "" && tfSukunimi.getText() != ""
        && tfOsoite.getText() != "" && tfEmail.getText() != "" && tfPuhNro.getText() != "") {

    Connection con = DriverManager.getConnection(
            // Tässä asetetaan tietokannan tiedot, osoite, käyttäjätunnus, salasana.
            "jdbc:mysql://localhost:3306/vn", "employee", "password");
    try {
        Statement stmt = con.createStatement();
        // Haetaan tiedot tekstikentistä ja muutetaan oikeisiin muotoihin.
        String postinro = tfPostiNro.getText();
        String etunimi = tfEtunimi.getText();
        String sukunimi = tfSukunimi.getText();
        String lahiosoite = tfOsoite.getText();
        String puhelinnro = tfPuhNro.getText();
        String email = tfEmail.getText();
       

        // Määrittää SQL komennon ja lähettää sen tietokannalle.
        stmt.executeUpdate(
                "INSERT INTO asiakas (postinro,etunimi,sukunimi,lahiosoite,email,puhelinnro) VALUES ('"
                        + postinro + "','" + etunimi + "','" + sukunimi + "','" + lahiosoite + "','" + email + "','"
                        + puhelinnro + "');");
        // TODO: Lisää tähän tvmokit listan päivitys jos mahdollista?
        
        // Nappaa poikkeukset ja tulostaa ne.
    } catch (Exception e) {
        System.out.println(e);
    } finally {
        // Yhteys tietokantaan suljetaan.
        con.close();
    }
    Stage stage = (Stage) btTallenna.getScene().getWindow();
    stage.close();
}
    }

}

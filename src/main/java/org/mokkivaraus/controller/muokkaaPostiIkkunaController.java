package org.mokkivaraus.controller;

import java.sql.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class muokkaaPostiIkkunaController {

    @FXML
    private Button btPeruuta;

    @FXML
    private Button btTallenna;

    @FXML
    private Label labelPostinumero;

    @FXML
    private TextField tfToimipaikka;

    @FXML
    void btPeruutaAction(ActionEvent event) {
        Stage stage = (Stage) btPeruuta.getScene().getWindow();
        stage.close();
    }

    @FXML
    void btTallennaAction(ActionEvent event) throws SQLException{
        Connection con = DriverManager.getConnection(
            // Tässä asetetaan tietokannan tiedot, osoite, käyttäjätunnus, salasana.
            "jdbc:mysql://localhost:3306/vn", "employee", "password");
        
            try {
                Statement stmt = con.createStatement();
                // Haetaan tiedot tekstikentistä ja muutetaan oikeisiin muotoihin.
                String postinumero = labelPostinumero.getText();
                String toimipaikka = tfToimipaikka.getText();
        
                // Määrittää SQL komennon ja lähettää sen tietokannalle.
                stmt.executeUpdate("UPDATE posti set toimipaikka = '"+ toimipaikka + "' WHERE postinro = '"+ postinumero+"' ;");
            } catch (Exception e) {
                System.out.println(e);
            } finally {
                // Yhteys tietokantaan suljetaan.
                con.close();
            }
            Stage stage = (Stage) btTallenna.getScene().getWindow();
            stage.close();
    }

    public void initdata(String postinro, String toimipaikka) {
        labelPostinumero.setText(postinro);
        tfToimipaikka.setText(toimipaikka);
    }

}

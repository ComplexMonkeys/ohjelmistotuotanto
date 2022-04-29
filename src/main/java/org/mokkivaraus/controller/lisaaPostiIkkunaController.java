package org.mokkivaraus.controller;

import java.sql.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class lisaaPostiIkkunaController {

    @FXML
    private Button btPeruuta;

    @FXML
    private Button btTallenna;

    @FXML
    private TextField tfPostinumero;

    @FXML
    private TextField tfToimipaikka;

    @FXML
    void btPeruutaAction(ActionEvent event) {
        Stage stage = (Stage) btPeruuta.getScene().getWindow();
        stage.close();
    }

    @FXML
    void btTallennaAction(ActionEvent event) {
        if (tfPostinumero.getText().length() >= 5 && tfToimipaikka.getText() != "") {

            try {
                Connection con = DriverManager.getConnection(
                        // Tässä asetetaan tietokannan tiedot, osoite, käyttäjätunnus, salasana.
                        "jdbc:mysql://localhost:3306/vn", "employee", "password");
                Statement stmt = con.createStatement();
                String postinumero = tfPostinumero.getText();
                String toimipaikka = tfToimipaikka.getText();

                // Määrittää SQL komennon ja lähettää sen tietokannalle.
                stmt.executeUpdate("INSERT INTO posti (postinro, toimipaikka) VALUE ('" + postinumero + "', '" + toimipaikka +"');");
                // Yhteys tietokantaan suljetaan komennon jälkeen.
                con.close();
                // Nappaa poikkeukset ja tulostaa ne.
            } catch (DataTruncation liikaapituutta){
                Alert constraitAlert = new Alert(AlertType.ERROR);
                constraitAlert.setHeaderText("Jotain meni vikaan");
                constraitAlert.setContentText("Postinumeron pituus ei saa ylittää viittä merkkiä!");
                constraitAlert.showAndWait();
            } catch (Exception e) {
                System.out.println(e);
            }
            Stage stage = (Stage) btTallenna.getScene().getWindow();
            stage.close();
        } else{
            Alert constraitAlert = new Alert(AlertType.ERROR);
            constraitAlert.setHeaderText("Jotain meni vikaan");
            constraitAlert.setContentText("Syötä postinumero ja toimipaikka.");
            constraitAlert.showAndWait();
        }
    }

}
